package com.lojusto.back.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lojusto.back.entities.Cliente;
import com.lojusto.back.entities.DetalleVenta;
import com.lojusto.back.entities.Producto;
import com.lojusto.back.entities.Venta;
import com.lojusto.back.repositories.ClienteRepository;
import com.lojusto.back.repositories.ProductoRepository;
import com.lojusto.back.repositories.VentaRepository;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    @Transactional
    public Venta crearVenta(Venta venta) {
        procesarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Transactional
public Venta actualizarVenta(Long id, Venta ventaRequest) {
    Venta ventaDB = ventaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

    // 1. Actualizar estado
    ventaDB.setEstado(ventaRequest.getEstado() != null ? ventaRequest.getEstado() : ventaDB.getEstado());

    // 2. LIMPIEZA TOTAL DE DETALLES (Para que orphanRemoval funcione)
    // Esto borra físicamente los registros de la tabla detalle_ventas que ya no estén
    ventaDB.getDetalles().clear();

    BigDecimal total = BigDecimal.ZERO;

    // 3. PROCESAR NUEVOS DETALLES (Reemplazo total)
    for (DetalleVenta detalleReq : ventaRequest.getDetalles()) {
        Producto producto = productoRepository.findById(detalleReq.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetalleVenta nuevoDetalle = new DetalleVenta();
        nuevoDetalle.setVenta(ventaDB); // Link vital para JPA
        nuevoDetalle.setProducto(producto);
        nuevoDetalle.setCantidad(detalleReq.getCantidad()); // Fijamos la cantidad de React
        nuevoDetalle.setPrecioAplicado(producto.getPrecioActual());

        ventaDB.getDetalles().add(nuevoDetalle);

        // Calcular subtotal
        BigDecimal subtotal = producto.getPrecioActual()
                .multiply(BigDecimal.valueOf(detalleReq.getCantidad()));
        total = total.add(subtotal);
    }

    // 4. Actualizar total de la venta
    ventaDB.setTotalVenta(total);

    return ventaRepository.save(ventaDB);
}

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public Venta obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    public List<Venta> listarVentasDeHoy() {
        return ventaRepository.findVentasDeHoy();
    }

    // lógica reutilizable
    private void procesarVenta(Venta venta) {

        Cliente cliente = clienteRepository.findById(venta.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        venta.setCliente(cliente);
        venta.setEstado(venta.getEstado() == null ? "PAGADO" : venta.getEstado());
        venta.setFecha(venta.getFecha() == null ? LocalDateTime.now() : venta.getFecha());

        BigDecimal total = BigDecimal.ZERO;
        List<DetalleVenta> detallesProcesados = new ArrayList<>();

        for (DetalleVenta detalle : venta.getDetalles()) {

            Producto producto = productoRepository.findById(
                    detalle.getProducto().getId()
            ).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            BigDecimal subtotal = producto.getPrecioActual()
                    .multiply(BigDecimal.valueOf(detalle.getCantidad()));

            total = total.add(subtotal);

            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setPrecioAplicado(producto.getPrecioActual());

            detallesProcesados.add(detalle);
        }

        venta.setTotalVenta(total);
        venta.setDetalles(detallesProcesados);
    }
}
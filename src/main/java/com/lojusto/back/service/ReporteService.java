package com.lojusto.back.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lojusto.back.repositories.ClienteRepository;
import com.lojusto.back.repositories.DetalleVentaRepository;
import com.lojusto.back.repositories.VentaRepository;

@Service
public class ReporteService {
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public Double ventaTotalHoy() {
        return ventaRepository.ventaTotalDelDia();
    }

    public Long pedidosHoy() {
        return ventaRepository.totalPedidosDelDia();
    }

    public List<Map<String, Object>> productosVendidosHoy() {
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Object[] fila : detalleVentaRepository.productosVendidosHoy()) {
            Map<String, Object> map = new HashMap<>();
            map.put("producto", fila[0]);
            map.put("cantidad", fila[1]);
            resultado.add(map);
        }
        return resultado;
    }

    public List<Map<String, Object>> ventasMensuales() {
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Object[] fila : ventaRepository.ventasPorMes()) {
            Map<String, Object> map = new HashMap<>();
            map.put("mesNumero", fila[0]);
            map.put("mesNombre", fila[1]);
            map.put("totalVentas", fila[2]);
            resultado.add(map);
        }
        return resultado;
    }

    public List<Map<String, Object>> totalComprasPorCliente() {
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Object[] fila : clienteRepository.totalComprasPorCliente()) {
            Map<String, Object> map = new HashMap<>();
            map.put("clienteId", fila[0]);
            map.put("nombre", fila[1]);
            map.put("email", fila[2]);
            map.put("totalCompras", fila[3]);
            resultado.add(map);
        }
        return resultado;
    }
    
}

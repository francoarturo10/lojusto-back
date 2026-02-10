package com.lojusto.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lojusto.back.entities.Venta;
import com.lojusto.back.service.VentaService;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin("*")
public class VentaController {
    
    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        Venta ventaCreada = ventaService.crearVenta(venta);
        return new ResponseEntity<>(ventaCreada, HttpStatus.CREATED);
    }

     // 🔹 Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizarVenta(
            @PathVariable Long id,
            @RequestBody Venta venta
    ) {
        return ResponseEntity.ok(
                ventaService.actualizarVenta(id, venta)
        );
    }

    // 🔹 Listar
    @GetMapping("/listar-ventas")
    public ResponseEntity<List<Venta>> listarVentas() {
        return ResponseEntity.ok(
                ventaService.listarVentas()
        );
    }

    // 🔹 Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(
                ventaService.obtenerVentaPorId(id)
        );
    }

    @GetMapping("/hoy")
    public List<Venta> listarVentasHoy() {
        return ventaService.listarVentasDeHoy();
    }
}

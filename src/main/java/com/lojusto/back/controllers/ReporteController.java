package com.lojusto.back.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lojusto.back.service.ReporteService;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;
    

    @GetMapping("/ventas-hoy")
    public Double ventasHoy() {
        return reporteService.ventaTotalHoy();
    }

    @GetMapping("/pedidos-hoy")
    public Long pedidosHoy() {
        return reporteService.pedidosHoy();
    }

    @GetMapping("/productos-hoy")
    public List<Map<String, Object>> productosHoy() {
        return reporteService.productosVendidosHoy();
    }

    @GetMapping("/ventas-mensuales")
    public List<Map<String, Object>> ventasMensuales() {
        return reporteService.ventasMensuales();
    }

    @GetMapping("/clientes-total")
    public List<Map<String, Object>> clientesTotales() {
        return reporteService.totalComprasPorCliente();
    }
    
}
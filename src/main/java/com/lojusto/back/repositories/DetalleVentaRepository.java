package com.lojusto.back.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lojusto.back.entities.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long>{

    // 3️⃣ Productos vendidos hoy
    @Query("""
        SELECT 
            p.nombre,
            SUM(d.cantidad)
        FROM DetalleVenta d
        JOIN d.venta v
        JOIN d.producto p
        WHERE DATE(v.fecha) = CURRENT_DATE
        AND v.estado = 'PAGADO'
        GROUP BY p.id, p.nombre
        ORDER BY SUM(d.cantidad) DESC
    """)
    List<Object[]> productosVendidosHoy();
}

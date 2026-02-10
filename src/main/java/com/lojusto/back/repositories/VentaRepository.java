package com.lojusto.back.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lojusto.back.entities.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long>{

    // 1️⃣ Venta total del día
    @Query("""
        SELECT COALESCE(SUM(v.totalVenta), 0)
        FROM Venta v
        WHERE DATE(v.fecha) = CURRENT_DATE
        AND v.estado = 'PAGADO'
    """)
    Double ventaTotalDelDia();

    // 2️⃣ Número de pedidos del día
    @Query("""
        SELECT COUNT(v)
        FROM Venta v
        WHERE DATE(v.fecha) = CURRENT_DATE
        AND v.estado = 'PAGADO'
    """)
    Long totalPedidosDelDia();

    // 4️⃣ Total ventas por mes
    @Query("""
        SELECT
            FUNCTION('MONTH', v.fecha),
            FUNCTION('MONTHNAME', v.fecha),
            SUM(v.totalVenta)
        FROM Venta v
        WHERE v.estado = 'PAGADO'
        GROUP BY FUNCTION('MONTH', v.fecha), FUNCTION('MONTHNAME', v.fecha)
        ORDER BY FUNCTION('MONTH', v.fecha)
    """)
    public List<Object[]> ventasPorMes();

    @Query("SELECT v FROM Venta v WHERE CAST(v.fecha AS date) = CURRENT_DATE")
    List<Venta> findVentasDeHoy();
}
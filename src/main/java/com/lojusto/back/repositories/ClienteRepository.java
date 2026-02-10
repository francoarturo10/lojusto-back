package com.lojusto.back.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lojusto.back.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    // 5️⃣ Clientes con total de compras
    @Query("""
    SELECT 
        c.id,
        c.nombre,
        c.email,
        COALESCE(SUM(v.totalVenta), 0)
    FROM Venta v
    RIGHT JOIN v.cliente c
    WHERE v.estado = 'PAGADO'
    GROUP BY c.id, c.nombre, c.email
    ORDER BY SUM(v.totalVenta) DESC
    """)
    List<Object[]> totalComprasPorCliente();

    Optional<Cliente> findByNombre(String nombre);
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    
}
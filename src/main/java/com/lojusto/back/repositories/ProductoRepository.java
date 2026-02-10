package com.lojusto.back.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lojusto.back.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
    public List<Producto> findByCategoriaId(Long categoriaId);
} 

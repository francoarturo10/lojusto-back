package com.lojusto.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lojusto.back.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
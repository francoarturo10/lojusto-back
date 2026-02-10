package com.lojusto.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lojusto.back.entities.Categoria;
import com.lojusto.back.service.CategoriaService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin("*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listaCategorias() {
        return categoriaService.listarCategorias();
    }
    
}
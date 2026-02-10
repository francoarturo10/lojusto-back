package com.lojusto.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lojusto.back.entities.Producto;
import com.lojusto.back.repositories.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarPorCategoriaId(Long categoria_id){
        return productoRepository.findByCategoriaId(categoria_id);
    }
}

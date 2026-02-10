package com.lojusto.back.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lojusto.back.entities.Cliente;
import com.lojusto.back.repositories.ClienteRepository;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente buscarOCrear(Cliente cliente) {
        // 1. Intentar buscar por celular (nuestro identificador único en este caso)
        return clienteRepository.findByNombre(cliente.getNombre())
                .orElseGet(() -> {
                    // 2. Si no existe, configurar fecha de registro y guardar
                    cliente.setFechaRegistro(LocalDateTime.now());
                    return clienteRepository.save(cliente);
                });
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
package com.lojusto.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lojusto.back.entities.Cliente;
import com.lojusto.back.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    
    @PostMapping
    // MUY IMPORTANTE: Asegúrate de que @RequestBody esté presente
    public ResponseEntity<Cliente> registrarOVerificar(@RequestBody Cliente cliente) {
        Cliente clienteProcesado = clienteService.buscarOCrear(cliente);
        return ResponseEntity.ok(clienteProcesado);
    }

    @GetMapping("/buscar")
    public List<Cliente> buscarPorNombre(@RequestParam String nombre) {
        return clienteService.buscarPorNombre(nombre); // Llama al repo
    }
}
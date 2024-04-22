package com.vivo.crm.vivo.controller;

import com.vivo.crm.vivo.model.Cliente;
import com.vivo.crm.vivo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;
    @GetMapping
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }
    @PostMapping
    public  Cliente adicionar(@RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente clienteDetalhes) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    clienteExistente.setName(clienteDetalhes.getName());
                    clienteExistente.setEmail(clienteDetalhes.getEmail());
                    clienteExistente.setCpf(clienteDetalhes.getCpf());
                    Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
                    return ResponseEntity.ok(clienteAtualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.controller;

import com.mycompany.bazar.model.Cliente;
import com.mycompany.bazar.service.IClienteService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author CharlyW
 */
@RestController
public class ClienteController {

    @Autowired
    IClienteService clienteServ;

    // lectura 
    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getClientes() {
        List<Cliente> listaClientes = clienteServ.getClientes();
        return new ResponseEntity<>(listaClientes, HttpStatus.OK);
    }

    // lectura de un solo objeto
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> findCliente(@PathVariable Long id) {
        Cliente cliente = clienteServ.findCliente(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    // alta
    @PostMapping("/clientes/crear")
    public ResponseEntity<Cliente> saveCliente(@RequestBody @Valid Cliente cli) {
        Cliente clienteCreado = clienteServ.saveCliente(cli);
        return new ResponseEntity<>(clienteCreado, HttpStatus.CREATED);
    }

    // baja
    @DeleteMapping("/clientes/eliminar/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        clienteServ.deleteCliente(id);
        return new ResponseEntity<>("El cliente ha sido borrado con exito", HttpStatus.OK);
    }

    // edicion
    @PutMapping("/clientes/editar")
    public ResponseEntity<Cliente> editCliente(@RequestBody @Valid Cliente cli) {
        Cliente cliente = clienteServ.editCliente(cli);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
}

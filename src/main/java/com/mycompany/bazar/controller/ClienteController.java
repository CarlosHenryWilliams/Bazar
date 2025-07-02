/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.controller;

import com.mycompany.bazar.model.Cliente;
import com.mycompany.bazar.service.IClienteService;
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
        if (cliente == null) {
            return new ResponseEntity<>(cliente, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    // alta
    @PostMapping("/clientes/crear")
    public ResponseEntity<String> saveCliente(@RequestBody Cliente cli) {
        clienteServ.saveCliente(cli);
        return new ResponseEntity<>("El cliente ha sido creado con exito.", HttpStatus.CREATED);
    }

    // baja
    @DeleteMapping("/clientes/eliminar/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        Cliente cliente = clienteServ.findCliente(id);
        if (cliente == null) {
            return new ResponseEntity<>("El cliente con el id: " + id + " no se encuentra.", HttpStatus.NOT_FOUND);
        }

        clienteServ.deleteCliente(id);
        return new ResponseEntity<>("El cliente ha sido borrado con exito", HttpStatus.OK);

    }

    // edicion
    @PutMapping("/clientes/editar")
    public ResponseEntity<Cliente> editCliente(@RequestBody Cliente cli) {
        Cliente cliente = clienteServ.findCliente(cli.getIdCliente());
        if (cliente == null) {
            return new ResponseEntity<>(cliente, HttpStatus.NOT_FOUND);
        }
        // si es el mismo id, lo edita si no avisa
        cliente.setNombre(cli.getNombre());
        cliente.setApellido(cli.getApellido());
        cliente.setDni(cli.getDni());
        clienteServ.editCliente(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.OK);

    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.controller;

import com.mycompany.bazar.dto.ClienteRequestDTO;
import com.mycompany.bazar.dto.ClienteResponseDTO;
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

    // Obtener todos los Clientes
    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteResponseDTO>> getClientes() {
        List<ClienteResponseDTO> listaCliResponseDTO = clienteServ.getClientes();
        return new ResponseEntity<>(listaCliResponseDTO, HttpStatus.OK);
    }

    // Obtener un solo Cliente
    @GetMapping("/clientes/{id}")
    public ResponseEntity<ClienteResponseDTO> findCliente(@PathVariable Long id) {
        ClienteResponseDTO cliResponseDTO = clienteServ.findCliente(id);
        return new ResponseEntity<>(cliResponseDTO, HttpStatus.OK);
    }

    // Dar de alta un Cliente
    @PostMapping("/clientes/crear")
    public ResponseEntity<ClienteResponseDTO> saveCliente(@RequestBody @Valid ClienteRequestDTO cliRequestDTO) {
        ClienteResponseDTO cliResponseDTO = clienteServ.saveCliente(cliRequestDTO);
        return new ResponseEntity<>(cliResponseDTO, HttpStatus.CREATED);
    }

    // Eliminar un Cliente
    @DeleteMapping("/clientes/eliminar/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        clienteServ.deleteCliente(id);
        return new ResponseEntity<>("El cliente ha sido borrado con exito", HttpStatus.OK);
    }

    // Editar un Cliente
    @PutMapping("/clientes/editar/{id}")
    public ResponseEntity<ClienteResponseDTO> editCliente(@PathVariable Long id, @RequestBody @Valid ClienteRequestDTO cliRequestDTO) {
        ClienteResponseDTO cliResponseDTO = clienteServ.editCliente(id, cliRequestDTO);
        return new ResponseEntity<>(cliResponseDTO, HttpStatus.OK);
    }
}

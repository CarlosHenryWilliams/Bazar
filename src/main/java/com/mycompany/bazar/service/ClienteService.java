/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.exception.ClienteNotFoundException;
import com.mycompany.bazar.model.Cliente;
import com.mycompany.bazar.repository.IClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CharlyW
 */
@Service
public class ClienteService implements IClienteService {

    @Autowired
    IClienteRepository clienteRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getClientes() {
        return clienteRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findCliente(Long id) {
        Cliente cli = clienteRepo.findById(id).orElseThrow(() -> new ClienteNotFoundException("El cliente con el id: " + id + " no existe."));
        return cli;
    }

    @Override
    @Transactional
    public void saveCliente(Cliente cli) {
        clienteRepo.save(cli);
    }

    @Override
    @Transactional
    public void deleteCliente(Long id) {
        Cliente clienteAEliminar = this.findCliente(id);
        clienteRepo.deleteById(clienteAEliminar.getIdCliente());
    }

    @Override
    @Transactional
    public Cliente editCliente(Cliente cli) {
        Cliente cliente = this.findCliente(cli.getIdCliente()); // busco al cliente
        // si existe, lo edito
        cliente.setNombre(cli.getNombre());
        cliente.setApellido(cli.getApellido());
        cliente.setDni(cli.getDni());

        this.saveCliente(cliente);  // no existe el metodo edit  
        return cliente;
    }

}

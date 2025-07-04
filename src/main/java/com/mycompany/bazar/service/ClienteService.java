/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.model.Cliente;
import com.mycompany.bazar.repository.IClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CharlyW
 */
@Service
public class ClienteService implements IClienteService {

    @Autowired
    IClienteRepository clienteRepo;

    @Override
    public List<Cliente> getClientes() {
        return clienteRepo.findAll();
    }

    @Override
    public Cliente findCliente(Long id) {
        Cliente cli = clienteRepo.findById(id).orElse(null);
        return cli;
    }

    @Override
    public void saveCliente(Cliente cli) {
        clienteRepo.save(cli);
    }

    @Override
    public Boolean deleteCliente(Long id) {
        Cliente clienteAEliminar = this.findCliente(id);
        if (clienteAEliminar == null) {
            return false;
        }
        clienteRepo.deleteById(id);
        return true;
    }

    @Override
    public Cliente editCliente(Cliente cli) {

        Cliente cliente = this.findCliente(cli.getIdCliente()); // busco al cliente

        if (cliente == null) {
            return cliente;
        }
        // si es el mismo id, lo edita si no avisa
        cliente.setNombre(cli.getNombre());
        cliente.setApellido(cli.getApellido());
        cliente.setDni(cli.getDni());

        this.saveCliente(cliente);  // no existe el metodo edit  
        return cliente;
    }

}

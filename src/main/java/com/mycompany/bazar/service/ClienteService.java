/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.dto.ClienteRequestDTO;
import com.mycompany.bazar.dto.ClienteResponseDTO;
import com.mycompany.bazar.exception.ClienteNotFoundException;
import com.mycompany.bazar.model.Cliente;
import com.mycompany.bazar.repository.IClienteRepository;
import java.util.ArrayList;
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
    public Cliente validarCliente(Long id) {
        return clienteRepo.findById(id).orElseThrow(() -> new ClienteNotFoundException("El cliente con el id: " + id + " no existe."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> getClientes() {
        List<Cliente> listaClientes = clienteRepo.findAll();
        List<ClienteResponseDTO> listaCliResponseDTO = new ArrayList<>();

        for (Cliente cli : listaClientes) {
            listaCliResponseDTO.add(new ClienteResponseDTO(cli.getIdCliente(), cli.getNombre(), cli.getApellido(), cli.getDni()));
        }
        return listaCliResponseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO findCliente(Long id) {
        Cliente cli = this.validarCliente(id);
        ClienteResponseDTO cliResponseDTO = new ClienteResponseDTO(cli.getIdCliente(), cli.getNombre(), cli.getApellido(), cli.getDni());
        return cliResponseDTO;
    }

    @Override
    @Transactional
    public ClienteResponseDTO saveCliente(ClienteRequestDTO cliDTO) {
        Cliente clienteAGuardar = new Cliente(cliDTO.getNombre(), cliDTO.getApellido(), cliDTO.getDni());
        clienteAGuardar = clienteRepo.save(clienteAGuardar);
        ClienteResponseDTO cliResDTO = new ClienteResponseDTO(clienteAGuardar.getIdCliente(), clienteAGuardar.getNombre(), clienteAGuardar.getApellido(), clienteAGuardar.getDni());
        return cliResDTO;
    }

    @Override
    @Transactional
    public void deleteCliente(Long id) {
        Cliente clienteAEliminar = clienteRepo.findById(id).orElseThrow(() -> new ClienteNotFoundException("El cliente con el id: " + id + " no existe."));;
        clienteRepo.deleteById(clienteAEliminar.getIdCliente());
    }

    @Override
    @Transactional
    public ClienteResponseDTO editCliente(Long id, ClienteRequestDTO cliRequestDTO) {

        Cliente cliente = this.validarCliente(id);
        cliente.setNombre(cliRequestDTO.getNombre());
        cliente.setApellido(cliRequestDTO.getApellido());
        cliente.setDni(cliRequestDTO.getDni());
        cliente = clienteRepo.save(cliente);
        ClienteResponseDTO cliResponseDTO = new ClienteResponseDTO(cliente.getIdCliente(), cliente.getNombre(), cliente.getApellido(), cliente.getDni()); // no existe el metodo edit  
        return cliResponseDTO;
    }

}

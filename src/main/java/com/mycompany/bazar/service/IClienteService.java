/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.dto.ClienteRequestDTO;
import com.mycompany.bazar.dto.ClienteResponseDTO;
import com.mycompany.bazar.model.Cliente;
import java.util.List;

/**
 *
 * @author CharlyW
 */
public interface IClienteService {

    // lectura
    public List<ClienteResponseDTO> getClientes();

    // lectura de un solo objeto
    public ClienteResponseDTO findCliente(Long id);

    // alta
    public ClienteResponseDTO saveCliente(ClienteRequestDTO cli);

    // baja
    public void deleteCliente(Long id);

    // edicion
    public ClienteResponseDTO editCliente(Long id, ClienteRequestDTO cliRequestDTO);

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.model.Cliente;
import java.util.List;

/**
 *
 * @author CharlyW
 */
public interface IClienteService {

    // lectura
    public List<Cliente> getClientes();

    // lectura de un solo objeto
    public Cliente findCliente(Long id);

    // alta
    public void saveCliente(Cliente cli);

    // baja
    public void deleteCliente(Long id);

    // edicion
    public Cliente editCliente(Cliente cli);

}

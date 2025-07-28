/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.bazar.repository;

import com.mycompany.bazar.model.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CharlyW
 */
@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    public Optional<Cliente> findClienteBydni(String dni); //  Con el optional se puede usar el orElseThrow
}

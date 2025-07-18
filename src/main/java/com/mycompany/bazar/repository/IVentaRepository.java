/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.bazar.repository;

import com.mycompany.bazar.model.Venta;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CharlyW
 */
@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long >{
    public List<Venta> findAllByfechaVenta(LocalDate fecha);
    public Venta findFirstByOrderByTotalDesc();
}

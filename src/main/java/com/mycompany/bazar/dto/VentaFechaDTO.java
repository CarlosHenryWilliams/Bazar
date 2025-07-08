/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author CharlyW
 */
@Getter @Setter
public class VentaFechaDTO {
    private LocalDate fechaVenta;
    private Double montoTotal;
    private int cantidadVentas;

    public VentaFechaDTO() {
    }

    
    public VentaFechaDTO(LocalDate fechaVenta, Double montoTotal, int cantidadVentas) {
        this.fechaVenta = fechaVenta;
        this.montoTotal = montoTotal;
        this.cantidadVentas = cantidadVentas;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author CharlyW
 */
@Getter
@Setter
public class VentaUsuarioMayorVentaDTO {

    private Long codigoventa;
    private String nombreCliente;
    private String apellidoCliente;
    private int cantidadProductos;
    private double montoTotal;

    public VentaUsuarioMayorVentaDTO() {
    }

    public VentaUsuarioMayorVentaDTO(Long codigoventa, String nombreCliente, String apellidoCliente, int cantidadProductos, double montoTotal) {
        this.codigoventa = codigoventa;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.cantidadProductos = cantidadProductos;
        this.montoTotal = montoTotal;
    }

}

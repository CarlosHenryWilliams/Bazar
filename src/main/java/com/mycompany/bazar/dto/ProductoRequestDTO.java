/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter; 

/**
 *
 * @author CharlyW
 */
@Getter @Setter
public class ProductoRequestDTO {
    @NotBlank
    private String nombre;
    @NotBlank
    private String marca;
    @NotNull
    @Positive(message = "El costo debe ser un valor positivo")
    private Double costo;
    @NotNull
    private Double cantidadDisponible;

    public ProductoRequestDTO() {
    }

    public ProductoRequestDTO(String nombre, String marca, Double costo, Double cantidadDisponible) {
        this.nombre = nombre;
        this.marca = marca;
        this.costo = costo;
        this.cantidadDisponible = cantidadDisponible;
    }
    
}

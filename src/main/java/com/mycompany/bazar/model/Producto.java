/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author CharlyW
 */
@Entity
@Getter
@Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del producto, generado automaticamente por el sistema.", accessMode = Schema.AccessMode.READ_ONLY)

    private Long codigoProducto;
    @NotBlank
    private String nombre;
    @NotBlank
    private String marca;
    @NotNull
    @Positive(message = "El costo debe ser un valor positivo")
    private Double costo;
    @NotNull
    private Double cantidadDisponible;

    public Producto() {
    }

    public Producto(Long codigoProducto, String nombre, String marca, Double costo, Double cantidadDisponible) {
        this.codigoProducto = codigoProducto;
        this.nombre = nombre;
        this.marca = marca;
        this.costo = costo;
        this.cantidadDisponible = cantidadDisponible;
    }
    
    

}

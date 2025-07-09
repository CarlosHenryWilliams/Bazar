/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.model;

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
    private Long codigoProducto;
    @NotBlank
    private String nombre;
    @NotBlank
    private String marca;
    @NotNull
    @Positive(message = "El costo debe ser un valor positivo")
    private Double costo;
    @NotNull
    @Positive(message = "La cantidad debe ser un valor positivo")
    private Double cantidadDisponible;

}

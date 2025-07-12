/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author CharlyW
 */
@Getter @Setter
public class ItemVentaRequestDTO {

    @NotNull
    @Positive(message = "La cantidad debe ser un valor positivo")
    private int cantidad;
    @NotNull
    @Positive(message = "El id producto debe ser positivo")
    private Long idProducto;

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author CharlyW
 */
@Getter @Setter
public class VentaRequestDTO {

    @NotNull
    private LocalDate fechaVenta;
    @NotEmpty
    @Valid
    private List<ItemVentaRequestDTO> listaDeItemsDTO = new ArrayList<>();
    
    @NotBlank
    private String dniCliente;

}

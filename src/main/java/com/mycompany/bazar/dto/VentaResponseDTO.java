/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.dto;

import jakarta.validation.Valid;
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
@Getter
@Setter
public class VentaResponseDTO {

    private Long codigoVenta;
    private Double total;
    @NotNull
    private LocalDate fechaVenta;
    @NotEmpty
    private List<ItemVentaResponseDTO> listaDeItems;

    @NotNull
    private ClienteResponseDTOParaVenta cliente;

    public VentaResponseDTO() {
    }

    public VentaResponseDTO(Long codigoVenta, Double total, LocalDate fechaVenta, List<ItemVentaResponseDTO> listaDeItems, ClienteResponseDTOParaVenta cliente) {
        this.codigoVenta = codigoVenta;
        this.total = total;
        this.fechaVenta = fechaVenta;
        this.listaDeItems = listaDeItems;
        this.cliente = cliente;
    }

}

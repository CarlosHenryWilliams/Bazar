/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ItemVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idItemVenta;
    @NotNull
    @Positive(message = "La cantidad debe ser un valor positivo")
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "idVenta")
    @JsonIgnore
    private Venta venta;  // poner mappedby del otro lado

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    public ItemVenta() {

    }

    public ItemVenta(long idItemVenta, int cantidad, Venta venta, Producto producto) {
        this.idItemVenta = idItemVenta;
        this.cantidad = cantidad;
        this.venta = venta;
        this.producto = producto;
    }
}

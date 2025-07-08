/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author CharlyW
 */
@Entity
@Getter @Setter
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoVenta;
    private LocalDate fechaVenta;
    private Double total;
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL) //  El cascade all hace que afecte tambien a los ItemVenta
    private List<ItemVenta> listaDeItems = new ArrayList<>(); // siempre va a tener una lista asi que esta bien inicializarla.

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente unCliente;

}

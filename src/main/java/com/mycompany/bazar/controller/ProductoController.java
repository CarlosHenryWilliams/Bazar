/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.controller;

import com.mycompany.bazar.model.Producto;
import com.mycompany.bazar.service.IProductoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author CharlyW
 */
@RestController
public class ProductoController {

    @Autowired
    IProductoService produServ;

    // lectura
    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getProductos() {
        List<Producto> listaProductos = produServ.getProductos();
        return new ResponseEntity<>(listaProductos, HttpStatus.OK);
    }

    // lectura de un solo objeto
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> findProducto(@PathVariable Long id) {
        Producto produ = produServ.findProducto(id);
        return new ResponseEntity<>(produ, HttpStatus.OK);
    }

    // alta
    @PostMapping("/productos/crear")
    public ResponseEntity<Producto> saveProducto(@RequestBody @Valid Producto produ) {
        Producto productoCreado = produServ.saveProducto(produ);
        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
    }

    // baja
    @DeleteMapping("/productos/eliminar/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        produServ.deleteProducto(id);
        return new ResponseEntity<>("Se ha eliminado el producto con el codigo: " + id + " con exito.", HttpStatus.OK);
    }

    // edicion
    @PutMapping("/productos/editar")
    public ResponseEntity<Producto> editProducto(@RequestBody @Valid Producto produ) {
        Producto productoAEditar = produServ.editProducto(produ);
        return new ResponseEntity<>(productoAEditar, HttpStatus.OK);
    }

    @GetMapping("/productos/falta_stock")
    public ResponseEntity<List<Producto>> findBycantidadDisponibleLessThan() {
        return new ResponseEntity<>(produServ.findBycantidadDisponibleLessThan(5), HttpStatus.OK);
    }
}

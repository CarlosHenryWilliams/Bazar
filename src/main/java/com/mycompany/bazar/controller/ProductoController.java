/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.controller;

import com.mycompany.bazar.model.Producto;
import com.mycompany.bazar.service.IProductoService;
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
        if (produ == null) {
            return new ResponseEntity<>(produ, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(produ, HttpStatus.OK);
    }

    // alta
    @PostMapping("/productos/crear")
    public ResponseEntity<String> saveProducto(@RequestBody Producto produ) {
        produServ.saveProducto(produ);
        return new ResponseEntity<>("El Producto ha sido creado con exito.", HttpStatus.CREATED);
    }

    // baja
    @DeleteMapping("/productos/eliminar/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        Producto productoEncontrado = produServ.findProducto(id);
        if (productoEncontrado == null) {
            return new ResponseEntity<>("El Producto con el codigo: " + id + " no se encuentra.", HttpStatus.NOT_FOUND);
        }
        produServ.deleteProducto(id);
        return new ResponseEntity<>("Se ha eliminado el producto con el codigo: " + id + " con exito.", HttpStatus.OK);
    }

    // edicion
    @PutMapping("/productos/editar")
    public ResponseEntity<Producto> editProducto(@RequestBody Producto produ) {
        Producto productoEncontrado = produServ.findProducto(produ.getCodigoProducto());
        if (productoEncontrado == null) {
            return new ResponseEntity<>(productoEncontrado, HttpStatus.NOT_FOUND);
        }
        productoEncontrado.setNombre(produ.getNombre());
        productoEncontrado.setMarca(produ.getMarca());
        productoEncontrado.setCosto(produ.getCosto());
        productoEncontrado.setCantidadDisponible(produ.getCantidadDisponible());
        produServ.editProducto(productoEncontrado);
        return new ResponseEntity<>(productoEncontrado, HttpStatus.OK);

    }

}

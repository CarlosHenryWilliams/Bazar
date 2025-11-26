/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.controller;

import com.mycompany.bazar.dto.ProductoRequestDTO;
import com.mycompany.bazar.dto.ProductoResponseDTO;
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

    // Obtener todos los Productos
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoResponseDTO>> getProductos() {
        List<ProductoResponseDTO> listaProduResponseDTO= produServ.getProductos();
        return new ResponseEntity<>(listaProduResponseDTO, HttpStatus.OK);
    }

    // Obtener un solo Producto
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoResponseDTO> findProducto(@PathVariable Long id) {
        ProductoResponseDTO produResponseDTO = produServ.findProducto(id);
        return new ResponseEntity<>(produResponseDTO, HttpStatus.OK);
    }

    // Dar de alta un Producto
    @PostMapping("/productos")
    public ResponseEntity<ProductoResponseDTO> saveProducto(@RequestBody @Valid ProductoRequestDTO produRequestDTO) {
        ProductoResponseDTO produResponseDTO = produServ.saveProducto(produRequestDTO);
        return new ResponseEntity<>(produResponseDTO, HttpStatus.CREATED);
    }

    // Eliminar un producto
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        produServ.deleteProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Editar un producto
    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoResponseDTO> editProducto(@PathVariable Long id, @RequestBody @Valid ProductoRequestDTO produRequestDTO) {
        ProductoResponseDTO produResponseDTO = produServ.editProducto(id,produRequestDTO);
        return new ResponseEntity<>(produResponseDTO, HttpStatus.OK);
    }
    // Obtener productos con falta de stock menores a 5
    @GetMapping("/productos/falta_stock")
    public ResponseEntity<List<ProductoResponseDTO>> findBycantidadDisponibleLessThan() {
        return new  ResponseEntity<>(produServ.findBycantidadDisponibleLessThan(5), HttpStatus.OK);
    }
}

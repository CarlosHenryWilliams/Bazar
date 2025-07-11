/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.controller;

import com.mycompany.bazar.dto.ProductoRequestDTO;
import com.mycompany.bazar.dto.ProductoResponseDTO;
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
    public ResponseEntity<List<ProductoResponseDTO>> getProductos() {
        List<ProductoResponseDTO> listaProduResponseDTO= produServ.getProductos();
        return new ResponseEntity<>(listaProduResponseDTO, HttpStatus.OK);
    }

    // lectura de un solo objeto
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoResponseDTO> findProducto(@PathVariable Long id) {
        ProductoResponseDTO produResponseDTO = produServ.findProducto(id);
        return new ResponseEntity<>(produResponseDTO, HttpStatus.OK);
    }

    // alta
    @PostMapping("/productos/crear")
    public ResponseEntity<ProductoResponseDTO> saveProducto(@RequestBody @Valid ProductoRequestDTO produRequestDTO) {
        ProductoResponseDTO produResponseDTO = produServ.saveProducto(produRequestDTO);
        return new ResponseEntity<>(produResponseDTO, HttpStatus.CREATED);
    }

    // baja
    @DeleteMapping("/productos/eliminar/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        produServ.deleteProducto(id);
        return new ResponseEntity<>("Se ha eliminado el producto con el codigo: " + id + " con exito.", HttpStatus.OK);
    }

    // edicion
    @PutMapping("/productos/editar/{id}")
    public ResponseEntity<ProductoResponseDTO> editProducto(@PathVariable Long id, @RequestBody @Valid ProductoRequestDTO produRequestDTO) {
        ProductoResponseDTO produResponseDTO = produServ.editProducto(id,produRequestDTO);
        return new ResponseEntity<>(produResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/productos/falta_stock")
    public ResponseEntity<List<ProductoResponseDTO>> findBycantidadDisponibleLessThan() {
        return new ResponseEntity<>(produServ.findBycantidadDisponibleLessThan(5), HttpStatus.OK);
    }
}

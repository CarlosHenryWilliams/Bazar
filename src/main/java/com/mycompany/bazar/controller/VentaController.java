/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.controller;

import com.mycompany.bazar.dto.VentaFechaDTO;
import com.mycompany.bazar.dto.VentaRequestDTO;
import com.mycompany.bazar.dto.VentaResponseDTO;
import com.mycompany.bazar.dto.VentaUsuarioMayorVentaDTO;
import com.mycompany.bazar.model.ItemVenta;
import com.mycompany.bazar.model.Venta;
import com.mycompany.bazar.service.IVentaService;
import jakarta.validation.Valid;
import java.time.LocalDate;
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
public class VentaController {

    @Autowired
    IVentaService ventaServ;

    // lectura
    @GetMapping("/ventas")
    public ResponseEntity<List<Venta>> getVentas() {
        List<Venta> listaVentas = ventaServ.getVentas();
        return new ResponseEntity<>(listaVentas, HttpStatus.OK);
    }

    // lectura de un solo objeto
    @GetMapping("/ventas/{id}")
    public ResponseEntity<Venta> findVenta(@PathVariable Long id) {
        Venta ventaEncontrada = ventaServ.findVenta(id);
        return new ResponseEntity<>(ventaEncontrada, HttpStatus.OK);
    }

    // alta
    @PostMapping("/ventas/crear")
    public ResponseEntity<VentaResponseDTO> saveVenta(@RequestBody @Valid VentaRequestDTO ventaRequestDTO) {
        VentaResponseDTO ventaResponseDTO = ventaServ.saveVenta(ventaRequestDTO);
        return new ResponseEntity<>( ventaResponseDTO,HttpStatus.CREATED);
    }

    /// Revisar las 2 de abajo
    // baja
    @DeleteMapping("/ventas/eliminar/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        ventaServ.deleteVenta(id);
        return new ResponseEntity<>("La venta con el codigo: " + id + " ha sido eliminada", HttpStatus.OK);
    }

    // edit 
    @PutMapping("/ventas/editar")
    public ResponseEntity<Venta> editVenta(@RequestBody @Valid Venta venta) {
        Venta ventaEditada = ventaServ.editVenta(venta);
        return new ResponseEntity<>(ventaEditada, HttpStatus.OK);
    }

    // productos de una venta en especifico
    @GetMapping("/ventas/productos/{codigoVenta}")
    public ResponseEntity<List<ItemVenta>> findlistaDeItemsByCodigoVenta(@PathVariable Long codigoVenta) {
        List<ItemVenta> listaItems = ventaServ.findlistaDeItemsByCodigoVenta(codigoVenta);
        return new ResponseEntity<>(listaItems, HttpStatus.OK);
    }
    
    // monto total y cantidad de ventas en un dia
    @GetMapping("/ventas/fecha/{fechaVenta}")
    public ResponseEntity<String> findAllByfechaVentaMontoCantidad(@PathVariable LocalDate fechaVenta) {
        VentaFechaDTO venDTO =  ventaServ.findAllByfechaVentaMontoCantidad(fechaVenta);
        return new ResponseEntity<>("Fecha: " + venDTO.getFechaVenta() + "\nMonto Total: " + venDTO.getMontoTotal()+ " \nCantidad de ventas: " + venDTO.getCantidadVentas(),HttpStatus.OK);
    }
    
    @GetMapping("/ventas/mayor_venta")
      public ResponseEntity<VentaUsuarioMayorVentaDTO>  getMayorVenta() {
          VentaUsuarioMayorVentaDTO ventaDatos =  ventaServ.getMayorVenta();
          return new ResponseEntity<>(ventaDatos, HttpStatus.OK);
      }
    

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.controller;

import com.mycompany.bazar.dto.ItemVentaResponseDTO;
import com.mycompany.bazar.dto.VentaFechaDTO;
import com.mycompany.bazar.dto.VentaRequestDTO;
import com.mycompany.bazar.dto.VentaResponseDTO;
import com.mycompany.bazar.dto.VentaUsuarioMayorVentaDTO;
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

    // Obtener todas las Ventas
    @GetMapping("/ventas")
    public ResponseEntity<List<VentaResponseDTO>> getVentas() {
        List<VentaResponseDTO> listaVentaResponseDTO = ventaServ.getVentas();
        return new ResponseEntity<>(listaVentaResponseDTO, HttpStatus.OK);
    }

    // Obtener una Venta
    @GetMapping("/ventas/{id}")
    public ResponseEntity<VentaResponseDTO> findVenta(@PathVariable Long id) {
        VentaResponseDTO ventaResponseDTO = ventaServ.findVenta(id);
        return new ResponseEntity<>(ventaResponseDTO, HttpStatus.OK);
    }

    // Dar de alta una Venta
    @PostMapping("/ventas/crear")
    public ResponseEntity<VentaResponseDTO> saveVenta(@RequestBody @Valid VentaRequestDTO ventaRequestDTO) {
        VentaResponseDTO ventaResponseDTO = ventaServ.saveVenta(ventaRequestDTO);
        return new ResponseEntity<>( ventaResponseDTO,HttpStatus.CREATED);
    }

    // Dar de baja una Venta
    @DeleteMapping("/ventas/eliminar/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        ventaServ.deleteVenta(id);
        return new ResponseEntity<>("La venta con el codigo: " + id + " ha sido eliminada", HttpStatus.OK);
    }

    // Editar una Venta
    @PutMapping("/ventas/editar/{id}")
    public ResponseEntity<VentaResponseDTO> editVenta(@PathVariable Long id, @RequestBody @Valid VentaRequestDTO ventaRequestDTO) {
        VentaResponseDTO ventaResponseDTO = ventaServ.editVenta(id, ventaRequestDTO);
        return new ResponseEntity<>(ventaResponseDTO, HttpStatus.OK);
    }

    // Obtener los Productos de una Venta
    @GetMapping("/ventas/productos/{codigoVenta}")
    public ResponseEntity<List<ItemVentaResponseDTO>> findlistaDeItemsByCodigoVenta(@PathVariable Long codigoVenta) {
        List<ItemVentaResponseDTO> listaItems = ventaServ.findlistaDeItemsByCodigoVenta(codigoVenta);
        return new ResponseEntity<>(listaItems, HttpStatus.OK);
    }
    
    // Obtener el monto total y la cantidad de ventas en una fecha especifica
    @GetMapping("/ventas/fecha/{fechaVenta}")
    public ResponseEntity<String> findAllByfechaVentaMontoCantidad(@PathVariable LocalDate fechaVenta) {
        VentaFechaDTO venDTO =  ventaServ.findAllByfechaVentaMontoCantidad(fechaVenta);
        return new ResponseEntity<>("Fecha: " + venDTO.getFechaVenta() + "\nMonto Total: " + venDTO.getMontoTotal()+ " \nCantidad de ventas: " + venDTO.getCantidadVentas(),HttpStatus.OK);
    }
    
    // Obtener la mayor venta historica
    @GetMapping("/ventas/mayor_venta")
      public ResponseEntity<VentaUsuarioMayorVentaDTO>  getMayorVenta() {
          VentaUsuarioMayorVentaDTO ventaDatos =  ventaServ.getMayorVenta();
          return new ResponseEntity<>(ventaDatos, HttpStatus.OK);
      }
    

}

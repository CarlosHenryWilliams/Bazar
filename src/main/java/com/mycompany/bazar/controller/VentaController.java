/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.controller;

import com.mycompany.bazar.model.ItemVenta;
import com.mycompany.bazar.model.Producto;
import com.mycompany.bazar.model.Venta;
import com.mycompany.bazar.service.IProductoService;
import com.mycompany.bazar.service.IVentaService;
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
    @Autowired
    IProductoService produServ;

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
        if (ventaEncontrada == null) {
            return new ResponseEntity<>(ventaEncontrada, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ventaEncontrada, HttpStatus.OK);
    }

    // alta
    @PostMapping("/ventas/crear")
    public ResponseEntity<String> saveVenta(@RequestBody Venta venta) {
        Double totalVenta = 0D;
        for (ItemVenta item : venta.getListaDeItems()) {
            Producto productoBD = produServ.findProducto(item.getProducto().getCodigoProducto());
            productoBD.setCantidadDisponible(productoBD.getCantidadDisponible()-item.getCantidad());
            produServ.editProducto(productoBD);
            totalVenta = totalVenta + productoBD.getCosto() * item.getCantidad();
            item.setVenta(venta);
        }
        venta.setTotal(totalVenta);
        ventaServ.saveVenta(venta);
        return new ResponseEntity<>("La venta se ha realizado correctamente.", HttpStatus.CREATED);
    }

    /// Revisar las 2 de abajo
    // baja
    @DeleteMapping("/ventas/eliminar/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        Venta ventaAEliminar = ventaServ.findVenta(id);
        if (ventaAEliminar == null) {
            return new ResponseEntity<>("La venta con el codigo: " + id + " no ha sido encontrada.", HttpStatus.NOT_FOUND);
        }
        ventaServ.deleteVenta(id);
        return new ResponseEntity<>("La venta con el codigo:" + id + " ha sido eliminada", HttpStatus.OK);
    }

    // edicion
    @PutMapping("/ventas/editar")
    public ResponseEntity<Venta> editVenta(@RequestBody Venta venta) {
        Venta ventaEncontrada = ventaServ.findVenta(venta.getCodigoVenta());
        if (ventaEncontrada == null) {
            return new ResponseEntity<>(ventaEncontrada, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ventaEncontrada, HttpStatus.OK);
    }
}

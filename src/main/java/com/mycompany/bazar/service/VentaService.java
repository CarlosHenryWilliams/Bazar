/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.model.ItemVenta;
import com.mycompany.bazar.model.Producto;
import com.mycompany.bazar.model.Venta;
import com.mycompany.bazar.repository.IVentaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CharlyW
 */
@Service
public class VentaService implements IVentaService {

    @Autowired
    IVentaRepository ventaRepo;
    @Autowired
    IProductoService produRepo;

    @Override
    public List<Venta> getVentas() {
        return ventaRepo.findAll();
    }

    @Override
    public Venta findVenta(Long id) {
        Venta ven = ventaRepo.findById(id).orElse(null);
        return ven;
    }

    @Override
    public Boolean saveVenta(Venta venta) {

        Double totalVenta = 0D;
        // Recorro la lista de items de la venta

            for (ItemVenta item : venta.getListaDeItems()) {
                // Encuentro cada producto
                Producto productoBD = produRepo.findProducto(item.getProducto().getCodigoProducto());
                if (productoBD == null) { // si no se encuentra el producto
                    return false;
                }
                //Compruebo si toda la lista tiene stock
                if (productoBD.getCantidadDisponible() < item.getCantidad()) { // si piden mas de lo que tienen false
                    return false;
                }
            }
    
        // Si hay suficiente stock
        for (ItemVenta item : venta.getListaDeItems()) {
            // Encuentro cada producto
            Producto productoBD = produRepo.findProducto(item.getProducto().getCodigoProducto());
            // Descuento stock del producto
            productoBD.setCantidadDisponible(productoBD.getCantidadDisponible() - item.getCantidad());
            produRepo.editProducto(productoBD); // edito el producto
            // Calculo el costo total
            totalVenta = totalVenta + productoBD.getCosto() * item.getCantidad();
            item.setVenta(venta); // Vinculo la venta al item que se vendio
        }

        venta.setTotal(totalVenta);
        ventaRepo.save(venta);
        return true;
    }

    @Override
    public void deleteVenta(Long id) {
        ventaRepo.deleteById(id);
    }

    @Override
    public void editVenta(Venta venta) {
        this.saveVenta(venta);
    }

    @Override
    public List<ItemVenta> findlistaDeItemsByCodigoVenta(Long codigoVenta) {
        Venta ventaEncontrada = this.findVenta(codigoVenta);

        return ventaEncontrada.getListaDeItems();

    }

}

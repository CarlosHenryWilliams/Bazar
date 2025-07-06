/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.exception.InsufficientStockException;
import com.mycompany.bazar.exception.ProductoNotFoundException;
import com.mycompany.bazar.exception.VentaNotFoundException;
import com.mycompany.bazar.model.ItemVenta;
import com.mycompany.bazar.model.Producto;
import com.mycompany.bazar.model.Venta;
import com.mycompany.bazar.repository.IProductoRepository;
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
    IProductoRepository produRepo;

    @Override
    public List<Venta> getVentas() {
        return ventaRepo.findAll();
    }

    @Override
    public Venta findVenta(Long id) {
        Venta ven = ventaRepo.findById(id).orElse(null);
        if (ven == null) {
            throw new VentaNotFoundException("La venta con el codigo: " + id + " no existe.");
        }
        return ven;
    }

    @Override
    public void saveVenta(Venta venta) {

        Double totalVenta = 0D;
        // Recorro la lista de items de la venta

        for (ItemVenta item : venta.getListaDeItems()) {
            // Encuentro cada producto
            Producto productoBD = produRepo.findById(item.getProducto().getCodigoProducto()).orElse(null);
            if (productoBD == null) { // si no se encuentra el producto
                throw new ProductoNotFoundException("El producto con el id: " + item.getProducto().getCodigoProducto() + " no existe.");
            }
            //Compruebo si toda la lista tiene stock
            if (productoBD.getCantidadDisponible() < item.getCantidad()) { // si piden mas de lo que tienen false
                throw new InsufficientStockException("No hay stock suficiente para el producto " + productoBD.getNombre()
                        + " con el codigo: " + productoBD.getCodigoProducto()
                        + " Stock actual: " + productoBD.getCantidadDisponible()
                        + " Cantidad pedida: " + item.getCantidad());
            }
        }

        // Si hay suficiente stock
        for (ItemVenta item : venta.getListaDeItems()) {
            // Encuentro cada producto
            Producto productoBD = produRepo.findById(item.getProducto().getCodigoProducto()).orElse(null);
            if (productoBD == null) { // si no se encuentra el producto
                throw new ProductoNotFoundException("El producto con el id: " + item.getProducto().getCodigoProducto() + " no existe.");
            }
            // Descuento stock del producto
            productoBD.setCantidadDisponible(productoBD.getCantidadDisponible() - item.getCantidad());
            produRepo.save(productoBD); // edito el producto
            // Calculo el costo total
            totalVenta = totalVenta + productoBD.getCosto() * item.getCantidad();
            item.setVenta(venta); // Vinculo la venta al item que se vendio
        }

        venta.setTotal(totalVenta);
        ventaRepo.save(venta);
    }

    @Override
    public void deleteVenta(Long id) {
        Venta ventaAEliminar = this.findVenta(id);
        // Recorro la lista de items de la venta
        for (ItemVenta item : ventaAEliminar.getListaDeItems()) {
            // Encuentro cada producto
            Producto productoBD = produRepo.findById(item.getProducto().getCodigoProducto()).orElse(null);
            if (productoBD == null) { // si no se encuentra el producto
                throw new ProductoNotFoundException("El producto con el id: " + item.getProducto().getCodigoProducto() + " no existe.");
            }
            // Devuelvo la cantidad que se pidio agregandolo como stock del producto - ya que si no se vendio no se desconto
            productoBD.setCantidadDisponible(productoBD.getCantidadDisponible() + item.getCantidad());
            produRepo.save(productoBD);
        }

        ventaRepo.deleteById(id);
    }

    /// REVISAR COMO DEVOLVER EL STOCK SI CAMBIA DE PRODUCTO
    @Override
    public Venta editVenta(Venta venta) {
        Venta ventaEncontrada = this.findVenta(venta.getCodigoVenta());
        Double costoTotal = 0D;

        // Primero devolver los items que compro al encontrar la venta en bd
        for (ItemVenta item : ventaEncontrada.getListaDeItems()) {
            Producto productoDB = produRepo.findById(item.getProducto().getCodigoProducto()).orElse(null);

            if (productoDB == null) { // si no se encuentra el producto
                throw new ProductoNotFoundException("El producto con el id: " + item.getProducto().getCodigoProducto() + " no existe.");
            }
            productoDB.setCantidadDisponible(productoDB.getCantidadDisponible() + item.getCantidad());
        }
        // 

        // Comprobar que los items de la venta que llega tengan stock
        for (ItemVenta item : venta.getListaDeItems()) {
            Producto productoBD = produRepo.findById(item.getProducto().getCodigoProducto()).orElse(null);
            if (productoBD.getCantidadDisponible() < item.getCantidad()) { // si no hay stock;
                throw new InsufficientStockException(
                        "No hay stock suficiente para el producto " + productoBD.getNombre()
                        + " con el codigo: " + productoBD.getCodigoProducto()
                        + " Stock actual:" + productoBD.getCantidadDisponible()
                        + " Cantidad pedida: " + item.getCantidad());
            }
        }

        // Si hay stock hacer la venta
        for (ItemVenta item : venta.getListaDeItems()) {
            Producto productoDB = produRepo.findById(item.getProducto().getCodigoProducto()).orElse(null);
            if (productoDB == null) { // si no se encuentra el producto
                throw new ProductoNotFoundException("El producto con el id: " + item.getProducto().getCodigoProducto() + " no existe.");
            }
            if (productoDB.getCantidadDisponible() >= item.getCantidad()) {  // si hay stock

                productoDB.setCantidadDisponible(productoDB.getCantidadDisponible() - item.getCantidad());
                produRepo.save(productoDB);
                costoTotal = costoTotal + (productoDB.getCosto() * item.getCantidad());
            }
        }

        ventaEncontrada.setFechaVenta(venta.getFechaVenta());
        ventaEncontrada.setListaDeItems(venta.getListaDeItems());
        ventaEncontrada.setUnCliente(venta.getUnCliente());
        ventaEncontrada.setTotal(costoTotal);
        this.saveVenta(venta);

        return ventaEncontrada;
    }

    @Override
    public List<ItemVenta> findlistaDeItemsByCodigoVenta(Long codigoVenta) {
        Venta ventaEncontrada = this.findVenta(codigoVenta);

        return ventaEncontrada.getListaDeItems();

    }

}

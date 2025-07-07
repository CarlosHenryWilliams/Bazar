/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.exception.ProductoNotFoundException;
import com.mycompany.bazar.model.Producto;
import com.mycompany.bazar.repository.IProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CharlyW
 */
@Service
public class ProductoService implements IProductoService {

    @Autowired
    IProductoRepository produRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductos() {
        return produRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findProducto(Long id) {
        Producto produ = produRepo.findById(id).orElseThrow(() -> new ProductoNotFoundException("El producto con el codigo: " + id + " no existe."));
        return produ;
    }

    @Override
    @Transactional
    public void saveProducto(Producto produ) {
        produRepo.save(produ);
    }

    @Override
    @Transactional
    public void deleteProducto(Long id) {
        Producto produ = this.findProducto(id);
        produRepo.deleteById(produ.getCodigoProducto());
    }

    @Override
    @Transactional
    public Producto editProducto(Producto produ) {
        Producto producto = this.findProducto(produ.getCodigoProducto());
        producto.setNombre(produ.getNombre());
        producto.setMarca(produ.getMarca());
        producto.setCosto(produ.getCosto());
        producto.setCantidadDisponible(produ.getCantidadDisponible());
        this.saveProducto(producto);
        return producto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findBycantidadDisponibleLessThan(Integer cantidad) {
        return produRepo.findBycantidadDisponibleLessThan(cantidad);
    }

}

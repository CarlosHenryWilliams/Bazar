/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.model.Producto;
import com.mycompany.bazar.repository.IProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CharlyW
 */
@Service
public class ProductoService implements IProductoService{

    @Autowired
    IProductoRepository produRepo;
    
    @Override
    public List<Producto> getProductos() {
       return produRepo.findAll();
    }

    @Override
    public Producto findProducto(Long id) {
        Producto pro = produRepo.findById(id).orElse(null);
        return pro;
    }

    @Override
    public void saveProducto(Producto produ) {
         produRepo.save(produ);
    }

    @Override
    public void deleteProducto(Long id) {
        produRepo.deleteById(id);
    }

    @Override
    public void editProducto(Producto produ) {
        this.saveProducto(produ);
    }
    
}

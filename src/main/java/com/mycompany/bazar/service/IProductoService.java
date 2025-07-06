/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.model.Producto;
import java.util.List;

/**
 *
 * @author CharlyW
 */
public interface IProductoService {

    // lectura
    public List<Producto> getProductos();

    // lectura de un solo objeto
    public Producto findProducto(Long id);

    // alta
    public void saveProducto(Producto produ);

    // baja
    public void deleteProducto(Long id);

    // edicion
    public Producto editProducto(Producto produ);
    
    // Traer productos con falta de sstock
    public List<Producto> findBycantidadDisponibleLessThan(Integer cantidad);
    
}

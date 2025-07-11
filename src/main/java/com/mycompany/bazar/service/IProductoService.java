/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.dto.ProductoRequestDTO;
import com.mycompany.bazar.dto.ProductoResponseDTO;
import com.mycompany.bazar.model.Producto;
import java.util.List;

/**
 *
 * @author CharlyW
 */
public interface IProductoService {

    // lectura
    public List<ProductoResponseDTO> getProductos();

    // lectura de un solo objeto
    public ProductoResponseDTO findProducto(Long id);

    // alta
    public ProductoResponseDTO saveProducto(ProductoRequestDTO produRequestDTO);

    // baja
    public void deleteProducto(Long id);

    // edicion
    public ProductoResponseDTO editProducto(Long id, ProductoRequestDTO produRequestDTO);
    
    // Traer productos con falta de sstock
    public List<ProductoResponseDTO> findBycantidadDisponibleLessThan(Integer cantidad);
    
}

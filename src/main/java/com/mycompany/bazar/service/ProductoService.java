/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.dto.ProductoRequestDTO;
import com.mycompany.bazar.dto.ProductoResponseDTO;
import com.mycompany.bazar.exception.ProductoNotFoundException;
import com.mycompany.bazar.model.Producto;
import com.mycompany.bazar.repository.IProductoRepository;
import java.util.ArrayList;
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
    public Producto validarProducto(Long id) {
        return produRepo.findById(id).orElseThrow(()-> new ProductoNotFoundException("El producto con el codigo: " + id + " no existe."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> getProductos() {

        List<Producto> listaProductos = produRepo.findAll();
        List<ProductoResponseDTO> listaProduResponseDTO = new ArrayList<>();
        for (Producto produ : listaProductos) {
            listaProduResponseDTO.add(new ProductoResponseDTO(produ.getCodigoProducto(), produ.getNombre(), produ.getMarca(), produ.getCosto(), produ.getCantidadDisponible()));
        }
        return listaProduResponseDTO;

    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO findProducto(Long id) {
        Producto produ = this.validarProducto(id);
        ProductoResponseDTO produResponseDTO = new ProductoResponseDTO(produ.getCodigoProducto(), produ.getNombre(), produ.getMarca(), produ.getCosto(), produ.getCantidadDisponible());
        return produResponseDTO;
    }

    @Override
    @Transactional
    public ProductoResponseDTO saveProducto(ProductoRequestDTO produRequestDTO) {
        Producto productoAGuardar = new Producto(produRequestDTO.getNombre(), produRequestDTO.getMarca(), produRequestDTO.getCosto(), produRequestDTO.getCantidadDisponible());
        productoAGuardar = produRepo.save(productoAGuardar);
        ProductoResponseDTO produResponseDTO = new ProductoResponseDTO(productoAGuardar.getCodigoProducto(), productoAGuardar.getNombre(), productoAGuardar.getMarca(), productoAGuardar.getCosto(), productoAGuardar.getCantidadDisponible());
        return produResponseDTO;
    }

    @Override
    @Transactional
    public void deleteProducto(Long id) {
        Producto producto =  this.validarProducto(id);
        produRepo.deleteById(producto.getCodigoProducto());
    }

    @Override
    @Transactional
    public ProductoResponseDTO editProducto(Long id, ProductoRequestDTO produRequestDTO) {
        Producto producto  = this.validarProducto(id);

        producto.setNombre(produRequestDTO.getNombre());
        producto.setMarca(produRequestDTO.getMarca());
        producto.setCosto(produRequestDTO.getCosto());
        producto.setCantidadDisponible(produRequestDTO.getCantidadDisponible());
        producto = produRepo.save(producto);
        ProductoResponseDTO produResponseDTO = new ProductoResponseDTO(producto.getCodigoProducto(), producto.getNombre(), producto.getMarca(), producto.getCosto(), producto.getCantidadDisponible());
        return produResponseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findBycantidadDisponibleLessThan(Integer cantidad) {
        List<Producto> listaProductos = produRepo.findBycantidadDisponibleLessThan(cantidad);
        List<ProductoResponseDTO> listaProduResponseDTO = new ArrayList<>();
        for (Producto produ : listaProductos) {
            listaProduResponseDTO.add(new ProductoResponseDTO(produ.getCodigoProducto(), produ.getNombre(), produ.getMarca(), produ.getCosto(), produ.getCantidadDisponible()));
        }
        return listaProduResponseDTO;
    }

}

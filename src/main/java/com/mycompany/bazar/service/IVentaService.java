/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.dto.VentaFechaDTO;
import com.mycompany.bazar.dto.VentaRequestDTO;
import com.mycompany.bazar.dto.VentaResponseDTO;
import com.mycompany.bazar.dto.VentaUsuarioMayorVentaDTO;
import com.mycompany.bazar.model.ItemVenta;
import com.mycompany.bazar.model.Venta;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author CharlyW
 */
public interface IVentaService {

    // lectura
    public List<Venta> getVentas();

    // lectura de un solo objeto
    public Venta findVenta(Long id);

    // alta
    public VentaResponseDTO saveVenta(VentaRequestDTO ventaRequestDTO);

    // baja
    public void deleteVenta(Long id);

    // edicion
    public Venta editVenta(Venta venta);

    // Devuelve los productos de una venta 
    public List<ItemVenta> findlistaDeItemsByCodigoVenta(Long codigoVenta);

    // Devuelve todas las ventas con la cantidad y la suma de tal fecha
    public VentaFechaDTO findAllByfechaVentaMontoCantidad(LocalDate fecha);
    
    // Devuelve la mayor venta hasta el momentot
    public VentaUsuarioMayorVentaDTO getMayorVenta();
    

}

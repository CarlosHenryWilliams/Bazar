/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.model.ItemVenta;
import com.mycompany.bazar.model.Venta;
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
    public Boolean saveVenta(Venta venta);

    // baja
    public void deleteVenta(Long id);

    // edicion
    public Venta  editVenta(Venta venta);

    public List<ItemVenta> findlistaDeItemsByCodigoVenta(Long codigoVenta);
}

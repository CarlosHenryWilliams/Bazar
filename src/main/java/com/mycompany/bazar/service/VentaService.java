/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.model.ItemVenta;
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

    @Override
    public List<Venta> getVentas() {
        return ventaRepo.findAll();
    }

    @Override
    public Venta findVenta(Long id) {
        Venta ven =ventaRepo.findById(id).orElse(null);
        return ven;
    }

    @Override
    public void saveVenta(Venta venta) {
        ventaRepo.save(venta);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.service;

import com.mycompany.bazar.dto.ClienteResponseDTOParaVenta;
import com.mycompany.bazar.dto.ItemVentaRequestDTO;
import com.mycompany.bazar.dto.ItemVentaResponseDTO;
import com.mycompany.bazar.dto.ProductoVentaResponseDTOParaVenta;
import com.mycompany.bazar.dto.VentaFechaDTO;
import com.mycompany.bazar.dto.VentaRequestDTO;
import com.mycompany.bazar.dto.VentaResponseDTO;
import com.mycompany.bazar.dto.VentaUsuarioMayorVentaDTO;
import com.mycompany.bazar.exception.ClienteNotFoundException;
import com.mycompany.bazar.exception.InsufficientStockException;
import com.mycompany.bazar.exception.ProductoNotFoundException;
import com.mycompany.bazar.exception.VentaNotFoundException;
import com.mycompany.bazar.model.Cliente;
import com.mycompany.bazar.model.ItemVenta;
import com.mycompany.bazar.model.Producto;
import com.mycompany.bazar.model.Venta;
import com.mycompany.bazar.repository.IClienteRepository;
import com.mycompany.bazar.repository.IProductoRepository;
import com.mycompany.bazar.repository.IVentaRepository;
import java.time.LocalDate;
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
public class VentaService implements IVentaService {

    @Autowired
    IVentaRepository ventaRepo;
    @Autowired
    IProductoRepository produRepo;
    @Autowired
    IClienteRepository clienteRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Venta> getVentas() {
        return ventaRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Venta findVenta(Long id) {
        Venta ven = ventaRepo.findById(id).orElseThrow(() -> new VentaNotFoundException("La venta con el codigo: " + id + " no existe."));
        return ven;
    }

    @Override
    @Transactional
    public VentaResponseDTO saveVenta(VentaRequestDTO ventaRequestDTO) {

        // Reviso si el cliente existe.
        Cliente clienteAsociado = clienteRepo.findClienteBydni(ventaRequestDTO.getDniCliente()).orElseThrow(
                () -> new ClienteNotFoundException("El cliente con el DNI: " + ventaRequestDTO.getDniCliente() + " no existe."));
        Venta ventaACrear = new Venta();

        Double totalVenta = 0D;

        // Recorro la lista de items de la venta
        for (ItemVentaRequestDTO itemDTO : ventaRequestDTO.getListaDeItemsDTO()) {
            // Encuentro cada producto
            Producto productoBD = produRepo.findById(itemDTO.getIdProducto()).orElseThrow(
                    () -> new ProductoNotFoundException("El producto con el id: " + itemDTO.getIdProducto() + " no existe."));

            //Compruebo si toda la lista tiene stock
            if (productoBD.getCantidadDisponible() < itemDTO.getCantidad()) { // si piden mas de lo que tienen false
                throw new InsufficientStockException("No hay stock suficiente para el producto " + productoBD.getNombre()
                        + " con el codigo: " + productoBD.getCodigoProducto()
                        + " Stock actual: " + productoBD.getCantidadDisponible()
                        + " Cantidad pedida: " + itemDTO.getCantidad());
            }
        }

        List<ItemVenta> listaItemVenta = new ArrayList<>();

        // Si hay suficiente stock
        for (ItemVentaRequestDTO itemDTO : ventaRequestDTO.getListaDeItemsDTO()) {
            // Encuentro cada producto
            Producto productoBD = produRepo.findById(itemDTO.getIdProducto()).orElseThrow(
                    () -> new ProductoNotFoundException("El producto con el id: " + itemDTO.getIdProducto() + " no existe."));

            listaItemVenta.add(new ItemVenta(itemDTO.getCantidad(), ventaACrear, productoBD)); // agrego cada item de venta a una lista  de ventas
            // Descuento stock del producto
            productoBD.setCantidadDisponible(productoBD.getCantidadDisponible() - itemDTO.getCantidad());
            produRepo.save(productoBD); // edito el producto
            // Calculo el costo total
            totalVenta = totalVenta + productoBD.getCosto() * itemDTO.getCantidad();
            //   item.setProducto(productoBD); // ASOCIO EL PRODUCTO AL ITEM
            //item.setVenta(venta); // Vinculo la venta al item que se vendio

        }

        ventaACrear.setUnCliente(clienteAsociado); // ASOCIAR CLIENTE A LA VENTA
        ventaACrear.setFechaVenta(ventaRequestDTO.getFechaVenta());
        ventaACrear.setTotal(totalVenta);
        ventaACrear.setListaDeItems(listaItemVenta); // vinculo la lista de items
        ventaACrear = ventaRepo.save(ventaACrear);

        ///Crear DTO'S
        // CLIENTE DTO
        ClienteResponseDTOParaVenta clienteDTO = new ClienteResponseDTOParaVenta(clienteAsociado.getNombre(), clienteAsociado.getApellido(), clienteAsociado.getDni());
        // Lista de items venta DTO
        List<ItemVentaResponseDTO> listaItemVentaResponseDTO = new ArrayList<>();
        for (ItemVenta itemVenta : ventaACrear.getListaDeItems()) {
            // ProductoResponseDTO
            ProductoVentaResponseDTOParaVenta produResponse = new ProductoVentaResponseDTOParaVenta(itemVenta.getProducto().getCodigoProducto(), itemVenta.getProducto().getNombre(), itemVenta.getProducto().getMarca(), itemVenta.getProducto().getCosto());
            listaItemVentaResponseDTO.add(new ItemVentaResponseDTO(itemVenta.getIdItemVenta(), itemVenta.getCantidad(), produResponse));
        }
        // VentaResponsseDTO

        VentaResponseDTO ventaResponseDTO = new VentaResponseDTO(ventaACrear.getCodigoVenta(), totalVenta, ventaACrear.getFechaVenta(), listaItemVentaResponseDTO, clienteDTO);
        return ventaResponseDTO;
    }

    @Override
    @Transactional
    public void deleteVenta(Long id) {
        Venta ventaAEliminar = this.findVenta(id);
        // Recorro la lista de items de la venta
        for (ItemVenta item : ventaAEliminar.getListaDeItems()) {
            // Encuentro cada producto
            Producto productoBD = produRepo.findById(item.getProducto().getCodigoProducto()).orElseThrow(
                    () -> new ProductoNotFoundException("El producto con el id: " + item.getProducto().getCodigoProducto() + " no existe."));

            // Devuelvo la cantidad que se pidio agregandolo como stock del producto - ya que si no se vendio no se desconto
            productoBD.setCantidadDisponible(productoBD.getCantidadDisponible() + item.getCantidad());
            produRepo.save(productoBD);
        }

        ventaRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Venta editVenta(Venta venta) {
        //Reviso si encuentro la venta
        Venta ventaEncontrada = this.findVenta(venta.getCodigoVenta());

        // Reviso si el cliente existe.
        clienteRepo.findById(venta.getUnCliente().getIdCliente()).orElseThrow(
                () -> new ClienteNotFoundException("El cliente con el id: " + venta.getUnCliente().getIdCliente() + " no existe."));

        // Primero devolver los items que compro al encontrar la venta en bd
        for (ItemVenta item : ventaEncontrada.getListaDeItems()) {
            Producto productoDB = produRepo.findById(item.getProducto().getCodigoProducto()).orElseThrow(
                    () -> new ProductoNotFoundException("El producto con el id: " + item.getProducto().getCodigoProducto() + " no existe."));
            productoDB.setCantidadDisponible(productoDB.getCantidadDisponible() + item.getCantidad());
        }

        Double costoTotal = 0D;

        // Comprobar que los items de la venta que llega tengan stock
        for (ItemVenta item : venta.getListaDeItems()) {
            Producto productoBD = produRepo.findById(item.getProducto().getCodigoProducto()).orElseThrow(
                    () -> new ProductoNotFoundException("El producto con el id: " + item.getProducto().getCodigoProducto() + " no existe."));

            if (productoBD.getCantidadDisponible() >= item.getCantidad()) {  // si hay stock
                productoBD.setCantidadDisponible(productoBD.getCantidadDisponible() - item.getCantidad()); // resto el stock
                produRepo.save(productoBD);  // actualizo el producto
                costoTotal = costoTotal + (productoBD.getCosto() * item.getCantidad());
            } else { // si no hay stock;
                throw new InsufficientStockException(
                        "No hay stock suficiente para el producto " + productoBD.getNombre()
                        + " con el codigo: " + productoBD.getCodigoProducto()
                        + " Stock actual:" + productoBD.getCantidadDisponible()
                        + " Cantidad pedida: " + item.getCantidad());
            }
        }

        ventaEncontrada.setFechaVenta(venta.getFechaVenta());
        ventaEncontrada.setListaDeItems(venta.getListaDeItems());
        ventaEncontrada.setUnCliente(venta.getUnCliente());
        ventaEncontrada.setTotal(costoTotal);
        ventaRepo.save(ventaEncontrada);

        return ventaEncontrada;
    }

    // Devuelve los productos de una venta 
    @Override
    @Transactional(readOnly = true)
    public List<ItemVenta> findlistaDeItemsByCodigoVenta(Long codigoVenta) {
        Venta ventaEncontrada = this.findVenta(codigoVenta);
        return ventaEncontrada.getListaDeItems();

    }

    @Override
    @Transactional
    public VentaFechaDTO findAllByfechaVentaMontoCantidad(LocalDate fecha) {
        //Encuentro las ventas de tal dia
        Double montoTotalDia = 0D;
        int cantidadVenta = 0;

        List<Venta> listaVentas = ventaRepo.findAllByfechaVenta(fecha);

        if (!listaVentas.isEmpty()) {
            for (Venta ven : listaVentas) {
                montoTotalDia = montoTotalDia + ven.getTotal();
                cantidadVenta++;
            }
        }
        return new VentaFechaDTO(fecha, montoTotalDia, cantidadVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public VentaUsuarioMayorVentaDTO getMayorVenta() {
        Venta venta = ventaRepo.findFirstByOrderByTotalDesc();
        if (venta == null) {
            throw new VentaNotFoundException("No se encuentran ventas registradas en el sistema.");
        }
        this.findVenta(venta.getCodigoVenta());
        return new VentaUsuarioMayorVentaDTO(venta.getCodigoVenta(), venta.getUnCliente().getNombre(), venta.getUnCliente().getApellido(), venta.getListaDeItems().size(), venta.getTotal());
    }

}

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
    public List<VentaResponseDTO> getVentas() {

        List<VentaResponseDTO> listaVentas = new ArrayList<>();
        for (Venta ven : ventaRepo.findAll()) {
            List<ItemVentaResponseDTO> listaItemsVentaResponseDTO = new ArrayList<>();

            for (ItemVenta itemVenta : ven.getListaDeItems()) {
                ProductoVentaResponseDTOParaVenta produResponeDTO = new ProductoVentaResponseDTOParaVenta(itemVenta.getProducto().getCodigoProducto(), itemVenta.getProducto().getNombre(), itemVenta.getProducto().getMarca(), itemVenta.getProducto().getCosto());

                listaItemsVentaResponseDTO.add(new ItemVentaResponseDTO(itemVenta.getIdItemVenta(), itemVenta.getCantidad(), produResponeDTO));
            }
            ClienteResponseDTOParaVenta clienteResponseDTO = new ClienteResponseDTOParaVenta(ven.getUnCliente().getNombre(), ven.getUnCliente().getApellido(), ven.getUnCliente().getDni());
            listaVentas.add(new VentaResponseDTO(ven.getCodigoVenta(), ven.getTotal(), ven.getFechaVenta(), listaItemsVentaResponseDTO, clienteResponseDTO));
        }
        return listaVentas;
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponseDTO findVenta(Long id) {
        Venta ven = ventaRepo.findById(id).orElseThrow(() -> new VentaNotFoundException("La venta con el codigo: " + id + " no existe."));
        // Creo los DTO's para la Venta.
        ClienteResponseDTOParaVenta cliResDTO = new ClienteResponseDTOParaVenta(ven.getUnCliente().getNombre(), ven.getUnCliente().getApellido(), ven.getUnCliente().getDni());
        List<ItemVentaResponseDTO> listaItemsResponseDTO = new ArrayList<>();
        for (ItemVenta item : ven.getListaDeItems()) {
            ProductoVentaResponseDTOParaVenta produVenResDTO = new ProductoVentaResponseDTOParaVenta(item.getProducto().getCodigoProducto(), item.getProducto().getNombre(), item.getProducto().getMarca(), item.getProducto().getCosto());
            ItemVentaResponseDTO itemVenResDTO = new ItemVentaResponseDTO(item.getIdItemVenta(), item.getCantidad(), produVenResDTO);
            listaItemsResponseDTO.add(itemVenResDTO);
        }
        VentaResponseDTO venResDTO = new VentaResponseDTO(ven.getCodigoVenta(), ven.getTotal(), ven.getFechaVenta(), listaItemsResponseDTO, cliResDTO);
        return venResDTO;
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
        Venta ventaAEliminar = ventaRepo.findById(id).orElseThrow(() -> new VentaNotFoundException("La venta con el codigo: " + id + " no existe."));
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
    public VentaResponseDTO editVenta(Long id, VentaRequestDTO ventaRequestDTO) {
        //Reviso si encuentro la venta
        Venta ventaEncontrada = ventaRepo.findById(id).orElseThrow(() -> new VentaNotFoundException("La venta con el codigo: " + id + " no existe."));

        // Reviso si el cliente existe.
        Cliente clienteEncontrado = clienteRepo.findClienteBydni(ventaRequestDTO.getDniCliente()).orElseThrow(() -> new ClienteNotFoundException("El cliente con el DNI: " + ventaRequestDTO.getDniCliente() + "no existe."));

        // Primero devolver los items que compro al encontrar la venta en bd
        for (ItemVenta item : ventaEncontrada.getListaDeItems()) {
            Producto productoDB = produRepo.findById(item.getProducto().getCodigoProducto()).orElseThrow(
                    () -> new ProductoNotFoundException("El producto con el id: " + item.getProducto().getCodigoProducto() + " no existe."));
            productoDB.setCantidadDisponible(productoDB.getCantidadDisponible() + item.getCantidad());
        }
        
        // Limpiamos la lista de Items que ya tenia por defecto.
        ventaEncontrada.getListaDeItems().clear();  // Esto los remueve tambien automaticamente de la BD por el orphanRemoval = true en la clase Venta
        Double costoTotal = 0D;

        
        
        
        
        
        
        
        // Comprobar que los items de la venta que llega tengan stock
        // y Agregar a la lista de Items de la Venta original a persistir.
        List<ItemVentaResponseDTO> listaDeItemsResponseDTO = new ArrayList<>(); // Lista de ItemsResponse a devolver

        for (ItemVentaRequestDTO itemVenRequestDTO : ventaRequestDTO.getListaDeItemsDTO()) {

            Producto productoBD = produRepo.findById(itemVenRequestDTO.getIdProducto()).orElseThrow(
                    () -> new ProductoNotFoundException("El producto con el id: " + itemVenRequestDTO.getIdProducto() + " no existe."));

            if (productoBD.getCantidadDisponible() >= itemVenRequestDTO.getCantidad()) {  // si hay stock
                productoBD.setCantidadDisponible(productoBD.getCantidadDisponible() - itemVenRequestDTO.getCantidad()); // resto el stock
                produRepo.save(productoBD);  // actualizo el producto
                costoTotal = costoTotal + (productoBD.getCosto() * itemVenRequestDTO.getCantidad());

                // Agrego el itemVenta nuevo a la venta original diferenciando los DTO
                ItemVenta itemVenta = new ItemVenta(itemVenRequestDTO.getCantidad(), ventaEncontrada, productoBD);
                ventaEncontrada.getListaDeItems().add(itemVenta); // Agrego los items a lista de ventas una vez encontrada y vaciada
                
                // Asocio un productoVentaDTO a un ItemVentaResponseDTO y posteriormente agrego el producto a la lista (item).
                ProductoVentaResponseDTOParaVenta produResponseVentaDTO = new ProductoVentaResponseDTOParaVenta(productoBD.getCodigoProducto(), productoBD.getNombre(), productoBD.getMarca(), productoBD.getCosto());
                
                ItemVentaResponseDTO  itemVentaResponseDTO = new ItemVentaResponseDTO(itemVenRequestDTO.getIdProducto(), itemVenRequestDTO.getCantidad(), produResponseVentaDTO);
                listaDeItemsResponseDTO.add(itemVentaResponseDTO); // agrego a la lista de items que se devuelven abajo.

            } else { // si no hay stock;
                throw new InsufficientStockException(
                        "No hay stock suficiente para el producto " + productoBD.getNombre()
                        + " con el codigo: " + productoBD.getCodigoProducto()
                        + " Stock actual:" + productoBD.getCantidadDisponible()
                        + " Cantidad pedida: " + itemVenRequestDTO.getCantidad());
            }
        }

        ventaEncontrada.setFechaVenta(ventaRequestDTO.getFechaVenta());
        ventaEncontrada.setTotal(costoTotal);
        ventaEncontrada.setUnCliente(clienteEncontrado);
        ventaRepo.save(ventaEncontrada);

        ClienteResponseDTOParaVenta clienteResponseDTO = new ClienteResponseDTOParaVenta(clienteEncontrado.getNombre(), clienteEncontrado.getApellido(), clienteEncontrado.getDni());

        // Devuelvo el ventaResponseDTO
        VentaResponseDTO ventaResponseDTO = new VentaResponseDTO(id, costoTotal, ventaRequestDTO.getFechaVenta(), listaDeItemsResponseDTO, clienteResponseDTO);

        return ventaResponseDTO;
    }

    // Devuelve los productos de una venta 
    @Override
    @Transactional(readOnly = true)
    public List<ItemVentaResponseDTO> findlistaDeItemsByCodigoVenta(Long codigoVenta) {
        Venta ventaEncontrada = ventaRepo.findById(codigoVenta).orElseThrow(() -> new VentaNotFoundException("La venta con el codigo: " + codigoVenta + " no existe."));
        List<ItemVentaResponseDTO> listaItemsVenta = new ArrayList<>();
        for (ItemVenta item : ventaEncontrada.getListaDeItems()) {
            // Creo un productoDTO
            ProductoVentaResponseDTOParaVenta produ = new ProductoVentaResponseDTOParaVenta(item.getProducto().getCodigoProducto(), item.getProducto().getNombre(), item.getProducto().getMarca(), item.getProducto().getCosto());
            listaItemsVenta.add(new ItemVentaResponseDTO(item.getIdItemVenta(), item.getCantidad(), produ));

        }

        return listaItemsVenta;

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

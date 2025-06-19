package com.microservice.sales.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.microservice.sales.model.Carrito;
import com.microservice.sales.model.ItemCarrito;
import com.microservice.sales.repository.CarritoRepository;
import com.microservice.sales.repository.ItemCarritoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class CarritoServiceImpl implements CarritoService{

    private final CarritoRepository carritoRepository;
    private final ItemCarritoRepository itemCarritoRepository;

     @Override
    public Carrito crearCarrito(Carrito carrito) {
        // Inicializar la lista si es necesario
        if(carrito.getItems() == null) {
            carrito.setItems(new ArrayList<>());
        }
        return carritoRepository.save(carrito);
    }

     @Override
    public Optional<Carrito> obtenerCarritoActivoPorCliente(String clienteId) {
        return carritoRepository.findByClienteIdAndFinalizadoFalse(clienteId);
    }

     @Override
    public Carrito agregarItem(Long carritoId, ItemCarrito item) {
        Carrito carrito = carritoRepository.findById(carritoId)
            .orElseThrow(() -> new EmptyResultDataAccessException("Carrito no encontrado", 1));
        
        // Inicializar lista si es necesario
        if(carrito.getItems() == null) {
            carrito.setItems(new ArrayList<>());
        }
        
        // Establecer relación bidireccional
        item.setCarrito(carrito);
        carrito.getItems().add(item);
        
        // Guardar el ítem
        itemCarritoRepository.save(item);
        
        // Actualizar carrito
        carrito.setUltimaActualizacion(LocalDateTime.now());
        return carritoRepository.save(carrito);
    }

    @Override
    public void eliminarItem(Long itemId) {
        itemCarritoRepository.deleteById(itemId);
    }

    @Override
    public void finalizarCarrito(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        carrito.setFinalizado(true);
        carritoRepository.save(carrito);
    }

}

package com.microservice.sales.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.microservice.sales.model.Carrito;
import com.microservice.sales.model.ItemCarrito;
import com.microservice.sales.repository.CarritoRepository;
import com.microservice.sales.repository.ItemCarritoReposiory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class CarritoServiceImpl implements CarritoService{

    private final CarritoRepository carritoRepository;
    private final ItemCarritoReposiory itemCarritoRepository;

    @Override
    public Carrito crearCarrito(Carrito carrito) {
        carrito.setUltimaActualizacion(LocalDateTime.now());
        carrito.setFinalizado(false);
        return carritoRepository.save(carrito);
    }

    @Override
    public Optional<Carrito> obtenerCarritoActivoPorCliente(String clienteId) {
        return carritoRepository.findByClienteId(clienteId);
    }

    @Override
    public Carrito agregarItem(Long carritoId, ItemCarrito item) {
        Carrito carrito = carritoRepository.findById(carritoId)
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        item.setCarrito(carrito);
        itemCarritoRepository.save(item);
        return carritoRepository.findById(carritoId).get();
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

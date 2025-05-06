package com.microservice.sales.service;

import java.util.Optional;

import com.microservice.sales.model.Carrito;
import com.microservice.sales.model.ItemCarrito;

public interface CarritoService {
    Carrito crearCarrito(Carrito carrito);
    Optional<Carrito> obtenerCarritoActivoPorCliente(String clienteId);
    Carrito agregarItem(Long carritoId, ItemCarrito item);
    void eliminarItem(Long itemId);
    void finalizarCarrito(Long carritoId);
}

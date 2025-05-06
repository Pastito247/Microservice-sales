package com.microservice.sales.service;

import java.util.List;
import java.util.Optional;

import com.microservice.sales.model.Carrito;
import com.microservice.sales.model.Orden;

public interface OrdenService {
    Orden crearOrdenDesdeCarrito(Carrito carrito);
    Optional<Orden> obtenerPorCodigo(String codigo);
    List<Orden> obtenerPorCliente(String clienteId);
    List<Orden> listarOrdenesPorCliente(String clienteId);
}

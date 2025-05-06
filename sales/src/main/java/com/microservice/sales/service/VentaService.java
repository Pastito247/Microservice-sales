package com.microservice.sales.service;

import java.util.List;
import java.util.Optional;

import com.microservice.sales.model.Venta;

public interface VentaService {

    Venta crearVenta(Venta venta);
    Optional<Venta> buscarVentaPorId(Long id);
    List<Venta> listarVentas();
    List<Venta> buscarPorCliente(String cliente);
    void eliminarVenta(Long id);
}

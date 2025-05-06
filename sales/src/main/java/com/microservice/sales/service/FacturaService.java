package com.microservice.sales.service;

import java.util.Optional;

import com.microservice.sales.model.Factura;
import com.microservice.sales.model.Venta;

public interface FacturaService {
    Factura generarFactura(Venta venta);
    Optional<Factura> obtenerPorNumero(String numero);
}

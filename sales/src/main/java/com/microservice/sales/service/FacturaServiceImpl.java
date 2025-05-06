package com.microservice.sales.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.microservice.sales.model.Factura;
import com.microservice.sales.model.Venta;
import com.microservice.sales.repository.FacturaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;

    @Override
    public Factura generarFactura(Venta venta) {
        Factura factura = new Factura();
        factura.setNumero("F-" + UUID.randomUUID().toString().substring(0, 8));
        factura.setFechaEmision(LocalDateTime.now());
        factura.setMontoTotal(venta.getTotal());
        factura.setRutCliente(venta.getCliente());
        factura.setRazonSocial("Cliente " + venta.getCliente());
        return facturaRepository.save(factura);
    }

    @Override
    public Optional<Factura> obtenerPorNumero(String numero) {
        return facturaRepository.findByNumero(numero);
    }
}

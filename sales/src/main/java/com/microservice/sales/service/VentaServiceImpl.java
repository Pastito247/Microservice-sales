package com.microservice.sales.service;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.microservice.sales.client.LogisticaClient;
import com.microservice.sales.dto.EnvioRequest;
import com.microservice.sales.model.Venta;
import com.microservice.sales.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService{
    private final VentaRepository ventaRepository;

    private final LogisticaClient logisticaClient;

    @Override
    public Venta crearVenta(Venta venta) {
        Venta nueva = ventaRepository.save(venta);
        
        // Crear solicitud para logística
        EnvioRequest envio = new EnvioRequest();
        envio.setCliente(nueva.getCliente());
        envio.setEstado("EN PREPARACIÓN");
        envio.setFechaCreacion(LocalDate.now());
        envio.setUbicacionActual("Bodega principal"); // o null si se asigna en logística
        logisticaClient.crearEnvioParaVenta(envio);
        
        return nueva;
    }

    @Override
    public Optional<Venta> buscarVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }

    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public List<Venta> buscarPorCliente(String cliente) {
        return ventaRepository.findByCliente(cliente);
    }

    @Override
    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }
}

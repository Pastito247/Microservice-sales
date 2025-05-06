package com.microservice.sales.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.microservice.sales.model.Venta;
import com.microservice.sales.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService{
    private final VentaRepository ventaRepository;

    @Override
    public Venta crearVenta(Venta venta){
        venta.setFecha(LocalDateTime.now());
        return ventaRepository.save(venta);
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

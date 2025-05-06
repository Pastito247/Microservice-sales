package com.microservice.sales.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.microservice.sales.model.Carrito;
import com.microservice.sales.model.ItemOrden;
import com.microservice.sales.model.Orden;
import com.microservice.sales.repository.OrdenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;

    @Override
    public Orden crearOrdenDesdeCarrito(Carrito carrito) {
        Orden orden = new Orden();
        orden.setClienteId(carrito.getClienteId());
        orden.setFecha(LocalDateTime.now());
        orden.setEstado("PENDIENTE");
        orden.setCodigoSeguimiento(UUID.randomUUID().toString());

        List<ItemOrden> items = carrito.getItems().stream().map(item -> {
            ItemOrden io = new ItemOrden();
            io.setProductoId(item.getProductoId());
            io.setNombreProducto(item.getNombreProducto());
            io.setCantidad(item.getCantidad());
            io.setPrecioUnitario(item.getPrecioUnitario());
            return io;
        }).collect(Collectors.toList());

        orden.setItems(items);
        orden.setTotal(items.stream().mapToDouble(i -> i.getCantidad() * i.getPrecioUnitario()).sum());

        return ordenRepository.save(orden);
    }

    @Override
    public Optional<Orden> obtenerPorCodigo(String codigo) {
        return ordenRepository.findByCodigoSeguimiento(codigo);
    }

    @Override
    public List<Orden> obtenerPorCliente(String clienteId) {
        return ordenRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Orden> listarOrdenesPorCliente(String clienteId) {
        return ordenRepository.findByClienteId(clienteId);
    }

}

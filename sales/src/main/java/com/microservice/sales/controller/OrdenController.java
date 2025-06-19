package com.microservice.sales.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.microservice.sales.model.Carrito;
import com.microservice.sales.model.Orden;
import com.microservice.sales.service.CarritoService;
import com.microservice.sales.service.OrdenService;


@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor

public class OrdenController {

    private final OrdenService ordenService;
    private final CarritoService carritoService;

    @PostMapping("/desde-carrito/{carritoId}")
    public ResponseEntity<Orden> crearOrdenDesdeCarrito(@PathVariable Long carritoId) {
        Carrito carrito = carritoService.obtenerCarritoActivoPorCliente("cliente")
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        Orden orden = ordenService.crearOrdenDesdeCarrito(carrito);
        return ResponseEntity.ok(orden);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Orden> obtenerPorCodigo(@PathVariable String codigo) {
        return ordenService.obtenerPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Orden> listarPorCliente(@PathVariable String clienteId) {
        return ordenService.listarOrdenesPorCliente(clienteId);
    }
}

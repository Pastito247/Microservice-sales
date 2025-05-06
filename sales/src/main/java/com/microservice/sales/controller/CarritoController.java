package com.microservice.sales.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.sales.model.Carrito;
import com.microservice.sales.model.ItemCarrito;
import com.microservice.sales.service.CarritoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor

public class CarritoController {

    private final CarritoService carritoService;

    @PostMapping
    public Carrito crearCarrito(@RequestBody Carrito carrito) {
        return carritoService.crearCarrito(carrito);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Carrito> obtenerCarritoActivo(@PathVariable String clienteId) {
        return carritoService.obtenerCarritoActivoPorCliente(clienteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{carritoId}/item")
    public Carrito agregarItem(@PathVariable Long carritoId, @RequestBody ItemCarrito item) {
        return carritoService.agregarItem(carritoId, item);
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long itemId) {
        carritoService.eliminarItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{carritoId}/finalizar")
    public ResponseEntity<Void> finalizarCarrito(@PathVariable Long carritoId) {
        carritoService.finalizarCarrito(carritoId);
        return ResponseEntity.ok().build();
    }
}

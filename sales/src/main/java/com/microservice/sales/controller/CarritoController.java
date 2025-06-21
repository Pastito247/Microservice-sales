package com.microservice.sales.controller;

import com.microservice.sales.model.Carrito;
import com.microservice.sales.model.ItemCarrito;
import com.microservice.sales.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
@Tag(name = "Carrito", description = "Operaciones relacionadas con el carrito de compras")
public class CarritoController {

    private final CarritoService carritoService;

    @Operation(summary = "Crear un nuevo carrito de compras")
    @PostMapping
    public Carrito crearCarrito(@RequestBody Carrito carrito) {
        return carritoService.crearCarrito(carrito);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Obtener el carrito activo de un cliente (con enlaces HATEOAS)")
    public ResponseEntity<EntityModel<Carrito>> obtenerCarritoActivo(@PathVariable String clienteId) {
        return carritoService.obtenerCarritoActivoPorCliente(clienteId)
                .map(carrito -> {
                    EntityModel<Carrito> recurso = EntityModel.of(carrito);
                    recurso.add(linkTo(methodOn(CarritoController.class).obtenerCarritoActivo(clienteId)).withSelfRel());
                    recurso.add(linkTo(methodOn(CarritoController.class).finalizarCarrito(carrito.getId())).withRel("finalizar"));
                    recurso.add(linkTo(methodOn(CarritoController.class).agregarItem(carrito.getId(), new ItemCarrito())).withRel("agregar-item"));
                    return ResponseEntity.ok(recurso);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{carritoId}/item")
    @Operation(summary = "Agregar un item al carrito")
    public Carrito agregarItem(@PathVariable Long carritoId, @RequestBody ItemCarrito item) {
        return carritoService.agregarItem(carritoId, item);
    }

    @DeleteMapping("/item/{itemId}")
    @Operation(summary = "Eliminar un item del carrito")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long itemId) {
        carritoService.eliminarItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{carritoId}/finalizar")
    @Operation(summary = "Finalizar el carrito de compras")
    public ResponseEntity<Void> finalizarCarrito(@PathVariable Long carritoId) {
        carritoService.finalizarCarrito(carritoId);
        return ResponseEntity.ok().build();
    }
}

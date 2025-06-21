package com.microservice.sales.controller;

import com.microservice.sales.model.Carrito;
import com.microservice.sales.model.Orden;
import com.microservice.sales.service.CarritoService;
import com.microservice.sales.service.OrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
@Tag(name = "Órdenes", description = "Operaciones relacionadas con órdenes de compra")
public class OrdenController {

    private final OrdenService ordenService;
    private final CarritoService carritoService;

    @PostMapping("/desde-carrito/{carritoId}")
    @Operation(summary = "Crear una orden desde un carrito de compras (respuesta con HATEOAS)")
    public ResponseEntity<EntityModel<Orden>> crearOrdenDesdeCarrito(@PathVariable Long carritoId) {
        Carrito carrito = carritoService.obtenerCarritoActivoPorCliente("cliente")
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Orden orden = ordenService.crearOrdenDesdeCarrito(carrito);

        EntityModel<Orden> recurso = EntityModel.of(orden);
        recurso.add(linkTo(methodOn(OrdenController.class).crearOrdenDesdeCarrito(carritoId)).withSelfRel());
        recurso.add(linkTo(methodOn(OrdenController.class).obtenerPorCodigo(orden.getCodigoSeguimiento())).withRel("consultar-por-codigo"));
        recurso.add(linkTo(methodOn(OrdenController.class).listarPorCliente(orden.getClienteId())).withRel("ordenes-del-cliente"));

        return ResponseEntity.ok(recurso);
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Obtener una orden por código (respuesta con HATEOAS)")
    public ResponseEntity<EntityModel<Orden>> obtenerPorCodigo(@PathVariable String codigo) {
        return ordenService.obtenerPorCodigo(codigo)
                .map(orden -> {
                    EntityModel<Orden> recurso = EntityModel.of(orden);
                    recurso.add(linkTo(methodOn(OrdenController.class).obtenerPorCodigo(codigo)).withSelfRel());
                    recurso.add(linkTo(methodOn(OrdenController.class).listarPorCliente(orden.getClienteId())).withRel("ordenes-del-cliente"));
                    return ResponseEntity.ok(recurso);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar órdenes por cliente")
    public List<Orden> listarPorCliente(@PathVariable String clienteId) {
        return ordenService.listarOrdenesPorCliente(clienteId);
    }
}

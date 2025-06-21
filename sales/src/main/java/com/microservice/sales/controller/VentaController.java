package com.microservice.sales.controller;

import com.microservice.sales.model.Venta;
import com.microservice.sales.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Tag(name = "Ventas", description = "Operaciones relacionadas con ventas")
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    @Operation(summary = "Crear una nueva venta")
    public ResponseEntity<EntityModel<Venta>> crearVenta(@RequestBody Venta venta) {
        Venta nueva = ventaService.crearVenta(venta);
        EntityModel<Venta> recurso = EntityModel.of(nueva);
        recurso.add(linkTo(methodOn(VentaController.class).obtenerVenta(nueva.getId())).withRel("ver-venta"));
        recurso.add(linkTo(methodOn(VentaController.class).buscarPorCliente(nueva.getCliente())).withRel("ventas-del-cliente"));
        return ResponseEntity.ok(recurso);
    }

    @GetMapping
    @Operation(summary = "Listar todas las ventas")
    public List<Venta> listarVentas() {
        return ventaService.listarVentas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una venta por ID (respuesta con HATEOAS)")
    public ResponseEntity<EntityModel<Venta>> obtenerVenta(@PathVariable Long id) {
        return ventaService.buscarVentaPorId(id)
                .map(venta -> {
                    EntityModel<Venta> recurso = EntityModel.of(venta);
                    recurso.add(linkTo(methodOn(VentaController.class).obtenerVenta(id)).withSelfRel());
                    recurso.add(linkTo(methodOn(VentaController.class).buscarPorCliente(venta.getCliente())).withRel("ventas-del-cliente"));
                    return ResponseEntity.ok(recurso);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una venta por ID")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{cliente}")
    @Operation(summary = "Buscar ventas por cliente")
    public List<Venta> buscarPorCliente(@PathVariable String cliente) {
        return ventaService.buscarPorCliente(cliente);
    }
}

package com.microservice.sales.controller;

import com.microservice.sales.model.Factura;
import com.microservice.sales.model.Venta;
import com.microservice.sales.service.FacturaService;
import com.microservice.sales.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
@Tag(name = "Facturas", description = "Operaciones relacionadas con facturas de ventas")
public class FacturaController {

    private final FacturaService facturaService;
    private final VentaService ventaService;

    @PostMapping("/generar/{ventaId}")
    @Operation(summary = "Generar una factura a partir de una venta (respuesta con HATEOAS)")
    public ResponseEntity<EntityModel<Factura>> generarFactura(@PathVariable Long ventaId) {
        Venta venta = ventaService.buscarVentaPorId(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        Factura factura = facturaService.generarFactura(venta);
        EntityModel<Factura> recurso = EntityModel.of(factura);

        recurso.add(linkTo(methodOn(FacturaController.class).generarFactura(ventaId)).withSelfRel());
        recurso.add(linkTo(methodOn(FacturaController.class).obtenerPorNumero(factura.getNumero())).withRel("obtener-por-numero"));

        return ResponseEntity.ok(recurso);
    }

    @GetMapping("/{numero}")
    @Operation(summary = "Obtener una factura por número (respuesta con HATEOAS)")
    public ResponseEntity<EntityModel<Factura>> obtenerPorNumero(@PathVariable String numero) {
        return facturaService.obtenerPorNumero(numero)
                .map(factura -> {
                    EntityModel<Factura> recurso = EntityModel.of(factura);
                    recurso.add(linkTo(methodOn(FacturaController.class).obtenerPorNumero(numero)).withSelfRel());
                    return ResponseEntity.ok(recurso);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

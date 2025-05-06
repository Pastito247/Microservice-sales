package com.microservice.sales.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.microservice.sales.model.Factura;
import com.microservice.sales.model.Venta;
import com.microservice.sales.service.FacturaService;
import com.microservice.sales.service.VentaService;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor

public class FacturaController {

    private final FacturaService facturaService;
    private final VentaService ventaService;

    @PostMapping("/generar/{ventaId}")
    public ResponseEntity<Factura> generarFactura(@PathVariable Long ventaId) {
        Venta venta = ventaService.buscarVentaPorId(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        Factura factura = facturaService.generarFactura(venta);
        return ResponseEntity.ok(factura);
    }

    @GetMapping("/{numero}")
    public ResponseEntity<Factura> obtenerPorNumero(@PathVariable String numero) {
        return facturaService.obtenerPorNumero(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

package com.microservice.sales.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.microservice.sales.model.Cupon;
import com.microservice.sales.service.CuponService;

@RestController
@RequestMapping("/api/cupones")
@RequiredArgsConstructor

public class CuponController {

    private final CuponService cuponService;

    @GetMapping("/validar/{codigo}")
    public ResponseEntity<Cupon> validarCupon(@PathVariable String codigo) {
        return cuponService.validarCupon(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

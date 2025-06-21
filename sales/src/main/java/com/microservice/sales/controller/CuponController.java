package com.microservice.sales.controller;

import com.microservice.sales.model.Cupon;
import com.microservice.sales.service.CuponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/cupones")
@RequiredArgsConstructor
@Tag(name = "Cupones", description = "Operaciones relacionadas con cupones de descuento")
public class CuponController {

    private final CuponService cuponService;

    @GetMapping("/validar/{codigo}")
    @Operation(summary = "Validar un cupón por su código (respuesta con enlaces HATEOAS)")
    public ResponseEntity<EntityModel<Cupon>> validarCupon(@PathVariable String codigo) {
        return cuponService.validarCupon(codigo)
                .map(cupon -> {
                    EntityModel<Cupon> recurso = EntityModel.of(cupon);
                    recurso.add(linkTo(methodOn(CuponController.class).validarCupon(codigo)).withSelfRel());
                    return ResponseEntity.ok(recurso);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

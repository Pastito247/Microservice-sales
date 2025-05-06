package com.microservice.sales.service;

import java.util.Optional;

import com.microservice.sales.model.Cupon;

public interface CuponService {
    Optional<Cupon> validarCupon(String codigo);
}

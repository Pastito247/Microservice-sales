package com.microservice.sales.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.microservice.sales.model.Cupon;
import com.microservice.sales.repository.CuponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class CuponServiceImpl implements CuponService {

    private final CuponRepository cuponRepository;

    @Override
    public Optional<Cupon> validarCupon(String codigo) {
        return cuponRepository.findByCodigo(codigo)
                .filter(c -> !c.getFechaExpiracion().isBefore(LocalDateTime.now()));
    }
}

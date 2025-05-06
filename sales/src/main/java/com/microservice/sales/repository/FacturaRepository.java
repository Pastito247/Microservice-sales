package com.microservice.sales.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.sales.model.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByNumero(String numero);
}

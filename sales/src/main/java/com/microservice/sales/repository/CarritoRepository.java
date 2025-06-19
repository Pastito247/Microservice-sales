package com.microservice.sales.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.sales.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByClienteId(String clienteId);

    Optional<Carrito> findByClienteIdAndFinalizadoFalse(String clienteId);

}

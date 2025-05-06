package com.microservice.sales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.sales.model.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Long>{
    Optional<Orden> findByCodigoSeguimiento(String codigoSeguimiento);
    List<Orden> findByClienteId(Long clienteId);
}

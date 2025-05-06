package com.microservice.sales.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.sales.model.Cupon;

public interface CuponRepository extends JpaRepository<Cupon,Long>{
    Optional<Cupon> findByCodigo(String codigo); //Activo = true :b
}

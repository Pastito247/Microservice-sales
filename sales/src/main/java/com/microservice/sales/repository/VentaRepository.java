package com.microservice.sales.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.sales.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByCliente(String cliente);
    List<Venta> findBySucursal(String sucursal);
    List<Venta> findByEmpleado(String empleado);
    List<Venta> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}

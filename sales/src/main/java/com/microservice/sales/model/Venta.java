package com.microservice.sales.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    
    private String tipo; // Online o Presencial

    private Double total;

    private String metodoPago; // Efectivo, Tarjeta de crédito, etc.

    private String sucursal;

    private String empleado;

    private String cliente;

    @OneToOne(cascade = CascadeType.ALL)
    private Factura factura;

    private String direccionEnvio;

    private String producto;

}

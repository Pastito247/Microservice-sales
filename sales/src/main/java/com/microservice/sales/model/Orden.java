package com.microservice.sales.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Orden {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoSeguimiento;

    private LocalDate fecha;

    private String estado; // Pendiente, Enviado, Entregado, Cancelado

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemOrden> itemsOrden = new ArrayList<>();

    private Double totalOrden;

    private String clienteId;
}

package com.microservice.sales.model;

import java.time.LocalDateTime;
import java.util.List;

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

    private LocalDateTime fecha;

    private String estado; // Pendiente, Enviado, Entregado, Cancelado

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemOrden> items;

    private Double total;

    private String clienteId;
}

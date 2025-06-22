package com.microservice.sales.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EnvioRequest {
    private Long ventaId;
    private String direccionDestino;
    private LocalDate fechaEstimada;
    private String estado;
}

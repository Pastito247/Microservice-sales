package com.microservice.sales.dto;

import lombok.Data;

@Data
public class EnvioResponse {
    private Long id;
    private String estado;
    private String direccionDestino;
}

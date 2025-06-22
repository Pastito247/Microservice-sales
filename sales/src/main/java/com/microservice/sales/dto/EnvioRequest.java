package com.microservice.sales.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EnvioRequest {
    private String cliente;
    private String estado;
    private LocalDate fechaCreacion;
    private String ubicacionActual; // opcional
}

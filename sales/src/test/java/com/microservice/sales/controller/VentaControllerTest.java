package com.microservice.sales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.sales.model.Venta;
import com.microservice.sales.service.VentaService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VentaController.class)
class VentaControllerTest {

    @Autowired private MockMvc mvc;
    @MockBean private VentaService ventaService;
    @Autowired private ObjectMapper mapper;

    @Test void testListarVentas() throws Exception {
        Venta v = new Venta(); v.setId(5L);
        when(ventaService.listarVentas()).thenReturn(List.of(v));

        mvc.perform(get("/api/ventas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(5));
    }

    @Test void testCrearVentaApi() throws Exception {
        Venta input = new Venta();
        input.setCliente("cli");
        input.setTotal(200.0);

        Venta saved = new Venta();
        saved.setId(10L);
        saved.setCliente("cli");
        saved.setTotal(200.0);

        when(ventaService.crearVenta(any())).thenReturn(saved);

        mvc.perform(post("/api/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(10));
    }

}
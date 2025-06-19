package com.microservice.sales.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.microservice.sales.model.Venta;
import com.microservice.sales.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

public class VentaServiceTest {

    @Mock private VentaRepository ventaRepo;
    @InjectMocks private VentaServiceImpl ventaService;

    @BeforeEach void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test void testCrearVenta() {
        Venta input = new Venta();
        input.setCliente("cli");
        input.setTotal(100.0);

        Venta saved = new Venta();
        saved.setId(1L);
        saved.setCliente("cli");
        saved.setTotal(100.0);

        when(ventaRepo.save(any())).thenReturn(saved);

        Venta result = ventaService.crearVenta(input);

        assertThat(result.getId()).isEqualTo(1L);
        verify(ventaRepo).save(input);
    }

    @Test void testObtenerVentaPorId_existente() {
        Venta v = new Venta(); v.setId(2L);
        when(ventaRepo.findById(2L)).thenReturn(Optional.of(v));

        Optional<Venta> op = ventaService.buscarVentaPorId(2L);
        assertThat(op).isPresent();
        assertThat(op.get().getId()).isEqualTo(2L);
    }

    @Test void testObtenerVentaPorId_noExiste() {
        when(ventaRepo.findById(99L)).thenReturn(Optional.empty());
        assertThat(ventaService.buscarVentaPorId(99L)).isEmpty();
    }
}

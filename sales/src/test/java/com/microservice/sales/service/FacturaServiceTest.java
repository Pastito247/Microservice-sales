package com.microservice.sales.service;

import com.microservice.sales.model.Factura;
import com.microservice.sales.model.Venta;
import com.microservice.sales.repository.FacturaRepository;
import com.microservice.sales.service.FacturaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private FacturaServiceImpl facturaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerarFactura() {
        Venta venta = new Venta();
        venta.setCliente("Juan");
        venta.setTotal(10000.0);

        Factura f = new Factura();
        f.setNumero("F001");

        when(facturaRepository.save(any())).thenReturn(f);

        Factura resultado = facturaService.generarFactura(venta);

        assertThat(resultado.getNumero()).isEqualTo("F001");
        verify(facturaRepository).save(any());
    }

    @Test
    void testObtenerPorNumero() {
        Factura f = new Factura(); f.setNumero("X123");
        when(facturaRepository.findByNumero("X123"))
            .thenReturn(Optional.of(f));

        Optional<Factura> resultado = facturaService.obtenerPorNumero("X123");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNumero()).isEqualTo("X123");
    }
}

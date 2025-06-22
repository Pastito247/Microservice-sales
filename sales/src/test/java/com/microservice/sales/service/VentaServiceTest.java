package com.microservice.sales.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.microservice.sales.client.LogisticaClient;
import com.microservice.sales.dto.EnvioResponse;
import com.microservice.sales.model.Venta;
import com.microservice.sales.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private LogisticaClient logisticaClient;

    @InjectMocks
    private VentaServiceImpl ventaService;

    @Test
    void testCrearVenta() {
        Venta venta = new Venta();
        venta.setCliente("cliente1");
        venta.setDireccionEnvio("Calle Falsa 123");

        when(ventaRepository.save(any())).thenReturn(venta);
        when(logisticaClient.crearEnvioParaVenta(any())).thenReturn(new EnvioResponse());

        Venta resultado = ventaService.crearVenta(venta);

        assertThat(resultado).isNotNull();
        verify(ventaRepository).save(venta);
        verify(logisticaClient).crearEnvioParaVenta(any());
    }
}

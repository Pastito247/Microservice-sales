package com.microservice.sales.service;

import com.microservice.sales.model.Cupon;
import com.microservice.sales.repository.CuponRepository;
import com.microservice.sales.service.CuponServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CuponServiceTest {

    @Mock
    private CuponRepository cuponRepository;

    @InjectMocks
    private CuponServiceImpl cuponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidarCupon_valido() {
        Cupon cupon = new Cupon();
        cupon.setCodigo("DESC10");
        cupon.setFechaExpiracion(LocalDateTime.now().plusDays(5));
        cupon.setActivo(true);

        when(cuponRepository.findByCodigo("DESC10"))
            .thenReturn(Optional.of(cupon));

        Optional<Cupon> resultado = cuponService.validarCupon("DESC10");

        assertThat(resultado).isPresent();
    }

    @Test
    void testValidarCupon_expirado() {
        Cupon cupon = new Cupon();
        cupon.setCodigo("DESC20");
        cupon.setFechaExpiracion(LocalDateTime.now().minusDays(1));
        cupon.setActivo(true);

        when(cuponRepository.findByCodigo("DESC20"))
            .thenReturn(Optional.of(cupon));

        Optional<Cupon> resultado = cuponService.validarCupon("DESC20");

        assertThat(resultado).isEmpty();
    }

    @Test
    void testValidarCupon_noExiste() {
        when(cuponRepository.findByCodigo("INEXISTENTE"))
            .thenReturn(Optional.empty());

        assertThat(cuponService.validarCupon("INEXISTENTE")).isEmpty();
    }
}

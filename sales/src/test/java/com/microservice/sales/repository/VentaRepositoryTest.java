package com.microservice.sales.repository;

import com.microservice.sales.model.Venta;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest

public class VentaRepositoryTest {

        @Autowired private VentaRepository ventaRepo;

    @BeforeEach void seed() {
        ventaRepo.save(new Venta(null, LocalDateTime.now().minusDays(1), "ONLINE", 50.0, "Tarjeta", "S1", "E1", "C1", null));
        ventaRepo.save(new Venta(null, LocalDateTime.now().minusDays(2), "FISICA", 150.0, "Efectivo", "S1", "E2", "C2", null));
    }

    @Test void testFindByCliente() {
        List<Venta> list = ventaRepo.findByCliente("C1");
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getTotal()).isEqualTo(50.0);
    }

    @Test void testFindByFechaBetween() {
        LocalDateTime desde = LocalDateTime.now().minusDays(3);
        LocalDateTime hasta = LocalDateTime.now();
        List<Venta> list = ventaRepo.findByFechaBetween(desde, hasta);
        assertThat(list.size()).isGreaterThanOrEqualTo(2);
    }
}

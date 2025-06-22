package com.microservice.sales.repository;

import com.microservice.sales.model.Venta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VentaRepositoryTest {

    @Autowired
    private VentaRepository ventaRepository;

    @Test
    void testFindByCliente() {
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setCliente("cliente_test");
        venta.setTotal(5000.0);
        venta.setMetodoPago("Débito");
        venta.setDireccionEnvio("Calle Falsa 123");
        venta.setProducto("Producto Test");
        venta.setEmpleado("Empleado Test");
        venta.setSucursal("Sucursal Test");
        venta.setFactura(null); // si es requerido

        ventaRepository.save(venta);

        List<Venta> resultados = ventaRepository.findByCliente("cliente_test");

        assertThat(resultados).isNotEmpty();
        assertThat(resultados.get(0).getCliente()).isEqualTo("cliente_test");
    }

    @Test
    void testFindByFechaBetween() {
        Venta venta1 = new Venta();
        venta1.setFecha(LocalDateTime.now().minusDays(2));
        venta1.setCliente("clienteX");
        venta1.setTotal(3000.0);
        venta1.setMetodoPago("Crédito");
        venta1.setDireccionEnvio("Av. Siempre Viva");
        venta1.setProducto("Test Producto");
        venta1.setEmpleado("Empleado 1");
        venta1.setSucursal("Sucursal 1");
        venta1.setFactura(null);

        Venta venta2 = new Venta();
        venta2.setFecha(LocalDateTime.now().minusDays(1));
        venta2.setCliente("clienteX");
        venta2.setTotal(3500.0);
        venta2.setMetodoPago("Transferencia");
        venta2.setDireccionEnvio("Calle 456");
        venta2.setProducto("Producto 2");
        venta2.setEmpleado("Empleado 2");
        venta2.setSucursal("Sucursal 2");
        venta2.setFactura(null);

        ventaRepository.save(venta1);
        ventaRepository.save(venta2);

        List<Venta> resultados = ventaRepository.findByFechaBetween(
            LocalDateTime.now().minusDays(3),
            LocalDateTime.now()
        );

        assertThat(resultados).hasSizeGreaterThanOrEqualTo(2);
    }
}

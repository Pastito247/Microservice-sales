package com.microservice.sales.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.microservice.sales.model.Carrito;
import com.microservice.sales.model.Cupon;
import com.microservice.sales.model.Factura;
import com.microservice.sales.model.Orden;
import com.microservice.sales.model.Venta;
import com.microservice.sales.repository.CarritoRepository;
import com.microservice.sales.repository.CuponRepository;
import com.microservice.sales.repository.FacturaRepository;
import com.microservice.sales.repository.OrdenRepository;
import com.microservice.sales.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class DataSeeder implements CommandLineRunner {

    private final VentaRepository ventaRepository;
    private final CarritoRepository carritoRepository;
    private final OrdenRepository ordenRepository;
    private final FacturaRepository facturaRepository;
    private final CuponRepository cuponRepository;

    @Override
    public void run(String... args) throws Exception {

        // 1. Crear Cupones
        for (int i = 1; i <= 5; i++) {
            Cupon cupon = new Cupon();
            cupon.setCodigo("DESC" + i);
            cupon.setDescuentoPorcentaje(5.0 * i);
            cupon.setFechaExpiracion(LocalDateTime.now().plusDays(30));
            cupon.setActivo(true);
            cuponRepository.save(cupon);
        }

        // 2. Crear Ventas
        for (int i = 1; i <= 5; i++) {
            Venta venta = new Venta();
            venta.setCliente("cliente" + i);
            venta.setEmpleado("empleado" + i);
            venta.setFecha(LocalDateTime.now().minusDays(i));
            venta.setMetodoPago("Tarjeta");
            venta.setSucursal("Sucursal " + i);
            venta.setTipo(i % 2 == 0 ? "ONLINE" : "FISICA");
            venta.setTotal(10000.0 + (i * 1000));
            ventaRepository.save(venta);
        }

        // 3. Crear Carritos
        for (int i = 1; i <= 5; i++) {
            Carrito carrito = new Carrito();
            carrito.setClienteId("cliente" + i);
            carrito.setUltimaActualizacion(LocalDateTime.now().minusMinutes(i * 10));
            carrito.setFinalizado(false);
            carritoRepository.save(carrito);
        }

        // 4. Crear Órdenes
        for (int i = 1; i <= 5; i++) {
            Orden orden = new Orden();
            orden.setClienteId("cliente" + i);
            orden.setCodigoSeguimiento("ORD" + i);
            orden.setFecha(LocalDateTime.now().minusDays(i));
            orden.setEstado("PAGADA");
            orden.setTotal(20000.0 + (i * 1500));
            ordenRepository.save(orden);
        }

        // 5. Crear Facturas
        for (int i = 1; i <= 5; i++) {
            Factura factura = new Factura();
            factura.setNumero("F-" + i);
            factura.setFechaEmision(LocalDateTime.now().minusDays(i));
            factura.setMontoTotal(25000.0 + (i * 2000));
            factura.setRutCliente("1234567" + i + "-K");
            factura.setRazonSocial("Cliente " + i);
            facturaRepository.save(factura);
        }

        System.out.println("✅ Base de datos poblada con 5 registros por entidad.");
    }
}

package com.microservice.sales.service;

import com.microservice.sales.model.Carrito;
import com.microservice.sales.model.ItemCarrito;
import com.microservice.sales.model.ItemOrden;
import com.microservice.sales.model.Orden;
import com.microservice.sales.repository.OrdenRepository;
import com.microservice.sales.service.OrdenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrdenServiceTest {

    @Mock
    private OrdenRepository ordenRepository;

    @InjectMocks
    private OrdenServiceImpl ordenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   @Test
    void testCrearOrdenDesdeCarrito() {
        Carrito carrito = new Carrito();
        carrito.setClienteId("cliente1");

        // Usa ItemCarrito, no ItemOrden
        ItemCarrito item = new ItemCarrito();
        item.setProductoId(1L);
        item.setNombreProducto("Producto test");
        item.setCantidad(2);
        item.setPrecioUnitario(1000.0);

        carrito.setItems(List.of(item));  // ✅ tipo correcto

        Orden orden = new Orden();
        orden.setId(1L);
        orden.setClienteId("cliente1");

        when(ordenRepository.save(any())).thenReturn(orden);

        Orden resultado = ordenService.crearOrdenDesdeCarrito(carrito);

        assertThat(resultado.getClienteId()).isEqualTo("cliente1");
        verify(ordenRepository).save(any());
    }
    @Test
    void testObtenerOrdenPorCodigo() {
        Orden orden = new Orden();
        orden.setCodigoSeguimiento("ABC123");

        when(ordenRepository.findByCodigoSeguimiento("ABC123"))
            .thenReturn(Optional.of(orden));

        Optional<Orden> resultado = ordenService.obtenerPorCodigo("ABC123");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCodigoSeguimiento()).isEqualTo("ABC123");
    }

    @Test
    void testListarOrdenesPorCliente() {
        Orden o1 = new Orden(); o1.setClienteId("clienteX");
        when(ordenRepository.findByClienteId("clienteX"))
            .thenReturn(List.of(o1));

        List<Orden> lista = ordenService.listarOrdenesPorCliente("clienteX");

        assertThat(lista).hasSize(1);
    }
}

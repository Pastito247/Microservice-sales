package com.microservice.sales.service;

import com.microservice.sales.model.Carrito;
import com.microservice.sales.model.ItemCarrito;
import com.microservice.sales.repository.CarritoRepository;
import com.microservice.sales.repository.ItemCarritoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private ItemCarritoRepository itemCarritoRepository;

    @InjectMocks
    private CarritoServiceImpl carritoService;

    @BeforeEach
    void setUp() {
        // Resetear mocks antes de cada prueba
        reset(carritoRepository, itemCarritoRepository);
    }

    @Test
    void testCrearCarrito() {
        Carrito c = new Carrito();
        c.setClienteId("cliente1");
        c.setUltimaActualizacion(LocalDateTime.now());
        c.setFinalizado(false);

        // Asegurar que el mock devuelve el carrito
        when(carritoRepository.save(any(Carrito.class))).thenReturn(c);

        Carrito guardado = carritoService.crearCarrito(c);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getClienteId()).isEqualTo("cliente1");
        verify(carritoRepository).save(c);
    }

    @Test
    void testObtenerCarritoActivoPorCliente_existente() {
        String clienteId = "clienteX";
        Carrito carrito = new Carrito();
        carrito.setClienteId(clienteId);
        carrito.setFinalizado(false);

        // Usar eq() con el valor específico
        when(carritoRepository.findByClienteIdAndFinalizadoFalse(eq(clienteId)))
            .thenReturn(Optional.of(carrito));

        Optional<Carrito> result = carritoService.obtenerCarritoActivoPorCliente(clienteId);

        assertThat(result)
            .isPresent()
            .hasValueSatisfying(c -> {
                assertThat(c.getClienteId()).isEqualTo(clienteId);
                assertThat(c.isFinalizado()).isFalse();
            });
        
        // Verificar que se llamó al método
        verify(carritoRepository).findByClienteIdAndFinalizadoFalse(clienteId);
    }

    @Test
    void testObtenerCarritoActivoPorCliente_noExistente() {
        String clienteId = "noExiste";

        when(carritoRepository.findByClienteIdAndFinalizadoFalse(eq(clienteId)))
            .thenReturn(Optional.empty());

        Optional<Carrito> result = carritoService.obtenerCarritoActivoPorCliente(clienteId);

        assertThat(result).isEmpty();
        // Verificar que se llamó al método
        verify(carritoRepository).findByClienteIdAndFinalizadoFalse(clienteId);
    }

    @Test
    void testAgregarItem() {
        Long carritoId = 1L;
        Carrito carrito = new Carrito();
        carrito.setId(carritoId);
        carrito.setClienteId("cliente123");
        carrito.setItems(new ArrayList<>()); // Inicializar lista

        ItemCarrito item = new ItemCarrito();
        item.setNombreProducto("Producto A");
        item.setCantidad(2);
        item.setPrecioUnitario(10.99);

        // Configurar mock para findById
        when(carritoRepository.findById(eq(carritoId))).thenReturn(Optional.of(carrito));
        
        // Configurar mock para guardar item
        when(itemCarritoRepository.save(any(ItemCarrito.class))).thenAnswer(invocation -> {
            ItemCarrito savedItem = invocation.getArgument(0);
            savedItem.setId(1L); // Simular ID generado
            return savedItem;
        });
        
        // Configurar mock para guardar carrito
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        Carrito result = carritoService.agregarItem(carritoId, item);

        assertThat(result).isNotNull();
        assertThat(result.getItems())
            .hasSize(1)
            .first()
            .satisfies(savedItem -> {
                assertThat(savedItem.getNombreProducto()).isEqualTo("Producto A");
                assertThat(savedItem.getCantidad()).isEqualTo(2);
            });
        
        verify(carritoRepository).findById(carritoId);
        verify(itemCarritoRepository).save(item);
        verify(carritoRepository).save(carrito);
    }

    @Test
    void testAgregarItem_carritoNoEncontrado() {
        Long carritoIdInexistente = 999L;
        ItemCarrito item = new ItemCarrito();

        when(carritoRepository.findById(eq(carritoIdInexistente)))
            .thenReturn(Optional.empty());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            carritoService.agregarItem(carritoIdInexistente, item);
        });
        
        verify(carritoRepository).findById(carritoIdInexistente);
        verifyNoInteractions(itemCarritoRepository);
    }
}
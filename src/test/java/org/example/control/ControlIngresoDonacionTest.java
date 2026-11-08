package org.example.control;

import org.example.model.Adoptante;
import org.example.model.Donacion;
import org.example.model.Inventario;
import org.example.model.Refugio;
import org.example.model.RepositorioDonaciones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ControlIngresoDonacionTest {
    private final Adoptante donante = new Adoptante("Ana", "Pérez", "0991234567", "ana@correo.com");
    private Refugio refugio;
    private ControlIngresoDonacion control;

    @BeforeEach
    void setUp() {
        refugio = new Refugio(1L, "Refugio Central", "Quito");
        control = new ControlIngresoDonacion(refugio, refugio.getInventario());
    }

    @Test
    void listarDonacionesPorIngresarMuestraSoloLasRecibidas() {
        Donacion recibida = donacionRecibida("Croquetas", 10);
        Donacion ingresada = donacionRecibida("Mantas", 3);
        ingresada.marcarIngresada();
        Donacion pendiente = nuevaDonacion("Correas", 2);
        RepositorioDonaciones repositorio = mock(RepositorioDonaciones.class);
        when(repositorio.listarDonaciones()).thenReturn(List.of(recibida, ingresada, pendiente));
        ControlIngresoDonacion controlConMock = new ControlIngresoDonacion(repositorio, new Inventario());

        List<Donacion> porIngresar = controlConMock.listarDonacionesPorIngresar();

        assertEquals(List.of(recibida), porIngresar);
        verify(repositorio).listarDonaciones();
    }

    @Test
    void listarDonacionesPorIngresarDevuelveListaVaciaSiTodasEstanIngresadas() {
        Donacion ingresada = donacionRecibida("Croquetas", 10);
        ingresada.marcarIngresada();
        refugio.agregarDonacion(ingresada);

        assertTrue(control.listarDonacionesPorIngresar().isEmpty());
    }

    private Donacion nuevaDonacion(String material, int cantidad) {
        Donacion donacion = new Donacion(refugio.obtenerIdDonacion(), donante, "");
        donacion.agregarMaterial(material, cantidad);
        return donacion;
    }

    private Donacion donacionRecibida(String material, int cantidad) {
        Donacion donacion = nuevaDonacion(material, cantidad);
        donacion.marcarRecibida();
        return donacion;
    }
}

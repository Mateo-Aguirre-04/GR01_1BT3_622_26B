package org.example.control;

import org.example.model.*;
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


    @Test
    void aprobarDonacionDeMaterialRegistradoSumaAlStockYLaMarcaIngresada() {
        Inventario inventario = refugio.getInventario();
        inventario.agregar(new Material("Croquetas", "kg", 20));
        Donacion donacion = registrarDonacionRecibida("Croquetas", 10, "kg");

        String confirmacion = control.aprobarIngreso(donacion.getIdDonacion());

        assertEquals(30, inventario.buscar("Croquetas").getStock());
        assertEquals(Donacion.Estado.INGRESADA, donacion.getEstado());
        assertEquals("Donación #1 ingresada al inventario. "
                + "Croquetas: cantidad ingresada 10 kg, stock actualizado 30 kg.", confirmacion);
    }

    @Test
    void aprobarDonacionDeMaterialNuevoLoCreaConSuStockInicial() {
        Inventario inventario = refugio.getInventario();
        Donacion donacion = registrarDonacionRecibida("Arena sanitaria", 2, "sacos");

        String confirmacion = control.aprobarIngreso(donacion.getIdDonacion());

        assertTrue(inventario.existe("Arena sanitaria"));
        Material arena = inventario.buscar("Arena sanitaria");
        assertEquals(2, arena.getStock());
        assertEquals("sacos", arena.getUnidad());
        assertEquals("Donación #1 ingresada al inventario. "
                + "Arena sanitaria: cantidad recibida 2 sacos, stock inicial registrado 2 sacos.", confirmacion);
    }

    private Donacion registrarDonacionRecibida(String material, int cantidad, String unidad) {
        Donacion donacion = new Donacion(refugio.obtenerIdDonacion(), donante, "");
        donacion.agregarMaterial(material, cantidad, unidad);
        donacion.marcarRecibida();
        refugio.agregarDonacion(donacion);
        return donacion;
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

package org.example.web;

import jakarta.servlet.http.HttpServletRequest;
import org.example.control.ControlRegistroDonacion;
import org.example.model.Donacion;
import org.junit.jupiter.api.BeforeEach;
import org.example.model.Refugio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InterfazDonacionTest {
    private Refugio refugio;
    private InterfazDonacion interfaz;

    @BeforeEach
    void setUp() {
        refugio = new Refugio(1L, "Refugio Central", "Quito");
        interfaz = new InterfazDonacion(new ControlRegistroDonacion(refugio));
    }

    @Test
    void enviarDonacionLeeElFormularioYRegistraLaDonacion() {
        HttpServletRequest formulario = mock(HttpServletRequest.class);
        when(formulario.getParameter("nombres")).thenReturn("Ana");
        when(formulario.getParameter("apellidos")).thenReturn("Pérez");
        when(formulario.getParameter("telefono")).thenReturn("0991234567");
        when(formulario.getParameter("correo")).thenReturn("ana@correo.com");
        when(formulario.getParameterValues("material")).thenReturn(new String[]{"Croquetas"});
        when(formulario.getParameterValues("cantidad")).thenReturn(new String[]{"5"});


        Donacion donacion = interfaz.enviarDonacion(formulario);

        verify(formulario).getParameterValues("material");
        verify(formulario).getParameterValues("cantidad");
        assertEquals(List.of(donacion), refugio.listarDonacionesPendientes());
        assertEquals(5, donacion.cantidadDe("Croquetas"));
        assertEquals("/WEB-INF/vistas/registroDonacion.jsp", interfaz.mostrarFormulario());
        assertEquals("/WEB-INF/vistas/confirmacionDonacion.jsp", interfaz.mostrarConfirmacion());
    }
}

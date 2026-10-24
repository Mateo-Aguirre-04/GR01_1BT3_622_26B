package org.example.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.control.ControlModificacionPerro;
import org.example.model.Perro;
import org.example.model.Refugio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ModificacionPerroServletTest {
    private static final String VISTA = "/WEB-INF/vistas/modificarPerro.jsp";
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private Map<String, String> parametros;
    private Map<String, Object> atributos;
    private Map<String, Object> sesion;
    private ModificacionPerroServlet servlet;
    private Perro perro;

    @BeforeEach
    void setUp() {
        Refugio refugio = new Refugio(1L, "Refugio", "Quito");
        perro = new Perro(10L, "Luna", 3, "Tranquila", "Sana", "Patio 1",
                true, LocalDate.of(2026, 10, 1), refugio);
        refugio.agregarPerro(perro);
        servlet = new ModificacionPerroServlet(new InterfazModificacionPerro(new ControlModificacionPerro(refugio)));
        parametros = new LinkedHashMap<>(Map.of("idPerro", "10", "nombre", "Sol", "edad", "4",
                "caracteristicas", "Juguetona", "estadoSalud", "En tratamiento",
                "espacioAsignado", "Patio 2", "fechaIngreso", "2026-10-02"));
        atributos = new LinkedHashMap<>();
        sesion = new LinkedHashMap<>();
        sesion.put("vistaActual", "gestor");
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/gestion-refugio");
        when(request.getParameter(anyString())).thenAnswer(inv -> parametros.get(inv.getArgument(0)));
        when(request.getRequestDispatcher(VISTA)).thenReturn(dispatcher);
        when(session.getAttribute(anyString())).thenAnswer(inv -> sesion.get(inv.getArgument(0)));
        doAnswer(inv -> { atributos.put(inv.getArgument(0), inv.getArgument(1)); return null; })
                .when(request).setAttribute(anyString(), any());
        doAnswer(inv -> { sesion.put(inv.getArgument(0), inv.getArgument(1)); return null; })
                .when(session).setAttribute(anyString(), any());
        doAnswer(inv -> { sesion.remove(inv.getArgument(0)); return null; })
                .when(session).removeAttribute(anyString());
    }

    @Test
    void getPrecargaLosDatosDelPerroSeleccionado() throws Exception {
        servlet.doGet(request, response);
        assertSame(perro, atributos.get("perro"));
        Map<?, ?> datos = (Map<?, ?>) atributos.get("datosEdicion");
        assertEquals("Luna", datos.get("nombre"));
        assertEquals("3", datos.get("edad"));
        assertEquals("2026-10-01", datos.get("fechaIngreso"));
        verify(dispatcher).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }

    @Test
    void postActualizaYRedirigeYGetMuestraConfirmacionSoloUnaVez() throws Exception {
        servlet.doPost(request, response);
        assertEquals("Sol", perro.getNombre());
        verify(request).setCharacterEncoding("UTF-8");
        verify(response).sendRedirect("/gestion-refugio/perros/modificar?idPerro=10");
        verify(dispatcher, never()).forward(any(), any());

        servlet.doGet(request, response);
        assertTrue(atributos.get("resultadoModificacion").toString().contains("correctamente"));
        assertFalse(sesion.containsKey("perroModificado"));
        atributos.clear();
        servlet.doGet(request, response);
        assertFalse(atributos.containsKey("resultadoModificacion"));
    }

    @Test
    void postInvalidoConservaDatosIngresadosYMuestraErrorSinActualizar() throws Exception {
        parametros.put("fechaIngreso", "2026-02-30");
        servlet.doPost(request, response);
        assertEquals("Luna", perro.getNombre());
        assertEquals(3, perro.getEdad());
        assertEquals(LocalDate.of(2026, 10, 1), perro.getFechaIngreso());
        assertTrue(atributos.get("errorModificacion").toString().contains("fecha de ingreso"));
        assertEquals("Sol", ((Map<?, ?>) atributos.get("datosEdicion")).get("nombre"));
        verify(dispatcher).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }

    @Test
    void postCampoObligatorioVacioIndicaCualCompletar() throws Exception {
        parametros.put("estadoSalud", "   ");
        servlet.doPost(request, response);
        assertEquals("Debe completar el campo estado de salud.", atributos.get("errorModificacion"));
        assertEquals("Luna", perro.getNombre());
        assertEquals("Sana", perro.getEstadoSalud());
        verify(dispatcher).forward(request, response);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"adoptante", "otro"})
    void getYPostSinVistaGestorRedirigenSinActualizar(String vista) throws Exception {
        sesion.put("vistaActual", vista);
        servlet.doGet(request, response);
        servlet.doPost(request, response);
        assertEquals("Luna", perro.getNombre());
        verify(response, times(2)).sendRedirect("/gestion-refugio/inicio.jsp");
        verify(request, never()).getParameter(anyString());
        verify(dispatcher, never()).forward(any(), any());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "abc", "0", "-1", "999", "999999999999999999999"})
    void idInvalidoOInexistenteEnGetYPostMuestraErrorSinFormulario(String id) throws Exception {
        parametros.put("idPerro", id);
        servlet.doGet(request, response);
        assertNotNull(atributos.get("errorModificacion"));
        assertFalse(atributos.containsKey("perro"));
        atributos.clear();
        servlet.doPost(request, response);
        assertNotNull(atributos.get("errorModificacion"));
        assertFalse(atributos.containsKey("perro"));
        assertEquals("Luna", perro.getNombre());
        verify(dispatcher, times(2)).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }
}

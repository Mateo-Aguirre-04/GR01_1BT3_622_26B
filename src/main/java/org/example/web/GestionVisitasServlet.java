package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;

import java.io.IOException;

@WebServlet("/visitas/gestionar")
public class GestionVisitasServlet extends HttpServlet {
    private static final String VISTA = "/WEB-INF/vistas/gestionarVisitas.jsp";
    private final InterfazGestionVisitas interfaz =
            new InterfazGestionVisitas(Main.getControlGestionVisita());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esGestor(request, response)) {
            return;
        }
        Object mensaje = request.getSession().getAttribute("resultadoVisita");
        if (mensaje != null) {
            request.setAttribute("resultadoVisita", mensaje);
            request.getSession().removeAttribute("resultadoVisita");
        }
        cargarPendientes(request);
        request.getRequestDispatcher(VISTA).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esGestor(request, response)) {
            return;
        }
        try {
            Long idVisita = parsearId(request.getParameter("idVisita"));
            String accion = request.getParameter("accion");
            String resultado;
            if ("aprobar".equals(accion)) {
                resultado = interfaz.aprobarVisita(idVisita);
            } else if ("rechazar".equals(accion)) {
                resultado = interfaz.rechazarVisita(idVisita);
            } else {
                throw new IllegalArgumentException("La acción seleccionada no es válida");
            }
            request.getSession().setAttribute("resultadoVisita", resultado);
            response.sendRedirect(request.getContextPath() + "/visitas/gestionar");
            return;
        } catch (IllegalArgumentException | IllegalStateException exception) {
            request.setAttribute("errorGestionVisita", exception.getMessage());
        }
        cargarPendientes(request);
        request.getRequestDispatcher(VISTA).forward(request, response);
    }

    private void cargarPendientes(HttpServletRequest request) {
        request.setAttribute("visitasPendientes", interfaz.mostrarVisitasPendientes());
    }

    private Long parsearId(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El ID de la visita es obligatorio");
        }
        try {
            long id = Long.parseLong(valor.trim());
            if (id <= 0) {
                throw new IllegalArgumentException("El ID de la visita debe ser mayor que cero");
            }
            return id;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El ID de la visita no es válido", exception);
        }
    }

    private boolean esGestor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("gestor".equals(request.getSession().getAttribute("vistaActual"))) {
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/inicio.jsp");
        return false;
    }
}

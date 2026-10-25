package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;

import java.io.IOException;

@WebServlet("/donaciones/gestionar")
public class GestionDonacionServlet extends HttpServlet {
    private final InterfazGestionDonacion interfaz =
            new InterfazGestionDonacion(Main.getControlGestionDonacion());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esGestor(request, response)) {
            return;
        }
        Object mensaje = request.getSession().getAttribute("resultadoDonacion");
        if (mensaje != null) {
            request.setAttribute("resultadoDonacion", mensaje);
            request.getSession().removeAttribute("resultadoDonacion");
        }
        String idDonacion = request.getParameter("idDonacion");
        if (idDonacion != null && !idDonacion.isBlank()) {
            try {
                request.setAttribute("donacion", interfaz.mostrarDetalle(parsearId(idDonacion)));
                request.getRequestDispatcher(interfaz.vistaDetalle()).forward(request, response);
                return;
            } catch (IllegalArgumentException exception) {
                request.setAttribute("errorDonacion", exception.getMessage());
            }
        }
        request.setAttribute("donacionesPendientes", interfaz.mostrarDonacionesPendientes());
        request.getRequestDispatcher(interfaz.vistaLista()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esGestor(request, response)) {
            return;
        }
        try {
            String resultado = interfaz.confirmarRecepcion(parsearId(request.getParameter("idDonacion")));
            request.getSession().setAttribute("resultadoDonacion", resultado);
            response.sendRedirect(request.getContextPath() + "/donaciones/gestionar");
            return;
        } catch (IllegalArgumentException | IllegalStateException exception) {
            request.setAttribute("errorDonacion", exception.getMessage());
        }
        request.setAttribute("donacionesPendientes", interfaz.mostrarDonacionesPendientes());
        request.getRequestDispatcher(interfaz.vistaLista()).forward(request, response);
    }

    private Long parsearId(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El ID de la donación es obligatorio");
        }
        try {
            return Long.parseLong(valor.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El ID de la donación no es válido", exception);
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

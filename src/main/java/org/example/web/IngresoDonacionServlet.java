package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;
import org.example.model.Donacion;

import java.io.IOException;
import java.util.List;

@WebServlet("/inventario/donaciones")
public class IngresoDonacionServlet extends HttpServlet {
    private final InterfazIngresoDonacion interfaz =
            new InterfazIngresoDonacion(Main.getControlIngresoDonacion());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esGestor(request, response)) {
            return;
        }
        Object resultado = request.getSession().getAttribute("resultadoIngreso");
        if (resultado != null) {
            request.setAttribute("resultadoIngreso", resultado);
            request.getSession().removeAttribute("resultadoIngreso");
        }
        mostrarLista(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esGestor(request, response)) {
            return;
        }
        try {
            String resultado = interfaz.aprobarIngreso(parsearId(request.getParameter("idDonacion")));
            request.getSession().setAttribute("resultadoIngreso", resultado);
            response.sendRedirect(request.getContextPath() + "/inventario/donaciones");
            return;
        } catch (IllegalArgumentException | IllegalStateException exception) {
            request.setAttribute("errorIngreso", exception.getMessage());
        }
        mostrarLista(request, response);
    }

    private void mostrarLista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Donacion> donaciones = interfaz.mostrarDonacionesPorIngresar();
        request.setAttribute("donacionesPorIngresar", donaciones);
        if (donaciones.isEmpty()) {
            request.setAttribute("mensajeSinPendientes", interfaz.mensajeSinPendientes());
        }
        request.getRequestDispatcher(interfaz.vista()).forward(request, response);
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

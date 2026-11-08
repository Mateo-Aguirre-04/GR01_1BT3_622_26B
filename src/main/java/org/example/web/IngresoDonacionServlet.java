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

    private boolean esGestor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("gestor".equals(request.getSession().getAttribute("vistaActual"))) {
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/inicio.jsp");
        return false;
    }
}

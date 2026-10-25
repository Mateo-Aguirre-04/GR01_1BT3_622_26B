package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;
import org.example.model.Donacion;

import java.io.IOException;

@WebServlet("/donaciones/registrar")
public class DonacionServlet extends HttpServlet {
    private final InterfazDonacion interfaz = new InterfazDonacion(Main.getControlRegistroDonacion());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esAdoptante(request, response)) {
            return;
        }
        request.getRequestDispatcher(interfaz.mostrarFormulario()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esAdoptante(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        try {
            Donacion donacion = interfaz.enviarDonacion(request);
            request.setAttribute("donacionRegistrada", donacion);
            request.getRequestDispatcher(interfaz.mostrarConfirmacion()).forward(request, response);
        } catch (IllegalArgumentException exception) {
            request.setAttribute("errorDonacion", exception.getMessage());
            request.getRequestDispatcher(interfaz.mostrarFormulario()).forward(request, response);
        }
    }

    private boolean esAdoptante(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object vistaActual = request.getSession().getAttribute("vistaActual");
        if (vistaActual == null || "adoptante".equals(vistaActual)) {
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/inicio.jsp");
        return false;
    }
}

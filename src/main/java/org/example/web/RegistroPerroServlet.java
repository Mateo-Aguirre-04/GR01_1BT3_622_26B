package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;
import org.example.model.Perro;

import java.io.IOException;
import java.time.format.DateTimeParseException;

@WebServlet("/registro")
public class RegistroPerroServlet extends HttpServlet {
    private final InterfazGestionPerros interfaz =
            new InterfazGestionPerros(Main.getControlRegistro());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(interfaz.mostrarFormularioRegistro()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            Perro perro = interfaz.enviarDatosPerros(request);
            request.setAttribute("perroRegistrado", perro);
            request.getRequestDispatcher(interfaz.mostrarConfirmacion()).forward(request, response);
        } catch (DateTimeParseException | IllegalArgumentException exception) {
            request.setAttribute("errorRegistro", exception.getMessage());
            request.getRequestDispatcher(interfaz.mostrarFormularioRegistro()).forward(request, response);
        }
    }
}

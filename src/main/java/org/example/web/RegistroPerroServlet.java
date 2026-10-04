package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;
import org.example.model.DatosRegistroPerro;
import org.example.model.Perro;

import java.io.IOException;
import java.time.LocalDate;
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
            DatosRegistroPerro datos = new DatosRegistroPerro(
                    Long.valueOf(parametro(request, "idPerro")),
                    parametro(request, "nombre"),
                    Integer.parseInt(parametro(request, "edad")),
                    parametro(request, "caracteristicas"),
                    parametro(request, "estadoSalud"),
                    parametro(request, "espacioAsignado"),
                    disponibilidad(parametro(request, "disponible")),
                    LocalDate.parse(parametro(request, "fechaIngreso")),
                    Long.valueOf(parametro(request, "idRefugio")));
            Perro perro = interfaz.enviarDatosPerros(datos);
            request.setAttribute("perroRegistrado", perro);
            request.getRequestDispatcher(interfaz.mostrarConfirmacion()).forward(request, response);
        } catch (DateTimeParseException | IllegalArgumentException exception) {
            request.setAttribute("errorRegistro", exception.getMessage());
            request.getRequestDispatcher(interfaz.mostrarFormularioRegistro()).forward(request, response);
        }
    }

    private String parametro(HttpServletRequest request, String nombre) {
        String valor = request.getParameter(nombre);
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + nombre + " es obligatorio");
        }
        return valor.trim();
    }

    private boolean disponibilidad(String valor) {
        if ("true".equals(valor)) {
            return true;
        }
        if ("false".equals(valor)) {
            return false;
        }
        throw new IllegalArgumentException("La disponibilidad seleccionada no es válida");
    }
}

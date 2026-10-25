package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/agenda")
public class AgendaMedicaServlet extends HttpServlet {
    private final InterfazAgendaMedica interfaz = new InterfazAgendaMedica(Main.getControlAgenda());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esGestor(request, response)) {
            return;
        }
        Object mensaje = request.getSession().getAttribute("resultadoAgenda");
        if (mensaje != null) {
            request.setAttribute("resultadoAgenda", mensaje);
            request.getSession().removeAttribute("resultadoAgenda");
        }
        String idPerro = request.getParameter("idPerro");
        if (idPerro != null && !idPerro.isBlank()) {
            try {
                Long id = parsearId(idPerro);
                request.setAttribute("perroSeleccionado", interfaz.seleccionarPerro(id));
                request.setAttribute("agenda", interfaz.consultarAgenda(id));
                request.getRequestDispatcher(interfaz.vistaAgenda()).forward(request, response);
                return;
            } catch (IllegalArgumentException exception) {
                request.setAttribute("errorAgenda", exception.getMessage());
            }
        }
        request.setAttribute("perros", interfaz.mostrarPerros());
        request.getRequestDispatcher(interfaz.vistaFormulario()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esGestor(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        try {
            Long idPerro = parsearId(request.getParameter("idPerro"));
            interfaz.registrar(idPerro, request.getParameter("tipo"),
                    request.getParameter("detalle"), parsearFecha(request.getParameter("fecha")));
            request.getSession().setAttribute("resultadoAgenda", "El registro se guardó en la agenda médica.");
            response.sendRedirect(request.getContextPath() + "/agenda?idPerro=" + idPerro);
            return;
        } catch (IllegalArgumentException exception) {
            request.setAttribute("errorAgenda", exception.getMessage());
        }
        request.setAttribute("perros", interfaz.mostrarPerros());
        request.getRequestDispatcher(interfaz.vistaFormulario()).forward(request, response);
    }

    private Long parsearId(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Seleccione un perro");
        }
        try {
            return Long.parseLong(valor.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El ID del perro no es válido", exception);
        }
    }

    private LocalDate parsearFecha(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(valor.trim());
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("La fecha no es válida", exception);
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

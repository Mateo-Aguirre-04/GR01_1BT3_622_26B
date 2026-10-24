package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;
import org.example.model.Perro;
import org.example.model.Visita;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/visitas/solicitar")
public class SolicitudVisitaServlet extends HttpServlet {
    private static final String VISTA = "/WEB-INF/vistas/solicitarVisita.jsp";
    private final InterfazSolicitarVisita interfaz = new InterfazSolicitarVisita(
            Main.getControlConsulta(), Main.getControlSolicitudVisita());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esAdoptante(request, response)) {
            return;
        }
        cargarPerros(request);
        String idPerro = request.getParameter("idPerro");
        if (idPerro != null && !idPerro.isBlank()) {
            try {
                request.setAttribute("perroSeleccionado", interfaz.seleccionarPerro(parsearId(idPerro)));
            } catch (IllegalArgumentException exception) {
                request.setAttribute("errorVisita", exception.getMessage());
            }
        }
        request.getRequestDispatcher(VISTA).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esAdoptante(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        Long idPerro = null;
        try {
            idPerro = parsearId(request.getParameter("idPerro"));
            request.setAttribute("idPerroIngresado", idPerro);
            request.setAttribute("perroSeleccionado", interfaz.seleccionarPerro(idPerro));
            String fechaTexto = fechaSolicitada(request);
            LocalDate fecha;
            try {
                fecha = LocalDate.parse(fechaTexto);
            } catch (DateTimeParseException exception) {
                throw new IllegalArgumentException("La fecha de visita no es válida", exception);
            }
            Visita visita = interfaz.solicitarVisita(idPerro, fecha,
                    request.getParameter("nombres"), request.getParameter("apellidos"),
                    request.getParameter("telefono"), request.getParameter("correo"));
            request.setAttribute("visita", visita);
            request.setAttribute("perroSeleccionado", visita.getPerro());
        } catch (IllegalArgumentException | IllegalStateException exception) {
            request.setAttribute("errorVisita", exception.getMessage());
            request.setAttribute("fechaIngresada", request.getParameter("fechaVisita"));
            request.setAttribute("nombresIngresados", request.getParameter("nombres"));
            request.setAttribute("apellidosIngresados", request.getParameter("apellidos"));
            request.setAttribute("telefonoIngresado", request.getParameter("telefono"));
            request.setAttribute("correoIngresado", request.getParameter("correo"));
            if (idPerro == null) {
                request.setAttribute("idPerroIngresado", request.getParameter("idPerro"));
            }
        }
        cargarPerros(request);
        request.getRequestDispatcher(VISTA).forward(request, response);
    }

    private void cargarPerros(HttpServletRequest request) {
        List<Perro> perrosDisponibles = interfaz.mostrarPerrosDisponibles();
        request.setAttribute("perrosDisponibles", perrosDisponibles);
    }

    private Long parsearId(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Seleccione un perro disponible");
        }
        try {
            long id = Long.parseLong(valor.trim());
            if (id <= 0) {
                throw new IllegalArgumentException("El ID del perro no es válido");
            }
            return id;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El ID del perro no es válido", exception);
        }
    }

    private String fechaSolicitada(HttpServletRequest request) {
        String valor = request.getParameter("fechaVisita");
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("La fecha de visita es obligatoria");
        }
        return valor.trim();
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

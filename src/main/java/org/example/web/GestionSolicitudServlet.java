package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;
import org.example.model.SolicitudAdopcion;

import java.io.IOException;
import java.util.Map;

/**
 * HTTP adapter for the "Gestionar Solicitud" sequence (Incremento 2).
 */
@WebServlet("/solicitudes")
public class GestionSolicitudServlet extends HttpServlet {
    private static final String LISTADO = "/WEB-INF/vistas/solicitudes.jsp";
    private static final String DETALLE = "/WEB-INF/vistas/detalleSolicitud.jsp";
    private final InterfazGestionSolicitud interfaz =
            new InterfazGestionSolicitud(Main.getControlSolicitud());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParametro = request.getParameter("idSolicitud");
        if (idParametro == null) {
            cargarListado(request);
            request.getRequestDispatcher(LISTADO).forward(request, response);
            return;
        }

        try {
            Long idSolicitud = parsearId(idParametro);
            SolicitudAdopcion solicitud = interfaz.seleccionarSolicitud(idSolicitud);
            Map<String, String> datosAdoptante = interfaz.obtenerFormulario(idSolicitud);
            String fichaPerro = interfaz.obtenerFichaPerro(idSolicitud);
            request.setAttribute("solicitud", interfaz.mostrarDetalleSolicitud(solicitud));
            request.setAttribute("datosAdoptante", interfaz.mostrarDatosAdoptante(datosAdoptante));
            request.setAttribute("fichaPerro", interfaz.mostrarFichaPerro(fichaPerro));
        } catch (IllegalArgumentException | IllegalStateException exception) {
            request.setAttribute("errorGestion", exception.getMessage());
            cargarListado(request);
            request.getRequestDispatcher(LISTADO).forward(request, response);
            return;
        }
        request.getRequestDispatcher(DETALLE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParametro = request.getParameter("idSolicitud");
        try {
            Long idSolicitud = parsearId(idParametro);
            boolean aprobada = parsearDecision(request.getParameter("aprobada"));
            request.setAttribute("resultadoDecision", interfaz.enviarDecision(idSolicitud, aprobada));
            request.getRequestDispatcher(interfaz.mostrarResultado()).forward(request, response);
        } catch (IllegalArgumentException | IllegalStateException exception) {
            request.setAttribute("errorGestion", exception.getMessage());
            cargarListado(request);
            request.getRequestDispatcher(LISTADO).forward(request, response);
        }
    }

    private void cargarListado(HttpServletRequest request) {
        request.setAttribute("solicitudes", interfaz.mostrarSolicitudes());
    }

    private Long parsearId(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El ID de la solicitud es obligatorio");
        }
        try {
            long id = Long.parseLong(valor.trim());
            if (id <= 0) {
                throw new IllegalArgumentException("El ID de la solicitud debe ser mayor que cero");
            }
            return id;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El ID de la solicitud no es válido", exception);
        }
    }

    private boolean parsearDecision(String valor) {
        if ("true".equalsIgnoreCase(valor)) {
            return true;
        }
        if ("false".equalsIgnoreCase(valor)) {
            return false;
        }
        throw new IllegalArgumentException("La decisión de la solicitud no es válida");
    }
}

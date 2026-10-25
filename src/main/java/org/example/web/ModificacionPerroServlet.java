package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;
import org.example.model.Perro;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet("/perros/modificar")
public class ModificacionPerroServlet extends HttpServlet {
    private static final String CONFIRMACION = "perroModificado";
    private final InterfazModificacionPerro interfaz;

    public ModificacionPerroServlet() {
        this(new InterfazModificacionPerro(Main.getControlModificacionPerro()));
    }

    ModificacionPerroServlet(InterfazModificacionPerro interfaz) {
        this.interfaz = Objects.requireNonNull(interfaz, "La interfaz es obligatoria");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esGestor(request, response)) {
            return;
        }
        try {
            Perro perro = interfaz.seleccionarPerro(parsearId(request.getParameter("idPerro")));
            request.setAttribute("perro", perro);
            request.setAttribute("datosEdicion", datosActuales(perro));
            if (perro.getIdPerro().equals(request.getSession().getAttribute(CONFIRMACION))) {
                request.setAttribute("resultadoModificacion", "Los datos del perro se actualizaron correctamente.");
                request.getSession().removeAttribute(CONFIRMACION);
            }
        } catch (IllegalArgumentException exception) {
            request.setAttribute("errorModificacion", exception.getMessage());
        }
        request.getRequestDispatcher(interfaz.mostrarFormulario()).forward(request, response);
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
            Perro perro = interfaz.seleccionarPerro(idPerro);
            request.setAttribute("perro", perro);
            Map<String, String> datos = new LinkedHashMap<>();
            for (String campo : new String[]{"nombre", "edad", "caracteristicas",
                    "estadoSalud", "espacioAsignado", "fechaIngreso"}) {
                datos.put(campo, request.getParameter(campo));
            }
            request.setAttribute("datosEdicion", datos);
            interfaz.enviarDatosPerro(idPerro, datos);
            request.getSession().setAttribute(CONFIRMACION, idPerro);
            response.sendRedirect(request.getContextPath() + "/perros/modificar?idPerro=" + idPerro);
            return;
        } catch (IllegalArgumentException exception) {
            request.setAttribute("errorModificacion", exception.getMessage());
        }
        request.getRequestDispatcher(interfaz.mostrarFormulario()).forward(request, response);
    }

    private Map<String, String> datosActuales(Perro perro) {
        return Map.of("nombre", perro.getNombre(), "edad", Integer.toString(perro.getEdad()),
                "caracteristicas", perro.getCaracteristicas(), "estadoSalud", perro.getEstadoSalud(),
                "espacioAsignado", perro.getEspacioAsignado(), "fechaIngreso", perro.getFechaIngreso().toString());
    }

    private Long parsearId(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Seleccione un perro registrado para modificar sus datos.");
        }
        try {
            return Long.valueOf(valor.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El ID del perro no es válido: debe ser un número entero mayor que cero.", exception);
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

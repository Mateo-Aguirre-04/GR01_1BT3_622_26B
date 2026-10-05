package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;
import org.example.model.Perro;
import org.example.model.SolicitudAdopcion;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HTTP adapter for the adoption form sequence (Incremento 2).
 */
@WebServlet("/adopcion")
public class AdopcionServlet extends HttpServlet {
    private final InterfazAdopcion interfaz = new InterfazAdopcion(
            Main.getControlFormulario(), Main.getControlEnvioSolicitud());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            Long idPerro = Long.valueOf(parametro(request, "idPerro"));
            Perro ficha = interfaz.manifestarInteres(idPerro);
            request.setAttribute("perro", ficha);
            request.getRequestDispatcher(interfaz.mostrarFormulario(ficha)).forward(request, response);
        } catch (IllegalArgumentException exception) {
            request.setAttribute("errorAdopcion", exception.getMessage());
            request.getRequestDispatcher("/WEB-INF/vistas/formularioAdopcion.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            SolicitudAdopcion solicitud = interfaz.enviarDatosFormulario(datosFormulario(request));
            request.setAttribute("idSolicitud", solicitud.getIdSolicitud());
            request.getRequestDispatcher(interfaz.mostrarConfirmacion(solicitud.getIdSolicitud()))
                    .forward(request, response);
        } catch (IllegalArgumentException | IllegalStateException exception) {
            request.setAttribute("errorAdopcion", exception.getMessage());
            request.getRequestDispatcher("/WEB-INF/vistas/formularioAdopcion.jsp").forward(request, response);
        }
    }

    private Map<String, String> datosFormulario(HttpServletRequest request) {
        Map<String, String> datos = new LinkedHashMap<>();
        for (String nombre : new String[]{
                "idPerro", "infoDomicilio", "experienciaMascotas", "condicionesHogar",
                "disponibilidadTiempo", "aceptacionTerminos", "nombres", "apellidos", "telefono", "correo"
        }) {
            datos.put(nombre, request.getParameter(nombre));
        }
        return datos;
    }

    private String parametro(HttpServletRequest request, String nombre) {
        String valor = request.getParameter(nombre);
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El parámetro " + nombre + " es obligatorio");
        }
        return valor.trim();
    }
}

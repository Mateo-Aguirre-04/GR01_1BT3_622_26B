package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;
import org.example.model.Perro;

import java.io.IOException;
import java.util.List;

@WebServlet("/consulta")
public class ConsultaPerrosServlet extends HttpServlet {
    private static final String CATALOGO = "/WEB-INF/vistas/consultaPerros.jsp";
    private static final String FICHA = "/WEB-INF/vistas/fichaPerro.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idPerroParametro = request.getParameter("idPerro");
        if (idPerroParametro == null) {
            List<Perro> perrosDisponibles = Main.getControlConsulta().consultarRegistro();
            request.setAttribute("perrosDisponibles", perrosDisponibles);
            request.getRequestDispatcher(CATALOGO).forward(request, response);
            return;
        }

        try {
            long idPerro = Long.parseLong(idPerroParametro);
            if (idPerro <= 0) {
                mostrarErrorFicha(request, response, "El ID debe ser un número entero mayor que 0.");
                return;
            }

            Perro perro = Main.getControlConsulta().consultarFicha(idPerro);
            if (perro == null) {
                mostrarErrorFicha(request, response, "No se encontró un perro con ese ID.");
                return;
            }

            request.setAttribute("perro", perro);
        } catch (NumberFormatException exception) {
            mostrarErrorFicha(request, response, "El ID debe ser un número entero mayor que 0.");
            return;
        }
        request.getRequestDispatcher(FICHA).forward(request, response);
    }

    private void mostrarErrorFicha(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        request.setAttribute("errorConsulta", mensaje);
        request.getRequestDispatcher(FICHA).forward(request, response);
    }
}

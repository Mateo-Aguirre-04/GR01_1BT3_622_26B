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

@WebServlet(urlPatterns = {"/consulta", "/vista"})
public class ConsultaPerrosServlet extends HttpServlet {
    private static final String CATALOGO = "/WEB-INF/vistas/consultaPerros.jsp";
    private static final String FICHA = "/WEB-INF/vistas/fichaPerro.jsp";
    private final InterfazConsultaRegistro interfaz =
            new InterfazConsultaRegistro(Main.getControlConsulta());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (!"/vista".equals(request.getServletPath())) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        String vista = request.getParameter("vista");
        if (!"adoptante".equals(vista) && !"gestor".equals(vista)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La vista seleccionada no es válida.");
            return;
        }

        request.getSession().setAttribute("vistaActual", vista);
        response.sendRedirect(request.getContextPath() + "/inicio.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idPerroParametro = request.getParameter("idPerro");
        if (idPerroParametro == null) {
            List<Perro> perros = interfaz.mostrarPerros();
            request.setAttribute("perros", perros);
            request.getRequestDispatcher(CATALOGO).forward(request, response);
            return;
        }

        try {
            long idPerro = Long.parseLong(idPerroParametro);
            if (idPerro <= 0) {
                mostrarErrorFicha(request, response, "El ID debe ser un número entero mayor que 0.");
                return;
            }

            Perro perro = interfaz.solicitarFicha(idPerro);
            if (perro == null) {
                mostrarErrorFicha(request, response, "No se encontró un perro con ese ID.");
                return;
            }

            request.setAttribute("perro", interfaz.mostrarFicha(perro));
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

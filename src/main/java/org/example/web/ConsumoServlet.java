package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;

import java.io.IOException;

@WebServlet("/inventario/consumos")
public class ConsumoServlet extends HttpServlet {
    private final InterfazConsumo interfaz = new InterfazConsumo(Main.getControlConsumo());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esGestor(request, response)) {
            return;
        }
        Object confirmacion = request.getSession().getAttribute("confirmacionConsumo");
        if (confirmacion != null) {
            request.setAttribute("confirmacionConsumo", confirmacion);
            request.getSession().removeAttribute("confirmacionConsumo");
        }
        mostrarFormulario(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esGestor(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        try {
            String confirmacion = interfaz.registrarConsumo(
                    request.getParameter("material"),
                    request.getParameter("cantidad"),
                    request.getParameter("motivo"));
            request.getSession().setAttribute("confirmacionConsumo", confirmacion);
            response.sendRedirect(request.getContextPath() + "/inventario/consumos");
            return;
        } catch (IllegalArgumentException exception) {
            request.setAttribute("errorConsumo", exception.getMessage());
        }
        mostrarFormulario(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("materiales", interfaz.mostrarMateriales());
        request.getRequestDispatcher(interfaz.formulario()).forward(request, response);
    }

    private boolean esGestor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("gestor".equals(request.getSession().getAttribute("vistaActual"))) {
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/inicio.jsp");
        return false;
    }
}

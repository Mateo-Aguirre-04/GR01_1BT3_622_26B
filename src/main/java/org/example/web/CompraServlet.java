package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Main;

import java.io.IOException;

@WebServlet("/inventario/compras")
public class CompraServlet extends HttpServlet {
    private final InterfazCompra interfaz = new InterfazCompra(Main.getControlCompra());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!esGestor(request, response)) {
            return;
        }
        Object confirmacion = request.getSession().getAttribute("confirmacionCompra");
        if (confirmacion != null) {
            request.setAttribute("confirmacionCompra", confirmacion);
            request.getSession().removeAttribute("confirmacionCompra");
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
            String confirmacion = interfaz.registrarCompra(
                    request.getParameter("material"),
                    request.getParameter("nombreNuevo"),
                    request.getParameter("unidadNueva"),
                    request.getParameter("cantidad"));
            request.getSession().setAttribute("confirmacionCompra", confirmacion);
            response.sendRedirect(request.getContextPath() + "/inventario/compras");
            return;
        } catch (IllegalArgumentException exception) {
            request.setAttribute("errorCompra", exception.getMessage());
        }
        mostrarFormulario(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("materiales", interfaz.mostrarMateriales());
        request.setAttribute("opcionMaterialNuevo", InterfazCompra.OPCION_MATERIAL_NUEVO);
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

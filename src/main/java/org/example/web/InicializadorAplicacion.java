package org.example.web;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.Main;

@WebListener
public class InicializadorAplicacion implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        Main.initialize();
        event.getServletContext().setAttribute("refugioActual", Main.getRefugio());
        event.getServletContext().setAttribute("encargadoActual", Main.getEncargado());
    }
}

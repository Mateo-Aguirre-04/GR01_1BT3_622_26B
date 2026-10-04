package org.example;

import org.example.control.ControlConsulta;
import org.example.control.ControlRegistro;
import org.example.model.Encargado;
import org.example.model.Refugio;

public class Main {
    private static Refugio refugio;
    private static Encargado encargado;
    private static ControlRegistro controlRegistro;
    private static ControlConsulta controlConsulta;

    private Main() {
    }

    public static synchronized void initialize() {
        if (refugio == null) {
            refugio = new Refugio(1L, "Refugio Central", "Ubicación por definir");
            encargado = new Encargado(1L, "Encargado del refugio");
            controlRegistro = new ControlRegistro(encargado, refugio);
            controlConsulta = new ControlConsulta(refugio);
        }
    }

    public static Refugio getRefugio() {
        initialize();
        return refugio;
    }

    public static Encargado getEncargado() {
        initialize();
        return encargado;
    }

    public static ControlRegistro getControlRegistro() {
        initialize();
        return controlRegistro;
    }

    public static ControlConsulta getControlConsulta() {
        initialize();
        return controlConsulta;
    }

    public static void main(String[] args) {
        initialize();
    }
}

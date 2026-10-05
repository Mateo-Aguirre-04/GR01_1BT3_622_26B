package org.example;

import org.example.control.ControlConsulta;
import org.example.control.ControlEnvioSolicitud;
import org.example.control.ControlFormulario;
import org.example.control.ControlRegistro;
import org.example.control.ControlSolicitud;
import org.example.model.Encargado;
import org.example.model.Refugio;

public class Main {
    private static Refugio refugio;
    private static Encargado encargado;
    private static ControlRegistro controlRegistro;
    private static ControlConsulta controlConsulta;
    private static ControlFormulario controlFormulario;
    private static ControlEnvioSolicitud controlEnvioSolicitud;
    private static ControlSolicitud controlSolicitud;

    private Main() {
    }

    public static synchronized void initialize() {
        if (refugio == null) {
            refugio = new Refugio(1L, "Refugio Central", "Ubicación por definir");
            encargado = new Encargado(1L, "Encargado del refugio");
            controlRegistro = new ControlRegistro(encargado, refugio);
            controlConsulta = new ControlConsulta(refugio);
            controlFormulario = new ControlFormulario(controlConsulta);
            controlEnvioSolicitud = new ControlEnvioSolicitud(refugio);
            controlSolicitud = new ControlSolicitud(refugio);
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

    public static ControlFormulario getControlFormulario() {
        initialize();
        return controlFormulario;
    }

    public static ControlEnvioSolicitud getControlEnvioSolicitud() {
        initialize();
        return controlEnvioSolicitud;
    }

    public static ControlSolicitud getControlSolicitud() {
        initialize();
        return controlSolicitud;
    }

    public static void main(String[] args) {
        initialize();
    }
}

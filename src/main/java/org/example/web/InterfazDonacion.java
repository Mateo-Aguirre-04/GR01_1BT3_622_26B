package org.example.web;

import jakarta.servlet.http.HttpServletRequest;
import org.example.control.ControlRegistroDonacion;
import org.example.model.Donacion;

public class InterfazDonacion {
    public InterfazDonacion(ControlRegistroDonacion controlRegistroDonacion) {
    }


    public Donacion enviarDonacion(HttpServletRequest formulario) {
        return null;
    }

    public String mostrarFormulario() {
        return "";
    }

    public String mostrarConfirmacion() {
        return "";
    }
}

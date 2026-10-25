package org.example.control;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.Adoptante;
import org.example.model.Donacion;
import org.example.model.Refugio;

import java.util.Objects;

public class ControlRegistroDonacion {
    private final Refugio refugio;

    public ControlRegistroDonacion(Refugio refugio) {
        this.refugio = Objects.requireNonNull(refugio, "El refugio es obligatorio");
    }

    public synchronized Donacion registrarDonacion(HttpServletRequest solicitud) {
        Objects.requireNonNull(solicitud, "La solicitud de donación es obligatoria");
        Adoptante donante = leerDonante(solicitud);
        Donacion donacion = new Donacion(refugio.obtenerIdDonacion(), donante, solicitud.getParameter("descripcion"));
        agregarMateriales(solicitud, donacion);
        if (donacion.cantidadDeMateriales() == 0) {
            throw new IllegalArgumentException("Debe indicar al menos un material para donar");
        }
        refugio.agregarDonacion(donacion);
        return donacion;
    }

    private Adoptante leerDonante(HttpServletRequest solicitud) {
        String nombres = obligatorio(solicitud, "nombres", "Los nombres del donante son obligatorios");
        String apellidos = obligatorio(solicitud, "apellidos", "Los apellidos del donante son obligatorios");
        String telefono = obligatorio(solicitud, "telefono", "El teléfono del donante es obligatorio");
        String correo = obligatorio(solicitud, "correo", "El correo del donante es obligatorio");
        return new Adoptante(nombres, apellidos, telefono, correo);
    }

    private String obligatorio(HttpServletRequest solicitud, String campo, String mensaje) {
        String valor = solicitud.getParameter(campo);
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    private void agregarMateriales(HttpServletRequest solicitud, Donacion donacion) {
        String[] materiales = solicitud.getParameterValues("material");
        String[] cantidades = solicitud.getParameterValues("cantidad");
        if (materiales == null) {
            return;
        }
        for (int i = 0; i < materiales.length; i++) {
            String material = materiales[i] == null ? "" : materiales[i].trim();
            String cantidadTexto = cantidades != null && i < cantidades.length && cantidades[i] != null
                    ? cantidades[i].trim() : "";
            if (material.isEmpty() && cantidadTexto.isEmpty()) {
                continue;
            }
            donacion.agregarMaterial(material, convertirCantidad(material, cantidadTexto));
        }
    }

    private int convertirCantidad(String material, String cantidadTexto) {
        if (cantidadTexto.isEmpty()) {
            throw new IllegalArgumentException("Indique la cantidad de " + material);
        }
        try {
            return Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("La cantidad de " + material + " no es válida", exception);
        }
    }
}

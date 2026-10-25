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
        String nombres = solicitud.getParameter("nombres");
        String apellidos = solicitud.getParameter("apellidos");
        String telefono = solicitud.getParameter("telefono");
        String correo = solicitud.getParameter("correo");
        if (nombres == null || nombres.isBlank()) {
            throw new IllegalArgumentException("Los nombres del donante son obligatorios");
        }
        if (apellidos == null || apellidos.isBlank()) {
            throw new IllegalArgumentException("Los apellidos del donante son obligatorios");
        }
        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("El teléfono del donante es obligatorio");
        }
        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El correo del donante es obligatorio");
        }
        Adoptante donante = new Adoptante(nombres.trim(), apellidos.trim(), telefono.trim(), correo.trim());
        Donacion donacion = new Donacion(refugio.obtenerIdDonacion(), donante, solicitud.getParameter("descripcion"));

        String[] materiales = solicitud.getParameterValues("material");
        String[] cantidades = solicitud.getParameterValues("cantidad");
        if (materiales != null) {
            for (int i = 0; i < materiales.length; i++) {
                String material = materiales[i] == null ? "" : materiales[i].trim();
                String cantidadTexto = cantidades != null && i < cantidades.length && cantidades[i] != null
                        ? cantidades[i].trim() : "";
                if (material.isEmpty() && cantidadTexto.isEmpty()) {
                    continue;
                }
                if (cantidadTexto.isEmpty()) {
                    throw new IllegalArgumentException("Indique la cantidad de " + material);
                }
                int cantidad;
                try {
                    cantidad = Integer.parseInt(cantidadTexto);
                } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException("La cantidad de " + material + " no es válida", exception);
                }
                donacion.agregarMaterial(material, cantidad);
            }
        }
        if (donacion.cantidadDeMateriales() == 0) {
            throw new IllegalArgumentException("Debe indicar al menos un material para donar");
        }
        refugio.agregarDonacion(donacion);
        return donacion;
    }
}

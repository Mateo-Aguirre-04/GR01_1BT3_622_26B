package org.example.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Adoptante form data from the "Solicitar Adopcion" sequence (Incremento 2).
 */
public class FormularioAdoptante {
    private final String infoDomicilio;
    private final String experienciaMascotas;
    private final String condicionesHogar;
    private final String disponibilidadTiempo;
    private final boolean aceptacionTerminos;
    private final Adoptante adoptante;

    /*EXTRACT CLASS*/
    public FormularioAdoptante(Map<String, String> datos) {
        Objects.requireNonNull(datos, "Los datos del formulario son obligatorios");
        infoDomicilio = valor(datos, "infoDomicilio");
        experienciaMascotas = valor(datos, "experienciaMascotas");
        condicionesHogar = valor(datos, "condicionesHogar");
        disponibilidadTiempo = valor(datos, "disponibilidadTiempo");
        aceptacionTerminos = Boolean.parseBoolean(valor(datos, "aceptacionTerminos"));
        adoptante = new Adoptante(
                valor(datos, "nombres"),
                valor(datos, "apellidos"),
                valor(datos, "telefono"),
                valor(datos, "correo")
        );
    }

    public boolean verificar(Map<String, String> datos) {
        Objects.requireNonNull(datos, "Los datos del formulario son obligatorios");

        String[] campos = {
                infoDomicilio,
                experienciaMascotas,
                condicionesHogar,
                disponibilidadTiempo
        };

        for (String campo : campos) {
            if (!noVacio(campo)) {
                return false;
            }
        }

        return adoptante.datosCompletos()
                && aceptacionTerminos
                && "true".equalsIgnoreCase(datos.get("aceptacionTerminos"));
    }

    public Map<String, String> obtenerDatos() {
        Map<String, String> datos = new LinkedHashMap<>();
        datos.put("infoDomicilio", infoDomicilio);
        datos.put("experienciaMascotas", experienciaMascotas);
        datos.put("condicionesHogar", condicionesHogar);
        datos.put("disponibilidadTiempo", disponibilidadTiempo);
        datos.put("aceptacionTerminos", Boolean.toString(aceptacionTerminos));
        datos.put("nombres", adoptante.getNombres());
        datos.put("apellidos", adoptante.getApellidos());
        datos.put("telefono", adoptante.getTelefono());
        datos.put("correo", adoptante.getCorreo());
        return Collections.unmodifiableMap(datos);
    }

    public String getInfoDomicilio() {
        return infoDomicilio;
    }

    public String getExperienciaMascotas() {
        return experienciaMascotas;
    }

    public String getCondicionesHogar() {
        return condicionesHogar;
    }

    public String getDisponibilidadTiempo() {
        return disponibilidadTiempo;
    }

    public boolean isAceptacionTerminos() {
        return aceptacionTerminos;
    }

    public String getNombres() {
        return adoptante.getNombres();
    }

    public String getApellidos() {
        return adoptante.getApellidos();
    }

    public String getTelefono() {
        return adoptante.getTelefono();
    }

    public String getCorreo() {
        return adoptante.getCorreo();
    }

    private static String valor(Map<String, String> datos, String nombre) {
        String valor = datos.get(nombre);
        return valor == null ? "" : valor.trim();
    }

    private static boolean noVacio(String valor) {
        return valor != null && !valor.isBlank();
    }
}

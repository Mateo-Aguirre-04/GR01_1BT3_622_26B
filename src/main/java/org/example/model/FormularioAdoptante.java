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
    private final String nombres;
    private final String apellidos;
    private final String telefono;
    private final String correo;

    /*EXTRACT CLASS*/
    public FormularioAdoptante(Map<String, String> datos) {
        Objects.requireNonNull(datos, "Los datos del formulario son obligatorios");
        infoDomicilio = valor(datos, "infoDomicilio");
        experienciaMascotas = valor(datos, "experienciaMascotas");
        condicionesHogar = valor(datos, "condicionesHogar");
        disponibilidadTiempo = valor(datos, "disponibilidadTiempo");
        aceptacionTerminos = Boolean.parseBoolean(valor(datos, "aceptacionTerminos"));
        nombres = valor(datos, "nombres");
        apellidos = valor(datos, "apellidos");
        telefono = valor(datos, "telefono");
        correo = valor(datos, "correo");
    }

    /*SUBSTITUTE ALGORITHM*/
    public boolean verificar(Map<String, String> datos) {
        Objects.requireNonNull(datos, "Los datos del formulario son obligatorios");
        return noVacio(infoDomicilio)
                && noVacio(experienciaMascotas)
                && noVacio(condicionesHogar)
                && noVacio(disponibilidadTiempo)
                && aceptacionTerminos
                && noVacio(nombres)
                && noVacio(apellidos)
                && noVacio(telefono)
                && noVacio(correo)
                && "true".equalsIgnoreCase(datos.get("aceptacionTerminos"));
    }

    public Map<String, String> obtenerDatos() {
        Map<String, String> datos = new LinkedHashMap<>();
        datos.put("infoDomicilio", infoDomicilio);
        datos.put("experienciaMascotas", experienciaMascotas);
        datos.put("condicionesHogar", condicionesHogar);
        datos.put("disponibilidadTiempo", disponibilidadTiempo);
        datos.put("aceptacionTerminos", Boolean.toString(aceptacionTerminos));
        datos.put("nombres", nombres);
        datos.put("apellidos", apellidos);
        datos.put("telefono", telefono);
        datos.put("correo", correo);
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
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    private static String valor(Map<String, String> datos, String nombre) {
        String valor = datos.get(nombre);
        return valor == null ? "" : valor.trim();
    }

    private static boolean noVacio(String valor) {
        return valor != null && !valor.isBlank();
    }
}

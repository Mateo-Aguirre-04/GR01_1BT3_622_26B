package org.example.control;

import org.example.model.Perro;
import org.example.model.Refugio;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;

/** Coordina el caso de uso "Modificar datos del perro". */
public class ControlModificacionPerro {
    private final Refugio refugio;

    public ControlModificacionPerro(Refugio refugio) {
        this.refugio = Objects.requireNonNull(refugio, "El refugio es obligatorio");
    }

    public Perro obtenerPerro(Long idPerro) {
        if (idPerro == null || idPerro <= 0) {
            throw new IllegalArgumentException("El ID del perro debe ser un número entero mayor que cero");
        }
        Perro perro = refugio.obtenerFichaPerro(idPerro);
        if (perro == null) {
            throw new IllegalArgumentException("No se encontró un perro con ese ID");
        }
        return perro;
    }

    public Perro modificarPerro(Long idPerro, Map<String, String> datos) {
        Objects.requireNonNull(datos, "Los datos del perro son obligatorios");
        synchronized (refugio) {
            Perro perro = obtenerPerro(idPerro);
            String nombre = obligatorio(datos, "nombre", "nombre");
            int edad = edad(obligatorio(datos, "edad", "edad"));
            String caracteristicas = obligatorio(datos, "caracteristicas", "características");
            String estadoSalud = obligatorio(datos, "estadoSalud", "estado de salud");
            String espacioAsignado = obligatorio(datos, "espacioAsignado", "espacio asignado");
            LocalDate fechaIngreso = fecha(obligatorio(datos, "fechaIngreso", "fecha de ingreso"));

            // Validar todo antes de modificar la instancia compartida con solicitudes y visitas.
            perro.setNombre(nombre);
            perro.setEdad(edad);
            perro.setCaracteristicas(caracteristicas);
            perro.setEstadoSalud(estadoSalud);
            perro.setEspacioAsignado(espacioAsignado);
            perro.setFechaIngreso(fechaIngreso);
            return perro;
        }
    }

    private String obligatorio(Map<String, String> datos, String campo, String etiqueta) {
        String valor = datos.get(campo);
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Debe completar el campo " + etiqueta + ".");
        }
        return valor.trim();
    }

    private int edad(String valor) {
        try {
            int edad = Integer.parseInt(valor);
            if (edad >= 0) {
                return edad;
            }
        } catch (NumberFormatException exception) {
            // El mismo mensaje cubre texto, decimales y números fuera del rango de int.
        }
        throw new IllegalArgumentException("La edad no es válida: debe ser un número entero igual o mayor que cero.");
    }

    private LocalDate fecha(String valor) {
        try {
            return LocalDate.parse(valor);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("La fecha de ingreso no es válida: use una fecha real con formato AAAA-MM-DD.", exception);
        }
    }
}

package org.example.control;

import org.example.model.DatosRegistroPerro;
import org.example.model.Encargado;
import org.example.model.Perro;
import org.example.model.Refugio;

import java.util.Objects;

public class ControlRegistro {
    private final Encargado encargadoActual;
    private final Refugio refugioConfigurado;
    private Refugio refugioActual;

    public ControlRegistro(Encargado encargadoActual, Refugio refugioActual) {
        this.encargadoActual = Objects.requireNonNull(encargadoActual, "El encargado actual es obligatorio");
        this.refugioConfigurado = Objects.requireNonNull(refugioActual, "El refugio actual es obligatorio");
        this.refugioActual = refugioConfigurado;
    }

    public synchronized Perro registrarPerro(DatosRegistroPerro datos) {
        Objects.requireNonNull(datos, "Los datos del registro son obligatorios");
        validar(datos);
        asociarRefugio(datos.idRefugio());

        Perro perro = new Perro(
                datos.idPerro(),
                datos.nombre().trim(),
                datos.edad(),
                datos.caracteristicas().trim(),
                datos.estadoSalud().trim(),
                datos.espacioAsignado().trim(),
                datos.disponible(),
                datos.fechaIngreso(),
                refugioActual);

        refugioActual.agregarPerro(perro);
        encargadoActual.asociarRegistro(perro);
        return perro;
    }

    public synchronized void asociarRefugio(Long idRefugio) {
        if (idRefugio == null || !idRefugio.equals(refugioConfigurado.getIdRefugio())) {
            throw new IllegalArgumentException("El refugio seleccionado no está inicializado en la aplicación");
        }
        refugioActual = refugioConfigurado;
    }

    public Encargado getEncargadoActual() {
        return encargadoActual;
    }

    public synchronized Refugio getRefugioActual() {
        return refugioActual;
    }

    private void validar(DatosRegistroPerro datos) {
        if (datos.idPerro() == null || datos.idPerro() <= 0) {
            throw new IllegalArgumentException("El ID del perro debe ser mayor que cero");
        }
        if (datos.nombre() == null || datos.nombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del perro es obligatorio");
        }
        if (datos.edad() < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        if (datos.caracteristicas() == null || datos.caracteristicas().isBlank()) {
            throw new IllegalArgumentException("Las características son obligatorias");
        }
        if (datos.estadoSalud() == null || datos.estadoSalud().isBlank()) {
            throw new IllegalArgumentException("El estado de salud es obligatorio");
        }
        if (datos.espacioAsignado() == null || datos.espacioAsignado().isBlank()) {
            throw new IllegalArgumentException("El espacio asignado es obligatorio");
        }
        if (datos.fechaIngreso() == null) {
            throw new IllegalArgumentException("La fecha de ingreso es obligatoria");
        }
    }
}

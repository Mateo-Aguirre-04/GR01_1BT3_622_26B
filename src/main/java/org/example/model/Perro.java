package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "perros")
public class Perro {
    @Id
    private Long idPerro;
    private String nombre;
    private int edad;
    private String caracteristicas;
    private String estadoSalud;
    private String espacioAsignado;
    private boolean disponible;
    private LocalDate fechaIngreso;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_refugio", nullable = false)
    private Refugio refugio;

    protected Perro() {
    }

    public Perro(Long idPerro, String nombre, int edad, String caracteristicas,
                 String estadoSalud, String espacioAsignado, boolean disponible,
                 LocalDate fechaIngreso, Refugio refugio) {
        this.idPerro = Objects.requireNonNull(idPerro, "El ID del perro es obligatorio");
        this.nombre = Objects.requireNonNull(nombre, "El nombre es obligatorio");
        this.edad = edad;
        this.caracteristicas = Objects.requireNonNull(caracteristicas, "Las características son obligatorias");
        this.estadoSalud = Objects.requireNonNull(estadoSalud, "El estado de salud es obligatorio");
        this.espacioAsignado = Objects.requireNonNull(espacioAsignado, "El espacio asignado es obligatorio");
        this.disponible = disponible;
        this.fechaIngreso = Objects.requireNonNull(fechaIngreso, "La fecha de ingreso es obligatoria");
        this.refugio = Objects.requireNonNull(refugio, "El refugio es obligatorio");
    }

    public Long getIdPerro() {
        return idPerro;
    }

    public void setIdPerro(Long idPerro) {
        this.idPerro = idPerro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public String getEspacioAsignado() {
        return espacioAsignado;
    }

    public void setEspacioAsignado(String espacioAsignado) {
        this.espacioAsignado = espacioAsignado;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Refugio getRefugio() {
        return refugio;
    }

    public void setRefugio(Refugio refugio) {
        this.refugio = refugio;
    }

    public String obtenerFicha() {
        return "Perro{id=%d, nombre='%s', edad=%d, estadoSalud='%s', disponible=%s}"
                .formatted(idPerro, nombre, edad, estadoSalud, disponible);
    }

    public boolean estaDisponible() {
        return disponible;
    }
}

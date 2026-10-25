package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.Objects;

@Entity
@Table(name = "encargados")
public class Encargado {
    @Id
    private Long idEncargado;
    private String nombre;

    @Transient
    private Perro ultimoRegistro;

    protected Encargado() {
    }

    public Encargado(Long idEncargado, String nombre) {
        this.idEncargado = Objects.requireNonNull(idEncargado, "El ID del encargado es obligatorio");
        this.nombre = Objects.requireNonNull(nombre, "El nombre del encargado es obligatorio");
    }

    public Long getIdEncargado() {
        return idEncargado;
    }

    public void setIdEncargado(Long idEncargado) {
        this.idEncargado = idEncargado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Perro getUltimoRegistro() {
        return ultimoRegistro;
    }

    public void asociarRegistro(Perro perro) {
        ultimoRegistro = Objects.requireNonNull(perro, "El perro es obligatorio");
    }
}

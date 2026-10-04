package org.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "refugios")
public class Refugio {
    @Id
    private Long idRefugio;
    private String nombre;
    private String ubicacion;

    @OneToMany(mappedBy = "refugio", cascade = CascadeType.ALL)
    private List<Perro> perros = new ArrayList<>();

    protected Refugio() {
    }

    public Refugio(Long idRefugio, String nombre, String ubicacion) {
        this.idRefugio = Objects.requireNonNull(idRefugio, "El ID del refugio es obligatorio");
        this.nombre = Objects.requireNonNull(nombre, "El nombre del refugio es obligatorio");
        this.ubicacion = Objects.requireNonNull(ubicacion, "La ubicación del refugio es obligatoria");
    }

    public Long getIdRefugio() {
        return idRefugio;
    }

    public void setIdRefugio(Long idRefugio) {
        this.idRefugio = idRefugio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Perro> getPerros() {
        return Collections.unmodifiableList(perros);
    }

    public synchronized void agregarPerro(Perro perro) {
        Objects.requireNonNull(perro, "El perro es obligatorio");
        if (!Objects.equals(idRefugio, perro.getRefugio().getIdRefugio())) {
            throw new IllegalArgumentException("El perro está asociado a otro refugio");
        }
        if (perros.stream().anyMatch(registrado -> Objects.equals(registrado.getIdPerro(), perro.getIdPerro()))) {
            throw new IllegalArgumentException("Ya existe un perro con ese ID en el refugio");
        }
        perros.add(perro);
    }

    public synchronized List<Perro> listarPerrosDisponibles() {
        return perros.stream()
                .filter(Perro::estaDisponible)
                .toList();
    }

    public synchronized Perro obtenerFichaPerro(Long idPerro) {
        if (idPerro == null) {
            return null;
        }
        return perros.stream()
                .filter(perro -> Objects.equals(perro.getIdPerro(), idPerro))
                .findFirst()
                .orElse(null);
    }
}

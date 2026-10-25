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
    private final List<SolicitudAdopcion> solicitudes = new ArrayList<>();
    private final List<Visita> visitas = new ArrayList<>();
    private final List<Donacion> donaciones = new ArrayList<>();
    private long siguienteIdSolicitud = 1L;
    private long siguienteIdVisita = 1L;
    private long siguienteIdDonacion = 1L;

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

    public Long obtenerId() {
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

    public synchronized List<Perro> listarPerros() {
        return List.copyOf(perros);
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

    public synchronized Long obtenerIdSolicitud() {
        return siguienteIdSolicitud++;
    }

    public synchronized void agregarSolicitud(SolicitudAdopcion solicitud) {
        Objects.requireNonNull(solicitud, "La solicitud es obligatoria");
        if (solicitudes.stream().anyMatch(registrada ->
                Objects.equals(registrada.getIdSolicitud(), solicitud.getIdSolicitud()))) {
            throw new IllegalArgumentException("Ya existe una solicitud con ese ID");
        }
        if (obtenerFichaPerro(solicitud.obtenerIdPerro()) == null) {
            throw new IllegalArgumentException("El perro asociado a la solicitud no existe en el refugio");
        }
        solicitudes.add(solicitud);
        siguienteIdSolicitud = Math.max(siguienteIdSolicitud, solicitud.getIdSolicitud() + 1);
    }

    public synchronized SolicitudAdopcion obtenerSolicitud(Long idSolicitud) {
        if (idSolicitud == null) {
            return null;
        }
        return solicitudes.stream()
                .filter(solicitud -> Objects.equals(solicitud.getIdSolicitud(), idSolicitud))
                .findFirst()
                .orElse(null);
    }

    public synchronized List<SolicitudAdopcion> listarSolicitudes() {
        return List.copyOf(solicitudes);
    }

    public synchronized boolean verificarDisponibilidad(Long idPerro) {
        Perro perro = obtenerFichaPerro(idPerro);
        if (perro == null) {
            throw new IllegalArgumentException("No se encontró un perro con ese ID");
        }
        return perro.estaDisponible();
    }

    public synchronized void marcarNoDisponible(Long idPerro) {
        Perro perro = obtenerFichaPerro(idPerro);
        if (perro == null) {
            throw new IllegalArgumentException("No se encontró un perro con ese ID");
        }
        perro.marcarNoDisponible();
    }

    public synchronized Long obtenerIdVisita() {
        return siguienteIdVisita++;
    }

    public synchronized void agregarVisita(Visita visita) {
        Objects.requireNonNull(visita, "La visita es obligatoria");
        if (visita.getIdVisita() == null || visita.getIdVisita() <= 0) {
            throw new IllegalArgumentException("El ID de la visita debe ser mayor que cero");
        }
        if (visitas.stream().anyMatch(registrada ->
                Objects.equals(registrada.getIdVisita(), visita.getIdVisita()))) {
            throw new IllegalArgumentException("Ya existe una visita con ese ID");
        }
        if (visita.getPerro() == null || obtenerFichaPerro(visita.getPerro().getIdPerro()) != visita.getPerro()) {
            throw new IllegalArgumentException("El perro asociado a la visita no existe en el refugio");
        }
        if (visita.getVisitante() == null) {
            throw new IllegalArgumentException("Los datos del visitante son obligatorios");
        }
        visitas.add(visita);
        siguienteIdVisita = Math.max(siguienteIdVisita, visita.getIdVisita() + 1);
    }

    public synchronized Visita obtenerVisita(Long idVisita) {
        if (idVisita == null) {
            return null;
        }
        return visitas.stream()
                .filter(visita -> Objects.equals(visita.getIdVisita(), idVisita))
                .findFirst()
                .orElse(null);
    }

    public synchronized List<Visita> listarVisitas() {
        return List.copyOf(visitas);
    }

    public synchronized List<Visita> listarVisitasPendientes() {
        return visitas.stream()
                .filter(visita -> visita.getEstado() == Visita.Estado.PENDIENTE)
                .toList();
    }

    public synchronized Long obtenerIdDonacion() {
        return siguienteIdDonacion++;
    }

    public synchronized void agregarDonacion(Donacion donacion) {
        Objects.requireNonNull(donacion, "La donación es obligatoria");
        if (donacion.cantidadDeMateriales() == 0) {
            throw new IllegalArgumentException("La donación debe tener al menos un material");
        }
        if (donaciones.stream().anyMatch(registrada ->
                Objects.equals(registrada.getIdDonacion(), donacion.getIdDonacion()))) {
            throw new IllegalArgumentException("Ya existe una donación con ese ID");
        }
        donaciones.add(donacion);
        siguienteIdDonacion = Math.max(siguienteIdDonacion, donacion.getIdDonacion() + 1);
    }

    public synchronized Donacion obtenerDonacion(Long idDonacion) {
        if (idDonacion == null) {
            return null;
        }
        return donaciones.stream()
                .filter(donacion -> Objects.equals(donacion.getIdDonacion(), idDonacion))
                .findFirst()
                .orElse(null);
    }

    public synchronized List<Donacion> listarDonaciones() {
        return List.copyOf(donaciones);
    }

    public synchronized List<Donacion> listarDonacionesPendientes() {
        return donaciones.stream()
                .filter(donacion -> donacion.getEstado() == Donacion.Estado.PENDIENTE)
                .toList();
    }
}

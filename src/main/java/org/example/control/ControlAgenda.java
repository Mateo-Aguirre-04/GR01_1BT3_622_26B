package org.example.control;

import org.example.model.Perro;
import org.example.model.RegistroMedico;
import org.example.model.Refugio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControlAgenda {
    private final Refugio refugio;
    private long siguienteIdRegistro = 1L;

    public ControlAgenda(Refugio refugio) {
        this.refugio = Objects.requireNonNull(refugio, "El refugio es obligatorio");
    }

    public List<Perro> listarPerros() {
        return refugio.listarPerros();
    }

    public Perro obtenerPerro(Long idPerro) {
        Perro perro = refugio.obtenerFichaPerro(idPerro);
        if (perro == null) {
            throw new IllegalArgumentException("No se encontró un perro con ese ID");
        }
        return perro;
    }

    public synchronized RegistroMedico registrarEnAgenda(Long idPerro, String tipo, String detalle, LocalDate fecha) {
        Perro perro = obtenerPerro(idPerro);
        RegistroMedico.Tipo tipoRegistro = convertirTipo(tipo);
        if (detalle == null || detalle.isBlank()) {
            throw new IllegalArgumentException("El detalle de la vacuna o control es obligatorio");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        RegistroMedico registro = new RegistroMedico(siguienteIdRegistro++, tipoRegistro, detalle.trim(), fecha);
        perro.agregarRegistroMedico(registro);
        return registro;
    }


    public List<RegistroMedico> consultarAgenda(Long idPerro) {
        List<RegistroMedico> agenda = new ArrayList<>();
        for (RegistroMedico registro : obtenerPerro(idPerro).getRegistrosMedicos()) {
            int posicion = 0;
            while (posicion < agenda.size() && !agenda.get(posicion).getFecha().isAfter(registro.getFecha())) {
                posicion++;
            }
            agenda.add(posicion, registro);
        }
        return agenda;
    }

    private RegistroMedico.Tipo convertirTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de registro es obligatorio");
        }
        try {
            return RegistroMedico.Tipo.valueOf(tipo.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("El tipo de registro debe ser vacuna o control médico", exception);
        }
    }
}
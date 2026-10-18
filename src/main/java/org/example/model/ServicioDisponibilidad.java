package org.example.model;
import java.time.LocalDate;

public interface ServicioDisponibilidad {
    boolean estaLibre(LocalDate fecha);
}

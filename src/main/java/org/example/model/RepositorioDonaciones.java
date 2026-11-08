package org.example.model;

import java.util.List;

public interface RepositorioDonaciones {
    List<Donacion> listarDonaciones();

    Donacion obtenerDonacion(Long idDonacion);
}

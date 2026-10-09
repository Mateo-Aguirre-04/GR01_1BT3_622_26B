package org.example.control;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Proxy;
import java.time.LocalDate;
import java.util.Map;

final class SolicitudRegistroTestFactory {
    private SolicitudRegistroTestFactory() {
    }

    static HttpServletRequest crear(Long idPerro, String nombre, int edad, String caracteristicas,
                                    String estadoSalud, String espacioAsignado, boolean disponible,
                                    LocalDate fechaIngreso, Long idRefugio) {
        Map<String, String> parametros = Map.of(
                "idPerro", idPerro.toString(),
                "nombre", nombre,
                "edad", Integer.toString(edad),
                "caracteristicas", caracteristicas,
                "estadoSalud", estadoSalud,
                "espacioAsignado", espacioAsignado,
                "disponible", Boolean.toString(disponible),
                "fechaIngreso", fechaIngreso.toString(),
                "idRefugio", idRefugio.toString());
        return (HttpServletRequest) Proxy.newProxyInstance(
                HttpServletRequest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class},
                (proxy, method, args) -> {
                    if ("getParameter".equals(method.getName())) {
                        return parametros.get((String) args[0]);
                    }
                    throw new UnsupportedOperationException(method.getName());
                });
    }
}

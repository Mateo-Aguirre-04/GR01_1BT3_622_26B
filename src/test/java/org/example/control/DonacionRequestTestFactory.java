package org.example.control;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Proxy;
import java.util.Map;

final class DonacionRequestTestFactory {
    private DonacionRequestTestFactory() {
    }

    static HttpServletRequest crear(Map<String, String> datos, String[] materiales, String[] cantidades) {
        return (HttpServletRequest) Proxy.newProxyInstance(
                HttpServletRequest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class},
                (proxy, method, args) -> {
                    if ("getParameter".equals(method.getName())) {
                        return datos.get((String) args[0]);
                    }
                    if ("getParameterValues".equals(method.getName())) {
                        if ("material".equals(args[0])) {
                            return materiales;
                        }
                        if ("cantidad".equals(args[0])) {
                            return cantidades;
                        }
                        return null;
                    }
                    throw new UnsupportedOperationException(method.getName());
                });
    }

    static Map<String, String> contactoCompleto() {
        return Map.of(
                "nombres", "Ana",
                "apellidos", "Pérez",
                "telefono", "0991234567",
                "correo", "ana@correo.com",
                "descripcion", "Donación de la familia Pérez");
    }
}

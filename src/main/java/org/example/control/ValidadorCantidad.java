package org.example.control;

public class ValidadorCantidad {
    static final String CANTIDAD_NO_VALIDA = "Debe ingresar una cantidad válida.";

    public int convertir(String cantidadTexto) {
        if (cantidadTexto == null || cantidadTexto.isBlank()) {
            throw new IllegalArgumentException(CANTIDAD_NO_VALIDA);
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadTexto.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(CANTIDAD_NO_VALIDA, exception);
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException(CANTIDAD_NO_VALIDA);
        }
        return cantidad;
    }
}
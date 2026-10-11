package org.example.model;

public class Adoptante {
    private final String nombres;
    private final String apellidos;
    private final String telefono;
    private final String correo;

    public Adoptante(String nombres, String apellidos, String telefono, String correo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public boolean datosCompletos() {
        return nombres != null && !nombres.isBlank()
                && apellidos != null && !apellidos.isBlank()
                && telefono != null && !telefono.isBlank()
                && correo != null && !correo.isBlank();
    }
}

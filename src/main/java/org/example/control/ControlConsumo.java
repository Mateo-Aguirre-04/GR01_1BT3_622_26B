package org.example.control;

import org.example.model.Inventario;
import org.example.model.Material;
import org.example.model.MovimientoSalida;
import org.example.model.RegistroMovimientos;

import java.util.List;
import java.util.Objects;

public class ControlConsumo {
    private static final String SIN_EXISTENCIAS = "No existen suficientes unidades disponibles.";

    private final Inventario inventario;
    private final RegistroMovimientos registroMovimientos;
    private final ValidadorCantidad validadorCantidad = new ValidadorCantidad();

    public ControlConsumo(Inventario inventario, RegistroMovimientos registroMovimientos) {
        this.inventario = Objects.requireNonNull(inventario, "El inventario es obligatorio");
        this.registroMovimientos = Objects.requireNonNull(registroMovimientos, "El registro de movimientos es obligatorio");
    }

    public List<Material> listarMateriales() {
        return inventario.listarMateriales();
    }

    public synchronized String registrarConsumo(String nombreMaterial, String cantidadTexto, String motivo) {
        int cantidad = validadorCantidad.convertir(cantidadTexto);
        Material material = inventario.buscar(nombreMaterial);
        if (material == null) {
            throw new IllegalArgumentException("El material seleccionado no existe en el inventario.");
        }
        int stockRestante = material.getStock() - cantidad;
        if (stockRestante < 0) {
            throw new IllegalArgumentException(SIN_EXISTENCIAS);
        }
        material.descontar(cantidad);
        registroMovimientos.guardar(new MovimientoSalida(material.getNombre(), cantidad, material.getUnidad(), motivo));
        return "Consumo registrado: " + material.getNombre()
                + ", cantidad consumida " + cantidad + " " + material.getUnidad()
                + ", stock restante " + stockRestante + " " + material.getUnidad() + ".";
    }
}
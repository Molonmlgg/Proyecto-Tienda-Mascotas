package modelo;

import excepciones.PresupuestoInsuficienteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase principal que gestiona el inventario de la tienda, el presupuesto y las mascotas.
 * Implementa el patrón de diseño Singleton para asegurar una única instancia global.
 * @author Cristóbal Araya Lillo
 */
public class Tienda {

    private static Tienda instanciaUnica;
    private double presupuesto;
    private List<Mascota> inventarioMascotas;
    private Map<TipoSuministro, Integer> inventarioSuministros;
    private MascotaFactory fabrica;

    /**
     * Constructor privado de Tienda.
     * Inicializa el presupuesto en un intervalo pequeño ($50.0), las listas de inventario
     * y otorga 10 unidades de cada suministro inicial.
     */
    private Tienda() {
        this.presupuesto = 50.0;
        this.inventarioMascotas = new ArrayList<>();
        this.inventarioSuministros = new HashMap<>();
        this.fabrica = new MascotaFactory();

        for (TipoSuministro tipo : TipoSuministro.values()) {
            inventarioSuministros.put(tipo, 10);
        }
    }

    public static Tienda getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new Tienda();
        }
        return instanciaUnica;
    }

    public void comprarMascota(String tipo, String nombre, double precio) throws PresupuestoInsuficienteException {
        if (precio > presupuesto) {
            throw new PresupuestoInsuficienteException("No tienes dinero para comprar a " + nombre);
        }
        Mascota nuevaMascota = fabrica.crearMascota(tipo, nombre, precio);
        if (nuevaMascota != null) {
            this.presupuesto -= precio;
            this.inventarioMascotas.add(nuevaMascota);
        }
    }

    public void venderMascota(Mascota mascota, double precioVenta) {
        if (inventarioMascotas.remove(mascota)) {
            this.presupuesto += precioVenta;
        }
    }

    public void comprarSuministro(TipoSuministro tipo, int cantidad, double costoTotal) throws PresupuestoInsuficienteException {
        if (costoTotal > presupuesto) {
            throw new PresupuestoInsuficienteException("No tienes fondos para comprar suministros.");
        }
        this.presupuesto -= costoTotal;
        inventarioSuministros.put(tipo, inventarioSuministros.get(tipo) + cantidad);
    }

    public boolean consumirSuministro(TipoSuministro tipo) {
        int cantidadActual = inventarioSuministros.getOrDefault(tipo, 0);
        if (cantidadActual > 0) {
            inventarioSuministros.put(tipo, cantidadActual - 1);
            return true;
        }
        return false;
    }

    public Mascota buscarMascota(String nombre) {
        for (Mascota m : inventarioMascotas) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                return m;
            }
        }
        return null;
    }

    public double getPresupuesto() { return presupuesto; }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = Math.max(0, presupuesto);
    }

    public void agregarPresupuesto(double cantidad) {
        this.presupuesto += cantidad;
    }

    public List<Mascota> getInventarioMascotas() { return inventarioMascotas; }
    public Map<TipoSuministro, Integer> getInventarioSuministros() { return inventarioSuministros; }
}
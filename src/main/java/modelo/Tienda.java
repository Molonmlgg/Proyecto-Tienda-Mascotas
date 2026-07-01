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

        if (tipo == null || tipo.isBlank()){
            throw new IllegalArgumentException("Debe indicar el tipo de mascota.");
        }

        if (existeMascota(nombre)) {
            throw new IllegalArgumentException("Ya existe una mascota con ese nombre.");
        }

        if (nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("Debe indicar el nombre de la mascota.");
        }

        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

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

        if (mascota == null){
            return;
        }

        if (inventarioMascotas.remove(mascota)) {
            agregarPresupuesto(precioVenta);
        }
    }

    public void comprarSuministro(TipoSuministro tipo, int cantidad, double costoTotal) throws PresupuestoInsuficienteException {

        if (tipo == null){
            throw new IllegalArgumentException("Debe indicar un tipo de suministro.");
        }
        if (cantidad <= 0){
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (costoTotal < 0){
            throw new IllegalArgumentException("El costo no puede ser negativo.");
        }
        if (costoTotal > presupuesto) {
            throw new PresupuestoInsuficienteException("No tienes fondos para comprar suministros.");
        }
        this.presupuesto -= costoTotal;
        inventarioSuministros.put(tipo, inventarioSuministros.get(tipo) + cantidad);
    }

    public boolean consumirSuministro(TipoSuministro tipo) {

        if (tipo == null){
            return false;
        }

        int cantidadActual = inventarioSuministros.getOrDefault(tipo, 0);

        if (cantidadActual > 0) {
            inventarioSuministros.put(tipo, cantidadActual - 1);
            return true;
        }
        return false;
    }

    public Mascota buscarMascota(String nombre) {

        if (nombre == null || nombre.isBlank()){
            return null;
        }

        for (Mascota mascota : inventarioMascotas) {
            if (mascota.getNombre().equalsIgnoreCase(nombre)) {
                return mascota;
            }
        }
        return null;
    }

    public boolean existeMascota(String nombre){
        return buscarMascota(nombre) != null;
    }

    public int getCantidadMascotas(){
        return inventarioMascotas.size();
    }

    public int getCantidadSuministro(TipoSuministro tipo){
        return inventarioSuministros.getOrDefault(tipo, 0);
    }

    public void agregarPresupuesto(double cantidad){
        this.presupuesto += cantidad;
        if (this.presupuesto < 0){
            this.presupuesto = 0;
        }
    }

    public double getPresupuesto() { return presupuesto; }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = Math.max(0, presupuesto);
    }

    public List<Mascota> getInventarioMascotas() { return inventarioMascotas; }
    public Map<TipoSuministro, Integer> getInventarioSuministros() { return inventarioSuministros; }
}
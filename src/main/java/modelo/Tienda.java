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

    /**
     * Obtiene la instancia única de la tienda (Patrón Singleton).
     * @return La instancia única de la clase Tienda.
     */
    public static Tienda getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new Tienda();
        }
        return instanciaUnica;
    }

    /**
     * Gestiona la compra de una nueva mascota, validando presupuesto y datos.
     * @param tipo Tipo de mascota a comprar.
     * @param nombre Nombre asignado a la mascota.
     * @param precio Precio de la mascota.
     * @throws PresupuestoInsuficienteException Si el costo excede el presupuesto disponible.
     * @throws IllegalArgumentException Si los parámetros son nulos o vacíos.
     */
    public void comprarMascota(String tipo, String nombre, double precio) throws PresupuestoInsuficienteException {

        if (tipo == null || tipo.isBlank()){
            throw new IllegalArgumentException("Debe indicar el tipo de mascota.");
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

    /**
     * Elimina una mascota del inventario y suma el precio de venta al presupuesto.
     * @param mascota La mascota a vender.
     * @param precioVenta El monto obtenido por la venta.
     */
    public void venderMascota(Mascota mascota, double precioVenta) {

        if (mascota == null){
            return;
        }

        if (inventarioMascotas.remove(mascota)) {
            agregarPresupuesto(precioVenta);
        }
    }

    /**
     * Gestiona la compra de suministros y actualiza el stock disponible.
     * @param tipo Tipo de suministro a comprar.
     * @param cantidad Cantidad de unidades a adquirir.
     * @param costoTotal Costo total de la operación.
     * @throws PresupuestoInsuficienteException Si los fondos son insuficientes.
     * @throws IllegalArgumentException Si la cantidad es menor o igual a cero o costo negativo.
     */
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

    /**
     * Reduce la cantidad disponible de un suministro específico.
     * @param tipo El tipo de suministro a consumir.
     * @return true si la operación fue exitosa, false si no hay stock suficiente o el tipo es nulo.
     */
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

    /**
     * Busca una mascota en el inventario por su nombre.
     * @param nombre El nombre de la mascota a buscar.
     * @return La mascota encontrada o null si no existe.
     */
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

    /**
     * Verifica si una mascota existe en el inventario.
     * @param nombre El nombre de la mascota.
     * @return true si la mascota existe, false en caso contrario.
     */
    public boolean existeMascota(String nombre){
        return buscarMascota(nombre) != null;
    }

    /**
     * Obtiene el número total de mascotas en el inventario.
     * @return Cantidad de mascotas.
     */
    public int getCantidadMascotas(){
        return inventarioMascotas.size();
    }

    /**
     * Obtiene la cantidad de unidades disponibles de un suministro.
     * @param tipo El tipo de suministro a consultar.
     * @return Cantidad disponible.
     */
    public int getCantidadSuministro(TipoSuministro tipo){
        return inventarioSuministros.getOrDefault(tipo, 0);
    }

    /**
     * Agrega dinero al presupuesto total, asegurando que no sea negativo.
     * @param cantidad Cantidad a añadir al presupuesto.
     */
    public void agregarPresupuesto(double cantidad){
        this.presupuesto += cantidad;
        if (this.presupuesto < 0){
            this.presupuesto = 0;
        }
    }

    /**
     * Obtiene el presupuesto actual de la tienda.
     * @return El presupuesto actual.
     */
    public double getPresupuesto() { return presupuesto; }

    /**
     * Establece un nuevo presupuesto, asegurando un valor mínimo de 0.
     * @param presupuesto El nuevo valor del presupuesto.
     */
    public void setPresupuesto(double presupuesto) {
        this.presupuesto = Math.max(0, presupuesto);
    }

    /**
     * Obtiene la lista actual de mascotas en el inventario.
     * @return Lista de mascotas.
     */
    public List<Mascota> getInventarioMascotas() { return inventarioMascotas; }

    /**
     * Obtiene el mapa que contiene el inventario de suministros.
     * @return El mapa de suministros y sus cantidades.
     */
    public Map<TipoSuministro, Integer> getInventarioSuministros() { return inventarioSuministros; }
}
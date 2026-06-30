package modelo;

import excepciones.PresupuestoInsuficienteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase principal que gestiona el inventario de mascotas, suministros y el presupuesto del jugador.
 * Implementa el patrón de diseño Singleton para asegurar que solo exista una instancia
 * de la tienda durante toda la ejecución del juego.
 * * @author Cristóbal Araya Lillo
 */
public class Tienda {

    private static Tienda instanciaUnica;

    private double presupuesto;
    private List<Mascota> inventarioMascotas;
    private Map<TipoSuministro, Integer> inventarioSuministros;
    private MascotaFactory fabrica;

    /**
     * Constructor privado para evitar que otras clases usen "new Tienda()".
     * Parte del patrón Singleton.
     */
    private Tienda() {
        presupuesto = 1000;
        inventarioMascotas = new ArrayList<>();
        inventarioSuministros = new HashMap<>();
        fabrica = new MascotaFactory();

        // Inicializar el inventario de suministros en 0 usando el Enum
        for (TipoSuministro tipo : TipoSuministro.values()) {
            inventarioSuministros.put(tipo, 0);
        }
    }

    /**
     * Obtiene la única instancia válida de la Tienda.
     * @return La instancia Singleton de Tienda.
     */
    public static Tienda getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new Tienda();
        }
        return instanciaUnica;
    }

    /**
     * Compra una mascota y la añade al inventario.
     * @param tipo El tipo de mascota ("perro", "gato", etc.)
     * @param nombre El nombre de la mascota.
     * @param precio El costo de la mascota.
     * @throws PresupuestoInsuficienteException Si el precio supera el dinero disponible.
     */
    public void comprarMascota(String tipo, String nombre, double precio)
            throws PresupuestoInsuficienteException {
        if (nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("Debe ingresar un nombre.");
        }
        if (precio <= 0){
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }

        descontarPresupuesto(precio);

        Mascota nuevaMascota = fabrica.crearMascota(tipo, nombre, precio);
        if (nuevaMascota == null){
            return;
        }
        inventarioMascotas.add(nuevaMascota);

    }

    /**
     * Vende una mascota a un cliente virtual, eliminándola del inventario y aumentando el presupuesto.
     * @param mascota La mascota a vender.
     * @param precioVenta El dinero que paga el cliente virtual.
     */
    public void venderMascota(Mascota mascota, double precioVenta) {
        if (mascota == null){
            return;
        }
        if (inventarioMascotas.contains(mascota)) {
            inventarioMascotas.remove(mascota);
            aumentarPresupuesto(precioVenta);
            System.out.println("Has vendido a " + mascota.getNombre() + " por $" + precioVenta);
        }
    }

    /**
     * Busca una mascota por su nombre
     * @param nombre nombre de la mascota
     * @return la mascota encontrada o null si no existe
     */
    public Mascota buscarMascota(String nombre) {
        if (nombre == null){
            return null;
        }
        for (Mascota mascota : inventarioMascotas){
            if (mascota.getNombre().equalsIgnoreCase(nombre)){
                return mascota;
            }
        }
        return null;
    }

    /**
     * Verifica si una mascota existe
     * @param nombre nombre de la mascota
     * @return true si existe
     */
    public boolean existeMascota(String nombre){
        return buscarMascota(nombre) != null;
    }

    /**
     * Compra un suministro específico para la tienda.
     * @param tipo El tipo de suministro definido en el Enum.
     * @param cantidad La cantidad a comprar.
     * @param costoTotal El costo total de la compra.
     * @throws PresupuestoInsuficienteException Si no hay fondos.
     */
    public void comprarSuministro(TipoSuministro tipo, int cantidad, double costoTotal) throws PresupuestoInsuficienteException {
        if (cantidad <= 0){
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (costoTotal <= 0){
            throw new IllegalArgumentException("El costo debe ser mayor que cero.");
        }

        descontarPresupuesto(costoTotal);
        int cantidadActual = inventarioSuministros.get(tipo);
        inventarioSuministros.put(tipo, cantidadActual + cantidad);
    }

    /**
     * Consume una cantidad de un suministro
     * @param tipo tipo de suministro
     * @return true si pudo consumirlo
     */
    public boolean consumirSuministro(TipoSuministro tipo){
        if (tipo == null){
            return false;
        }
        int cantidad = inventarioSuministros.get(tipo);
        if (cantidad > 0){
            inventarioSuministros.put(tipo, cantidad-1);
            return true;
        }
        return false;
    }

    /**
     * Obtiene la cantidad disponible de un suministro
     * @param tipo tipo de suministro
     * @return cantidad disponible
     */
    public int getCantidadSuministro(TipoSuministro tipo){
        return inventarioSuministros.get(tipo);
    }

    /**
     * Obtiene la cantidad de mascotas
     * @return numero de mascotas
     */
    public int getCantidadMascota(){
        return inventarioMascotas.size();
    }

    /**
     * Descuenta dinero del presupuesto
     * @param monto dinero a descontar
     * @throws PresupuestoInsuficienteException si no hay fondos
     */
    private void descontarPresupuesto(double monto) throws PresupuestoInsuficienteException{
        if (monto > presupuesto){
            throw new PresupuestoInsuficienteException("No tienes presupuesto suficiente.");
        }
        presupuesto -= monto;
    }

    /**
     * Aumenta el presupuesto
     * @param monto dinero a agregar
     */
    private void aumentarPresupuesto(double monto){
        if (monto > 0){
            presupuesto += monto;
        }
    }

    // Getters y Setters

    /**
     * Obtiene el presupuesto disponible
     * @return presupuesto
     */
    public double getPresupuesto() { return presupuesto; }

    /**
     * Modifica el presupuesto
     * @param presupuesto nuevo presupuesto
     */
    public void setPresupuesto(double presupuesto) { this.presupuesto = presupuesto; }

    /**
     * Obtiene el invetario de mascotas
     * @return lista de mascotas
     */
    public List<Mascota> getInventarioMascotas() { return inventarioMascotas; }

    /**
     * Obtiene el inventario de suministros
     * @return mapa de suministros
     */
    public Map<TipoSuministro, Integer> getInventarioSuministros() { return inventarioSuministros; }
}
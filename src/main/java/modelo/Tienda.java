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
        this.presupuesto = 1000.0;
        this.inventarioMascotas = new ArrayList<>();
        this.inventarioSuministros = new HashMap<>();
        this.fabrica = new MascotaFactory();

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
    public void comprarMascota(String tipo, String nombre, double precio) throws PresupuestoInsuficienteException {
        if (precio > presupuesto) {
            throw new PresupuestoInsuficienteException("No tienes dinero para comprar a " + nombre);
        }

        Mascota nuevaMascota = fabrica.crearMascota(tipo, nombre, precio);
        if (nuevaMascota != null) {
            this.presupuesto -= precio;
            this.inventarioMascotas.add(nuevaMascota);
            System.out.println("Has comprado un " + tipo + " llamado " + nombre);
        }
    }

    /**
     * Vende una mascota a un cliente virtual, eliminándola del inventario y aumentando el presupuesto.
     * @param mascota La mascota a vender.
     * @param precioVenta El dinero que paga el cliente virtual.
     */
    public void venderMascota(Mascota mascota, double precioVenta) {
        if (inventarioMascotas.contains(mascota)) {
            inventarioMascotas.remove(mascota);
            this.presupuesto += precioVenta;
            System.out.println("Has vendido a " + mascota.getNombre() + " por $" + precioVenta);
        }
    }

    /**
     * Compra un suministro específico para la tienda.
     * @param tipo El tipo de suministro definido en el Enum.
     * @param cantidad La cantidad a comprar.
     * @param costoTotal El costo total de la compra.
     * @throws PresupuestoInsuficienteException Si no hay fondos.
     */
    public void comprarSuministro(TipoSuministro tipo, int cantidad, double costoTotal) throws PresupuestoInsuficienteException {
        if (costoTotal > presupuesto) {
            throw new PresupuestoInsuficienteException("No tienes fondos para comprar suministros.");
        }
        this.presupuesto -= costoTotal;
        inventarioSuministros.put(tipo, inventarioSuministros.get(tipo) + cantidad);
    }

    // Getters y Setters
    public double getPresupuesto() { return presupuesto; }
    public void setPresupuesto(double presupuesto) { this.presupuesto = presupuesto; }
    public List<Mascota> getInventarioMascotas() { return inventarioMascotas; }
    public Map<TipoSuministro, Integer> getInventarioSuministros() { return inventarioSuministros; }
}
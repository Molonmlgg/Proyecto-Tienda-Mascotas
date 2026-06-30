package modelo;

import excepciones.PresupuestoInsuficienteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase principal que gestiona el inventario de la tienda, el presupuesto y las mascotas.
 * Implementa el patrón de diseño Singleton para asegurar una única instancia global.
 * * @author Cristóbal Araya Lillo
 */
public class Tienda {

    private static Tienda instanciaUnica;
    private double presupuesto;
    private List<Mascota> inventarioMascotas;
    private Map<TipoSuministro, Integer> inventarioSuministros;
    private MascotaFactory fabrica;

    /**
     * Constructor privado de Tienda.
     * Inicializa el presupuesto, las listas de inventario y otorga 10 unidades
     * de cada suministro inicial para poder comenzar el simulador.
     */
    private Tienda() {
        this.presupuesto = 1000.0;
        this.inventarioMascotas = new ArrayList<>();
        this.inventarioSuministros = new HashMap<>();
        this.fabrica = new MascotaFactory();

        for (TipoSuministro tipo : TipoSuministro.values()) {
            inventarioSuministros.put(tipo, 10);
        }
    }

    /**
     * Obtiene la instancia única de la Tienda.
     * * @return instancia de la clase Tienda.
     */
    public static Tienda getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new Tienda();
        }
        return instanciaUnica;
    }

    /**
     * Permite comprar una mascota y agregarla al inventario si hay fondos suficientes.
     * * @param tipo El tipo de mascota a crear (ej. "perro", "gato").
     * @param nombre El nombre que se le asignará a la mascota.
     * @param precio El costo de la mascota a descontar del presupuesto.
     * @throws PresupuestoInsuficienteException si el precio supera el presupuesto actual.
     */
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

    /**
     * Vende una mascota y la remueve del inventario, aumentando el presupuesto.
     * * @param mascota La mascota que se desea vender.
     * @param precioVenta El monto de dinero que se sumará al presupuesto.
     */
    public void venderMascota(Mascota mascota, double precioVenta) {
        if (inventarioMascotas.remove(mascota)) {
            this.presupuesto += precioVenta;
        }
    }

    /**
     * Compra unidades de un suministro específico para la tienda.
     * * @param tipo El tipo de suministro a comprar (enum TipoSuministro).
     * @param cantidad La cantidad de unidades a agregar al inventario.
     * @param costoTotal El costo total de la transacción a descontar.
     * @throws PresupuestoInsuficienteException si el costo supera el presupuesto.
     */
    public void comprarSuministro(TipoSuministro tipo, int cantidad, double costoTotal) throws PresupuestoInsuficienteException {
        if (costoTotal > presupuesto) {
            throw new PresupuestoInsuficienteException("No tienes fondos para comprar suministros.");
        }
        this.presupuesto -= costoTotal;
        inventarioSuministros.put(tipo, inventarioSuministros.get(tipo) + cantidad);
    }

    /**
     * Consume una unidad de un suministro específico si existe disponibilidad.
     * * @param tipo El tipo de suministro que se desea consumir.
     * @return true si el suministro fue consumido exitosamente, false si no había stock.
     */
    public boolean consumirSuministro(TipoSuministro tipo) {
        int cantidadActual = inventarioSuministros.getOrDefault(tipo, 0);
        if (cantidadActual > 0) {
            inventarioSuministros.put(tipo, cantidadActual - 1);
            return true;
        }
        return false;
    }

    /**
     * Busca una mascota en el inventario actual mediante su nombre.
     * * @param nombre El nombre exacto de la mascota a buscar.
     * @return La mascota encontrada o null si no existe en el inventario.
     */
    public Mascota buscarMascota(String nombre) {
        for (Mascota m : inventarioMascotas) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Obtiene el presupuesto actual de la tienda.
     * @return cantidad de dinero disponible.
     */
    public double getPresupuesto() { return presupuesto; }

    /**
     * Obtiene la lista completa de mascotas en el inventario.
     * @return lista de mascotas.
     */
    public List<Mascota> getInventarioMascotas() { return inventarioMascotas; }

    /**
     * Obtiene el mapa completo de los suministros actuales y sus cantidades.
     * @return mapa de suministros.
     */
    public Map<TipoSuministro, Integer> getInventarioSuministros() { return inventarioSuministros; }
}
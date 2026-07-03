package controlador;

import comandos.AccionCuidado;
import excepciones.PresupuestoInsuficienteException;
import modelo.Mascota;
import modelo.Tienda;

/**
 * Controlador principal del simulador.
 * Delega las acciones específicas a objetos del patrón Command y gestiona
 * el estado general de la partida (compras, ventas y selección de mascotas).
 */
public class Juego {

    private final Tienda tienda;
    private Mascota mascotaActiva;

    /**
     * Constructor del juego.
     * Inicializa la instancia única de la tienda y establece la mascota activa en null.
     */
    public Juego() {
        this.tienda = Tienda.getInstance();
        this.mascotaActiva = null;
    }

    /**
     * Compra una nueva mascota y la establece como activa.
     * @param tipo El tipo de mascota ("perro", "gato", "pajaro").
     * @param nombre El nombre asignado a la mascota.
     * @param precio El costo de la adopción.
     * @throws PresupuestoInsuficienteException Si no hay fondos suficientes.
     */
    public void comprarMascota(String tipo, String nombre, double precio) throws PresupuestoInsuficienteException {
        tienda.comprarMascota(tipo, nombre, precio);
        Mascota mascota = tienda.buscarMascota(nombre);
        if (mascota != null){
            mascotaActiva = mascota;
        }
    }

    /**
     * Vende la mascota activa actual y limpia la referencia.
     * @param precioVenta El monto que se sumará al presupuesto de la tienda.
     */
    public void venderMascota(double precioVenta) {
        if (mascotaActiva != null) {
            tienda.venderMascota(mascotaActiva, precioVenta);
            mascotaActiva = null;
        }
    }

    /**
     * Cambia la mascota activa del simulador.
     * @param mascota La nueva mascota a controlar.
     */
    public void seleccionarMascota(Mascota mascota) {
        this.mascotaActiva = mascota;
    }

    /**
     * Ejecuta una acción encapsulada bajo el patrón Command.
     * @param comando La acción a ejecutar (Alimentar, Jugar, Curar, Limpiar).
     * @return true si la acción se ejecutó correctamente, false en caso contrario.
     */
    public boolean ejecutarAccion(AccionCuidado comando) {
        return comando.ejecutar(mascotaActiva, tienda);
    }

    /**
     * Obtiene la mascota activa.
     * @return Mascota activa.
     */
    public Mascota getMascotaActiva() {
        return mascotaActiva;
    }

    /**
     * Obtiene la instancia de la tienda.
     * @return Tienda principal.
     */
    public Tienda getTienda() {
        return tienda;
    }


/**
 * Elimina la mascota activa actual debido a que su salud llegó a cero,
 * sin otorgar dinero a cambio (a diferencia de venderMascota).
 */
public void perderMascotaActual() {
    if (mascotaActiva != null) {
        // La eliminamos del inventario de la tienda pasando un precio de venta 0
        tienda.venderMascota(mascotaActiva, 0.0);

        // Limpiamos la referencia activa
        mascotaActiva = null;
    }
}
}
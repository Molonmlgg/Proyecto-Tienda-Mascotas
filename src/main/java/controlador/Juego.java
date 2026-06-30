package controlador;

import excepciones.PresupuestoInsuficienteException;
import modelo.*;

/**
 * Controlador principal del simulador.
 * Coordina la comunicación entre la vista (VentanaPrincipal) y el modelo (Tienda y Mascota).
 * * @author Cristóbal Araya Lillo
 */
public class Juego {

    private final Tienda tienda;
    private Mascota mascotaActiva;

    /**
     * Constructor de la clase Juego.
     * Inicializa la referencia al Singleton de la tienda y establece la mascota activa en nulo.
     */
    public Juego() {
        this.tienda = Tienda.getInstance();
        this.mascotaActiva = null;
    }

    /**
     * Solicita a la tienda comprar una mascota y la establece como la mascota activa.
     * * @param tipo El tipo de mascota (ej. "perro", "gato").
     * @param nombre El nombre que tendrá la nueva mascota.
     * @param precio El precio de compra de la mascota.
     * @throws PresupuestoInsuficienteException si la tienda no tiene fondos suficientes.
     */
    public void comprarMascota(String tipo, String nombre, double precio) throws PresupuestoInsuficienteException {
        tienda.comprarMascota(tipo, nombre, precio);
        this.mascotaActiva = tienda.buscarMascota(nombre);
    }

    /**
     * Vende la mascota activa actual a través de la tienda y limpia la referencia activa.
     * * @param precioVenta El monto por el cual será vendida la mascota.
     */
    public void venderMascota(double precioVenta) {
        if (mascotaActiva == null) return;
        tienda.venderMascota(mascotaActiva, precioVenta);
        this.mascotaActiva = null;
    }

    /**
     * Intenta alimentar a la mascota activa consumiendo Comida Premium.
     * * @return true si la mascota fue alimentada exitosamente, false si no hay mascota activa o falta comida en la tienda.
     */
    public boolean alimentarMascota() {
        if (mascotaActiva == null) return false;
        if (tienda.consumirSuministro(TipoSuministro.COMIDA_PREMIUM)) {
            mascotaActiva.alimentar();
            return true;
        }
        return false;
    }

    /**
     * Interactúa con la mascota activa llamando a su método de jugar,
     * validando previamente que implemente la interfaz Jugable.
     * * @return true si la mascota jugó correctamente, false si no hay mascota o no es de tipo Jugable.
     */
    public boolean jugarConMascota() {
        if (mascotaActiva == null) return false;
        if (mascotaActiva instanceof Jugable) {
            ((Jugable) mascotaActiva).jugar();
            return true;
        }
        return false;
    }

    /**
     * Intenta curar a la mascota activa consumiendo Medicina Básica.
     * * @return true si la curación fue exitosa, false si no hay mascota activa o falta medicina en la tienda.
     */
    public boolean curarMascota() {
        if (mascotaActiva == null) return false;
        if (tienda.consumirSuministro(TipoSuministro.MEDICINA_BASICA)) {
            mascotaActiva.curar(30);
            return true;
        }
        return false;
    }

    /**
     * Realiza un proceso de limpieza en la mascota activa, restaurando su nivel de higiene.
     * * @return true si se limpió la mascota, false si no hay ninguna mascota activa seleccionada.
     */
    public boolean limpiarMascota() {
        if (mascotaActiva == null) return false;
        mascotaActiva.limpiar(30);
        return true;
    }

    /**
     * Establece una nueva mascota como la mascota activa del jugador.
     * * @param mascota La mascota que pasará a estar activa.
     */
    public void seleccionarMascota(Mascota mascota) {
        this.mascotaActiva = mascota;
    }

    /**
     * Obtiene la mascota con la que el jugador está interactuando actualmente.
     * @return la mascota activa.
     */
    public Mascota getMascotaActiva() { return mascotaActiva; }

    /**
     * Obtiene la instancia de la tienda asociada al juego actual.
     * @return la tienda Singleton.
     */
    public Tienda getTienda() { return tienda; }
}
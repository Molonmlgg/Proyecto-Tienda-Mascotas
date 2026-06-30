package controlador;

import excepciones.PresupuestoInsuficienteException;
import modelo.*;

/**
 * Controlador principal del simulador
 * Coordina la comunicacion entre la vista y el modelo
 */
public class Juego {

    private Tienda tienda;
    private Mascota mascotaActiva;

    /**
     * Constructor del juego.
     * Inicializa la tienda.
     */
    public Juego(){
        tienda = Tienda.getInstance();
        mascotaActiva = null;
    }

    /**
     * Compra una mascota y la deja como mascota activa.
     * @param tipo tipo de mascota
     * @param nombre nombre de la mascota
     * @param precio precio de compra
     * @throws PresupuestoInsuficienteException si no hay dinero suficiente
     */
    public void comprarMascota(String tipo, String nombre, double precio)
        throws PresupuestoInsuficienteException{
        tienda.comprarMascota(tipo, nombre, precio);
        mascotaActiva = tienda.buscarMascota(nombre);
    }

    /**
     * Vende la mascota activa.
     * @param precioVenta precio por el cual será vendida.
     */
    public void venderMascota(double precioVenta){
        if (mascotaActiva == null){
            return;
        }
        tienda.venderMascota(mascotaActiva, precioVenta);
        mascotaActiva = null;
    }

    /**
     * Alimenta la mascota activa.
     * Consume una unidad de comida antes de alimentar
     */
    public void alimentarMascota(){
        if (mascotaActiva == null){
            return;
        }
        if (tienda.consumirSuministro(TipoSuministro.COMIDA_PREMIUM)){
            mascotaActiva.alimentar();
        }
    }

    /**
     * Juega con la mascota activa
     */
    public void jugarConMascota() {
        if (mascotaActiva == null) {
            return;
        }
        if (mascotaActiva instanceof Jugable) {
            Jugable jugable = (Jugable) mascotaActiva;
            jugable.jugar();
        }
    }

    /**
     * Cura a la mascota
     */
    public void curarMascota(){
        if (mascotaActiva == null){
            return;
        }
        if (tienda.consumirSuministro(TipoSuministro.MEDICINA_BASICA)){
            mascotaActiva.curar(30);
        }
    }

    /**
     * Limpia a la mascota
     */
    public void limpiarMascota(){
        if (mascotaActiva == null){
            return;
        }
        mascotaActiva.limpiar(30);
    }

    /**
     * Cambia la mascota activa
     * @param mascota nueva mascota activa
     */
    public void seleccionarMascota(Mascota mascota){
        mascotaActiva = mascota;
    }


    /**
     * Obtiene la mascota activa
     * @return mascota activa
     */
    public Mascota getMascotaActiva(){
        return mascotaActiva;
    }

    /**
     * Obtiene la tienda del juego
     * @return tienda
     */
    public Tienda getTienda(){
        return tienda;
    }
}

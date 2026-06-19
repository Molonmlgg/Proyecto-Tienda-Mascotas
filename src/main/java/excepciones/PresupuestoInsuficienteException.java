package excepciones;

/**
 * Excepción lanzada cuando el jugador intenta comprar algo sin fondos suficientes.
 * @author Cristóbal Araya Lillo
 */
public class PresupuestoInsuficienteException extends Exception {
    public PresupuestoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
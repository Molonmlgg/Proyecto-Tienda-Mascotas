package excepciones;

/**
 * Excepción lanzada cuando el jugador intenta comprar algo sin fondos suficientes.
 * @author Cristóbal Araya Lillo
 */
public class PresupuestoInsuficienteException extends Exception {

    /**
     * Crea la excepción con un mensaje descriptivo del error.
     * @param mensaje detalle de por qué el presupuesto fue insuficiente
     */
    public PresupuestoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
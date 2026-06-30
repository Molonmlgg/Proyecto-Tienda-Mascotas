package comandos;

import modelo.Mascota;
import modelo.Tienda;

/**
 * Interfaz del patrón Command. Define el contrato para ejecutar
 * una acción sobre una mascota y gestionar sus recompensas/costos.
 * @author Cristóbal Araya Lillo
 */
public interface AccionCuidado {
    /**
     * Ejecuta la acción específica.
     * @param mascota la mascota sobre la que recae la acción.
     * @param tienda la tienda que provee suministros y maneja el dinero.
     * @return true si la acción se completó (había recursos), false si falló.
     */
    boolean ejecutar(Mascota mascota, Tienda tienda);
}
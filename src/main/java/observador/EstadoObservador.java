package observador;

import modelo.Mascota;

/**
 * Interfaz del patrón de diseño Observer.
 * Define el contrato que debe cumplir cualquier clase interesada en ser
 * notificada cuando una {@link Mascota} cambia su estado interno
 * (hambre, felicidad, higiene o salud).
 *
 * Se utiliza para desacoplar el modelo ({@code Mascota}) de la vista
 * ({@code VentanaPrincipal}), evitando que el controlador o la vista
 * deban llamar manualmente a un método de refresco cada vez que ocurre
 * una acción sobre la mascota.
 *
 * @author Cristóbal Araya Lillo
 */
public interface EstadoObservador {

    /**
     * Metodo invocado automáticamente por la mascota observada
     * cada vez que alguno de sus niveles de estado cambia.
     *
     * @param mascota la mascota cuyo estado acaba de cambiar.
     */
    void actualizarEstado(Mascota mascota);
}

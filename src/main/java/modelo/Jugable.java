package modelo;

/**
 * Interfaz que define el comportamiento interactivo para las mascotas
 * que permiten la acción de jugar con el usuario.
 * * @author Cristóbal Araya Lillo
 */
public interface Jugable {

    /**
     * Define la lógica de cómo la mascota interactúa al jugar,
     * afectando sus niveles de felicidad, higiene o hambre.
     */
    void jugar();
}
package modelo;

/**
 * Clase que representa a un Pájaro en el simulador.
 * Hereda de la clase abstracta Mascota e implementa la interfaz Jugable.
 * @author Cristóbal Araya Lillo
 */
public class Pajaro extends Mascota implements Jugable {

    /**
     * Crea un nuevo Pájaro.
     * @param nombre nombre asignado a la mascota
     * @param precioCompra precio pagado por la mascota
     */
    public Pajaro(String nombre, double precioCompra) {
        super(nombre, precioCompra);
    }

    /**
     * Emite el sonido característico del pájaro por consola.
     */
    @Override
    public void emitirSonido() {
        System.out.println(getNombre() + " canta: ¡Pío pío!");
    }

    /**
     * Alimenta al pájaro con semillas, reduciendo su hambre en 15
     * y aumentando su felicidad en 15.
     */
    @Override
    public void alimentar() {
        System.out.println("Alimentando a " + getNombre() + " con semillas.");
        setNivelHambre(getNivelHambre() - 15);
        setNivelFelicidad(getNivelFelicidad() + 15);
    }

    /**
     * Juega con el pájaro usando la campanita de su jaula. Aumenta la
     * felicidad en 15 y el hambre en 5.
     */
    @Override
    public void jugar() {
        System.out.println(getNombre() + " está jugando con la campanita de su jaula.");
        setNivelFelicidad(getNivelFelicidad() + 15);
        setNivelHambre(getNivelHambre() + 5);
    }
}
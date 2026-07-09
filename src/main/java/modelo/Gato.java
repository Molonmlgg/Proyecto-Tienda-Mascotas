package modelo;

/**
 * Clase que representa a un Gato en el simulador.
 * Hereda de la clase abstracta Mascota e implementa la interfaz Jugable.
 * @author Cristóbal Araya Lillo
 */
public class Gato extends Mascota implements Jugable {

    /**
     * Crea un nuevo Gato.
     * @param nombre nombre asignado a la mascota
     * @param precioCompra precio pagado por la mascota
     */
    public Gato(String nombre, double precioCompra) {
        super(nombre, precioCompra);
    }

    /**
     * Emite el sonido característico del gato por consola.
     */
    @Override
    public void emitirSonido() {
        System.out.println(getNombre() + " dice: ¡Miau miau!");
    }

    /**
     * Alimenta al gato con atún, reduciendo su hambre en 20
     * y aumentando su felicidad en 20.
     */
    @Override
    public void alimentar() {
        System.out.println("Alimentando a " + getNombre() + " con un poco de atún.");
        setNivelHambre(getNivelHambre() - 20);
        setNivelFelicidad(getNivelFelicidad() + 20);
    }

    /**
     * Juega con el gato usando un ovillo de lana. Aumenta la felicidad en 25,
     * el hambre en 10, y disminuye la higiene en 1.
     */
    @Override
    public void jugar() {
        System.out.println("Jugando con " + getNombre() + " y un ovillo de lana.");
        setNivelFelicidad(getNivelFelicidad() + 25);
        setNivelHambre(getNivelHambre() + 10);
        setHigiene(getHigiene() - 1);
    }
}
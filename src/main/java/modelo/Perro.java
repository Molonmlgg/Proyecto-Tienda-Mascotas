package modelo;

/**
 * Clase que representa a un Perro en el simulador.
 * Hereda de la clase abstracta Mascota e implementa sus comportamientos específicos,
 * además de la interfaz Jugable.
 * @author Cristóbal Araya Lillo
 */
public class Perro extends Mascota implements Jugable {

    /**
     * Crea un nuevo Perro.
     * @param nombre nombre asignado a la mascota
     * @param precioCompra precio pagado por la mascota
     */
    public Perro(String nombre, double precioCompra) {
        super(nombre, precioCompra);
    }

    /**
     * Emite el sonido característico del perro por consola.
     */
    @Override
    public void emitirSonido() {
        System.out.println(getNombre() + " dice: ¡Guau guau!");
    }

    /**
     * Alimenta al perro con croquetas, reduciendo su hambre en 25
     * y aumentando su felicidad en 15.
     */
    @Override
    public void alimentar() {
        System.out.println("Alimentando a " + getNombre() + " con croquetas nutritivas.");
        setNivelHambre(getNivelHambre() - 25);
        setNivelFelicidad(getNivelFelicidad() + 15);
    }

    /**
     * Juega con el perro lanzándole la pelota. Aumenta la felicidad en 30,
     * pero también el hambre en 15 y disminuye la higiene en 2.
     */
    @Override
    public void jugar() {
        System.out.println("Lanzando la pelota a " + getNombre() + ".");
        setNivelFelicidad(getNivelFelicidad() + 30);
        setNivelHambre(getNivelHambre() + 15);
        setHigiene(getHigiene() - 2);
    }
}
package modelo;

/**
 * Clase que representa a un Gato en el simulador.
 * Hereda de la clase abstracta Mascota e implementa la interfaz Jugable.
 * @author Cristóbal Araya Lillo
 */
public class Gato extends Mascota implements Jugable {

    public Gato(String nombre, double precioCompra) {
        super(nombre, precioCompra);
    }

    @Override
    public void emitirSonido() {
        System.out.println(getNombre() + " dice: ¡Miau miau!");
    }

    @Override
    public void alimentar() {
        System.out.println("Alimentando a " + getNombre() + " con un poco de atún.");
        setNivelHambre(getNivelHambre() - 20);
        setNivelFelicidad(getNivelFelicidad() + 20);
    }

    @Override
    public void jugar() {
        System.out.println("Jugando con " + getNombre() + " y un ovillo de lana.");
        setNivelFelicidad(getNivelFelicidad() + 25);
        setNivelHambre(getNivelHambre() + 10); // Le da un poco de hambre
        setHigiene(getHigiene() - 1); // Se ensucia un poco
    }
}
package modelo;

/**
 * Clase que representa a un Pájaro en el simulador.
 * Hereda de la clase abstracta Mascota e implementa la interfaz Jugable.
 * @author Cristóbal Araya Lillo
 */
public class Pajaro extends Mascota implements Jugable {

    public Pajaro(String nombre, double precioCompra) {
        super(nombre, precioCompra);
    }

    @Override
    public void emitirSonido() {
        System.out.println(getNombre() + " canta: ¡Pío pío!");
    }

    @Override
    public void alimentar() {
        System.out.println("Alimentando a " + getNombre() + " con semillas.");
        setNivelHambre(getNivelHambre() - 15);
        setNivelFelicidad(getNivelFelicidad() + 15);
    }

    @Override
    public void jugar() {
        System.out.println(getNombre() + " está jugando con la campanita de su jaula.");
        setNivelFelicidad(getNivelFelicidad() + 15);
        setNivelHambre(getNivelHambre() + 5);
    }
}
package modelo;

/**
 * Clase que representa a un Perro en el simulador.
 * Hereda de la clase abstracta Mascota e implementa sus comportamientos específicos,
 * además de la interfaz Jugable.
 * * @author Cristóbal Araya Lillo
 */
public class Perro extends Mascota implements Jugable {

    public Perro(String nombre, double precioCompra) {
        super(nombre, precioCompra);
    }

    @Override
    public void emitirSonido() {
        System.out.println(getNombre() + " dice: ¡Guau guau!");
    }

    @Override
    public void alimentar() {
        System.out.println("Alimentando a " + getNombre() + " con croquetas nutritivas.");
        setNivelHambre(getNivelHambre() - 25);
        setNivelFelicidad(getNivelFelicidad() + 15);
    }

    @Override
    public void jugar() {
        System.out.println("Lanzando la pelota a " + getNombre() + ".");
        setNivelFelicidad(getNivelFelicidad() + 30);
        setNivelHambre(getNivelHambre() + 15); // Jugar da hambre
        setHigiene(getHigiene() - 2); // Jugar ensucia
    }
}
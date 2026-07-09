package modelo;

/**
 * Clase que representa a un Pez en el simulador.
 * Hereda de la clase abstracta Mascota. No implementa {@link Jugable} ya que,
 * a diferencia de las demás mascotas, no tiene una interacción de juego definida.
 * @author Cristóbal Araya Lillo
 */
public class Pez extends Mascota {

    /**
     * Crea un nuevo Pez.
     * @param nombre nombre asignado a la mascota
     * @param precioCompra precio pagado por la mascota
     */
    public Pez(String nombre, double precioCompra) {
        super(nombre, precioCompra);
    }

    /**
     * Emite el sonido característico del pez por consola.
     */
    @Override
    public void emitirSonido() {
        System.out.println(getNombre() + " hace: *bloop bloop*");
    }

    /**
     * Alimenta al pez con hojuelas, reduciendo su hambre en 10
     * y aumentando su felicidad en 10.
     */
    @Override
    public void alimentar() {
        System.out.println("Alimentando a " + getNombre() + " con hojuelas para peces.");
        setNivelHambre(getNivelHambre() - 10);
        setNivelFelicidad(getNivelFelicidad() + 10);
    }
}
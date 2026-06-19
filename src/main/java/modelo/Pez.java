package modelo;

/**
 * Clase que representa a un Pez en el simulador.
 * @author Cristóbal Araya Lillo
 */
public class Pez extends Mascota {

    public Pez(String nombre, double precioCompra) {
        super(nombre, precioCompra);
    }

    @Override
    public void emitirSonido() {
        System.out.println(getNombre() + " hace: *bloop bloop*");
    }

    @Override
    public void alimentar() {
        System.out.println("Alimentando a " + getNombre() + " con hojuelas para peces.");
        setNivelHambre(getNivelHambre() - 10);
        setNivelFelicidad(getNivelFelicidad() + 10);
    }
}
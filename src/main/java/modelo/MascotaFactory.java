package modelo;

/**
 * Fábrica encargada de instanciar los diferentes tipos de mascotas.
 * Centraliza la creación de objetos {@link Mascota}, evitando el uso disperso
 * de {@code new} y facilitando agregar nuevos tipos de animales a futuro.
 * @author Cristóbal Araya Lillo
 */
public class MascotaFactory {

    /**
     * Crea una instancia concreta de {@link Mascota} según el tipo indicado.
     * El tipo no distingue mayúsculas/minúsculas ni tildes (ej. "Pájaro" == "pajaro").
     *
     * @param tipo tipo de mascota a crear ("perro", "gato", "pez" o "pajaro")
     * @param nombre nombre asignado a la mascota
     * @param precioCompra precio pagado por la mascota
     * @return la mascota creada, o {@code null} si el tipo no es reconocido
     */
    public Mascota crearMascota(String tipo, String nombre, double precioCompra) {
        if (tipo == null) return null;

        tipo = tipo.toLowerCase().trim().replace("á", "a");

        switch (tipo) {
            case "perro": return new Perro(nombre, precioCompra);
            case "gato": return new Gato(nombre, precioCompra);
            case "pez": return new Pez(nombre, precioCompra);
            case "pajaro": return new Pajaro(nombre, precioCompra);
            default:
                System.out.println("Error: Tipo de mascota (" + tipo + ") no reconocido.");
                return null;
        }
    }
}
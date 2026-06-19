package modelo;

/**
 * Fábrica encargada de instanciar los diferentes tipos de mascotas.
 * @author Cristóbal Araya Lillo
 */
public class MascotaFactory {

    public Mascota crearMascota(String tipo, String nombre, double precioCompra) {
        if (tipo == null) return null;

        switch (tipo.toLowerCase()) {
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
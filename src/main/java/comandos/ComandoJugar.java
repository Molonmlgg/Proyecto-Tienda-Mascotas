package comandos;

import modelo.Jugable;
import modelo.Mascota;
import modelo.Tienda;
import java.util.Random;

/**
 * Comando para jugar con la mascota.
 * Recompensa ajustada a la nueva escala económica: +$3.0.
 * @author Cristóbal Araya Lillo
 */
public class ComandoJugar implements AccionCuidado {

    private final Random random = new Random();

    /**
     * Hace jugar a la mascota (si implementa {@link Jugable}) y otorga
     * una recompensa de $3.0. Existe un 30% de probabilidad de que la
     * mascota se lastime y pierda salud (10 o 15 puntos al azar).
     * @param mascota la mascota con la que se quiere jugar
     * @param tienda la tienda que recibe la recompensa
     * @return true si la mascota implementa {@link Jugable} y se jugó con ella; false en caso contrario
     */
    @Override
    public boolean ejecutar(Mascota mascota, Tienda tienda) {

        if (mascota == null) {
            return false;
        }

        if (!(mascota instanceof Jugable)) {
            return false;
        }

        ((Jugable) mascota).jugar();

        tienda.agregarPresupuesto(3.0);

        if (random.nextInt(100) < 30) {
            if (random.nextBoolean()) {
                mascota.disminuirSalud(10);
            } else {
                mascota.disminuirSalud(15);
            }
            System.out.println("¡" + mascota.getNombre() + " se lastimó mientras jugaba!");
        }

        return true;
    }
}
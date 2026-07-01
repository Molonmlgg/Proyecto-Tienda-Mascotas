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

    @Override
    public boolean ejecutar(Mascota mascota, Tienda tienda) {

        if (mascota == null) {
            return false;
        }

        if (!(mascota instanceof Jugable)) {
            return false;
        }

        ((Jugable) mascota).jugar();

        // Recompensa por jugar
        tienda.agregarPresupuesto(3.0);

        // 30% de probabilidad de lesión
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

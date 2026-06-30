package comandos;

import modelo.Jugable;
import modelo.Mascota;
import modelo.Tienda;

/**
 * Comando para jugar con la mascota.
 * Recompensa ajustada a la nueva escala económica: +$3.0.
 * @author Cristóbal Araya Lillo
 */
public class ComandoJugar implements AccionCuidado {
    @Override
    public boolean ejecutar(Mascota mascota, Tienda tienda) {
        if (mascota == null) return false;
        if (mascota instanceof Jugable) {
            ((Jugable) mascota).jugar();
            tienda.agregarPresupuesto(3.0);
            return true;
        }
        return false;
    }
}
package comandos;

import modelo.Mascota;
import modelo.Tienda;

/**
 * Comando para limpiar a la mascota.
 * Aumenta el nivel de higiene a cambio de un costo monetario.
 * @author Cristóbal Araya Lillo
 */
public class ComandoLimpiar implements AccionCuidado {
    @Override
    public boolean ejecutar(Mascota mascota, Tienda tienda) {
        if (mascota == null) return false;
        mascota.limpiar(30);
        return true;
    }
}
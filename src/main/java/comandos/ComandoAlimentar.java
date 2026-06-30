package comandos;

import modelo.Mascota;
import modelo.Tienda;
import modelo.TipoSuministro;

/**
 * Comando para alimentar a la mascota. Recompensa: $5.0.
 * @author Cristóbal Araya Lillo
 */
public class ComandoAlimentar implements AccionCuidado {
    @Override
    public boolean ejecutar(Mascota mascota, Tienda tienda) {
        if (mascota == null) return false;
        if (tienda.consumirSuministro(TipoSuministro.COMIDA_PREMIUM)) {
            mascota.alimentar();
            tienda.agregarPresupuesto(5.0); // Recompensa Arcade
            return true;
        }
        return false;
    }
}
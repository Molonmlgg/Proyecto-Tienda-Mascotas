package comandos;

import modelo.Mascota;
import modelo.Tienda;
import modelo.TipoSuministro;

/**
 * Comando para alimentar a la mascota.
 * @author Cristóbal Araya Lillo
 */
public class ComandoAlimentar implements AccionCuidado {

    /**
     * Alimenta a la mascota, consumiendo una unidad de Comida Premium del inventario.
     * @param mascota la mascota a alimentar
     * @param tienda la tienda que provee el suministro
     * @return true si había comida disponible y se alimentó a la mascota; false en caso contrario
     */
    @Override
    public boolean ejecutar(Mascota mascota, Tienda tienda) {
        if (mascota == null) return false;
        if (tienda.consumirSuministro(TipoSuministro.COMIDA_PREMIUM)) {
            mascota.alimentar();
            return true;
        }
        return false;
    }
}
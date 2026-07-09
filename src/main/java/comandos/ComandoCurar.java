package comandos;

import modelo.Mascota;
import modelo.Tienda;
import modelo.TipoSuministro;

/**
 * Comando para curar a la mascota. Recompensa: $2.0.
 * @author Cristóbal Araya Lillo
 */
public class ComandoCurar implements AccionCuidado {

    /**
     * Cura a la mascota consumiendo una unidad de Medicina Básica
     * y otorga una recompensa de $2.0 a la tienda.
     * @param mascota la mascota a curar
     * @param tienda la tienda que provee el suministro y recibe la recompensa
     * @return true si había medicina disponible y se curó a la mascota; false en caso contrario
     */
    @Override
    public boolean ejecutar(Mascota mascota, Tienda tienda) {
        if (mascota == null) return false;
        if (tienda.consumirSuministro(TipoSuministro.MEDICINA_BASICA)) {
            mascota.curar(30);
            tienda.agregarPresupuesto(2.0);
            return true;
        }
        return false;
    }
}
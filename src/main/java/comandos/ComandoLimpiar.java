package comandos;

import modelo.Mascota;
import modelo.Tienda;
import modelo.TipoSuministro;

/**
 * Comando para limpiar a la mascota.
 * Aumenta el nivel de higiene y genera una pequeña recompensa.
 * @author Cristóbal Araya Lillo
 */
public class ComandoLimpiar implements AccionCuidado {

    /**
     * Limpia a la mascota consumiendo una unidad de Kit de Limpieza
     * y otorga una recompensa de $1.0 a la tienda.
     * @param mascota la mascota a limpiar
     * @param tienda la tienda que provee el suministro y recibe la recompensa
     * @return true si había kit de limpieza disponible y se limpió a la mascota; false en caso contrario
     */
    @Override
    public boolean ejecutar(Mascota mascota, Tienda tienda) {
        if (mascota == null) return false;

        if (!tienda.consumirSuministro(TipoSuministro.KIT_LIMPIEZA)){
            return false;
        }

        mascota.limpiar(30);
        tienda.agregarPresupuesto(1.0);
        return true;
    }
}
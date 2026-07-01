package comandos;

import modelo.Mascota;
import modelo.Tienda;
import modelo.TipoSuministro;

/**
 * Comando para limpiar a la mascota.
 * Aumenta el nivel de higiene y genera una pequeña recompensa.
 */
public class ComandoLimpiar implements AccionCuidado {
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
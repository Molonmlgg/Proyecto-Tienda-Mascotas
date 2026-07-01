package comandos;

import modelo.Mascota;
import modelo.Tienda;
import modelo.TipoSuministro;

/**
 * Comando para alimentar a la mascota.
 * @author Cristóbal Araya Lillo
 */
public class ComandoAlimentar implements AccionCuidado {
    @Override
    public boolean ejecutar(Mascota mascota, Tienda tienda) {
        if (mascota == null) return false;
        if (tienda.consumirSuministro(TipoSuministro.COMIDA_PREMIUM)) {
            mascota.alimentar();
            //Elimine el ganar dinero por alimentar
            return true;
        }
        return false;
    }
}
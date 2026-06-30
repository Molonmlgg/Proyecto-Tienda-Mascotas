package comandos;

import modelo.Mascota;
import modelo.Tienda;
import modelo.TipoSuministro;

/**
 * Comando para curar a la mascota. Recompensa: $2.0.
 * @author Cristóbal Araya Lillo
 */
public class ComandoCurar implements AccionCuidado {
    @Override
    public boolean ejecutar(Mascota mascota, Tienda tienda) {
        if (mascota == null) return false;
        if (tienda.consumirSuministro(TipoSuministro.MEDICINA_BASICA)) {
            mascota.curar(30);
            tienda.agregarPresupuesto(2.0); // Recompensa Arcade
            return true;
        }
        return false;
    }
}
package modelo;

import excepciones.PresupuestoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la gestión de la Tienda (Singleton).
 * @author Cristóbal Araya Lillo
 */
public class TestTienda {

    private Tienda miTienda;

    @BeforeEach
    void setup() {
        // Al ser Singleton, obtenemos la instancia y reseteamos sus valores para los tests
        miTienda = Tienda.getInstance();
        miTienda.setPresupuesto(1000);
        miTienda.getInventarioMascotas().clear();
    }

    @Test
    @DisplayName("Test: Vender mascota aumenta el presupuesto y vacía el inventario")
    void testVenderMascota() throws PresupuestoInsuficienteException {
        // Compramos primero para tener qué vender
        miTienda.comprarMascota("perro", "Boby", 300);
        Mascota mascotaAVender = miTienda.getInventarioMascotas().get(0);

        // Ejecutamos la venta a un cliente virtual por 500
        miTienda.venderMascota(mascotaAVender, 500);

        // Si teníamos 1000, gastamos 300 y ganamos 500, el saldo debe ser 1200
        assertEquals(1200, miTienda.getPresupuesto());

        // El inventario debe estar vacío (0)
        assertEquals(0, miTienda.getInventarioMascotas().size());
    }

    @Test
    @DisplayName("Test: Comprar suministros actualiza el Enum del inventario")
    void testComprarSuministro() throws PresupuestoInsuficienteException {
        miTienda.comprarSuministro(TipoSuministro.MEDICINA_BASICA, 5, 200);

        // Presupuesto bajó a 800
        assertEquals(800, miTienda.getPresupuesto());

        // Verificar que hay 5 medicinas en el inventario usando el Enum
        int cantidadMedicinas = miTienda.getInventarioSuministros().get(TipoSuministro.MEDICINA_BASICA);
        assertEquals(5, cantidadMedicinas);
    }
}
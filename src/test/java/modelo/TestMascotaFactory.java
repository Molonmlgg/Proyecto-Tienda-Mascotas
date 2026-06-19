package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la fábrica de mascotas.
 * @author Cristóbal Araya Lillo
 */
public class TestMascotaFactory {

    private MascotaFactory fabrica;

    @BeforeEach
    void setup() {
        fabrica = new MascotaFactory();
    }

    @Test
    @DisplayName("Test: La fábrica crea la instancia correcta (Perro)")
    void testCrearMascotaCorrecta() {
        Mascota miMascota = fabrica.crearMascota("perro", "Cachupín", 5000);

        // Verifica que la mascota creada no sea nula y efectivamente sea de la clase Perro
        assertNotNull(miMascota);
        assertTrue(miMascota instanceof Perro);
        assertEquals("Cachupín", miMascota.getNombre());
    }

    @Test
    @DisplayName("Test: La fábrica devuelve null si el animal no existe")
    void testCrearMascotaInvalida() {
        Mascota mascotaInvalida = fabrica.crearMascota("dragon", "Fuego", 10000);

        // Verifica el caso de error: debe ser null
        assertNull(mascotaInvalida);
    }
}
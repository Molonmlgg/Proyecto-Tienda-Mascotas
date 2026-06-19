package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Perro.
 * @author Cristóbal Araya Lillo
 */
public class TestPerro {

    private Perro miPerro;

    @BeforeEach
    void setup() {
        // Hace que esta función se ejecute ántes de cada test [cite: 16]
        miPerro = new Perro("Cachupín", 5000);
    }

    @Test
    @DisplayName("Test: Alimentar al perro disminuye hambre y aumenta felicidad")
    void testAlimentarPerro() {
        // 1. Estado inicial
        miPerro.setNivelHambre(50);
        miPerro.setNivelFelicidad(50);

        // 2. Ejecutar acción
        miPerro.alimentar();

        // 3. Verificar resultados usando asserts [cite: 39]
        // Verifica que el valor actual sea igual al valor esperado [cite: 46, 47]
        assertEquals(25, miPerro.getNivelHambre());
        assertEquals(65, miPerro.getNivelFelicidad());
    }
}
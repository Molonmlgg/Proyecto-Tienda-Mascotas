package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Gato.
 * @author Cristóbal Araya Lillo
 */
public class TestGato {

    private Gato miGato;

    @BeforeEach
    void setup() {
        miGato = new Gato("Michi", 4500);
    }

    @Test
    @DisplayName("Test: Alimentar al gato disminuye hambre y aumenta felicidad")
    void testAlimentarGato() {
        // 1. Estado inicial
        miGato.setNivelHambre(50);
        miGato.setNivelFelicidad(50);

        // 2. Ejecutar acción
        miGato.alimentar();

        // 3. Verificar resultados (El gato baja 20 de hambre y sube 20 de felicidad)
        assertEquals(30, miGato.getNivelHambre());
        assertEquals(70, miGato.getNivelFelicidad());
    }
}
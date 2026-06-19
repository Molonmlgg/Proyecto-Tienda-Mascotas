package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TestPez {
    private Pez miPez;

    @BeforeEach
    void setup() {
        miPez = new Pez("Nemo", 1500);
    }

    @Test
    @DisplayName("Test: Alimentar al pez")
    void testAlimentarPez() {
        miPez.setNivelHambre(50);
        miPez.setNivelFelicidad(50);
        miPez.alimentar();
        assertEquals(40, miPez.getNivelHambre());
        assertEquals(60, miPez.getNivelFelicidad());
    }
}
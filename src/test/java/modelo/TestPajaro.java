package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TestPajaro {
    private Pajaro miPajaro;

    @BeforeEach
    void setup() {
        miPajaro = new Pajaro("Tweety", 2000);
    }

    @Test
    @DisplayName("Test: Alimentar al pájaro")
    void testAlimentarPajaro() {
        miPajaro.setNivelHambre(50);
        miPajaro.setNivelFelicidad(50);
        miPajaro.alimentar();
        assertEquals(35, miPajaro.getNivelHambre());
        assertEquals(65, miPajaro.getNivelFelicidad());
    }
}
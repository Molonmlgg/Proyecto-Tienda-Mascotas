package principal;

import vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

/**
 * Clase principal que arranca el simulador de la tienda de mascotas.
 * Contiene el punto de entrada que inicializa la interfaz gráfica.
 * * @author Cristóbal Araya Lillo
 */
public class Main {

    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica en el hilo de eventos de Swing (Buenas prácticas)
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
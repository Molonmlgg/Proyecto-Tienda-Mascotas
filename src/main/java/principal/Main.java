package principal;

import vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

/**
 * Clase principal que arranca el simulador de la tienda de mascotas.
 * Contiene el punto de entrada que inicializa la interfaz gráfica.
 * @author Cristóbal Araya Lillo
 */
public class Main {

    /**
     * Punto de entrada de la aplicación. Lanza la ventana principal
     * dentro del hilo de eventos de Swing (Event Dispatch Thread).
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
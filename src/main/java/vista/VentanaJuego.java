package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal que muestra el hábitat y la mascota como en un juego.
 */
public class VentanaJuego extends JFrame {

    /**
     * Punto de entrada para ejecutar este ejemplo.
     * @param args argumentos de línea de comando (no se usan)
     */
    public static void main(String[] args) {
        // Crear la ventana en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            VentanaJuego frame = new VentanaJuego();
            frame.setVisible(true);
        });
    }

    /**
     * Constructor de la ventana.
     */
    public VentanaJuego() {
        setTitle("Simulador de Tienda de Mascotas - Cuidando a: Cachupín");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Mantener el tamaño para evitar que se estire

        // Crear el panel del hábitat y agregarlo
        HabitatPanel panel = new HabitatPanel();
        add(panel);

        pack(); // Ajustar el tamaño de la ventana al panel
        setLocationRelativeTo(null); // Centrar en la pantalla
    }

    /**
     * Un panel personalizado para dibujar el hábitat y la mascota.
     */
    class HabitatPanel extends JPanel {

        // Coordenadas iniciales para la mascota
        int petX = 300;
        int petY = 200;

        /**
         * Constructor del panel.
         */
        public HabitatPanel() {
            // Establecer el tamaño preferido del panel (el tamaño del "juego")
            setPreferredSize(new Dimension(800, 600));
        }

        /**
         * Sobrescribir este método para dibujar el contenido personalizado.
         * @param g el objeto Graphics para dibujar
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Llamar al método padre
            Graphics2D g2d = (Graphics2D) g;

            // --- 1. Dibujar el HÁBITAT (Fondo) ---
            g2d.setColor(new Color(135, 206, 235)); // Azul cielo
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(new Color(34, 139, 34)); // Verde pasto
            g2d.fillRect(0, 400, getWidth(), 200);

            g2d.setColor(new Color(255, 215, 0)); // Sol
            g2d.fillOval(50, 50, 80, 80);

            // --- 2. Dibujar la MASCOTA (Forma Simple) ---
            // Cuerpo
            g2d.setColor(new Color(139, 69, 19)); // Marrón
            g2d.fillOval(petX, petY, 150, 100);

            // Cabeza
            g2d.setColor(new Color(160, 82, 45)); // Marrón más claro
            g2d.fillOval(petX + 110, petY - 40, 80, 80);

            // Ojos y Nariz
            g2d.setColor(Color.BLACK);
            g2d.fillOval(petX + 140, petY - 10, 10, 10); // Ojo 1
            g2d.fillOval(petX + 160, petY - 10, 10, 10); // Ojo 2
            g2d.fillRect(petX + 150, petY + 10, 10, 10); // Nariz

            // --- 3. Dibujar TEXTO de la Mascota ---
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));
            g2d.setColor(Color.WHITE);
            // Dibujar el nombre sobre la mascota
            g2d.drawString("Cachupín", petX + 35, petY - 50);
            // Dibujar coordenadas (para depuración)
            g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            g2d.drawString("Pos: (" + petX + ", " + petY + ")", petX + 5, petY + 85);
        }
    }
}
package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal que fusiona el hábitat visual con la lógica del juego.
 * @author Cristóbal Araya Lillo
 */
public class VentanaPrincipal extends JFrame {
    private Tienda tienda;
    private Mascota mascotaActiva;
    private JProgressBar barHambre, barFelicidad;
    private Timer gameLoop;

    public VentanaPrincipal() {
        this.tienda = Tienda.getInstance();
        if (tienda.getInventarioMascotas().isEmpty()) {
            mascotaActiva = new MascotaFactory().crearMascota("perro", "Cachupín", 0);
            tienda.getInventarioMascotas().add(mascotaActiva);
        } else {
            mascotaActiva = tienda.getInventarioMascotas().getFirst();
        }

        setTitle("Simulador: " + mascotaActiva.getNombre());
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con el hábitat
        setContentPane(new HabitatPanel());
        iniciarGameLoop();
    }

    private class HabitatPanel extends JPanel {
        public HabitatPanel() {
            setLayout(new BorderLayout());

            // --- Panel Superior: Estado ---
            JPanel panelEstado = new JPanel(new GridLayout(2, 2, 5, 5));
            panelEstado.setOpaque(false); // Transparente para ver el fondo
            barHambre = crearBarra(mascotaActiva.getNivelHambre(), Color.RED);
            barFelicidad = crearBarra(mascotaActiva.getNivelFelicidad(), Color.GREEN);
            panelEstado.add(new JLabel(" Hambre:")); panelEstado.add(barHambre);
            panelEstado.add(new JLabel(" Felicidad:")); panelEstado.add(barFelicidad);
            add(panelEstado, BorderLayout.NORTH);

            // --- Panel Inferior: Acciones ---
            JPanel panelAcciones = new JPanel();
            panelAcciones.setOpaque(false);
            JButton btnAlimentar = new JButton("Alimentar");
            JButton btnJugar = new JButton("Jugar");

            btnAlimentar.addActionListener(e -> { mascotaActiva.alimentar(); actualizarInterfaz(); });
            btnJugar.addActionListener(e -> {
                if (mascotaActiva instanceof Jugable) ((Jugable) mascotaActiva).jugar();
                actualizarInterfaz();
            });

            panelAcciones.add(btnAlimentar);
            panelAcciones.add(btnJugar);
            add(panelAcciones, BorderLayout.SOUTH);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Fondo del hábitat (Aquí puedes cargar tu imagen de fondo)
            g.setColor(new Color(34, 139, 34)); // Verde pasto
            g.fillRect(0, 400, getWidth(), 200);
            g.setColor(new Color(135, 206, 235)); // Cielo
            g.fillRect(0, 0, getWidth(), 400);

            // Dibujar a Cachupín (Forma simple, cámbialo por g.drawImage(imagenPerro...))
            g.setColor(new Color(205, 133, 63));
            g.fillOval(300, 250, 200, 150); // Cuerpo
        }
    }

    private JProgressBar crearBarra(int val, Color color) {
        JProgressBar b = new JProgressBar(0, 100);
        b.setValue(val); b.setForeground(color); b.setStringPainted(true);
        return b;
    }

    private void iniciarGameLoop() {
        gameLoop = new Timer(3000, e -> {
            mascotaActiva.setNivelHambre(mascotaActiva.getNivelHambre() + 5);
            mascotaActiva.setNivelFelicidad(mascotaActiva.getNivelFelicidad() - 2);
            actualizarInterfaz();
        });
        gameLoop.start();
    }

    private void actualizarInterfaz() {
        barHambre.setValue(mascotaActiva.getNivelHambre());
        barFelicidad.setValue(mascotaActiva.getNivelFelicidad());
        repaint();
    }
}
package vista;

import controlador.Juego;
import excepciones.PresupuestoInsuficienteException;
import modelo.Mascota;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal que fusiona el hábitat visual con la lógica del juego.
 * @author Cristóbal Araya Lillo
 */
public class VentanaPrincipal extends JFrame {

    private Juego juego;
    private JProgressBar barHambre, barFelicidad;
    private Timer gameLoop;

    public VentanaPrincipal() {
        juego = new Juego();

        try{
            juego.comprarMascota("perro", "Cachupin", 100);
        } catch (PresupuestoInsuficienteException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        setTitle("Simulador: " + juego.getMascotaActiva().getNombre());
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(new HabitatPanel());

        iniciarGameLoop();
    }

    /**
     * Panel encargado de dibujar el hábitat y contener los controles
     */
    private class HabitatPanel extends JPanel {

        public HabitatPanel() {

            setLayout(new BorderLayout());

            // Panel Superior: Estado
            JPanel panelEstado = new JPanel(new GridLayout(2, 2, 5, 5));
            panelEstado.setOpaque(false); // Transparente para ver el fondo

            barHambre = crearBarra(juego.getMascotaActiva().getNivelHambre(), Color.RED);
            barFelicidad = crearBarra(juego.getMascotaActiva().getNivelFelicidad(), Color.GREEN);

            panelEstado.add(new JLabel(" Hambre:"));
            panelEstado.add(barHambre);

            panelEstado.add(new JLabel(" Felicidad:"));
            panelEstado.add(barFelicidad);

            this.add(panelEstado, BorderLayout.NORTH);

            // Panel Inferior: Acciones
            JPanel panelAcciones = new JPanel();
            panelAcciones.setOpaque(false);

            JButton btnAlimentar = new JButton("Alimentar");
            JButton btnJugar = new JButton("Jugar");

            btnAlimentar.addActionListener(e -> {
                juego.alimentarMascota();
                actualizarInterfaz();
            });

            btnJugar.addActionListener(e -> {
                juego.jugarConMascota();
                actualizarInterfaz();
            });

            panelAcciones.add(btnAlimentar);
            panelAcciones.add(btnJugar);

            this.add(panelAcciones, BorderLayout.SOUTH);
        }

        /**
         * Dibuja el habitat
         */
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

    /**
     * Crea una barra de progreso
     * @param valor valor inicial
     * @param color color de la barra
     * @return barra creada
     */
    private JProgressBar crearBarra(int valor, Color color) {

        JProgressBar barra = new JProgressBar(0, 100);

        barra.setValue(valor);
        barra.setForeground(color);
        barra.setStringPainted(true);

        return barra;
    }

    /**
     * Inicia el ciclo principal del juego
     */
    private void iniciarGameLoop() {

        gameLoop = new Timer(3000, e -> {

            Mascota mascota = juego.getMascotaActiva();

            mascota.aumentarHambre(5);
            mascota.disminuirFelicidad(2);

            actualizarInterfaz();
        });

        gameLoop.start();

    }

    /**
     * Actualiza las barras de estado y repinta la ventana
     */
    private void actualizarInterfaz() {

        Mascota mascota = juego.getMascotaActiva();

        barHambre.setValue(mascota.getNivelHambre());

        barFelicidad.setValue(mascota.getNivelFelicidad());

        repaint();
    }
}
package vista;

import controlador.Juego;
import excepciones.PresupuestoInsuficienteException;
import modelo.Mascota;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Ventana gráfica principal que integra la lógica del controlador Juego
 * con la renderización del hábitat y los estados de la mascota.
 * * @author Cristóbal Araya Lillo
 */
public class VentanaPrincipal extends JFrame {

    private final Juego juego;
    private JProgressBar barHambre, barFelicidad, barSalud, barHigiene;
    private BufferedImage imgMascota;
    private Timer gameLoop;

    /**
     * Constructor de la ventana principal.
     * Inicializa el controlador, intenta efectuar una compra inicial de mascota,
     * configura la ventana de Swing e inicia el bucle de eventos del juego.
     */
    public VentanaPrincipal() {
        this.juego = new Juego();

        try {
            juego.comprarMascota("perro", "Cachupin", 100);
            cargarImagenMascota("perro");
        } catch (PresupuestoInsuficienteException e) {
            JOptionPane.showMessageDialog(this, "Error inicial: " + e.getMessage());
        }

        setTitle("Simulador: " + (juego.getMascotaActiva() != null ? juego.getMascotaActiva().getNombre() : "Sin mascota"));
        setSize(800, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(new HabitatPanel());
        iniciarGameLoop();
    }

    /**
     * Intenta cargar el archivo de imagen ("sprite") de la mascota desde la carpeta resources.
     * * @param tipo El tipo de mascota para armar el nombre del archivo (ej. "perro.png").
     */
    private void cargarImagenMascota(String tipo) {
        try {
            String ruta = "/resources/" + tipo.toLowerCase() + ".png";
            imgMascota = ImageIO.read(getClass().getResource(ruta));
        } catch (IOException | IllegalArgumentException e) {
            imgMascota = null;
        }
    }

    /**
     * Clase interna que representa el panel gráfico donde se dibuja
     * el hábitat de la mascota y se ubican los controles de estado.
     */
    private class HabitatPanel extends JPanel {

        /**
         * Constructor del panel del hábitat. Configura las barras de progreso
         * y los botones de interacción del usuario.
         */
        public HabitatPanel() {
            setLayout(new BorderLayout());

            JPanel panelEstado = new JPanel(new GridLayout(2, 4, 10, 5));
            panelEstado.setOpaque(false);

            barHambre = crearBarra(0, Color.RED);
            barFelicidad = crearBarra(100, Color.GREEN);
            barSalud = crearBarra(100, Color.BLUE);
            barHigiene = crearBarra(100, Color.CYAN);

            panelEstado.add(new JLabel(" Hambre:")); panelEstado.add(barHambre);
            panelEstado.add(new JLabel(" Felicidad:")); panelEstado.add(barFelicidad);
            panelEstado.add(new JLabel(" Salud:")); panelEstado.add(barSalud);
            panelEstado.add(new JLabel(" Higiene:")); panelEstado.add(barHigiene);
            add(panelEstado, BorderLayout.NORTH);

            JPanel panelAcciones = new JPanel();
            panelAcciones.setOpaque(false);

            JButton btnAlimentar = new JButton("Alimentar");
            JButton btnJugar = new JButton("Jugar");
            JButton btnCurar = new JButton("Curar");

            btnAlimentar.addActionListener(e -> {
                if (!juego.alimentarMascota()) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "¡No tienes Comida Premium en el inventario!");
                }
                actualizarInterfaz();
            });

            btnJugar.addActionListener(e -> {
                if (!juego.jugarConMascota()) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "¡Esta mascota no quiere o no puede jugar ahora!");
                }
                actualizarInterfaz();
            });

            btnCurar.addActionListener(e -> {
                if (!juego.curarMascota()) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "¡No tienes Medicina Básica en el inventario!");
                }
                actualizarInterfaz();
            });

            panelAcciones.add(btnAlimentar);
            panelAcciones.add(btnJugar);
            panelAcciones.add(btnCurar);
            add(panelAcciones, BorderLayout.SOUTH);
        }

        /**
         * Método sobrescrito de Swing encargado de dibujar en pantalla el entorno y la mascota.
         * * @param g Objeto Graphics utilizado para trazar las figuras y colores en la ventana.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(new Color(135, 206, 235));
            g2d.fillRect(0, 0, getWidth(), 400);
            g2d.setColor(new Color(34, 139, 34));
            g2d.fillRect(0, 400, getWidth(), 250);

            g2d.setColor(new Color(255, 215, 0));
            g2d.fillOval(50, 50, 80, 80);

            if (imgMascota != null) {
                g2d.drawImage(imgMascota, 300, 250, 200, 150, this);
            } else {
                g2d.setColor(new Color(205, 133, 63));
                g2d.fillOval(300, 250, 200, 150);
                g2d.setColor(Color.WHITE);
                g2d.drawString("Cachupín", 350, 240);
            }
        }
    }

    /**
     * Método auxiliar para instanciar rápidamente barras de progreso uniformes.
     * * @param val Valor inicial de la barra.
     * @param color Color de la barra de progreso.
     * @return El componente JProgressBar configurado.
     */
    private JProgressBar crearBarra(int val, Color color) {
        JProgressBar b = new JProgressBar(0, 100);
        b.setValue(val);
        b.setForeground(color);
        b.setStringPainted(true);
        return b;
    }

    /**
     * Inicializa un Timer de Swing que funge como bucle principal (Game Loop).
     * Simula el paso del tiempo aumentando el hambre y disminuyendo la felicidad
     * periódicamente.
     */
    private void iniciarGameLoop() {
        gameLoop = new Timer(3000, e -> {
            Mascota m = juego.getMascotaActiva();
            if (m != null) {
                m.aumentarHambre(5);
                m.disminuirFelicidad(2);
                actualizarInterfaz();
            }
        });
        gameLoop.start();
    }

    /**
     * Sincroniza los valores visuales de la interfaz gráfica con el estado
     * real actual de la mascota almacenado en la lógica de negocio.
     */
    private void actualizarInterfaz() {
        Mascota m = juego.getMascotaActiva();
        if (m != null) {
            barHambre.setValue(Math.min(100, Math.max(0, m.getNivelHambre())));
            barFelicidad.setValue(Math.min(100, Math.max(0, m.getNivelFelicidad())));
            barSalud.setValue(Math.min(100, Math.max(0, m.getSalud())));
            barHigiene.setValue(Math.min(100, Math.max(0, m.getHigiene())));
        }
        repaint();
    }
}
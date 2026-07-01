package vista;

import comandos.ComandoAlimentar;
import comandos.ComandoCurar;
import comandos.ComandoJugar;
import comandos.ComandoLimpiar;
import comandos.AccionCuidado;
import controlador.Juego;
import excepciones.PresupuestoInsuficienteException;
import modelo.Mascota;
import modelo.TipoSuministro;
import observador.EstadoObservador;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana gráfica principal del simulador.
 * Gestiona el flujo de vistas mediante CardLayout y aplica el patrón Observer
 * para reaccionar a los cambios de estado de la mascota.
 * @author Cristóbal Araya Lillo
 */
public class VentanaPrincipal extends JFrame implements EstadoObservador {

    private final Juego juego;
    private JPanel panelGestorCards;
    private CardLayout cardLayout;

    private BarraNivel barHambre, barFelicidad, barSalud, barHigiene;
    private JLabel lblDinero;
    private EscenaPanel panelEscena;
    private Timer gameLoop;
    private long ultimoDesgaste = 0;
    private JComboBox<String> selectorMascotas;

    private final Color COLOR_FONDO = new Color(245, 235, 220);
    private final Color COLOR_MADERA = new Color(139, 90, 43);
    private final Color COLOR_MADERA_CLARA = new Color(205, 170, 125);

    public VentanaPrincipal() {
        this.juego = new Juego();

        setTitle("Simulador de Mascotas");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        panelGestorCards = new JPanel(cardLayout);
        panelGestorCards.setBackground(COLOR_FONDO);

        panelGestorCards.add(crearPanelBienvenida(), "BIENVENIDA");
        panelGestorCards.add(crearPanelSeleccion(), "SELECCION");
        panelGestorCards.add(crearPanelJuego(), "JUEGO");

        setContentPane(panelGestorCards);
        cardLayout.show(panelGestorCards, "BIENVENIDA");
    }

    private JPanel crearPanelBienvenida() {
        JPanel panel = new FondoCalido(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int cx = getWidth() / 2;
                int tituloY = (int) (getHeight() * 0.32);

                TipografiaCalida.pintarTitulo(g2, "Pet Store", cx, tituloY,
                        new Font("SansSerif", Font.BOLD, 54),
                        new Color(255, 214, 140), COLOR_MADERA);

                g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
                g2.setColor(new Color(120, 85, 55));
                String subtitulo = "Cuida, alimenta y hazlos felices";
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(subtitulo, cx - fm.stringWidth(subtitulo) / 2, tituloY + 34);

                IconosPintados.dibujar(g2, IconosPintados.Tipo.PATA, cx - 220, tituloY - 40, 34);
                IconosPintados.dibujar(g2, IconosPintados.Tipo.PATA, cx + 190, tituloY - 40, 34);
                g2.dispose();
            }
        };
        GridBagConstraints gbc = new GridBagConstraints();

        BotonAccion btnEmpezar = new BotonAccion("▶  INICIAR ", new Color(120, 170, 95));
        btnEmpezar.setPreferredSize(new Dimension(260, 62));
        btnEmpezar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnEmpezar.addActionListener(e -> cardLayout.show(panelGestorCards, "SELECCION"));
        gbc.gridy = 1;
        gbc.insets = new Insets(140, 0, 0, 0);
        panel.add(btnEmpezar, gbc);

        return panel;
    }

    private JPanel crearPanelSeleccion() {
        JPanel panel = new FondoCalido(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(18, 18, 18, 18);

        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                TipografiaCalida.pintarTitulo(g2, "Adopta un Compañero", getWidth() / 2, 46,
                        new Font("SansSerif", Font.BOLD, 38),
                        new Color(255, 214, 140), COLOR_MADERA);
                g2.dispose();
            }
        };
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(600, 70));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        panel.add(headerPanel, gbc);

        TarjetaMascota tarjetaPerro = new TarjetaMascota("Perro", IconosPintados.Tipo.PERRO, new Color(196, 130, 90));
        TarjetaMascota tarjetaGato = new TarjetaMascota("Gato", IconosPintados.Tipo.GATO, new Color(190, 140, 170));
        TarjetaMascota tarjetaPajaro = new TarjetaMascota("Pájaro", IconosPintados.Tipo.PAJARO, new Color(220, 175, 90));

        tarjetaPerro.addActionListener(e -> iniciarJuegoConMascota("perro"));
        tarjetaGato.addActionListener(e -> iniciarJuegoConMascota("gato"));
        tarjetaPajaro.addActionListener(e -> iniciarJuegoConMascota("pajaro"));

        gbc.gridwidth = 1; gbc.gridy = 1;
        gbc.gridx = 0; panel.add(tarjetaPerro, gbc);
        gbc.gridx = 1; panel.add(tarjetaGato, gbc);
        gbc.gridx = 2; panel.add(tarjetaPajaro, gbc);

        return panel;
    }

    private void iniciarJuegoConMascota(String tipo) {

        String nombre = JOptionPane.showInputDialog(
                this,
                "¿Cómo quieres llamar a tu " + tipo + "?"
        );

        if (nombre == null) {
            return;
        }

        nombre = nombre.trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar un nombre."
            );
            return;
        }

        try {

            juego.comprarMascota(tipo, nombre, 0);

            Mascota m = juego.getMascotaActiva();

            if (m != null) {

                m.agregarObservador(this);

                panelEscena.setMascota(m);

                actualizarSelector();

                actualizarInterfaz();

                iniciarGameLoop();

                cardLayout.show(panelGestorCards, "JUEGO");
            }

        } catch (PresupuestoInsuficienteException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage()
            );

        }
    }

    private JPanel crearPanelJuego() {
        JPanel panelJuego = new JPanel(new BorderLayout());

        // --- SECCIÓN SUPERIOR: Selector y Tienda ---
        JPanel panelTopControl = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelTopControl.setBackground(COLOR_MADERA_CLARA);

        selectorMascotas = new JComboBox<>();
        selectorMascotas.setFont(new Font("SansSerif", Font.BOLD, 14));
        selectorMascotas.addActionListener(e -> cambiarMascotaDesdeSelector());

        BotonAccion btnComprarMascota = new BotonAccion("🐾 Adoptar", new Color(180, 120, 70));
        btnComprarMascota.setPreferredSize(new Dimension(130, 35));
        btnComprarMascota.addActionListener(e -> abrirTiendaMascotas());

        BotonAccion btnSuministros = new BotonAccion("📦 Suministros", new Color(100, 150, 200));
        btnSuministros.setPreferredSize(new Dimension(150, 35));
        btnSuministros.addActionListener(e -> abrirTiendaSuministros());

        BotonAccion btnVender = new BotonAccion("💸 Vender", new Color(200, 80, 80));
        btnVender.setPreferredSize(new Dimension(120, 35));
        btnVender.addActionListener(e -> venderMascotaActual());

        BotonAccion btnMochila = new BotonAccion("🎒 Mochila", new Color(120, 120, 180));
        btnMochila.setPreferredSize(new Dimension(120, 35));
        btnMochila.addActionListener(e -> mostrarMochila());

        panelTopControl.add(new JLabel("Mascota Activa: "));
        panelTopControl.add(selectorMascotas);
        panelTopControl.add(btnComprarMascota);
        panelTopControl.add(btnSuministros);
        panelTopControl.add(btnVender);
        panelTopControl.add(btnMochila);

        // --- HUD DE ESTADÍSTICAS ---
        JPanel panelHUD = new JPanel(new BorderLayout());
        panelHUD.setBackground(COLOR_MADERA_CLARA);
        panelHUD.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));

        lblDinero = new JLabel("💰 $" + (int) juego.getTienda().getPresupuesto());
        lblDinero.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblDinero.setForeground(COLOR_MADERA.darker());
        panelHUD.add(lblDinero, BorderLayout.WEST);

        JPanel panelBarras = new JPanel(new GridLayout(2, 2, 8, 8));
        panelBarras.setOpaque(false);
        barHambre = new BarraNivel("Hambre", IconosPintados.Tipo.HAMBRE, new Color(220, 100, 80));
        barFelicidad = new BarraNivel("Ánimo", IconosPintados.Tipo.FELICIDAD, new Color(120, 180, 100));
        barSalud = new BarraNivel("Salud", IconosPintados.Tipo.SALUD, new Color(200, 80, 100));
        barHigiene = new BarraNivel("Higiene", IconosPintados.Tipo.HIGIENE, new Color(100, 180, 220));

        panelBarras.add(barHambre); panelBarras.add(barFelicidad);
        panelBarras.add(barSalud); panelBarras.add(barHigiene);
        panelHUD.add(panelBarras, BorderLayout.EAST);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelTopControl, BorderLayout.NORTH);
        panelNorte.add(panelHUD, BorderLayout.CENTER);
        panelJuego.add(panelNorte, BorderLayout.NORTH);

        // --- ESCENA ---
        panelEscena = new EscenaPanel();
        panelJuego.add(panelEscena, BorderLayout.CENTER);

        // --- BOTONES DE ACCIÓN ---
        JPanel panelAcciones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelAcciones.setBackground(COLOR_FONDO);
        panelAcciones.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        BotonAccion btnAlimentar = new BotonAccion("Alimentar", new Color(200, 110, 90));
        BotonAccion btnJugar = new BotonAccion("Jugar (+$3)", new Color(110, 160, 90));
        BotonAccion btnCurar = new BotonAccion("Curar (+$2)", new Color(90, 150, 190));
        BotonAccion btnLimpiar = new BotonAccion("Limpiar (+$1)", new Color(100, 180, 220));

        btnAlimentar.addActionListener(e -> ejecutarYNotificar(new ComandoAlimentar(), "¡Necesitas comida premium!"));
        btnJugar.addActionListener(e -> ejecutarYNotificar(new ComandoJugar(), "¡No quiere jugar!"));
        btnCurar.addActionListener(e -> ejecutarYNotificar(new ComandoCurar(), "¡Necesitas medicina!"));
        btnLimpiar.addActionListener(e -> ejecutarYNotificar(new ComandoLimpiar(), "¡Error al limpiar!"));

        panelAcciones.add(btnAlimentar); panelAcciones.add(btnJugar);
        panelAcciones.add(btnCurar); panelAcciones.add(btnLimpiar);

        JPanel contenedorAcciones = new JPanel(new BorderLayout());
        contenedorAcciones.setBackground(COLOR_FONDO);
        contenedorAcciones.add(panelAcciones, BorderLayout.NORTH);
        panelJuego.add(contenedorAcciones, BorderLayout.SOUTH);

        return panelJuego;
    }

    private void procesarAccion(double costo, AccionCuidado comando, String error) {
        if (juego.getTienda().getPresupuesto() >= Math.abs(costo)) {
            juego.getTienda().agregarPresupuesto(costo);
            ejecutarYNotificar(comando, error);
        } else {
            JOptionPane.showMessageDialog(this, "¡Fondos insuficientes!");
        }
    }

    private void abrirTiendaMascotas() {
        String[] opciones = {"Perro ($25)", "Gato ($25)", "Pajaro ($25)"};
        String eleccion = (String) JOptionPane.showInputDialog(this,
                "Adopta una nueva mascota:\nPresupuesto actual: $" + (int)juego.getTienda().getPresupuesto(),
                "Tienda", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (eleccion != null) {
            String tipo = eleccion.split(" ")[0].toLowerCase();
            String nombre = JOptionPane.showInputDialog(this, "¿Qué nombre le pondrás?");

            if (nombre != null && !nombre.trim().isEmpty()) {
                try {
                    juego.comprarMascota(tipo, nombre, 25.0);
                    Mascota nueva = juego.getMascotaActiva();
                    if (nueva != null){
                        nueva.agregarObservador(this);
                    }

                    actualizarSelector();
                    panelEscena.setMascota(nueva);
                    actualizarInterfaz();
                } catch (PresupuestoInsuficienteException ex) {
                    JOptionPane.showMessageDialog(this, "¡No tienes $25 para adoptar!");
                }
            }
        }
    }

    private void abrirTiendaSuministros() {
        String[] opciones = {
                "Comida Premium ($5 x 5u)",
                "Medicina Básica ($8 x 3u)",
                "Kit de Limpieza ($4 x 5u)"
        };
        String eleccion = (String) JOptionPane.showInputDialog(
                this,
                "Compra suministros para tu mascota:\n\n" +
                        "Presupuesto actual: $" + (int) juego.getTienda().getPresupuesto(),
                "Tienda de Suministros",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);


        if (eleccion != null) {
            try {
                if (eleccion.startsWith("Comida")) {
                    juego.getTienda().comprarSuministro(modelo.TipoSuministro.COMIDA_PREMIUM, 5, 5.0);
                    JOptionPane.showMessageDialog(this, "¡Has comprado 5 unidades de Comida Premium!");
                } else if (eleccion.startsWith("Medicina")) {
                    juego.getTienda().comprarSuministro(modelo.TipoSuministro.MEDICINA_BASICA, 3, 8.0);
                    JOptionPane.showMessageDialog(this, "¡Has comprado 3 unidades de Medicina Básica!");
                } else if (eleccion.startsWith("Kit")){
                    juego.getTienda().comprarSuministro(modelo.TipoSuministro.KIT_LIMPIEZA, 5, 4.0);
                    JOptionPane.showMessageDialog(this, "¡Has comprado 5 Kits de Limpieza!");
                }
                actualizarInterfaz();
            } catch (PresupuestoInsuficienteException ex) {
                JOptionPane.showMessageDialog(this, "¡Fondos insuficientes para esta compra!");
            }
        }
    }

    private void venderMascotaActual() {
        Mascota m = juego.getMascotaActiva();
        if (m == null) return;

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas vender a " + m.getNombre() + " por $15?",
                "Confirmar Venta", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            juego.venderMascota(15.0);
            JOptionPane.showMessageDialog(this, m.getNombre() + " ha sido vendido. ¡Recibiste $15!");

            actualizarSelector();

            if (!juego.getTienda().getInventarioMascotas().isEmpty()) {
                juego.seleccionarMascota(juego.getTienda().getInventarioMascotas().get(0));
                panelEscena.setMascota(juego.getMascotaActiva());
            } else {
                panelEscena.setMascota(null);
            }
            actualizarInterfaz();
        }
    }

    private void actualizarSelector() {
        java.awt.event.ActionListener[] listeners = selectorMascotas.getActionListeners();
        for (java.awt.event.ActionListener l : listeners) selectorMascotas.removeActionListener(l);

        selectorMascotas.removeAllItems();
        for (Mascota m : juego.getTienda().getInventarioMascotas()) {
            selectorMascotas.addItem(m.getNombre());
        }

        if (juego.getMascotaActiva() != null) {
            selectorMascotas.setSelectedItem(juego.getMascotaActiva().getNombre());
        }

        for (java.awt.event.ActionListener l : listeners) selectorMascotas.addActionListener(l);
    }

    private void cambiarMascotaDesdeSelector() {
        if (selectorMascotas.getSelectedItem() != null) {
            String nombreSel = (String) selectorMascotas.getSelectedItem();
            Mascota m = juego.getTienda().buscarMascota(nombreSel);

            if (m != null && m != juego.getMascotaActiva()) {
                juego.seleccionarMascota(m);
                panelEscena.setMascota(m);
                actualizarInterfaz();
            }
        }
    }

    private void ejecutarYNotificar(AccionCuidado comando, String msgError) {
        if (!juego.ejecutarAccion(comando)) {
            JOptionPane.showMessageDialog(this, msgError, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        actualizarInterfaz();
    }

    private void iniciarGameLoop() {
        if (gameLoop != null) gameLoop.stop();
        ultimoDesgaste = System.currentTimeMillis();

        gameLoop = new Timer(16, e -> {
            long tiempoActual = System.currentTimeMillis();

            panelEscena.setFrame((int) (tiempoActual / 30));
            panelEscena.repaint();

            if (tiempoActual - ultimoDesgaste > 3000) {
                Mascota m = juego.getMascotaActiva();
                if (m != null) {
                    m.aumentarHambre(3);
                    m.disminuirFelicidad(2);
                    m.disminuirHigiene(4);
                }
                ultimoDesgaste = tiempoActual;
            }
        });
        gameLoop.start();
    }

    @Override
    public void actualizarEstado(Mascota mascota) {
        actualizarInterfaz();
    }

    private void actualizarInterfaz() {
        Mascota m = juego.getMascotaActiva();
        if (m != null) {
            barHambre.setValor(m.getNivelHambre());
            barFelicidad.setValor(m.getNivelFelicidad());
            barSalud.setValor(m.getSalud());
            barHigiene.setValor(m.getHigiene());
            lblDinero.setText("💰 $" + (int) juego.getTienda().getPresupuesto());
        }
    }

    private void mostrarMochila(){
        String mensaje =
                "========== MOCHILA ==========\n\n" +

                        "💰 Dinero: $" +
                        (int) juego.getTienda().getPresupuesto() +

                        "\n\n🐾 Mascotas: " +
                        juego.getTienda().getCantidadMascotas() +

                        "\n\n🍖 Comida Premium: " +
                        juego.getTienda().getCantidadSuministro(modelo.TipoSuministro.COMIDA_PREMIUM) +

                        "\n💊 Medicina Básica: " +
                        juego.getTienda().getCantidadSuministro(modelo.TipoSuministro.MEDICINA_BASICA) +

                        "\n🧽 Kit de Limpieza: " +
                        juego.getTienda().getCantidadSuministro(modelo.TipoSuministro.KIT_LIMPIEZA);


        JOptionPane.showMessageDialog(this, mensaje, "Mochila", JOptionPane.INFORMATION_MESSAGE);
    }
}
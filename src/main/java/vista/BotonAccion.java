package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Botón estilizado con diseño cálido. v2: el brillo superior ya no es un
 * rectángulo blanco duro (efecto "plástico") sino un halo elíptico suave
 * con degradado radial, coherente con el nuevo estilo de {@link BarraNivel}.
 *
 * @author Cristóbal Araya Lillo
 */
public class BotonAccion extends JButton {

    private final Color colorBase;
    private boolean sobreElBoton = false;

    public BotonAccion(String texto, Color colorBase) {
        super(texto);
        this.colorBase = colorBase;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sobreElBoton = true;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                sobreElBoton = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arco = h;

        Color claro = sobreElBoton ? colorBase.brighter() : colorBase;
        Color oscuro = sobreElBoton ? colorBase : colorBase.darker();
        int hundido = 0;
        if (getModel().isPressed()) {
            claro = colorBase.darker();
            oscuro = colorBase.darker().darker();
            hundido = 2;
        }

        // Sombra de elevación
        g2.setColor(new Color(60, 40, 20, sobreElBoton ? 55 : 35));
        g2.fillRoundRect(3, 5 + hundido, w - 6, h - 6, arco, arco);

        // Cuerpo del botón
        GradientPaint relleno = new GradientPaint(0, 0, claro, 0, h, oscuro);
        g2.setPaint(relleno);
        g2.fillRoundRect(0, hundido, w - 3, h - 4, arco, arco);

        // Halo de luz suave (radial), no un bloque rectangular
        Point2D centro = new Point2D.Float(w * 0.32f, h * 0.3f + hundido);
        float radio = h * 0.9f;
        RadialGradientPaint halo = new RadialGradientPaint(
                centro, radio,
                new float[]{0f, 1f},
                new Color[]{new Color(255, 255, 255, sobreElBoton ? 110 : 75), new Color(255, 255, 255, 0)}
        );
        g2.setPaint(halo);
        g2.fillRoundRect(0, hundido, w - 3, h - 4, arco, arco);

        // Borde suave
        g2.setColor(oscuro.darker());
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawRoundRect(1, hundido, w - 4, h - 5, arco, arco);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(170, 50);
    }
}
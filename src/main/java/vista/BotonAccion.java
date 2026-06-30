package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Botón estilizado con diseño cálido y bordes suaves.
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

        // Colores más pasteles y suaves
        Color claro = sobreElBoton ? colorBase.brighter() : colorBase;
        Color oscuro = sobreElBoton ? colorBase : colorBase.darker();
        if (getModel().isPressed()) {
            claro = colorBase.darker();
            oscuro = colorBase.darker().darker();
        }

        // Sombra sutil
        g2.setColor(new Color(0, 0, 0, 30));
        g2.fillRoundRect(2, 4, w - 4, h - 4, 25, 25);

        // Degradado principal
        GradientPaint relleno = new GradientPaint(0, 0, claro, 0, h, oscuro);
        g2.setPaint(relleno);
        g2.fillRoundRect(0, 0, w - 2, h - 4, 25, 25);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(160, 48);
    }
}
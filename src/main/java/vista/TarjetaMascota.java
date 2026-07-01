package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Tarjeta seleccionable para elegir el tipo de mascota a adoptar.
 * Reemplaza al botón de píldora simple de la pantalla de selección por
 * una tarjeta con ícono propio, nombre y efecto de elevación al pasar
 * el mouse.
 *
 * @author Cristóbal Araya Lillo
 */
public class TarjetaMascota extends JButton {

    private final Color colorBase;
    private final IconosPintados.Tipo icono;
    private boolean sobreLaTarjeta = false;

    public TarjetaMascota(String nombre, IconosPintados.Tipo icono, Color colorBase) {
        super(nombre);
        this.icono = icono;
        this.colorBase = colorBase;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("SansSerif", Font.BOLD, 18));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { sobreLaTarjeta = true; repaint(); }
            @Override
            public void mouseExited(MouseEvent e) { sobreLaTarjeta = false; repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int w = getWidth();
        final int h = getHeight();
        final int elevar = sobreLaTarjeta ? -6 : 0;
        final int arco = 28;

        // Sombra
        g2.setColor(new Color(60, 40, 20, sobreLaTarjeta ? 70 : 45));
        g2.fillRoundRect(6, 10 + (sobreLaTarjeta ? 4 : 8), w - 12, h - 16, arco, arco);

        // Cuerpo de la tarjeta
        GradientPaint cuerpo = new GradientPaint(0, elevar, Color.WHITE, 0, h + elevar, new Color(255, 246, 235));
        g2.setPaint(cuerpo);
        g2.fillRoundRect(4, 4 + elevar, w - 8, h - 12, arco, arco);

        // Borde de color según el tipo de mascota
        g2.setColor(colorBase);
        g2.setStroke(new BasicStroke(sobreLaTarjeta ? 3f : 2f));
        g2.drawRoundRect(4, 4 + elevar, w - 9, h - 13, arco, arco);

        // Insignia circular con el ícono del animal
        int badgeD = (int) (h * 0.42);
        int badgeX = (w - badgeD) / 2;
        int badgeY = (int) (h * 0.14) + elevar;
        GradientPaint badgeGrad = new GradientPaint(0, badgeY, colorBase.brighter(), 0, badgeY + badgeD, colorBase.darker());
        g2.setPaint(badgeGrad);
        g2.fillOval(badgeX, badgeY, badgeD, badgeD);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2.5f));
        g2.drawOval(badgeX, badgeY, badgeD, badgeD);

        int iconSize = (int) (badgeD * 0.62);
        IconosPintados.dibujar(g2, icono, badgeX + (badgeD - iconSize) / 2, badgeY + (badgeD - iconSize) / 2, iconSize);

        // Nombre de la mascota, dibujado a mano para controlar su posición exacta
        String nombre = getText();
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int tx = (w - fm.stringWidth(nombre)) / 2;
        int ty = badgeY + badgeD + fm.getAscent() + 16;
        g2.setColor(new Color(70, 45, 25, 150));
        g2.drawString(nombre, tx + 1, ty + 1);
        g2.setColor(new Color(70, 45, 25));
        g2.drawString(nombre, tx, ty);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(210, 200);
    }
}

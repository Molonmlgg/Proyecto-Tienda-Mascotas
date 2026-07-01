package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de fondo reutilizable con degradado cálido tipo "atardecer suave",
 * viñeta en las esquinas para dar profundidad y un patrón sutil de
 * huellitas. Se usa como base decorativa para las pantallas de
 * bienvenida y selección de mascota.
 *
 * @author Cristóbal Araya Lillo
 */
public class FondoCalido extends JPanel {

    public FondoCalido(LayoutManager layout) {
        super(layout);
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        GradientPaint fondo = new GradientPaint(0, 0, new Color(255, 244, 227), 0, h, new Color(255, 222, 189));
        g2.setPaint(fondo);
        g2.fillRect(0, 0, w, h);

        RadialGradientPaint vineta = new RadialGradientPaint(
                new Point(w / 2, h / 2), Math.max(w, h) * 0.75f,
                new float[]{0.6f, 1f},
                new Color[]{new Color(0, 0, 0, 0), new Color(90, 60, 30, 35)});
        g2.setPaint(vineta);
        g2.fillRect(0, 0, w, h);

        g2.setColor(new Color(160, 110, 70, 20));
        for (int y = 40; y < h; y += 130) {
            int desfase = (y / 130 % 2 == 0) ? 30 : 100;
            for (int x = desfase; x < w; x += 160) {
                IconosPintados.dibujar(g2, IconosPintados.Tipo.PATA, x, y, 26);
            }
        }

        g2.dispose();
        super.paintComponent(g);
    }
}

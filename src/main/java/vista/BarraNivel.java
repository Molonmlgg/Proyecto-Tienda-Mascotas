package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Componente Swing personalizado que reemplaza al JProgressBar por defecto.
 * v2: el ícono ahora vive dentro de una <b>insignia circular</b> que se
 * solapa con el borde izquierdo de la píldora (patrón de HUD de juegos
 * cálidos), en vez de flotar suelto sobre la barra. El brillo superior
 * duro se reemplazó por un hilo de luz sutil para evitar el efecto
 * "reflejo plástico".
 *
 * @author Cristóbal Araya Lillo
 */
public class BarraNivel extends JComponent {

    private int valor = 0;
    private final String etiqueta;
    private final IconosPintados.Tipo icono;
    private final Color colorPrincipal;

    public BarraNivel(String etiqueta, IconosPintados.Tipo icono, Color colorPrincipal) {
        this.etiqueta = etiqueta;
        this.icono = icono;
        this.colorPrincipal = colorPrincipal;
        setOpaque(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /** Actualiza el valor mostrado (0-100) y repinta la barra. */
    public void setValor(int valor) {
        this.valor = Math.max(0, Math.min(100, valor));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int w = getWidth();
        final int h = getHeight();
        final int badgeD = h;              // la insignia ocupa toda la altura disponible
        final int barX = badgeD / 2;        // la píldora arranca desde el centro de la insignia
        final int barW = w - barX - 2;

        // --- Sombra suave del conjunto (da sensación de "elevación") ---
        g2.setColor(new Color(90, 60, 30, 35));
        g2.fillRoundRect(barX, 2, barW, h - 2, h, h);
        g2.fillOval(1, 2, badgeD, badgeD - 2);

        // --- Pista de fondo, tono crema cálido en vez de gris ---
        g2.setColor(new Color(255, 247, 235));
        g2.fillRoundRect(barX, 0, barW, h, h, h);
        g2.setColor(new Color(222, 196, 165));
        g2.setStroke(new BasicStroke(1.3f));
        g2.drawRoundRect(barX, 0, barW - 1, h - 1, h, h);

        // --- Relleno proporcional al nivel ---
        int relleno = (int) ((barW - 6) * (valor / 100.0));
        if (relleno > 2) {
            GradientPaint grad = new GradientPaint(barX, 0, colorPrincipal.brighter(), barX + relleno, 0, colorPrincipal.darker());
            g2.setPaint(grad);
            g2.fillRoundRect(barX + 3, 3, relleno, h - 6, h - 6, h - 6);

            // Hilo de luz sutil (no un bloque) simulando un brillo real
            g2.setColor(new Color(255, 255, 255, 100));
            g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(barX + 9, 6, barX + 3 + Math.max(relleno - 9, 1), 6);
        }

        // --- Texto alineado a la derecha de la píldora ---
        String texto = etiqueta + "  " + valor + "%";
        g2.setFont(getFont().deriveFont(Font.BOLD, h * 0.40f));
        FontMetrics fm = g2.getFontMetrics();
        int tx = barX + barW - fm.stringWidth(texto) - 14;
        int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
        g2.setColor(new Color(90, 60, 30, 150));
        g2.drawString(texto, tx + 1, ty + 1);
        g2.setColor(new Color(75, 48, 28));
        g2.drawString(texto, tx, ty);

        // --- Insignia circular con el ícono, siempre por encima de todo ---
        GradientPaint badgeGrad = new GradientPaint(0, 0, colorPrincipal.brighter(), 0, badgeD, colorPrincipal.darker());
        g2.setPaint(badgeGrad);
        g2.fillOval(0, 0, badgeD, badgeD);
        g2.setColor(new Color(255, 255, 255, 230));
        g2.setStroke(new BasicStroke(2.2f));
        g2.drawOval(1, 1, badgeD - 2, badgeD - 2);

        int iconSize = (int) (badgeD * 0.52);
        int iconX = (badgeD - iconSize) / 2;
        int iconY = (badgeD - iconSize) / 2;
        IconosPintados.dibujar(g2, icono, iconX, iconY, iconSize);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(320, 36);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, 36);
    }
}
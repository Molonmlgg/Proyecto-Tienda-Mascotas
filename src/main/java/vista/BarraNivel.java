package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Componente Swing personalizado que reemplaza al JProgressBar por defecto:
 * dibuja una barra de nivel redondeada, con relleno en degradado, ícono
 * y texto superpuesto, pensada para un HUD de estilo "juego" en vez de
 * una interfaz de formulario tradicional.
 *
 * @author Cristóbal Araya Lillo
 */
public class BarraNivel extends JComponent {

    private int valor = 0;
    private final String etiqueta;
    private final String icono;
    private final Color colorPrincipal;

    /**
     * Crea una nueva barra de nivel.
     *
     * @param etiqueta nombre del atributo que representa (ej. "Hambre").
     * @param icono carácter/ícono (emoji) a mostrar junto a la etiqueta.
     * @param colorPrincipal color base usado para el degradado de relleno.
     */
    public BarraNivel(String etiqueta, String icono, Color colorPrincipal) {
        this.etiqueta = etiqueta;
        this.icono = icono;
        this.colorPrincipal = colorPrincipal;
        setOpaque(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /**
     * Actualiza el valor mostrado (0-100) y repinta la barra.
     *
     * @param valor nuevo valor del nivel, se acota automáticamente a [0, 100].
     */
    public void setValor(int valor) {
        this.valor = Math.max(0, Math.min(100, valor));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(new Color(255, 255, 255, 35));
        g2.fillRoundRect(0, 0, w, h, h, h);

        int anchoRelleno = Math.max((int) (w * (valor / 100.0)), h);
        GradientPaint relleno = new GradientPaint(0, 0, colorPrincipal.brighter(), anchoRelleno, 0, colorPrincipal.darker());
        g2.setPaint(relleno);
        g2.fillRoundRect(0, 0, anchoRelleno, h, h, h);

        g2.setColor(new Color(255, 255, 255, 90));
        g2.setStroke(new BasicStroke(1.4f));
        g2.drawRoundRect(0, 0, w - 1, h - 1, h, h);

        String texto = icono + " " + etiqueta + "  " + valor + "%";
        g2.setFont(getFont().deriveFont(Font.BOLD, h * 0.5f));
        FontMetrics fm = g2.getFontMetrics();
        int tx = 10;
        int ty = (h + fm.getAscent() - fm.getDescent()) / 2;

        g2.setColor(new Color(0, 0, 0, 130));
        g2.drawString(texto, tx + 1, ty + 1);
        g2.setColor(Color.WHITE);
        g2.drawString(texto, tx, ty);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(320, 28);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, 28);
    }
}

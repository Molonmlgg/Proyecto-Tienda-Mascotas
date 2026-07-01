package vista;

import java.awt.*;

/**
 * Utilidad para pintar títulos con un estilo cálido y "de juego": texto
 * con degradado vertical, sombra suave y un leve efecto de relieve, en
 * vez de depender de la tipografía plana por defecto de {@link javax.swing.JLabel}.
 *
 * @author Cristóbal Araya Lillo
 */
public final class TipografiaCalida {

    private TipografiaCalida() {}

    /**
     * Pinta un título centrado horizontalmente en torno a {@code centroX}.
     *
     * @param g2 contexto gráfico donde dibujar
     * @param texto texto del título
     * @param centroX coordenada x sobre la que se centra el texto
     * @param baseY línea base (baseline) donde se apoya el texto
     * @param fuente fuente a utilizar
     * @param colorClaro color superior del degradado
     * @param colorOscuro color inferior del degradado
     */
    public static void pintarTitulo(Graphics2D g2, String texto, int centroX, int baseY,
                                     Font fuente, Color colorClaro, Color colorOscuro) {
        Graphics2D g = (Graphics2D) g2.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(fuente);
        FontMetrics fm = g.getFontMetrics();
        final int x = centroX - fm.stringWidth(texto) / 2;

        // Sombra suave (dos pasadas para simular un difuminado barato)
        g.setColor(new Color(90, 55, 25, 55));
        g.drawString(texto, x + 3, baseY + 4);
        g.setColor(new Color(90, 55, 25, 35));
        g.drawString(texto, x + 2, baseY + 3);

        // Relieve: trazo claro desplazado antes del relleno principal
        g.setColor(new Color(255, 255, 255, 130));
        g.drawString(texto, x - 1, baseY - 1);

        // Relleno principal con degradado vertical
        GradientPaint degradado = new GradientPaint(0, baseY - fm.getAscent(), colorClaro, 0, baseY, colorOscuro);
        g.setPaint(degradado);
        g.drawString(texto, x, baseY);

        g.dispose();
    }
}

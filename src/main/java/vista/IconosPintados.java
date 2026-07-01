package vista;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

/**
 * Utilidad que dibuja íconos vectoriales "hechos a mano" con Graphics2D,
 * pensados para reemplazar el uso de emojis en la interfaz y así tener
 * un look consistente, cálido y propio del juego.
 *
 * Todos los métodos dibujan dentro de un cuadrado imaginario de lado
 * {@code size}, con esquina superior izquierda en (x, y), por lo que
 * son fáciles de reutilizar en distintos tamaños (HUD, botones, etc.).
 *
 * @author Cristóbal Araya Lillo
 */
public final class IconosPintados {

    private IconosPintados() {}

    /** Tipos de ícono disponibles para {@link BarraNivel} y {@link TarjetaMascota}. */
    public enum Tipo { HAMBRE, FELICIDAD, SALUD, HIGIENE, DINERO, PATA, PERRO, GATO, PAJARO }

    /**
     * Dibuja el ícono correspondiente al tipo indicado.
     *
     * @param g2 contexto gráfico, se restaura tras dibujar
     * @param tipo tipo de ícono a renderizar
     * @param x coordenada x superior izquierda
     * @param y coordenada y superior izquierda
     * @param size lado del cuadrado en el que se dibuja
     */
    public static void dibujar(Graphics2D g2, Tipo tipo, int x, int y, int size) {
        Graphics2D g = (Graphics2D) g2.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        switch (tipo) {
            case HAMBRE -> hueso(g, x, y, size);
            case FELICIDAD -> corazonAlegre(g, x, y, size);
            case SALUD -> cruzVida(g, x, y, size);
            case HIGIENE -> burbuja(g, x, y, size);
            case DINERO -> moneda(g, x, y, size);
            case PATA -> pata(g, x, y, size);
            case PERRO -> perro(g, x, y, size);
            case GATO -> gato(g, x, y, size);
            case PAJARO -> pajarito(g, x, y, size);
        }
        g.dispose();
    }

    private static void hueso(Graphics2D g, int x, int y, int s) {
        Color hueso = new Color(255, 248, 230);
        g.setColor(new Color(0, 0, 0, 40));
        drawHuesoForma(g, x + 1, y + 2, s);
        g.setColor(hueso);
        drawHuesoForma(g, x, y, s);
        g.setColor(new Color(210, 190, 150));
        g.setStroke(new BasicStroke(Math.max(1f, s * 0.06f)));
        drawHuesoContorno(g, x, y, s);
    }

    private static void drawHuesoForma(Graphics2D g, int x, int y, int s) {
        int r = (int) (s * 0.22);
        int barW = (int) (s * 0.34);
        int barY = y + (s - barW) / 2;
        g.fillRoundRect(x + r / 2, y + (s / 2 - barW / 2), s - r, barW, barW, barW);
        g.fillOval(x, y, r * 2, r * 2);
        g.fillOval(x, y + s - r * 2, r * 2, r * 2);
        g.fillOval(x + s - r * 2, y, r * 2, r * 2);
        g.fillOval(x + s - r * 2, y + s - r * 2, r * 2, r * 2);
    }

    private static void drawHuesoContorno(Graphics2D g, int x, int y, int s) {
        // Contorno simplificado (solo referencia visual, no crítico)
        int r = (int) (s * 0.22);
        g.drawOval(x, y, r * 2, r * 2);
        g.drawOval(x + s - r * 2, y + s - r * 2, r * 2, r * 2);
    }

    private static void corazonAlegre(Graphics2D g, int x, int y, int s) {
        GeneralPath corazon = crearCorazon(x, y, s);
        g.setColor(new Color(255, 150, 150));
        g.fill(corazon);
        g.setColor(new Color(210, 90, 90));
        g.setStroke(new BasicStroke(Math.max(1f, s * 0.05f)));
        g.draw(corazon);
        // Brillito para darle vida
        g.setColor(new Color(255, 255, 255, 180));
        g.fillOval(x + (int) (s * 0.22), y + (int) (s * 0.22), (int) (s * 0.16), (int) (s * 0.16));
    }

    private static GeneralPath crearCorazon(int x, int y, int s) {
        GeneralPath p = new GeneralPath();
        double w = s, h = s;
        p.moveTo(x + w / 2.0, y + h * 0.85);
        p.curveTo(x - w * 0.1, y + h * 0.55, x + w * 0.05, y + h * 0.05, x + w / 2.0, y + h * 0.30);
        p.curveTo(x + w * 0.95, y + h * 0.05, x + w * 1.10, y + h * 0.55, x + w / 2.0, y + h * 0.85);
        p.closePath();
        return p;
    }

    private static void cruzVida(Graphics2D g, int x, int y, int s) {
        g.setColor(new Color(230, 90, 100));
        g.fillRoundRect(x + (int) (s * 0.36), y + (int) (s * 0.08), (int) (s * 0.28), (int) (s * 0.84), 8, 8);
        g.fillRoundRect(x + (int) (s * 0.08), y + (int) (s * 0.36), (int) (s * 0.84), (int) (s * 0.28), 8, 8);
        g.setColor(new Color(255, 255, 255, 120));
        g.fillRoundRect(x + (int) (s * 0.40), y + (int) (s * 0.10), (int) (s * 0.10), (int) (s * 0.30), 4, 4);
    }

    private static void burbuja(Graphics2D g, int x, int y, int s) {
        g.setColor(new Color(160, 220, 245, 220));
        g.fillOval(x + (int) (s * 0.15), y + (int) (s * 0.15), (int) (s * 0.7), (int) (s * 0.7));
        g.setColor(new Color(255, 255, 255, 200));
        g.fillOval(x + (int) (s * 0.30), y + (int) (s * 0.22), (int) (s * 0.18), (int) (s * 0.18));
        g.setColor(new Color(120, 190, 220));
        g.fillOval(x, y + (int) (s * 0.62), (int) (s * 0.22), (int) (s * 0.22));
    }

    private static void moneda(Graphics2D g, int x, int y, int s) {
        Ellipse2D disco = new Ellipse2D.Double(x + s * 0.05, y + s * 0.05, s * 0.9, s * 0.9);
        g.setColor(new Color(255, 205, 90));
        g.fill(disco);
        g.setColor(new Color(200, 150, 40));
        g.setStroke(new BasicStroke(Math.max(1f, s * 0.06f)));
        g.draw(disco);
        g.setFont(new Font("SansSerif", Font.BOLD, (int) (s * 0.5)));
        FontMetrics fm = g.getFontMetrics();
        String simbolo = "$";
        int tx = x + (s - fm.stringWidth(simbolo)) / 2;
        int ty = y + (s + fm.getAscent()) / 2 - 2;
        g.setColor(new Color(150, 105, 20));
        g.drawString(simbolo, tx, ty);
    }

    private static void perro(Graphics2D g, int x, int y, int s) {
        Color pelaje = new Color(196, 148, 105);
        g.setColor(pelaje.darker());
        g.fillOval(x, y + (int) (s * 0.15), (int) (s * 0.28), (int) (s * 0.48));
        g.fillOval(x + (int) (s * 0.72), y + (int) (s * 0.15), (int) (s * 0.28), (int) (s * 0.48));
        g.setColor(pelaje);
        g.fillOval(x + (int) (s * 0.12), y, (int) (s * 0.76), (int) (s * 0.76));
        g.setColor(new Color(248, 235, 215));
        g.fillOval(x + (int) (s * 0.28), y + (int) (s * 0.36), (int) (s * 0.44), (int) (s * 0.36));
        g.setColor(new Color(80, 55, 40));
        g.fillOval(x + (int) (s * 0.45), y + (int) (s * 0.46), (int) (s * 0.12), (int) (s * 0.10));
        g.fillOval(x + (int) (s * 0.28), y + (int) (s * 0.28), (int) (s * 0.10), (int) (s * 0.10));
        g.fillOval(x + (int) (s * 0.62), y + (int) (s * 0.28), (int) (s * 0.10), (int) (s * 0.10));
    }

    private static void gato(Graphics2D g, int x, int y, int s) {
        Color pelaje = new Color(225, 200, 180);
        g.setColor(pelaje);
        g.fillPolygon(new int[]{x + (int) (s * 0.08), x + (int) (s * 0.30), x + (int) (s * 0.02)},
                new int[]{y + (int) (s * 0.30), y + (int) (s * 0.30), y}, 3);
        g.fillPolygon(new int[]{x + (int) (s * 0.70), x + (int) (s * 0.92), x + (int) (s * 0.98)},
                new int[]{y + (int) (s * 0.30), y + (int) (s * 0.30), y}, 3);
        g.fillOval(x + (int) (s * 0.08), y + (int) (s * 0.18), (int) (s * 0.84), (int) (s * 0.7));
        g.setColor(new Color(70, 50, 40));
        g.fillOval(x + (int) (s * 0.28), y + (int) (s * 0.45), (int) (s * 0.1), (int) (s * 0.1));
        g.fillOval(x + (int) (s * 0.62), y + (int) (s * 0.45), (int) (s * 0.1), (int) (s * 0.1));
        g.setStroke(new BasicStroke(Math.max(1f, s * 0.03f)));
        g.drawLine(x + (int) (s * 0.05), y + (int) (s * 0.6), x + (int) (s * 0.3), y + (int) (s * 0.58));
        g.drawLine(x + (int) (s * 0.7), y + (int) (s * 0.58), x + (int) (s * 0.95), y + (int) (s * 0.6));
    }

    private static void pajarito(Graphics2D g, int x, int y, int s) {
        Color plumas = new Color(240, 200, 90);
        g.setColor(plumas);
        g.fillOval(x + (int) (s * 0.12), y + (int) (s * 0.22), (int) (s * 0.7), (int) (s * 0.62));
        g.setColor(plumas.darker());
        g.fillOval(x + (int) (s * 0.05), y + (int) (s * 0.35), (int) (s * 0.35), (int) (s * 0.3));
        g.setColor(new Color(220, 130, 50));
        g.fillPolygon(new int[]{x + (int) (s * 0.78), x + (int) (s * 0.98), x + (int) (s * 0.78)},
                new int[]{y + (int) (s * 0.42), y + (int) (s * 0.5), y + (int) (s * 0.58)}, 3);
        g.setColor(new Color(50, 40, 30));
        g.fillOval(x + (int) (s * 0.58), y + (int) (s * 0.35), (int) (s * 0.08), (int) (s * 0.08));
    }

    private static void pata(Graphics2D g, int x, int y, int s) {
        g.setColor(new Color(139, 100, 80));
        g.fillOval(x + (int) (s * 0.2), y + (int) (s * 0.4), (int) (s * 0.6), (int) (s * 0.5));
        int dedo = (int) (s * 0.22);
        g.fillOval(x + (int) (s * 0.05), y + (int) (s * 0.05), dedo, dedo);
        g.fillOval(x + (int) (s * 0.39), y, dedo, dedo);
        g.fillOval(x + (int) (s * 0.73), y + (int) (s * 0.05), dedo, dedo);
    }
}
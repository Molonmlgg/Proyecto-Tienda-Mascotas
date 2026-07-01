package vista;

import modelo.Gato;
import modelo.Mascota;
import modelo.Pajaro;
import modelo.Perro;

import javax.swing.*;
import java.awt.*;

/**
 * Panel encargado de renderizar el hábitat de la mascota.
 * v2: cielo cálido "hora dorada", nubes esponjosas animadas, pasto con
 * textura de hebras, cerca de madera decorativa y flores dispersas para
 * dar sensación de un hogar acogedor en vez de un fondo plano.
 *
 * @author Cristóbal Araya Lillo
 */
public class EscenaPanel extends JPanel {

    private Mascota mascota;
    private int frame = 0;

    // Posiciones fijas (deterministas) de flores y hebras para que no "titilen" entre repaints
    private static final int[] FLORES_X = {40, 110, 250, 340, 430, 520, 610, 700, 780, 860};
    private static final int[] FLORES_TIPO = {0, 1, 0, 2, 1, 0, 2, 1, 0, 2};

    public void setMascota(Mascota mascota) { this.mascota = mascota; }
    public void setFrame(int frame) { this.frame = frame; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int horizonte = (int) (h * 0.68);

        dibujarCielo(g2, w, horizonte);
        dibujarNubes(g2, w, horizonte);
        dibujarSolConHalo(g2, w);
        dibujarPasto(g2, w, h, horizonte);
        dibujarCerca(g2, w, horizonte);
        dibujarFlores(g2, w, horizonte);

        if (mascota instanceof Perro) {
            dibujarCasaPerro(g2, w / 2 - 150, horizonte - 120);
        } else if (mascota instanceof Gato) {
            dibujarCojinGato(g2, w / 2 - 40, horizonte - 15);
        } else if (mascota instanceof Pajaro) {
            dibujarRama(g2, w / 2 - 80, horizonte - 40);
        }

        if (mascota != null) {
            RenderizadorMascota.dibujar(g2, mascota, frame, w / 2, horizonte - 20);
        }

        g2.dispose();
    }

    private void dibujarCielo(Graphics2D g2, int w, int horizonte) {
        // Degradado "hora dorada": lavanda suave arriba -> durazno cálido en el horizonte
        GradientPaint cielo = new GradientPaint(
                0, 0, new Color(180, 190, 230),
                0, horizonte, new Color(255, 214, 168));
        g2.setPaint(cielo);
        g2.fillRect(0, 0, w, horizonte);
    }

    private void dibujarNubes(Graphics2D g2, int w, int horizonte) {
        g2.setColor(new Color(255, 250, 245, 200));
        for (int i = 0; i < 3; i++) {
            int desplazo = (int) ((frame * 0.15 + i * 260) % (w + 200)) - 100;
            int cy = 40 + i * 45;
            dibujarNube(g2, desplazo, cy);
        }
    }

    private void dibujarNube(Graphics2D g2, int x, int y) {
        g2.fillOval(x, y, 60, 34);
        g2.fillOval(x + 30, y - 12, 55, 40);
        g2.fillOval(x + 65, y, 50, 30);
    }

    private void dibujarSolConHalo(Graphics2D g2, int w) {
        int solY = 55 + (int) (Math.sin(frame * 0.05) * 4);
        int cx = w - 90, cy = solY + 40;

        RadialGradientPaint halo = new RadialGradientPaint(
                new Point(cx, cy), 90,
                new float[]{0f, 1f},
                new Color[]{new Color(255, 225, 150, 130), new Color(255, 225, 150, 0)});
        g2.setPaint(halo);
        g2.fillOval(cx - 90, cy - 90, 180, 180);

        g2.setColor(new Color(255, 214, 120));
        g2.fillOval(w - 120, solY, 80, 80);
    }

    private void dibujarPasto(Graphics2D g2, int w, int h, int horizonte) {
        GradientPaint pasto = new GradientPaint(0, horizonte, new Color(150, 205, 110), 0, h, new Color(85, 155, 75));
        g2.setPaint(pasto);
        g2.fillRect(0, horizonte, w, h - horizonte);

        // Textura de hebras de pasto (posiciones deterministas según el ancho)
        g2.setColor(new Color(70, 135, 65, 140));
        g2.setStroke(new BasicStroke(2f));
        for (int x = 10; x < w; x += 24) {
            int variacion = (x * 37) % 10;
            int baseY = horizonte + 6 + variacion;
            g2.drawLine(x, baseY, x - 4, baseY - 10);
            g2.drawLine(x, baseY, x + 4, baseY - 8);
        }
    }

    private void dibujarCerca(Graphics2D g2, int w, int horizonte) {
        int y = horizonte - 6;
        g2.setColor(new Color(196, 154, 108));
        g2.fillRect(0, y, w, 6); // travesaño horizontal
        for (int x = 10; x < w; x += 55) {
            g2.setColor(new Color(178, 138, 94));
            g2.fillRoundRect(x, y - 22, 10, 30, 4, 4);
            g2.setColor(new Color(150, 112, 74));
            g2.drawRoundRect(x, y - 22, 10, 30, 4, 4);
        }
    }

    private void dibujarFlores(Graphics2D g2, int w, int horizonte) {
        Color[] petalos = {new Color(255, 170, 180), new Color(255, 225, 130), new Color(200, 170, 240)};
        for (int i = 0; i < FLORES_X.length; i++) {
            int x = FLORES_X[i] % Math.max(w, 1);
            int y = horizonte + 25 + (i % 3) * 14;
            Color color = petalos[FLORES_TIPO[i]];
            g2.setColor(color);
            for (int p = 0; p < 4; p++) {
                double ang = p * Math.PI / 2;
                g2.fillOval(x + (int) (Math.cos(ang) * 5) - 3, y + (int) (Math.sin(ang) * 5) - 3, 7, 7);
            }
            g2.setColor(new Color(255, 220, 90));
            g2.fillOval(x - 3, y - 3, 6, 6);
        }
    }

    private void dibujarCasaPerro(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(205, 95, 70));
        g2.fillPolygon(new int[]{x - 20, x + 60, x + 140}, new int[]{y + 50, y - 20, y + 50}, 3);
        g2.setColor(new Color(235, 210, 175));
        g2.fillRect(x, y + 50, 120, 80);
        g2.setColor(new Color(90, 65, 45));
        g2.fillArc(x + 35, y + 80, 50, 70, 0, 180);
        g2.fillRect(x + 35, y + 115, 50, 15);
    }

    private void dibujarCojinGato(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(190, 130, 160));
        g2.fillRoundRect(x - 50, y, 100, 30, 20, 20);
        g2.setColor(new Color(160, 105, 135));
        g2.drawRoundRect(x - 50, y, 100, 30, 20, 20);
    }

    private void dibujarRama(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(120, 80, 45));
        g2.fillRect(x - 100, y + 20, 250, 15);
        g2.setColor(new Color(90, 165, 60));
        g2.fillOval(x + 120, y - 10, 50, 50);
    }
}
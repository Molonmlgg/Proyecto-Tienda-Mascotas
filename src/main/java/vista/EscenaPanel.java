package vista;

import modelo.Gato;
import modelo.Mascota;
import modelo.Pajaro;
import modelo.Perro;

import javax.swing.*;
import java.awt.*;

/**
 * Panel encargado de renderizar el hábitat de la mascota.
 * Dibuja un fondo dinámico (cielo, sol, pasto) y un elemento decorativo
 * específico dependiendo del tipo de mascota activa.
 * @author Cristóbal Araya Lillo
 */
public class EscenaPanel extends JPanel {

    private Mascota mascota;
    private int frame = 0;

    public void setMascota(Mascota mascota) { this.mascota = mascota; }
    public void setFrame(int frame) { this.frame = frame; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int horizonte = (int) (h * 0.70);

        GradientPaint cielo = new GradientPaint(0, 0, new Color(135, 206, 235), 0, horizonte, new Color(200, 230, 250));
        g2.setPaint(cielo);
        g2.fillRect(0, 0, w, horizonte);

        g2.setColor(new Color(255, 230, 120));
        int solY = 50 + (int) (Math.sin(frame * 0.05) * 4);
        g2.fillOval(w - 120, solY, 80, 80);

        GradientPaint pasto = new GradientPaint(0, horizonte, new Color(120, 200, 90), 0, h, new Color(70, 150, 60));
        g2.setPaint(pasto);
        g2.fillRect(0, horizonte, w, h - horizonte);

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

    private void dibujarCasaPerro(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(200, 50, 50));
        g2.fillPolygon(new int[]{x - 20, x + 60, x + 140}, new int[]{y + 50, y - 20, y + 50}, 3);
        g2.setColor(new Color(220, 200, 170));
        g2.fillRect(x, y + 50, 120, 80);
        g2.setColor(Color.DARK_GRAY);
        g2.fillArc(x + 35, y + 80, 50, 70, 0, 180);
        g2.fillRect(x + 35, y + 115, 50, 15);
    }

    private void dibujarCojinGato(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(150, 100, 180));
        g2.fillRoundRect(x - 50, y, 100, 30, 20, 20);
        g2.setColor(new Color(120, 80, 150));
        g2.drawRoundRect(x - 50, y, 100, 30, 20, 20);
    }

    private void dibujarRama(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(101, 67, 33));
        g2.fillRect(x - 100, y + 20, 250, 15);
        g2.setColor(new Color(34, 139, 34));
        g2.fillOval(x + 120, y - 10, 50, 50);
    }
}
package vista;

import modelo.Gato;
import modelo.Mascota;
import modelo.Pajaro;
import modelo.Perro;
import modelo.Pez;

import java.awt.*;

/**
 * Dibuja cada tipo de mascota usando vectores (Graphics2D).
 * @author Cristóbal Araya Lillo
 */
public final class RenderizadorMascota {

    private RenderizadorMascota() {}

    public static void dibujar(Graphics2D g2, Mascota mascota, int frame, int cx, int cy) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Respiración suave
        int y = cy + (int) (Math.sin(frame * 0.08) * 4);

        if (mascota instanceof Perro) {
            dibujarPerro(g2, mascota, frame, cx, y);
        } else if (mascota instanceof Gato) {
            dibujarGato(g2, mascota, frame, cx, y);
        } else if (mascota instanceof Pez) {
            dibujarPez(g2, mascota, frame, cx, y);
        } else if (mascota instanceof Pajaro) {
            dibujarPajaro(g2, mascota, frame, cx, y);
        }
    }

    private static void dibujarPerro(Graphics2D g2, Mascota m, int frame, int cx, int cy) {
        Color cuerpo = new Color(205, 170, 125); // Café claro tierno

        // Cola animada
        double velocidadCola = m.estaTriste() ? 0.05 : 0.3;
        int colaX = cx + 55 + (int) (20 * Math.sin(frame * velocidadCola));
        g2.setColor(cuerpo.darker());
        g2.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(cx + 40, cy + 20, colaX, cy);

        // Cuerpo y cabeza
        g2.setColor(cuerpo);
        g2.fillOval(cx - 50, cy - 10, 100, 70); // Cuerpo más compacto
        g2.fillOval(cx - 45, cy - 70, 90, 85); // Cabeza grande

        // Orejas
        int caidaOreja = m.estaTriste() ? 25 : 5;
        g2.setColor(new Color(139, 90, 43)); // Orejas oscuras
        g2.fillOval(cx - 55, cy - 60 + caidaOreja, 30, 50);
        g2.fillOval(cx + 25, cy - 60 + caidaOreja, 30, 50);

        dibujarExpresion(g2, m, cx, cy - 35, 90);
    }

    private static void dibujarGato(Graphics2D g2, Mascota m, int frame, int cx, int cy) {
        Color cuerpo = new Color(230, 210, 190); // Beige/Gris cálido

        // Cola
        double angulo = frame * 0.08;
        int colaX = cx + 50 + (int) (15 * Math.sin(angulo));
        int colaY = cy + (int) (10 * Math.cos(angulo));
        g2.setColor(cuerpo);
        g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(cx + 40, cy + 30, colaX, colaY);

        // Orejas puntiagudas
        g2.fillPolygon(new int[]{cx - 35, cx - 15, cx - 45}, new int[]{cy - 50, cy - 50, cy - 85}, 3);
        g2.fillPolygon(new int[]{cx + 35, cx + 15, cx + 45}, new int[]{cy - 50, cy - 50, cy - 85}, 3);

        // Cuerpo y cabeza
        g2.setColor(cuerpo);
        g2.fillOval(cx - 45, cy - 5, 90, 65);
        g2.fillOval(cx - 40, cy - 65, 80, 75);

        dibujarExpresion(g2, m, cx, cy - 30, 80);
    }

    private static void dibujarPajaro(Graphics2D g2, Mascota m, int frame, int cx, int cy) {
        Color cuerpo = new Color(255, 220, 100);

        // Patas
        g2.setColor(new Color(210, 105, 30));
        g2.fillRect(cx - 10, cy + 45, 4, 15);
        g2.fillRect(cx + 6, cy + 45, 4, 15);

        // Cuerpo y cabeza
        g2.setColor(cuerpo);
        g2.fillOval(cx - 35, cy - 15, 70, 65);
        g2.fillOval(cx - 30, cy - 60, 60, 60);

        // Alas animadas
        double aleteo = Math.abs(Math.sin(frame * 0.3)) * 20;
        g2.setColor(new Color(255, 180, 50));
        g2.fillOval(cx - 45, (int) (cy - aleteo), 30, 25);

        // Pico
        g2.fillPolygon(new int[]{cx + 25, cx + 40, cx + 25}, new int[]{cy - 35, cy - 28, cy - 20}, 3);

        dibujarExpresion(g2, m, cx - 5, cy - 35, 60);
    }

    private static void dibujarPez(Graphics2D g2, Mascota m, int frame, int cx, int cy) {
        Color cuerpo = new Color(255, 127, 80);
        int x = cx + (int) (Math.sin(frame * 0.1) * 15);

        // Cola
        double anguloCola = Math.sin(frame * 0.4) * 0.5;
        Polygon cola = new Polygon();
        cola.addPoint(x - 45, cy);
        cola.addPoint(x - 70, cy - 20 + (int) (anguloCola * 20));
        cola.addPoint(x - 70, cy + 20 - (int) (anguloCola * 20));
        g2.setColor(cuerpo.darker());
        g2.fillPolygon(cola);

        // Cuerpo
        g2.setColor(cuerpo);
        g2.fillOval(x - 50, cy - 30, 90, 60);

        dibujarExpresion(g2, m, x + 10, cy - 5, 50);
    }

    private static void dibujarExpresion(Graphics2D g2, Mascota m, int cxCabeza, int cyCabeza, int anchoCabeza) {
        int separacion = anchoCabeza / 5;
        int ojoY = cyCabeza;

        if (m.estaEnferma()) {
            g2.setColor(new Color(100, 50, 50));
            g2.setStroke(new BasicStroke(3));
            dibujarX(g2, cxCabeza - separacion, ojoY, 5);
            dibujarX(g2, cxCabeza + separacion, ojoY, 5);
        } else {
            g2.setColor(new Color(40, 30, 20)); // Ojos oscuros suaves
            g2.fillOval(cxCabeza - separacion - 6, ojoY - 6, 12, 12);
            g2.fillOval(cxCabeza + separacion - 6, ojoY - 6, 12, 12);
        }

        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int bocaY = ojoY + 15;
        if (m.tieneHambre()) {
            g2.drawArc(cxCabeza - 10, bocaY - 5, 20, 15, 0, -180); // Boquita abierta
        } else if (m.estaTriste()) {
            g2.drawArc(cxCabeza - 10, bocaY, 20, 10, 0, 180); // Triste
        } else {
            g2.drawArc(cxCabeza - 10, bocaY - 5, 20, 15, 180, 180); // Feliz
        }

        if (m.estaSucia()) {
            g2.setColor(new Color(100, 80, 50, 150));
            g2.fillOval(cxCabeza - separacion - 15, ojoY + 10, 12, 8);
            g2.fillOval(cxCabeza + separacion + 5, ojoY + 15, 10, 6);
        }
    }

    private static void dibujarX(Graphics2D g2, int cx, int cy, int r) {
        g2.drawLine(cx - r, cy - r, cx + r, cy + r);
        g2.drawLine(cx - r, cy + r, cx + r, cy - r);
    }
}
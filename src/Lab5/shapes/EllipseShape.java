package Lab5.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class EllipseShape extends Shape {

    @Override
    public String getName() {
        return "Еліпс";
    }

    @Override
    public void show(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        g2d.setColor(Color.WHITE);
        g2d.fillOval(minX, minY, width, height);

        applyOutlineStyle(g2d, Color.BLACK);
        g2d.drawOval(minX, minY, width, height);
    }

    @Override
    public void trail(Graphics g, int currentX, int currentY) {
        //Пунктирний чорний "гумовий" слід по двох кутах
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLACK);
        float[] dash = {5.0f, 5.0f};
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));

        int x = Math.min(x1, currentX);
        int y = Math.min(y1, currentY);
        int width = Math.abs(currentX - x1);
        int height = Math.abs(currentY - y1);

        g2d.drawOval(x, y, width, height);
        g2d.dispose();
    }
}

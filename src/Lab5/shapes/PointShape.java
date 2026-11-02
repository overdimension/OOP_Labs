package Lab5.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PointShape extends Shape {

    @Override
    public String getName() {
        return "Крапка";
    }

    @Override
    public void show(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int r = selected ? 6 : 3;
        applyOutlineStyle(g2d, Color.BLACK);
        g2d.fillOval(x1 - r, y1 - r, 2 * r, 2 * r);
    }

    @Override
    public void trail(Graphics g, int currentX, int currentY) {
        g.setColor(Color.BLACK);
        g.fillRect(currentX, currentY, 2, 2);
    }
}

package Lab2;
import java.awt.Color;
import java.awt.Graphics;

public class PointShape extends Shape {
    @Override
    public void show(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x1, y1, 2, 2);
    }

    @Override
    public void trail(Graphics g, int currentX, int currentY) {
        g.setColor(Color.BLACK);
        g.fillRect(currentX, currentY, 2, 2);
    }
}
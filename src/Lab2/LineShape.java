package Lab2;
import java.awt.Color;
import java.awt.Graphics;

public class LineShape extends Shape {
    @Override
    public void show(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void trail(Graphics g, int currentX, int currentY) {
        //Гумовий слід: суцільна лінія синього кольору
        g.setColor(Color.BLUE);
        g.drawLine(x1, y1, currentX, currentY);
    }
}

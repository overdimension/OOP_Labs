package Lab3;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class LineShape extends Shape {
    @Override
    public void show(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void trail(Graphics g, int currentX, int currentY) {
        //"Гумовий" слід: пунктирна лінія чорного кольору
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLACK);
        float[] dash = {5.0f, 5.0f};
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
        g2d.drawLine(x1, y1, currentX, currentY);
        g2d.dispose();
    }
}

package Lab4;
import java.awt.Graphics;

public class LineCirclesShape extends Shape {
    private final LineShape line = new LineShape();
    private final EllipseShape circle1 = new EllipseShape();
    private final EllipseShape circle2 = new EllipseShape();
    private static final int RADIUS = 6;

    @Override
    public void setPoints(int x1, int y1, int x2, int y2) {
        super.setPoints(x1, y1, x2, y2);
        line.setPoints(x1, y1, x2, y2);
        circle1.setPoints(x1 - RADIUS, y1 - RADIUS, x1 + RADIUS, y1 + RADIUS);
        circle2.setPoints(x2 - RADIUS, y2 - RADIUS, x2 + RADIUS, y2 + RADIUS);
    }

    @Override
    public void show(Graphics g) {
        line.show(g);
        circle1.show(g);
        circle2.show(g);
    }

    @Override
    public void trail(Graphics g, int currentX, int currentY) {
        line.trail(g, currentX, currentY);
    }
}

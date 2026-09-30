package Lab2;
import java.awt.Graphics;

public abstract class Shape {
    protected int x1, y1, x2, y2;

    public void setPoints(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public abstract void show(Graphics g);
    public abstract void trail(Graphics g, int currentX, int currentY);
}

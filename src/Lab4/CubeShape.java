package Lab4;
import java.awt.Graphics;

public class CubeShape extends Shape {
    private final RectangleShape frontRect = new RectangleShape();
    private final RectangleShape backRect = new RectangleShape();
    private final LineShape[] connectLines = new LineShape[4];

    public CubeShape() {
        for (int i = 0; i < 4; i++) {
            connectLines[i] = new LineShape();
        }
    }

    @Override
    public void setPoints(int x1, int y1, int x2, int y2) {
        super.setPoints(x1, y1, x2, y2);

        //Передній прямокутник
        frontRect.setPoints(x1, y1, x2, y2);

        //Зсув для заднього прямокутника
        int dx = (x2 - x1) / 3;
        int dy = (y2 - y1) / 3;

        int bx1 = x1 + dx, by1 = y1 - dy;
        int bx2 = x2 + dx, by2 = y2 - dy;

        backRect.setPoints(bx1, by1, bx2, by2);

        //4 з'єднувальні лінії між вершинами
        connectLines[0].setPoints(x1, y1, bx1, by1);
        connectLines[1].setPoints(x2, y1, bx2, by1);
        connectLines[2].setPoints(x1, y2, bx1, by2);
        connectLines[3].setPoints(x2, y2, bx2, by2);
    }

    @Override
    public void show(Graphics g) {
        backRect.show(g);
        for (LineShape line : connectLines) {
            line.show(g);
        }
        frontRect.show(g);
    }

    @Override
    public void trail(Graphics g, int currentX, int currentY) {
        setPoints(x1, y1, currentX, currentY);
        show(g);
    }
}

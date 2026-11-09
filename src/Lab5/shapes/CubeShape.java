package Lab5.shapes;

import java.awt.Graphics;

public class CubeShape extends Shape {
    private final RectangleShape rectFront = new RectangleShape();
    private final RectangleShape rectBack = new RectangleShape();
    private final LineShape line1 = new LineShape();
    private final LineShape line2 = new LineShape();
    private final LineShape line3 = new LineShape();
    private final LineShape line4 = new LineShape();

    @Override
    public void setPoints(int x1, int y1, int x2, int y2) {
        super.setPoints(x1, y1, x2, y2);

        //Нормалізація координат прямокутника
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);

        //Зсув для об'єму
        int dx = (maxX - minX) / 3;
        int dy = (maxY - minY) / 3;

        //Передній прямокутник
        int fx1 = minX,       fy1 = minY + dy;
        int fx2 = maxX - dx, fy2 = maxY;
        rectFront.setPoints(fx1, fy1, fx2, fy2);

        //Задній прямокутник
        int bx1 = minX + dx,  by1 = minY;
        int bx2 = maxX,       by2 = maxY - dy;
        rectBack.setPoints(bx1, by1, bx2, by2);

        //Чотири сполучні лінії між вершинами двох прямокутників
        line1.setPoints(fx1, fy1, bx1, by1);
        line2.setPoints(fx2, fy1, bx2, by1);
        line3.setPoints(fx2, fy2, bx2, by2);
        line4.setPoints(fx1, fy2, bx1, by2);
    }

    @Override
    public String getName() {
        return "Каркас куба";
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        rectFront.setSelected(selected);
        rectBack.setSelected(selected);
        line1.setSelected(selected);
        line2.setSelected(selected);
        line3.setSelected(selected);
        line4.setSelected(selected);
    }

    @Override
    public void show(Graphics g) {
        rectBack.show(g);
        rectFront.show(g);
        line1.show(g);
        line2.show(g);
        line3.show(g);
        line4.show(g);
    }

    @Override
    public void trail(Graphics g, int currentX, int currentY) {
        setPoints(x1, y1, currentX, currentY);
        show(g);
    }
}

package Lab5.shapes;

import java.awt.Graphics;

public class CubeShape extends Shape {
    private final LineShape[] lines = new LineShape[12]; //12 ребер куба

    public CubeShape() {
        for (int i = 0; i < 12; i++) {
            lines[i] = new LineShape();
        }
    }

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

        //4 вершини передньої грані
        int fx1 = minX,       fy1 = minY + dy;
        int fx2 = maxX - dx, fy1_2 = minY + dy;
        int fx2_2 = maxX - dx, fy2 = maxY;
        int fx1_2 = minX,       fy2_2 = maxY;

        //4 вершини задньої грані
        int bx1 = minX + dx,  by1 = minY;
        int bx2 = maxX,       by1_2 = minY;
        int bx2_2 = maxX,       by2 = maxY - dy;
        int bx1_2 = minX + dx,  by2_2 = maxY - dy;

        //Передній квадрат
        lines[0].setPoints(fx1, fy1, fx2, fy1_2);
        lines[1].setPoints(fx2, fy1_2, fx2_2, fy2);
        lines[2].setPoints(fx2_2, fy2, fx1_2, fy2_2);
        lines[3].setPoints(fx1_2, fy2_2, fx1, fy1);

        //Задній квадрат
        lines[4].setPoints(bx1, by1, bx2, by1_2);
        lines[5].setPoints(bx2, by1_2, bx2_2, by2);
        lines[6].setPoints(bx2_2, by2, bx1_2, by2_2);
        lines[7].setPoints(bx1_2, by2_2, bx1, by1);

        //З'єднувальні ребра
        lines[8].setPoints(fx1, fy1, bx1, by1);
        lines[9].setPoints(fx2, fy1_2, bx2, by1_2);
        lines[10].setPoints(fx2_2, fy2, bx2_2, by2);
        lines[11].setPoints(fx1_2, fy2_2, bx1_2, by2_2);
    }

    @Override
    public String getName() {
        return "Каркас куба";
    }

    @Override
    public void show(Graphics g) {
        //Виклики LineShape.show() для відображення ліній каркаса
        for (LineShape line : lines) {
            line.show(g);
        }
    }

    @Override
    public void trail(Graphics g, int currentX, int currentY) {
        setPoints(x1, y1, currentX, currentY);
        show(g);
    }
}

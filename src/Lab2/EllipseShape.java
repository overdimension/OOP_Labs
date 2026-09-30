package Lab2;
import java.awt.Color;
import java.awt.Graphics;

public class EllipseShape extends Shape {
    @Override
    public void show(Graphics g) {
        //Ввід від центру (x1, y1) до кута охоплюючого прямокутника (x2, y2)
        int rx = Math.abs(x2 - x1);
        int ry = Math.abs(y2 - y1);
        int x = x1 - rx;
        int y = y1 - ry;
        int width = rx * 2;
        int height = ry * 2;

        //Еліпс: чорний контур без заповнення
        g.setColor(Color.BLACK);
        g.drawOval(x, y, width, height);
    }

    @Override
    public void trail(Graphics g, int currentX, int currentY) {
        //Синій "гумовий" слід еліпса
        g.setColor(Color.BLUE);
        int rx = Math.abs(currentX - x1);
        int ry = Math.abs(currentY - y1);
        int x = x1 - rx;
        int y = y1 - ry;
        int width = rx * 2;
        int height = ry * 2;
        g.drawOval(x, y, width, height);
    }
}
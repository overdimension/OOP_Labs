package Lab3;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RectangleShape extends Shape {
    @Override
    public void show(Graphics g) {
        //Ввід від центру до кута
        int rx = Math.abs(x2 - x1);
        int ry = Math.abs(y2 - y1);
        int x = x1 - rx;
        int y = y1 - ry;
        int width = rx * 2;
        int height = ry * 2;

        // Сіре заповнення
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);

        // Чорний контур
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }

    @Override
    public void trail(Graphics g, int currentX, int currentY) {
        //Пунктирний чорний "гумовий" слід від центру
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLACK);
        float[] dash = {5.0f, 5.0f};
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));

        int rx = Math.abs(currentX - x1);
        int ry = Math.abs(currentY - y1);
        int x = x1 - rx;
        int y = y1 - ry;
        int width = rx * 2;
        int height = ry * 2;

        g2d.drawRect(x, y, width, height);
        g2d.dispose();
    }
}

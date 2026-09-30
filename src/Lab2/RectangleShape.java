package Lab2;
import java.awt.Color;
import java.awt.Graphics;

public class RectangleShape extends Shape {
    @Override
    public void show(Graphics g) {
        //Розрахунок двох протилежних кутів
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        //Біле заповнення
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);

        //Чорний контур
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }

    @Override
    public void trail(Graphics g, int currentX, int currentY) {
        //Синій "гумовий" слід прямокутника
        g.setColor(Color.BLUE);
        int x = Math.min(x1, currentX);
        int y = Math.min(y1, currentY);
        int width = Math.abs(currentX - x1);
        int height = Math.abs(currentY - y1);
        g.drawRect(x, y, width, height);
    }
}

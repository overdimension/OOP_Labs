package Lab4;

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

        int totalRx = Math.abs(x2 - x1);
        int totalRy = Math.abs(y2 - y1);

        //Радіуси (півширина та піввисота) для кожного з двох прямокутників
        int frx = totalRx / 2;
        int fry = totalRy / 2;

        //Зсув між передньою та задньою гранню
        int dx = frx;
        int dy = fry;

        //Центр переднього прямокутника
        int fcx = x1 - dx / 2;
        int fcy = y1 + dy / 2;

        //Центр заднього прямокутника
        int bcx = x1 + dx / 2;
        int bcy = y1 - dy / 2;

        //Задаємо центри та радіуси для прямокутників
        rectFront.setPoints(fcx, fcy, fcx + frx, fcy + fry);
        rectBack.setPoints(bcx, bcy, bcx + frx, bcy + fry);

        //4 реальні кути переднього прямокутника
        int fTLx = fcx - frx, fTLy = fcy - fry; // Верхній лівий
        int fTRx = fcx + frx, fTRy = fcy - fry; // Верхній правий
        int fBRx = fcx + frx, fBRy = fcy + fry; // Нижній правий
        int fBLx = fcx - frx, fBLy = fcy + fry; // Нижній лівий

        //4 реальні кути заднього прямокутника
        int bTLx = bcx - frx, bTLy = bcy - fry;
        int bTRx = bcx + frx, bTRy = bcy - fry;
        int bBRx = bcx + frx, bBRy = bcy + fry;
        int bBLx = bcx - frx, bBLy = bcy + fry;

        //4 з'єднувальні лінії точно по кутах прямокутників
        line1.setPoints(fTLx, fTLy, bTLx, bTLy); // Верхній лівий
        line2.setPoints(fTRx, fTRy, bTRx, bTRy); // Верхній правий
        line3.setPoints(fBRx, fBRy, bBRx, bBRy); // Нижній правий
        line4.setPoints(fBLx, fBLy, bBLx, bBLy); // Нижній лівий
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
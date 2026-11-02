package Lab5.shapes;

import java.awt.Graphics;

public abstract class Shape {
    protected int x1, y1, x2, y2;
    protected boolean selected = false; //Позначка виділення об'єкта в таблиці

    public void setPoints(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public abstract String getName();
    public abstract void show(Graphics g);
    public abstract void trail(Graphics g, int currentX, int currentY);
}

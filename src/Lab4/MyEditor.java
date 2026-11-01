package Lab4;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class MyEditor {
    private static final int MAX_SHAPES = 111;
    private final Shape[] pcshape = new Shape[MAX_SHAPES];
    private int shapeCount = 0;

    private Shape currentShape = null;
    private int startX, startY;
    private boolean isDragging = false;

    public boolean addShape(Shape shape) {
        if (shapeCount < MAX_SHAPES) {
            pcshape[shapeCount++] = shape;
            return true;
        }
        return false;
    }

    public void startShape(int mode, int x, int y) {
        startX = x;
        startY = y;

        currentShape = switch (mode) {
            case MainFrame.MODE_POINT -> new PointShape();
            case MainFrame.MODE_LINE -> new LineShape();
            case MainFrame.MODE_RECTANGLE -> new RectangleShape();
            case MainFrame.MODE_ELLIPSE -> new EllipseShape();
            case MainFrame.MODE_LINE_OO -> new LineCirclesShape();
            case MainFrame.MODE_CUBE -> new CubeShape();
            default -> null;
        };

        if (currentShape != null) {
            currentShape.setPoints(startX, startY, startX, startY);
            isDragging = true;
        }
    }

    public void dragShape(int x, int y) {
        if (isDragging && currentShape != null) {
            currentShape.setPoints(startX, startY, x, y);
        }
    }

    public boolean releaseShape(int x, int y) {
        if (isDragging && currentShape != null) {
            currentShape.setPoints(startX, startY, x, y);
            boolean added = addShape(currentShape);
            currentShape = null;
            isDragging = false;
            return added;
        }
        return true;
    }

    public void drawAll(Graphics g, int currentX, int currentY) {
        for (int i = 0; i < shapeCount; i++) {
            if (pcshape[i] != null) {
                pcshape[i].show(g);
            }
        }
        if (isDragging && currentShape != null) {
            currentShape.trail(g, currentX, currentY);
        }
    }

    public boolean isDragging() {
        return isDragging;
    }
}

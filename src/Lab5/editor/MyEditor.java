package Lab5.editor;

import Lab5.MainFrame;
import Lab5.shapes.*;
import java.awt.Graphics;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyEditor {
    private static MyEditor instance;
    private static final int MAX_SHAPES = 111;
    private final List<Shape> shapes = new ArrayList<>();

    private Shape currentShape = null;
    private int startX, startY;
    private boolean isDragging = false;
    private Runnable onDataChangedCallback;

    //Приватний конструктор Singleton
    private MyEditor() {}

    public static synchronized MyEditor getInstance() {
        if (instance == null) {
            instance = new MyEditor();
        }
        return instance;
    }

    public void setOnDataChangedCallback(Runnable callback) {
        this.onDataChangedCallback = callback;
    }

    private void notifyDataChanged() {
        if (onDataChangedCallback != null) {
            onDataChangedCallback.run();
        }
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public boolean addShape(Shape shape) {
        if (shapes.size() < MAX_SHAPES) {
            shapes.add(shape);
            notifyDataChanged();
            return true;
        }
        return false;
    }

    public void selectShape(int index) {
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).setSelected(i == index);
        }
    }

    public void removeShape(int index) {
        if (index >= 0 && index < shapes.size()) {
            shapes.remove(index);
            notifyDataChanged();
        }
    }

    public void startShape(int mode, int x, int y) {
        startX = x; startY = y;
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
        for (Shape shape : shapes) {
            shape.show(g);
        }
        if (isDragging && currentShape != null) {
            currentShape.trail(g, currentX, currentY);
        }
    }

    //Збереження в CSV
    public void saveToCSV(File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Shape shape : shapes) {
                writer.println(shape.getName() + "," + shape.getX1() + "," + shape.getY1() + "," + shape.getX2() + "," + shape.getY2());
            }
        }
    }

    //Завантаження із CSV
    public void loadFromCSV(File file) throws IOException {
        shapes.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String name = parts[0];
                    int x1 = Integer.parseInt(parts[1]);
                    int y1 = Integer.parseInt(parts[2]);
                    int x2 = Integer.parseInt(parts[3]);
                    int y2 = Integer.parseInt(parts[4]);

                    Shape shape = switch (name) {
                        case "Крапка" -> new PointShape();
                        case "Лінія" -> new LineShape();
                        case "Прямокутник" -> new RectangleShape();
                        case "Еліпс" -> new EllipseShape();
                        case "Лінія з кружечками" -> new LineCirclesShape();
                        case "Каркас куба" -> new CubeShape();
                        default -> null;
                    };

                    if (shape != null) {
                        shape.setPoints(x1, y1, x2, y2);
                        shapes.add(shape);
                    }
                }
            }
        }
        notifyDataChanged();
    }
}

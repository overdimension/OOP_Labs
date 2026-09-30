package Lab2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    public static final int MODE_POINT = 1;
    public static final int MODE_LINE = 2;
    public static final int MODE_RECTANGLE = 3;
    public static final int MODE_ELLIPSE = 4;

    private int currentMode = MODE_POINT;

    private static final int MAX_SHAPES = 110; //N = 10 + 100
    private Shape[] pcshape = new Shape[MAX_SHAPES]; //Статичний масив
    private int shapeCount = 0;

    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenu menuObjects;
    private JMenuItem itemExit;
    private JMenuItem itemPoint;
    private JMenuItem itemLine;
    private JMenuItem itemRectangle;
    private JMenuItem itemEllipse;

    private CanvasPanel canvasPanel;

    public MainFrame() {
        setTitle("Лабораторна робота №2 - Варіант 10");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initMenu();

        canvasPanel = new CanvasPanel();
        add(canvasPanel, BorderLayout.CENTER);
    }

    public boolean addShape(Shape shape) {
        if (shapeCount < MAX_SHAPES) {
            pcshape[shapeCount++] = shape;
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Переповнення статичного масиву (максимум 110 об'єктів)!", "Помилка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void initMenu() {
        menuBar = new JMenuBar();

        menuFile = new JMenu("Файл");
        itemExit = new JMenuItem("Вихід");
        itemExit.addActionListener(e -> System.exit(0));
        menuFile.add(itemExit);

        menuObjects = new JMenu("Об'єкти");
        itemPoint = new JMenuItem("Крапка");
        itemLine = new JMenuItem("Лінія");
        itemRectangle = new JMenuItem("Прямокутник");
        itemEllipse = new JMenuItem("Еліпс");

        itemPoint.addActionListener(e -> currentMode = MODE_POINT);
        itemLine.addActionListener(e -> currentMode = MODE_LINE);
        itemRectangle.addActionListener(e -> currentMode = MODE_RECTANGLE);
        itemEllipse.addActionListener(e -> currentMode = MODE_ELLIPSE);

        menuObjects.add(itemPoint);
        menuObjects.add(itemLine);
        menuObjects.add(itemRectangle);
        menuObjects.add(itemEllipse);

        menuBar.add(menuFile);
        menuBar.add(menuObjects);

        setJMenuBar(menuBar);
    }

    private class CanvasPanel extends JPanel {
        private Shape currentShape = null;
        private int startX, startY;
        private int currentX, currentY;
        private boolean isDragging = false;

        public CanvasPanel() {
            setBackground(Color.WHITE);

            MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    startX = e.getX();
                    startY = e.getY();
                    currentX = startX;
                    currentY = startY;

                    switch (currentMode) {
                        case MODE_POINT:
                            currentShape = new PointShape();
                            break;
                        case MODE_LINE:
                            currentShape = new LineShape();
                            break;
                        case MODE_RECTANGLE:
                            currentShape = new RectangleShape();
                            break;
                    }

                    if (currentShape != null) {
                        currentShape.setPoints(startX, startY, startX, startY);
                        isDragging = true;
                    }
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (isDragging && currentShape != null) {
                        currentX = e.getX();
                        currentY = e.getY();
                        repaint(); //Автоматично викликає paintComponent для гумового сліду
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (isDragging && currentShape != null) {
                        currentShape.setPoints(startX, startY, e.getX(), e.getY());
                        addShape(currentShape);
                        currentShape = null;
                        isDragging = false;
                        repaint(); //Малює фігуру остаточно
                    }
                }
            };

            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            //Відмальовка всіх фігур з масиву
            for (int i = 0; i < shapeCount; i++) {
                if (pcshape[i] != null) {
                    pcshape[i].show(g);
                }
            }

            //Відмальовка гумового сліду під час перетягування
            if (isDragging && currentShape != null) {
                currentShape.trail(g, currentX, currentY);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

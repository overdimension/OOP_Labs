package Lab3;
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

    private static final int MAX_SHAPES = 111; //N = 11 + 100 = 111
    private Shape[] pcshape = new Shape[MAX_SHAPES];
    private int shapeCount = 0;

    //Menu
    private JMenuBar menuBar;
    private JMenu menuFile, menuObjects, menuHelp;
    private JRadioButtonMenuItem itemPoint, itemLine, itemRectangle, itemEllipse;
    private ButtonGroup menuGroup;

    //Toolbar
    private JToolBar toolBar;
    private JToggleButton btnPoint, btnLine, btnRectangle, btnEllipse;
    private ButtonGroup toolGroup;

    private CanvasPanel canvasPanel;

    public MainFrame() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initMenu();
        initToolBar();

        canvasPanel = new CanvasPanel();
        add(canvasPanel, BorderLayout.CENTER);

        setMode(MODE_POINT);
    }

    public boolean addShape(Shape shape) {
        if (shapeCount < MAX_SHAPES) {
            pcshape[shapeCount++] = shape;
            return true;
        } else {
            JOptionPane.showMessageDialog(this,
                    "Переповнення статичного масиву (максимум 111 об'єктів)!",
                    "Помилка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void updateTitle() {
        String modeName = switch (currentMode) {
            case MODE_POINT -> "Крапка";
            case MODE_LINE -> "Лінія";
            case MODE_RECTANGLE -> "Прямокутник";
            case MODE_ELLIPSE -> "Еліпс";
            default -> "";
        };
        //Позначка поточного типу об'єкта в заголовку вікна
        setTitle("Лабораторна робота №3 - [Обрано об'єкт: " + modeName + "]");
    }

    //Синхронізована зміна режиму
    private void setMode(int mode) {
        this.currentMode = mode;
        updateTitle();

        //Синхронізація кнопок Toolbar
        switch (mode) {
            case MODE_POINT -> { btnPoint.setSelected(true); itemPoint.setSelected(true); }
            case MODE_LINE -> { btnLine.setSelected(true); itemLine.setSelected(true); }
            case MODE_RECTANGLE -> { btnRectangle.setSelected(true); itemRectangle.setSelected(true); }
            case MODE_ELLIPSE -> { btnEllipse.setSelected(true); itemEllipse.setSelected(true); }
        }
    }

    private void initMenu() {
        menuBar = new JMenuBar();

        menuFile = new JMenu("Файл");
        JMenuItem itemExit = new JMenuItem("Вихід");
        itemExit.addActionListener(e -> System.exit(0));
        menuFile.add(itemExit);

        menuObjects = new JMenu("Об'єкти");
        menuGroup = new ButtonGroup();

        itemPoint = new JRadioButtonMenuItem("Крапка");
        itemLine = new JRadioButtonMenuItem("Лінія");
        itemRectangle = new JRadioButtonMenuItem("Прямокутник");
        itemEllipse = new JRadioButtonMenuItem("Еліпс");

        itemPoint.addActionListener(e -> setMode(MODE_POINT));
        itemLine.addActionListener(e -> setMode(MODE_LINE));
        itemRectangle.addActionListener(e -> setMode(MODE_RECTANGLE));
        itemEllipse.addActionListener(e -> setMode(MODE_ELLIPSE));

        menuGroup.add(itemPoint);
        menuGroup.add(itemLine);
        menuGroup.add(itemRectangle);
        menuGroup.add(itemEllipse);

        menuObjects.add(itemPoint);
        menuObjects.add(itemLine);
        menuObjects.add(itemRectangle);
        menuObjects.add(itemEllipse);

        menuHelp = new JMenu("Довідка");
        JMenuItem itemAbout = new JMenuItem("Про програму");
        itemAbout.addActionListener(e -> JOptionPane.showMessageDialog(this, "Графічний редактор. Лабораторна робота №3"));
        menuHelp.add(itemAbout);

        //Порядок пунктів меню за умовою: Файл | Об'єкти | Довідка
        menuBar.add(menuFile);
        menuBar.add(menuObjects);
        menuBar.add(menuHelp);

        setJMenuBar(menuBar);
    }

    private void initToolBar() {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        toolGroup = new ButtonGroup();

        btnPoint = new JToggleButton("Крапка");
        btnLine = new JToggleButton("Лінія");
        btnRectangle = new JToggleButton("Прямокутник");
        btnEllipse = new JToggleButton("Еліпс");

        //Додавання Tooltips (підказок)
        btnPoint.setToolTipText("Малювання крапки");
        btnLine.setToolTipText("Малювання лінії");
        btnRectangle.setToolTipText("Малювання прямокутника");
        btnEllipse.setToolTipText("Малювання еліпса");

        btnPoint.addActionListener(e -> setMode(MODE_POINT));
        btnLine.addActionListener(e -> setMode(MODE_LINE));
        btnRectangle.addActionListener(e -> setMode(MODE_RECTANGLE));
        btnEllipse.addActionListener(e -> setMode(MODE_ELLIPSE));

        toolGroup.add(btnPoint);
        toolGroup.add(btnLine);
        toolGroup.add(btnRectangle);
        toolGroup.add(btnEllipse);

        toolBar.add(btnPoint);
        toolBar.add(btnLine);
        toolBar.add(btnRectangle);
        toolBar.add(btnEllipse);

        add(toolBar, BorderLayout.NORTH);
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
                        case MODE_ELLIPSE:
                            currentShape = new EllipseShape();
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
                        repaint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (isDragging && currentShape != null) {
                        currentShape.setPoints(startX, startY, e.getX(), e.getY());
                        addShape(currentShape);
                        currentShape = null;
                        isDragging = false;
                        repaint();
                    }
                }
            };

            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int i = 0; i < shapeCount; i++) {
                if (pcshape[i] != null) {
                    pcshape[i].show(g);
                }
            }

            if (isDragging && currentShape != null) {
                currentShape.trail(g, currentX, currentY);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

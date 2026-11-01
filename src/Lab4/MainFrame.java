package Lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    public static final int MODE_POINT = 1;
    public static final int MODE_LINE = 2;
    public static final int MODE_RECTANGLE = 3;
    public static final int MODE_ELLIPSE = 4;
    public static final int MODE_LINE_OO = 5;
    public static final int MODE_CUBE = 6;

    private int currentMode = MODE_POINT;

    //Динамічний об'єкт класу MyEditor
    private MyEditor editor;

    //Menu
    private JMenuBar menuBar;
    private JRadioButtonMenuItem itemPoint, itemLine, itemRectangle, itemEllipse, itemLineOO, itemCube;
    private ButtonGroup menuGroup;

    //Toolbar
    private JToolBar toolBar;
    private JToggleButton btnPoint, btnLine, btnRectangle, btnEllipse, btnLineOO, btnCube;
    private ButtonGroup toolGroup;

    private CanvasPanel canvasPanel;

    public MainFrame() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //Динамічне створення екземпляра MyEditor
        editor = new MyEditor();

        initMenu();
        initToolBar();

        canvasPanel = new CanvasPanel();
        add(canvasPanel, BorderLayout.CENTER);

        setMode(MODE_POINT);
    }

    private void updateTitle() {
        String modeName = switch (currentMode) {
            case MODE_POINT -> "Крапка";
            case MODE_LINE -> "Лінія";
            case MODE_RECTANGLE -> "Прямокутник";
            case MODE_ELLIPSE -> "Еліпс";
            case MODE_LINE_OO -> "Лінія з кружечками";
            case MODE_CUBE -> "Каркас куба";
            default -> "";
        };
        setTitle("Лабораторна робота №4 - [Обрано об'єкт: " + modeName + "]");
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
            case MODE_LINE_OO -> { btnLineOO.setSelected(true); itemLineOO.setSelected(true); }
            case MODE_CUBE -> { btnCube.setSelected(true); itemCube.setSelected(true); }
        }
    }

    private void initMenu() {
        menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("Файл");
        JMenuItem itemExit = new JMenuItem("Вихід");
        itemExit.addActionListener(e -> System.exit(0));
        menuFile.add(itemExit);

        JMenu menuObjects = new JMenu("Об'єкти");
        menuGroup = new ButtonGroup();

        itemPoint = new JRadioButtonMenuItem("Крапка");
        itemLine = new JRadioButtonMenuItem("Лінія");
        itemRectangle = new JRadioButtonMenuItem("Прямокутник");
        itemEllipse = new JRadioButtonMenuItem("Еліпс");
        itemLineOO = new JRadioButtonMenuItem("Лінія з кружечками");
        itemCube = new JRadioButtonMenuItem("Каркас куба");

        itemPoint.addActionListener(e -> setMode(MODE_POINT));
        itemLine.addActionListener(e -> setMode(MODE_LINE));
        itemRectangle.addActionListener(e -> setMode(MODE_RECTANGLE));
        itemEllipse.addActionListener(e -> setMode(MODE_ELLIPSE));
        itemLineOO.addActionListener(e -> setMode(MODE_LINE_OO));
        itemCube.addActionListener(e -> setMode(MODE_CUBE));

        JRadioButtonMenuItem[] items = {itemPoint, itemLine, itemRectangle, itemEllipse, itemLineOO, itemCube};
        for (JRadioButtonMenuItem item : items) {
            menuGroup.add(item);
            menuObjects.add(item);
        }

        JMenu menuHelp = new JMenu("Довідка");
        JMenuItem itemAbout = new JMenuItem("Про програму");
        itemAbout.addActionListener(e -> JOptionPane.showMessageDialog(this, "Графічний редактор. Лабораторна робота №4"));
        menuHelp.add(itemAbout);

        //Порядок пунктів меню за умовою: Файл | Об'єкти | Довідка
        menuBar.add(menuFile);
        menuBar.add(menuObjects);
        menuBar.add(menuHelp);

        setJMenuBar(menuBar);
    }

    //Допоміжний метод для автоматичного масштабування PNG під розмір кнопки
    private ImageIcon loadScaledIcon(String path, int width, int height) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon originalIcon = new ImageIcon(imgURL);
            Image scaledImg = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        }
        return null;
    }

    private void initToolBar() {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolGroup = new ButtonGroup();

        btnPoint = new JToggleButton(loadScaledIcon("/Lab3/icons/point.png", 20, 20));
        btnLine = new JToggleButton(loadScaledIcon("/Lab3/icons/line.png", 20, 20));
        btnRectangle = new JToggleButton(loadScaledIcon("/Lab3/icons/rect.png", 20, 20));
        btnEllipse = new JToggleButton(loadScaledIcon("/Lab3/icons/ellipse.png", 20, 20));
        btnLineOO = new JToggleButton(loadScaledIcon("/Lab3/icons/LineCircles.jpg", 20, 20));
        btnCube = new JToggleButton(loadScaledIcon("/Lab3/icons/cube.png", 20, 20));

        btnPoint.setToolTipText("Крапка");
        btnLine.setToolTipText("Лінія");
        btnRectangle.setToolTipText("Прямокутник");
        btnEllipse.setToolTipText("Еліпс");
        btnLineOO.setToolTipText("Лінія з кружечками");
        btnCube.setToolTipText("Каркас куба");

        btnPoint.addActionListener(e -> setMode(MODE_POINT));
        btnLine.addActionListener(e -> setMode(MODE_LINE));
        btnRectangle.addActionListener(e -> setMode(MODE_RECTANGLE));
        btnEllipse.addActionListener(e -> setMode(MODE_ELLIPSE));
        btnLineOO.addActionListener(e -> setMode(MODE_LINE_OO));
        btnCube.addActionListener(e -> setMode(MODE_CUBE));

        JToggleButton[] buttons = {btnPoint, btnLine, btnRectangle, btnEllipse, btnLineOO, btnCube};
        for (JToggleButton btn : buttons) {
            toolGroup.add(btn);
            toolBar.add(btn);
        }

        add(toolBar, BorderLayout.NORTH);
    }

    private class CanvasPanel extends JPanel {
        private int currentX, currentY;

        public CanvasPanel() {
            setBackground(Color.WHITE);

            MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    currentX = e.getX();
                    currentY = e.getY();
                    editor.startShape(currentMode, currentX, currentY);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    currentX = e.getX();
                    currentY = e.getY();
                    editor.dragShape(currentX, currentY);
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    currentX = e.getX();
                    currentY = e.getY();
                    boolean ok = editor.releaseShape(currentX, currentY);
                    if (!ok) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Переповнення масиву об'єктів (111)!",
                                "Помилка", JOptionPane.ERROR_MESSAGE);
                    }
                    repaint();
                }
            };

            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            editor.drawAll(g, currentX, currentY);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
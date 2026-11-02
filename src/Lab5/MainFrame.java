package Lab5;

import Lab5.shapes.*;
import Lab5.editor.*;
import Lab5.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainFrame extends JFrame {
    public static final int MODE_POINT = 1, MODE_LINE = 2, MODE_RECTANGLE = 3, MODE_ELLIPSE = 4, MODE_LINE_OO = 5, MODE_CUBE = 6;
    private int currentMode = MODE_POINT;

    private MyEditor editor;
    private MyTable tableWindow;

    private JRadioButtonMenuItem itemPoint, itemLine, itemRectangle, itemEllipse, itemLineOO, itemCube;
    private JToggleButton btnPoint, btnLine, btnRectangle, btnEllipse, btnLineOO, btnCube;
    private CanvasPanel canvasPanel;

    public MainFrame() {
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //Отримання екземпляра Singleton
        editor = MyEditor.getInstance();

        //Створення вікна таблиці
        tableWindow = new MyTable(this);

        //Callback при зміні даних MyEditor
        editor.setOnDataChangedCallback(() -> {
            tableWindow.updateTable(editor.getShapes());
            if (canvasPanel != null) canvasPanel.repaint();
        });

        //Реалізація Listener для бонусів 1 та 2
        tableWindow.setTableListener(new TableListener() {
            @Override
            public void onRowSelected(int rowIndex) {
                editor.selectShape(rowIndex);
                canvasPanel.repaint();
            }

            @Override
            public void onRowDeleted(int rowIndex) {
                editor.removeShape(rowIndex);
            }
        });

        initMenu();
        initToolBar();

        canvasPanel = new CanvasPanel();
        add(canvasPanel, BorderLayout.CENTER);

        setMode(MODE_POINT);

        //Автоматичне закриття таблиці під час закриття програми
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (tableWindow != null) tableWindow.dispose();
            }
        });
    }

    private void setMode(int mode) {
        this.currentMode = mode;
        setTitle("Лабораторна робота №5 - [Обрано об'єкт: " + switch (mode) {
            case MODE_POINT -> "Крапка"; case MODE_LINE -> "Лінія"; case MODE_RECTANGLE -> "Прямокутник";
            case MODE_ELLIPSE -> "Еліпс"; case MODE_LINE_OO -> "Лінія з кружечками"; case MODE_CUBE -> "Каркас куба"; default -> "";
        } + "]");

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
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("Файл");
        JMenuItem itemSave = new JMenuItem("Зберегти у CSV...");
        itemSave.addActionListener(e -> saveFile());
        JMenuItem itemLoad = new JMenuItem("Завантажити з CSV...");
        itemLoad.addActionListener(e -> loadFile());
        JMenuItem itemExit = new JMenuItem("Вихід");
        itemExit.addActionListener(e -> System.exit(0));
        menuFile.add(itemSave);
        menuFile.add(itemLoad);
        menuFile.addSeparator();
        menuFile.add(itemExit);

        JMenu menuObjects = new JMenu("Об'єкти");
        ButtonGroup menuGroup = new ButtonGroup();
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
        for (JRadioButtonMenuItem item : items) { menuGroup.add(item); menuObjects.add(item); }

        JMenu menuView = new JMenu("Вигляд");
        JMenuItem itemTable = new JMenuItem("Таблиця об'єктів");
        itemTable.addActionListener(e -> tableWindow.setVisible(!tableWindow.isVisible()));
        menuView.add(itemTable);

        JMenu menuHelp = new JMenu("Довідка");
        JMenuItem itemAbout = new JMenuItem("Про програму");
        itemAbout.addActionListener(e -> JOptionPane.showMessageDialog(this, "Графічний редактор. Лабораторна робота №5"));
        menuHelp.add(itemAbout);

        menuBar.add(menuFile);
        menuBar.add(menuObjects);
        menuBar.add(menuView);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);
    }

    private void saveFile() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                editor.saveToCSV(fc.getSelectedFile());
                JOptionPane.showMessageDialog(this, "Файл успішно збережено!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Помилка збереження: " + ex.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                editor.loadFromCSV(fc.getSelectedFile());
                JOptionPane.showMessageDialog(this, "Файл успішно завантажено!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Помилка завантаження: " + ex.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private ImageIcon loadScaledIcon(String path, int width, int height) {
        java.net.URL imgURL = getClass().getResource(path);
        return (imgURL != null) ? new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)) : null;
    }

    private void initToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        ButtonGroup toolGroup = new ButtonGroup();

        btnPoint = new JToggleButton(loadScaledIcon("/Lab3/icons/point.png", 20, 20));
        btnLine = new JToggleButton(loadScaledIcon("/Lab3/icons/line.png", 20, 20));
        btnRectangle = new JToggleButton(loadScaledIcon("/Lab3/icons/rect.png", 20, 20));
        btnEllipse = new JToggleButton(loadScaledIcon("/Lab3/icons/ellipse.png", 20, 20));
        btnLineOO = new JToggleButton(loadScaledIcon("/Lab3/icons/LineCircles.jpg", 20, 20));
        btnCube = new JToggleButton(loadScaledIcon("/Lab3/icons/cube.png", 20, 20));

        btnPoint.setToolTipText("Крапка"); btnLine.setToolTipText("Лінія"); btnRectangle.setToolTipText("Прямокутник");
        btnEllipse.setToolTipText("Еліпс"); btnLineOO.setToolTipText("Лінія з кружечками"); btnCube.setToolTipText("Каркас куба");

        btnPoint.addActionListener(e -> setMode(MODE_POINT)); btnLine.addActionListener(e -> setMode(MODE_LINE));
        btnRectangle.addActionListener(e -> setMode(MODE_RECTANGLE)); btnEllipse.addActionListener(e -> setMode(MODE_ELLIPSE));
        btnLineOO.addActionListener(e -> setMode(MODE_LINE_OO)); btnCube.addActionListener(e -> setMode(MODE_CUBE));

        JToggleButton[] buttons = {btnPoint, btnLine, btnRectangle, btnEllipse, btnLineOO, btnCube};
        for (JToggleButton btn : buttons) { toolGroup.add(btn); toolBar.add(btn); }
        add(toolBar, BorderLayout.NORTH);
    }

    private class CanvasPanel extends JPanel {
        private int currentX, currentY;
        public CanvasPanel() {
            setBackground(Color.WHITE);
            MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    currentX = e.getX(); currentY = e.getY();
                    editor.startShape(currentMode, currentX, currentY);
                }
                @Override
                public void mouseDragged(MouseEvent e) {
                    currentX = e.getX(); currentY = e.getY();
                    editor.dragShape(currentX, currentY); repaint();
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    currentX = e.getX(); currentY = e.getY();
                    if (!editor.releaseShape(currentX, currentY)) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Переповнення масиву об'єктів (111)!", "Помилка", JOptionPane.ERROR_MESSAGE);
                    }
                    repaint();
                }
            };
            addMouseListener(mouseHandler); addMouseMotionListener(mouseHandler);
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

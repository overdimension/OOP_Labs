package Lab2;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public static final int MODE_POINT = 1;
    public static final int MODE_LINE = 2;
    public static final int MODE_RECTANGLE = 3;
    public static final int MODE_ELLIPSE = 4;

    private int currentMode = MODE_POINT;

    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenu menuObjects;
    private JMenuItem itemExit;
    private JMenuItem itemPoint;
    private JMenuItem itemLine;
    private JMenuItem itemRectangle;
    private JMenuItem itemEllipse;

    public MainFrame() {
        setTitle("Лабораторна робота №2 - Варіант 10");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initMenu();
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

    public int getCurrentMode() {
        return currentMode;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

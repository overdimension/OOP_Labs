package Lab3;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int MODE_POINT = 1;
    public static final int MODE_LINE = 2;
    public static final int MODE_RECTANGLE = 3;
    public static final int MODE_ELLIPSE = 4;

    private int currentMode = MODE_POINT;

    private static final int MAX_SHAPES = 111; //N = 11 + 100 = 111
    private Shape[] pcshape = new Shape[MAX_SHAPES];
    private int shapeCount = 0;

    private JToolBar toolBar;
    private JToggleButton btnPoint;
    private JToggleButton btnLine;
    private JToggleButton btnRectangle;
    private JToggleButton btnEllipse;
    private ButtonGroup toolGroup;

    public MainFrame() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initToolBar();
        updateTitle();
    }

    private void updateTitle() {
        String modeName = "";
        switch (currentMode) {
            case MODE_POINT: modeName = "Крапка"; break;
            case MODE_LINE: modeName = "Лінія"; break;
            case MODE_RECTANGLE: modeName = "Прямокутник"; break;
            case MODE_ELLIPSE: modeName = "Еліпс"; break;
        }
        //Позначка поточного типу об'єкта в заголовку вікна
        setTitle("Лабораторна робота №3 - [Обрано об'єкт: " + modeName + "]");
    }

    private void setMode(int mode) {
        this.currentMode = mode;
        updateTitle();
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

        btnPoint.setSelected(true); //За замовчуванням

        add(toolBar, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

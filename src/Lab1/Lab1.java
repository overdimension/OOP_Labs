package Lab1;
import javax.swing.*;
import java.awt.*;

public class Lab1 extends JFrame {

    private String selectedGroup = "Групу не обрано";
    private final JLabel displayLabel;

    public Lab1() {
        setTitle("Лабораторна робота №1");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Текстова мітка для відображення обраної групи (аналог TextOut з WinAPI)
        displayLabel = new JLabel(selectedGroup, SwingConstants.LEFT);
        displayLabel.setFont(new Font("Serif", Font.BOLD, 18));
        displayLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(displayLabel, BorderLayout.NORTH);

        //Створення головного меню
        JMenuBar menuBar = new JMenuBar();

        JMenu menuWork = new JMenu("Робота1");
        JMenuItem itemWork1 = new JMenuItem("Запустити Модуль 1 (Діалоги)");
        itemWork1.addActionListener(e -> Module1.run(this));
        menuWork.add(itemWork1);

        JMenu menuWork2 = new JMenu("Робота2");
        JMenuItem itemWork2 = new JMenuItem("Запустити Модуль 2 (Список)");
        itemWork2.addActionListener(e -> Module2.run(this, groupName -> {
            selectedGroup = "Обрана група: " + groupName;
            displayLabel.setText(selectedGroup); //Оновлюємо текст у вікні
        }));
        menuWork2.add(itemWork2);

        menuBar.add(menuWork);
        menuBar.add(menuWork2);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Lab1 app = new Lab1();
            app.setVisible(true);
        });
    }
}
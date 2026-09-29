package Lab1;
import javax.swing.*;
import java.awt.*;

public class Module1 {

    public static void run(JFrame parent) {
        int step = 1;

        while (step != 0) {
            if (step == 1) {
                //Перше вікно
                JDialog dialog1 = new JDialog(parent, "Крок 1 з 2", true);
                dialog1.setSize(300, 150);
                dialog1.setLocationRelativeTo(parent);
                dialog1.setLayout(new BorderLayout());

                JLabel label = new JLabel("Це перше діалогове вікно.", SwingConstants.CENTER);
                dialog1.add(label, BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel();
                JButton btnNext = new JButton("Далі >");
                JButton btnCancel = new JButton("Скасувати");

                final int[] choice = {0}; //1 = далі, 0 = скасувати

                btnNext.addActionListener(e -> {
                    choice[0] = 1;
                    dialog1.dispose();
                });

                btnCancel.addActionListener(e -> {
                    choice[0] = 0;
                    dialog1.dispose();
                });

                buttonPanel.add(btnNext);
                buttonPanel.add(btnCancel);
                dialog1.add(buttonPanel, BorderLayout.SOUTH);

                dialog1.setVisible(true);

                if (choice[0] == 1) {
                    step = 2; //Переходимо до 2 кроку
                } else {
                    step = 0; //Вихід
                }

            } else if (step == 2) {
                //Друге вікно
                JDialog dialog2 = new JDialog(parent, "Крок 2 з 2", true);
                dialog2.setSize(320, 150);
                dialog2.setLocationRelativeTo(parent);
                dialog2.setLayout(new BorderLayout());

                JLabel label = new JLabel("Це друге діалогове вікно. Завершити?", SwingConstants.CENTER);
                dialog2.add(label, BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel();
                JButton btnBack = new JButton("< Назад");
                JButton btnOk = new JButton("Так");
                JButton btnCancel = new JButton("Скасувати");

                final int[] choice = {0}; //-1 = назад, 1 = так, 0 = скасувати

                btnBack.addActionListener(e -> {
                    choice[0] = -1;
                    dialog2.dispose();
                });

                btnOk.addActionListener(e -> {
                    choice[0] = 1;
                    dialog2.dispose();
                });

                btnCancel.addActionListener(e -> {
                    choice[0] = 0;
                    dialog2.dispose();
                });

                buttonPanel.add(btnBack);
                buttonPanel.add(btnOk);
                buttonPanel.add(btnCancel);
                dialog2.add(buttonPanel, BorderLayout.SOUTH);

                dialog2.setVisible(true);

                if (choice[0] == -1) {
                    step = 1; //Повертаємося на 1 крок
                } else {
                    if (choice[0] == 1) {
                        JOptionPane.showMessageDialog(parent, "Ви успішно завершили процес!", "Інформація", JOptionPane.INFORMATION_MESSAGE);
                    }
                    step = 0; //Вихід
                }
            }
        }
    }
}
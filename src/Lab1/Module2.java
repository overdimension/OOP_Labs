package Lab1;
import javax.swing.*;
import java.awt.*;

public class Module2 {

    public interface OnGroupSelectedListener {
        void onSelected(String groupName);
    }

    public static void run(JFrame parent, OnGroupSelectedListener listener) {
        JDialog dialog = new JDialog(parent, "Вибір групи", true);
        dialog.setSize(300, 220);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        //Список груп
        String[] groups = {"ІМ-51", "ІМ-52", "ІМ-53", "ІМ-54", "ІМ-55", "ІК-52", "ІО-61", "ІС-43"};
        JList<String> groupList = new JList<>(groups);
        groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupList.setSelectedIndex(0); //Вибір першого елемента за замовчуванням

        JScrollPane scrollPane = new JScrollPane(groupList);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnOk = new JButton("Так");
        JButton btnCancel = new JButton("Скасувати");

        btnOk.addActionListener(e -> {
            String selected = groupList.getSelectedValue();
            if (selected != null && listener != null) {
                listener.onSelected(selected);
            }
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnOk);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
package Lab5.ui;

import Lab5.shapes.Shape;
import Lab5.editor.TableListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MyTable extends JDialog {
    private JTable table;
    private DefaultTableModel tableModel;
    private TableListener listener;

    public MyTable(Frame owner) {
        super(owner, "Таблиця графічних об'єктів", false); //false = Немодальне вікно
        setSize(450, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        String[] columns = {"Назва", "x1", "y1", "x2", "y2"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        //Обробка виділення рядка
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listener != null) {
                int selectedRow = table.getSelectedRow();
                listener.onRowSelected(selectedRow);
            }
        });

        //Панель з кнопкою видалення
        JPanel bottomPanel = new JPanel();
        JButton btnDelete = new JButton("Видалити об'єкт");
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1 && listener != null) {
                listener.onRowDeleted(selectedRow);
            }
        });
        bottomPanel.add(btnDelete);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setTableListener(TableListener listener) {
        this.listener = listener;
    }

    public void updateTable(java.util.List<Shape> shapes) {
        tableModel.setRowCount(0);
        for (Shape shape : shapes) {
            if (shape != null) {
                tableModel.addRow(new Object[]{
                        shape.getName(),
                        shape.getX1(),
                        shape.getY1(),
                        shape.getX2(),
                        shape.getY2()
                });
            }
        }
    }
}

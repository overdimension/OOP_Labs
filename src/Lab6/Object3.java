package Lab6;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Locale;

public class Object3 extends JFrame {
    private JTextArea displayArea;
    private static final int PORT_OBJ3 = 5003;
    private static final int PORT_LAB6 = 5000;

    public Object3() {
        setTitle("Відсортований вектор");
        setSize(440, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        startServer();
    }

    private void startServer() {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(PORT_OBJ3)) {
                while (true) {
                    try (Socket client = server.accept();
                         BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                        String msg = in.readLine();
                        if (msg == null) continue;

                        if ("EXIT".equals(msg)) {
                            System.exit(0);
                        } else if (msg.startsWith("POSITION;")) {
                            String[] parts = msg.split(";");
                            int winX = Integer.parseInt(parts[1]);
                            int winY = Integer.parseInt(parts[2]);
                            SwingUtilities.invokeLater(() -> {
                                setLocation(winX, winY);
                                setVisible(true);
                                toFront();
                            });
                        } else if ("READ_CLIPBOARD".equals(msg)) {
                            readSortAndDisplay();
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void readSortAndDisplay() {
        try {
            //Зчитування даних із буфера обміну
            String data = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);

            String[] tokens = data.trim().split("\\s+");
            double[] numbers = new double[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                numbers[i] = Double.parseDouble(tokens[i]);
            }

            //Сортування масиву за зростанням
            Arrays.sort(numbers);

            //Відображення у декількох стовпчиках та рядках
            StringBuilder tableView = new StringBuilder();
            int cols = 4;
            for (int i = 0; i < numbers.length; i++) {
                tableView.append(String.format(Locale.US, "%10.3f", numbers[i]));
                if ((i + 1) % cols == 0) tableView.append("\n");
            }

            SwingUtilities.invokeLater(() -> displayArea.setText(tableView.toString()));

            //Сповіщаємо Lab6 про завершення роботи
            try (Socket socket = new Socket("127.0.0.1", PORT_LAB6);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                out.println("OBJ3_DONE");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Object3().setVisible(true));
    }
}
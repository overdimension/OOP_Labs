package Lab6;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.net.*;
import java.util.Locale;
import java.util.Random;

public class Object2 extends JFrame {
    private JTextArea displayArea;
    private static final int PORT_OBJ2 = 5002;
    private static final int PORT_LAB6 = 5000;

    public Object2() {
        setTitle("Генератор вектора (double)");
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
            try (ServerSocket server = new ServerSocket(PORT_OBJ2)) {
                while (true) {
                    try (Socket client = server.accept();
                         BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                        String msg = in.readLine();
                        if (msg == null) continue;

                        if ("EXIT".equals(msg)) {
                            System.exit(0);
                        } else if (msg.startsWith("GENERATE;")) {
                            String[] parts = msg.split(";");
                            int n = Integer.parseInt(parts[1]);
                            double min = Double.parseDouble(parts[2]);
                            double max = Double.parseDouble(parts[3]);
                            int winX = Integer.parseInt(parts[4]);
                            int winY = Integer.parseInt(parts[5]);

                            SwingUtilities.invokeLater(() -> {
                                setLocation(winX, winY);
                                setVisible(true);
                                toFront();
                            });

                            generateAndCopy(n, min, max);
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void generateAndCopy(int n, double min, double max) {
        Random rand = new Random();
        double[] vector = new double[n];
        StringBuilder tableView = new StringBuilder();
        StringBuilder clipboardData = new StringBuilder();

        int cols = 4; //Відображення у декількох стовпчиках та рядках
        for (int i = 0; i < n; i++) {
            vector[i] = min + (max - min) * rand.nextDouble();
            String formatted = String.format(Locale.US, "%10.3f", vector[i]);
            tableView.append(formatted);
            if ((i + 1) % cols == 0) tableView.append("\n");

            clipboardData.append(String.format(Locale.US, "%.6f", vector[i]));
            if (i < n - 1) clipboardData.append(" ");
        }

        //Запис у системний буфер обміну
        StringSelection selection = new StringSelection(clipboardData.toString());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

        SwingUtilities.invokeLater(() -> displayArea.setText(tableView.toString()));

        //Повідомляємо Lab6 про готовність даних у буфері
        try (Socket socket = new Socket("127.0.0.1", PORT_LAB6);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("OBJ2_DONE");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Object2().setVisible(true));
    }
}
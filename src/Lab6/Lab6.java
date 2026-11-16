package Lab6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;

public class Lab6 extends JFrame {
    private Process proc2;
    private Process proc3;
    private JTextArea logArea;

    private static final int PORT_LAB6 = 5000;
    private static final int PORT_OBJ2 = 5002;
    private static final int PORT_OBJ3 = 5003;

    public Lab6() {
        setTitle("Менеджер системи (Варіант 2)");
        setSize(450, 300);
        setLocation(50, 50);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuWork = new JMenu("Робота");
        JMenuItem itemStart = new JMenuItem("Запустити обчислення...");
        itemStart.addActionListener(e -> openInputDialog());
        menuWork.add(itemStart);
        menuBar.add(menuWork);
        setJMenuBar(menuBar);

        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        //Завершення дочірніх процесів при закритті Lab6
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sendCommand(PORT_OBJ2, "EXIT");
                sendCommand(PORT_OBJ3, "EXIT");
                if (proc2 != null && proc2.isAlive()) proc2.destroy();
                if (proc3 != null && proc3.isAlive()) proc3.destroy();
                System.exit(0);
            }
        });

        startMessageServer();
    }

    //Потік прийому повідомлень від Object2 та Object3
    private void startMessageServer() {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(PORT_LAB6)) {
                while (true) {
                    try (Socket client = server.accept();
                         BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                        String msg = in.readLine();
                        if ("OBJ2_DONE".equals(msg)) {
                            log("Отримано повідомлення від Object2: дані у буфері обміну.");
                            log("Надсилаємо команду Object3 на зчитування та сортування...");
                            sendCommand(PORT_OBJ3, "READ_CLIPBOARD");
                        } else if ("OBJ3_DONE".equals(msg)) {
                            log("Отримано повідомлення від Object3: сортування завершено!");
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    //Діалогове вікно вводу параметрів n, Min, Max
    private void openInputDialog() {
        JTextField fieldN = new JTextField("15");
        JTextField fieldMin = new JTextField("-50.0");
        JTextField fieldMax = new JTextField("50.0");

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Кількість елементів (n):"));
        panel.add(fieldN);
        panel.add(new JLabel("Мінімум (Min):"));
        panel.add(fieldMin);
        panel.add(new JLabel("Максимум (Max):"));
        panel.add(fieldMax);

        int res = JOptionPane.showConfirmDialog(this, panel, "Введіть параметри вектора",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (res == JOptionPane.OK_OPTION) {
            try {
                int n = Integer.parseInt(fieldN.getText().trim());
                double min = Double.parseDouble(fieldMin.getText().trim());
                double max = Double.parseDouble(fieldMax.getText().trim());
                if (n <= 0 || min >= max) {
                    JOptionPane.showMessageDialog(this, "Некоректні межі або n <= 0!");
                    return;
                }
                executeWorkflow(n, min, max);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Помилка формату чисел!");
            }
        }
    }

    //Автоматичний запуск та координація процесів
    private void executeWorkflow(int n, double min, double max) {
        new Thread(() -> {
            try {
                log("!Початок нового сеансу!");
                ensureProcessRunning("Lab6.Object2", PORT_OBJ2, true);
                ensureProcessRunning("Lab6.Object3", PORT_OBJ3, false);

                //Надсилаємо параметри в Object2
                sendCommand(PORT_OBJ2, "GENERATE;" + n + ";" + min + ";" + max + ";520;50");
                //Позиціонуємо вікно Object3 праворуч
                sendCommand(PORT_OBJ3, "POSITION;980;50");
            } catch (Exception ex) {
                log("Помилка координації: " + ex.getMessage());
            }
        }).start();
    }

    private void ensureProcessRunning(String className, int port, boolean isObj2) throws Exception {
        if (!isPortOpen(port)) {
            log("Запуск незалежного процесу: " + className);
            String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            ProcessBuilder pb = new ProcessBuilder(javaBin, "-cp", classpath, className);
            Process p = pb.start();
            if (isObj2) proc2 = p; else proc3 = p;

            //Очікуємо готовності сокета дочірнього процесу
            for (int i = 0; i < 30; i++) {
                if (isPortOpen(port)) break;
                Thread.sleep(100);
            }
        } else {
            log("Процес " + className + " вже запущений. Використовуємо активне вікно.");
        }
    }

    private boolean isPortOpen(int port) {
        try (Socket s = new Socket("127.0.0.1", port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void sendCommand(int port, String command) {
        try (Socket socket = new Socket("127.0.0.1", port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(command);
        } catch (IOException ignored) {}
    }

    private void log(String text) {
        SwingUtilities.invokeLater(() -> logArea.append(text + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Lab6().setVisible(true));
    }
}
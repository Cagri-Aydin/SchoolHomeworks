import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenuEngineeringAndAnalytics extends JFrame {
    MainApplication mainApp;
    private JTextArea textAreaMenuItems;

    public MenuEngineeringAndAnalytics(MainApplication app) {
        this.mainApp = app;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Menü Mühendisliği ve Analitik");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Üst panel düzenlemeleri
        JPanel panelTop = new JPanel(new GridLayout(1, 1));
        panelTop.add(new JLabel("Menü Analiz İşlemleri"));

        add(panelTop, BorderLayout.NORTH);

        // Ürün listesi gösterimi
        textAreaMenuItems = new JTextArea();
        textAreaMenuItems.setEditable(false);
        add(new JScrollPane(textAreaMenuItems), BorderLayout.CENTER);

        // Analiz butonu
        JButton buttonAnalyze = new JButton("Menüyü Analiz Et");
        add(buttonAnalyze, BorderLayout.SOUTH);

        buttonAnalyze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzeMenu();
            }
        });
    }

    private void analyzeMenu() {
        Map<String, Double> itemRevenue = new HashMap<>();
        Map<String, Integer> itemSales = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("menu_items.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String itemName = parts[0];
                double itemPrice = Double.parseDouble(parts[1]);
                int quantity = Integer.parseInt(parts[2]);

                itemRevenue.put(itemName, itemRevenue.getOrDefault(itemName, 0.0) + (itemPrice * quantity));
                itemSales.put(itemName, itemSales.getOrDefault(itemName, 0) + quantity);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading from file: " + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder analysisReport = new StringBuilder("Menü Analiz Raporu:\n");
        double totalRevenue = 0;
        String popularItem = null;
        double highestRevenue = 0;

        for (Map.Entry<String, Integer> entry : itemSales.entrySet()) {
            double revenue = itemRevenue.get(entry.getKey());
            analysisReport.append(entry.getKey()).append(": ").append(entry.getValue()).append(" adet, ").append(String.format("%.2f TL", revenue)).append("\n");
            totalRevenue += revenue;

            if (revenue > highestRevenue) {
                highestRevenue = revenue;
                popularItem = entry.getKey();
            }
        }

        analysisReport.append("\nToplam Gelir: ").append(String.format("%.2f TL", totalRevenue));
        if (popularItem != null) {
            analysisReport.append("\nEn Popüler Ürün: ").append(popularItem).append(" (").append(String.format("%.2f TL", highestRevenue)).append(")");
        }

        JOptionPane.showMessageDialog(this, analysisReport.toString(), "Menü Analiz Raporu", JOptionPane.INFORMATION_MESSAGE);
    }
}

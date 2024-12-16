import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TablesidePaymentAndFeedback extends JFrame {
    private MainApplication mainApp;
    private JTextField textFieldTableNumber;
    private JButton buttonLoadOrders, buttonPaySelected;
    private JList<String> listOrders;
    private DefaultListModel<String> listModel;
    private Map<Integer, Double> orderPrices = new HashMap<>();
    private JLabel totalAmountLabel; // Label to display total amount

    public TablesidePaymentAndFeedback(MainApplication mainApp) {
        this.mainApp = mainApp;
        setTitle("Masa Üstü Ödeme ve Geri Bildirim");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        textFieldTableNumber = new JTextField();
        buttonLoadOrders = new JButton("Siparişleri Yükle");
        buttonPaySelected = new JButton("Seçilenleri Öde");
        listModel = new DefaultListModel<>();
        listOrders = new JList<>(listModel);
        totalAmountLabel = new JLabel("Toplam Borç: 0 TL");

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(1, 3));
        northPanel.add(new JLabel("Masa Numarası:"));
        northPanel.add(textFieldTableNumber);
        northPanel.add(buttonLoadOrders);

        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(listOrders), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(totalAmountLabel, BorderLayout.CENTER);
        southPanel.add(buttonPaySelected, BorderLayout.EAST);

        add(southPanel, BorderLayout.SOUTH);

        buttonLoadOrders.addActionListener(this::loadOrders);
        buttonPaySelected.addActionListener(this::paySelectedItems);
    }

    private void loadOrders(ActionEvent e) {
        listModel.clear();
        orderPrices.clear();
        String tableNumber = textFieldTableNumber.getText().trim();
        double totalAmount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Table: " + tableNumber)) {
                    String[] parts = line.split(", ");
                    int id = Integer.parseInt(parts[0].split(" ")[1]);
                    double price = Double.parseDouble(parts[3].split(": ")[1]) * Integer.parseInt(parts[4].split(": ")[1]);
                    orderPrices.put(id, price);
                    totalAmount += price;
                    listModel.addElement("ID: " + id + " - " + line);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        totalAmountLabel.setText("Toplam Borç: " + String.format("%.2f TL", totalAmount));
    }

    private void paySelectedItems(ActionEvent e) {
        List<String> selectedItems = listOrders.getSelectedValuesList();
        double totalPaidAmount = 0;

        for (String item : selectedItems) {
            int id = Integer.parseInt(item.split(" - ")[0].split("ID: ")[1]);
            double paidAmount = orderPrices.getOrDefault(id, 0.0);
            listModel.removeElement(item); // Remove paid item from the list
            updateOrdersFile(id); // Update the file by removing the paid item
            totalPaidAmount += paidAmount;
        }

        String tableNumber = textFieldTableNumber.getText().trim();
        mainApp.updateActiveTableAmount(Integer.parseInt(tableNumber), totalPaidAmount); // Update the active table amount

        JOptionPane.showMessageDialog(this, "Ödeme İşlemi Tamamlandı.", "Ödeme Bilgisi", JOptionPane.INFORMATION_MESSAGE);
        loadOrders(null); // Reload orders to update the list and total amount
    }

    private void updateOrdersFile(int paidItemId) {
        File inputFile = new File("orders.txt");
        File tempFile = new File("temp_orders.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int currentId = Integer.parseInt(line.split(", ")[0].split(" ")[1]);
                if (currentId != paidItemId) {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }
}

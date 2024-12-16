import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Order {
    private static int nextId = 1; // Static counter to assign IDs
    private int orderId;
    private int tableNumber;
    private String itemName;
    private double itemPrice;
    private int quantity;
    private String specialRequests;

    public Order(int tableNumber, String itemName, double itemPrice, int quantity, String specialRequests) {
        this.orderId = nextId++; // Assign an ID and increment the ID counter
        this.tableNumber = tableNumber;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.specialRequests = specialRequests;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public double getTotalPrice() {
        return itemPrice * quantity;
    }

    @Override
    public String toString() {
        return "ID: " + orderId + ", Table: " + tableNumber + ", Item: " + itemName + ", Price: " + itemPrice + ", Quantity: " + quantity + ", Special Requests: " + specialRequests;
    }
}

public class OrderAndKitchenCommunication extends JFrame {
    MainApplication mainApp;
    private List<Order> orders;
    private JTextArea textAreaOrders;
    private JTextField textFieldTableNumber;
    private JPanel panelMenu;

    private static final Map<String, Double> menuItems = new HashMap<>();
    static {
        menuItems.put("Mercimek Çorbası", 150.0);
        menuItems.put("Domates Çorbası", 140.0);
        menuItems.put("Tavuk Çorbası", 160.0);
        menuItems.put("Zeytinyağlı Yaprak Sarma", 175.0);
        menuItems.put("Humus", 150.0);
        menuItems.put("Sigara Böreği", 140.0);
        menuItems.put("Izgara Köfte", 300.0);
        menuItems.put("Tavuk Şiş", 275.0);
        menuItems.put("Adana Kebap", 325.0);
        menuItems.put("Karışık Izgara", 400.0);
        menuItems.put("Lahmacun", 125.0);
        menuItems.put("Pide", 150.0);
        menuItems.put("Baklava", 100.0);
        menuItems.put("Sütlaç", 90.0);
        menuItems.put("Kazandibi", 100.0);
        menuItems.put("Çay", 25.0);
        menuItems.put("Kahve", 50.0);
        menuItems.put("Cola", 60.0);
        menuItems.put("Ayran", 40.0);
        menuItems.put("Su", 25.0);
    }

    public OrderAndKitchenCommunication(MainApplication app) {
        this.mainApp = app;
        this.orders = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Sipariş ve Mutfak İletişimi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridLayout(1, 2));
        panelTop.add(new JLabel("Masa Numarası:"));
        textFieldTableNumber = new JTextField();
        panelTop.add(textFieldTableNumber);
        add(panelTop, BorderLayout.NORTH);

        panelMenu = new JPanel(new GridLayout(0, 3));
        JScrollPane scrollPane = new JScrollPane(panelMenu);
        add(scrollPane, BorderLayout.CENTER);

        for (Map.Entry<String, Double> entry : menuItems.entrySet()) {
            JButton button = new JButton(entry.getKey() + " - " + entry.getValue() + " TL");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addOrder(entry.getKey(), entry.getValue());
                }
            });
            panelMenu.add(button);
        }

        textAreaOrders = new JTextArea();
        textAreaOrders.setEditable(false);
        add(new JScrollPane(textAreaOrders), BorderLayout.SOUTH);
    }

    private void addOrder(String itemName, double itemPrice) {
        if (textFieldTableNumber.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen önce masa numarası giriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int tableNumber = Integer.parseInt(textFieldTableNumber.getText());
        Order order = new Order(tableNumber, itemName, itemPrice, 1, ""); // Miktar varsayılan olarak 1
        orders.add(order);
        mainApp.updateActiveTables(tableNumber, order.getTotalPrice()); // Aktif masalar güncelle
        updateOrderList();
        saveOrderToFile(order);
    }

    private void updateOrderList() {
        StringBuilder orderList = new StringBuilder();
        for (Order order : orders) {
            orderList.append(order.toString()).append("\n");
        }
        textAreaOrders.setText(orderList.toString());
    }

    private void saveOrderToFile(Order order) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt", true))) {
            writer.write(order.toString() + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

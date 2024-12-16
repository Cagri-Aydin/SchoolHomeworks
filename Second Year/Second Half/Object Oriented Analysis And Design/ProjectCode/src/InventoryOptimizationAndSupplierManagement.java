import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryOptimizationAndSupplierManagement extends JFrame {
    MainApplication mainApp;
    private List<InventoryItem> inventoryItems;
    private JTextArea textAreaInventory;
    private JTextField textFieldItemName;
    private JTextField textFieldItemQuantity;
    private JTextField textFieldSupplier;

    public InventoryOptimizationAndSupplierManagement(MainApplication app) {
        this.mainApp = app;
        this.inventoryItems = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Envanter Optimizasyonu ve Tedarikçi Yönetimi");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridLayout(4, 2));
        panelTop.add(new JLabel("Ürün Adı:"));
        textFieldItemName = new JTextField();
        panelTop.add(textFieldItemName);

        panelTop.add(new JLabel("Miktar:"));
        textFieldItemQuantity = new JTextField();
        panelTop.add(textFieldItemQuantity);

        panelTop.add(new JLabel("Tedarikçi:"));
        textFieldSupplier = new JTextField();
        panelTop.add(textFieldSupplier);

        JButton buttonAddItem = new JButton("Ürün Ekle");
        panelTop.add(buttonAddItem);

        add(panelTop, BorderLayout.NORTH);

        textAreaInventory = new JTextArea();
        textAreaInventory.setEditable(false);
        add(new JScrollPane(textAreaInventory), BorderLayout.CENTER);

        JButton buttonReviewInventory = new JButton("Envanteri İncele");
        add(buttonReviewInventory, BorderLayout.SOUTH);

        buttonAddItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInventoryItem();
            }
        });

        buttonReviewInventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reviewInventory();
            }
        });
    }

    private void addInventoryItem() {
        String itemName = textFieldItemName.getText();
        int itemQuantity = Integer.parseInt(textFieldItemQuantity.getText());
        String supplier = textFieldSupplier.getText();

        InventoryItem inventoryItem = new InventoryItem(itemName, itemQuantity, supplier);
        inventoryItems.add(inventoryItem);

        updateInventoryList();
        clearFields();
    }

    private void updateInventoryList() {
        StringBuilder inventoryList = new StringBuilder();
        for (InventoryItem inventoryItem : inventoryItems) {
            inventoryList.append(inventoryItem).append("\n");
        }
        textAreaInventory.setText(inventoryList.toString());
    }

    private void clearFields() {
        textFieldItemName.setText("");
        textFieldItemQuantity.setText("");
        textFieldSupplier.setText("");
    }

    private void reviewInventory() {
        StringBuilder inventoryReport = new StringBuilder("Envanter İnceleme Raporu:\n");
        for (InventoryItem inventoryItem : inventoryItems) {
            inventoryReport.append(inventoryItem).append("\n");
        }

        JOptionPane.showMessageDialog(this, inventoryReport.toString(), "Envanter İnceleme Raporu", JOptionPane.INFORMATION_MESSAGE);
    }


}

class InventoryItem {
    private String name;
    private int quantity;
    private String supplier;

    public InventoryItem(String name, int quantity, String supplier) {
        this.name = name;
        this.quantity = quantity;
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Ürün: " + name + ", Miktar: " + quantity + ", Tedarikçi: " + supplier;
    }
}

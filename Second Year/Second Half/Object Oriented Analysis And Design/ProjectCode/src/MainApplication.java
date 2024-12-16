import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class MainApplication extends JFrame {
    private OrderAndKitchenCommunication orderSystem;
    private BackgroundPanel backgroundPanel;
    private JPanel dashboardPanel;
    private JPanel mainPanel;

    // Şifreler
    private final String GARSON_PASSWORD = "garson123";
    private final String SEF_GARSON_PASSWORD = "sefgarson123";
    private final String PATRON_PASSWORD = "patron123";

    // Aktif masalar ve hesapları
    private Map<Integer, Double> activeTables = new HashMap<>();

    // Butonlar
    private JButton dashboardButton;
    private JButton[] featureButtons;

    public MainApplication() {
        setTitle("Restoran Yönetim Sistemi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // JLayeredPane kullanarak katmanları ayarlama
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        // Arka plan paneli ekleme
        backgroundPanel = new BackgroundPanel(new ImageIcon("images/background.jpg").getImage());
        backgroundPanel.setSize(800, 600);

        // Ana panel
        mainPanel = new JPanel(new GridLayout(3, 3, 20, 20));  // 3 sütunlu grid layout
        mainPanel.setOpaque(false);

        // Dashboard paneli
        dashboardPanel = new JPanel(new GridBagLayout());
        dashboardPanel.setOpaque(false);

        // Dashboard butonu
        dashboardButton = createButton("Dashboard", e -> showMainPanel());
        dashboardButton.setPreferredSize(new Dimension(780, 40)); // İnce uzun buton

        // OrderAndKitchenCommunication instance
        orderSystem = new OrderAndKitchenCommunication(this);

        // User role selection dialog
        Object[] possibleRoles = {"Garson", "Şef Garson", "Patron"};
        String selectedRole = (String) JOptionPane.showInputDialog(
                this, "Rolünüzü seçin:", "Kullanıcı Girişi",
                JOptionPane.QUESTION_MESSAGE, null, possibleRoles, possibleRoles[0]);

        if (selectedRole != null) {
            String password = JOptionPane.showInputDialog(this, "Şifrenizi girin:", "Şifre Girişi", JOptionPane.PLAIN_MESSAGE);

            if (isPasswordCorrect(selectedRole, password)) {
                setupUI(selectedRole, mainPanel);
                // Katmanları layeredPane'e ekleme
                layeredPane.add(backgroundPanel, Integer.valueOf(0));  // Arka plan katmanı
                layeredPane.add(mainPanel, Integer.valueOf(1));  // Ana panel katmanı
                layeredPane.add(dashboardPanel, Integer.valueOf(2));  // Dashboard katmanı (başlangıçta gizli)

                setContentPane(layeredPane);  // layeredPane'i contentPane olarak ayarlama

                showDashboard();  // Uygulama açıldığında dashboard ve aktif masalar görünsün
            } else {
                JOptionPane.showMessageDialog(this, "Yanlış şifre!", "Hata", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    private boolean isPasswordCorrect(String role, String password) {
        switch (role) {
            case "Garson":
                return GARSON_PASSWORD.equals(password);
            case "Şef Garson":
                return SEF_GARSON_PASSWORD.equals(password);
            case "Patron":
                return PATRON_PASSWORD.equals(password);
            default:
                return false;
        }
    }

    private void setupUI(String role, JPanel panel) {
        java.util.List<JButton> buttonsList = new java.util.ArrayList<>();

        if (role.equals("Garson") || role.equals("Şef Garson") || role.equals("Patron")) {
            buttonsList.add(addButton("Masa Yönetimi ve Rezervasyon", e -> {
                try {
                    new RestaurantManagementSystem(this).setVisible(true);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }, panel));
            buttonsList.add(addButton("Sipariş ve Mutfak İletişimi", e -> orderSystem.setVisible(true), panel));
            buttonsList.add(addButton("Masa Üstü Ödeme ve Geri Bildirim", e -> new TablesidePaymentAndFeedback(this).setVisible(true), panel));
        }

        if (role.equals("Şef Garson") || role.equals("Patron")) {
            buttonsList.add(addButton("Menü Mühendisliği ve Analitik", e -> new MenuEngineeringAndAnalytics(this).setVisible(true), panel));
            buttonsList.add(addButton("Personel Eğitimi ve Performans Yönetimi", e -> new StaffTrainingAndPerformanceManagement(this).setVisible(true), panel));
            buttonsList.add(addButton("Müşteri Bağlılığı ve Sadakat Programları", e -> new CustomerLoyaltyAndEngagement(this).setVisible(true), panel));
            buttonsList.add(addButton("Envanter Optimizasyonu ve Tedarikçi Yönetimi", e -> new InventoryOptimizationAndSupplierManagement(this).setVisible(true), panel));
            buttonsList.add(addButton("Pazarlama Entegrasyonu ve Kampanya Yönetimi", e -> new MarketingIntegrationAndCampaignManagement(this).setVisible(true), panel));
        }

        if (role.equals("Patron")) {
            // Additional admin controls can be added here for the Patron if needed
        }

        // Geri Dön butonu
        buttonsList.add(addButton("Geri Dön", e -> showDashboard(), panel));

        featureButtons = buttonsList.toArray(new JButton[0]);
    }

    private JButton addButton(String text, ActionListener listener, JPanel panel) {
        JButton button = createButton(text, listener);
        panel.add(button);
        return button;
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setForeground(Color.WHITE);  // Buton yazı rengini beyaz yapma
        button.setBackground(new Color(0, 0, 0, 150));  // Buton arka planını yarı saydam siyah yapma
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setBorder(new LineBorder(Color.WHITE, 2));  // Beyaz çerçeve ekleme
        button.setFont(new Font("Arial", Font.BOLD, 25));  // Yazı tipini büyütme ve kalın yapma
        return button;
    }

    public void updateActiveTables(int tableNumber, double amount) {
        activeTables.put(tableNumber, activeTables.getOrDefault(tableNumber, 0.0) + amount);
    }

    public void updateActiveTableAmount(int tableNumber, double amountPaid) {
        double currentAmount = activeTables.getOrDefault(tableNumber, 0.0);
        double updatedAmount = currentAmount - amountPaid;
        if (updatedAmount <= 0) {
            activeTables.remove(tableNumber);
        } else {
            activeTables.put(tableNumber, updatedAmount);
        }
    }

    public void removeActiveTable(int tableNumber) {
        activeTables.remove(tableNumber);
    }

    public double getActiveTableAmount(int tableNumber) {
        return activeTables.getOrDefault(tableNumber, 0.0);
    }

    private void showDashboard() {
        // Dashboard butonunu ekleyin
        JPanel dashboardContainer = new JPanel(new BorderLayout());
        dashboardContainer.setOpaque(false);
        dashboardContainer.add(dashboardButton, BorderLayout.NORTH);

        // Aktif masaları ve hesapları gösteren bir panel ekleyin
        JPanel activeTablesPanel = new JPanel(new GridLayout(0, 2, 10, 10));  // 2 sütunlu grid layout
        activeTablesPanel.setOpaque(false);

        for (Map.Entry<Integer, Double> entry : activeTables.entrySet()) {
            JLabel label = new JLabel("Masa " + entry.getKey() + ": " + String.format("%.2f TL", entry.getValue()));
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            JPanel tablePanel = new JPanel(new BorderLayout());
            tablePanel.setOpaque(false);
            tablePanel.setBorder(new LineBorder(Color.WHITE, 2));  // Beyaz çerçeve ekleme
            tablePanel.add(label, BorderLayout.CENTER);
            activeTablesPanel.add(tablePanel);
        }

        dashboardPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        dashboardPanel.add(dashboardContainer, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        dashboardPanel.add(activeTablesPanel, gbc);
        dashboardPanel.revalidate();
        dashboardPanel.repaint();

        // Dashboard panelini göster
        dashboardPanel.setVisible(true);

        // Ana panelde sadece dashboard butonunu ve aktif masaları göster
        mainPanel.removeAll();
        mainPanel.add(dashboardContainer, gbc);
        gbc.gridy++;
        mainPanel.add(activeTablesPanel, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showMainPanel() {
        // Ana panelin içeriklerini göster
        mainPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel buttonContainer = new JPanel(new GridLayout(0, 2, 10, 10));
        buttonContainer.setOpaque(false);

        for (JButton button : featureButtons) {
            buttonContainer.add(button);
        }

        mainPanel.add(buttonContainer, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();

        // Dashboard panelini gizle
        dashboardPanel.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApplication app = new MainApplication();
            app.setVisible(true);
        });
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

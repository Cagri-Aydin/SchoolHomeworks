import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class RestaurantManagementSystem extends JFrame {
    private JTextField textFieldReservationID;
    private JTextField textFieldTableNumber;
    private JTextField textFieldGuestName;
    private JTextField textFieldGuestCount;
    private JTextField textFieldDeleteID;
    private JTextField textFieldDeleteName;
    private JButton buttonAddReservation;
    private JButton buttonDeleteReservation;
    private JTextArea textAreaReservations;

    MainApplication mainApp;

    public RestaurantManagementSystem(MainApplication app) throws FileNotFoundException {
        this.mainApp = app;
        setTitle("Masa Yönetimi ve Rezervasyon");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Arka plan paneli ekleme
        BackgroundPanel backgroundPanel = new BackgroundPanel(new ImageIcon("images/background.jpg").getImage());
        backgroundPanel.setSize(800, 600);

        // İçerikler burada tanımlanacak
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        // Üst panel: Rezervasyon bilgileri girilecek alanlar
        JPanel panelTop = new JPanel(new GridLayout(5, 2));
        panelTop.setOpaque(false);

        panelTop.add(createLabel("Rezervasyon ID:"));
        JLabel ID = new JLabel(String.valueOf(getReservationID()));
        ID.setForeground(Color.WHITE);
        panelTop.add(ID);

        panelTop.add(createLabel("Masa Numarası:"));
        textFieldTableNumber = new JTextField();
        panelTop.add(textFieldTableNumber);

        panelTop.add(createLabel("Misafir Adı:"));
        textFieldGuestName = new JTextField();
        panelTop.add(textFieldGuestName);

        panelTop.add(createLabel("Misafir Sayısı:"));
        textFieldGuestCount = new JTextField();
        panelTop.add(textFieldGuestCount);

        buttonAddReservation = new JButton("Rezervasyon Ekle");
        buttonAddReservation.setForeground(Color.BLACK);
        buttonAddReservation.setBackground(new Color(0, 0, 0, 150));
        buttonAddReservation.setOpaque(true);
        buttonAddReservation.setContentAreaFilled(true);
        buttonAddReservation.setBorderPainted(true);
        buttonAddReservation.setFont(new Font("Arial", Font.BOLD, 14));
        panelTop.add(buttonAddReservation);

        // Silme paneli: Alt panel
        JPanel panelBottom = new JPanel(new GridLayout(3, 2));
        panelBottom.setOpaque(false);

        panelBottom.add(createLabel("Silinecek Rezervasyon ID:"));
        textFieldDeleteID = new JTextField();
        panelBottom.add(textFieldDeleteID);

        panelBottom.add(createLabel("Silinecek Misafir Adı:"));
        textFieldDeleteName = new JTextField();
        panelBottom.add(textFieldDeleteName);

        buttonDeleteReservation = new JButton("Rezervasyon Sil");
        buttonDeleteReservation.setForeground(Color.BLACK);
        buttonDeleteReservation.setBackground(new Color(0, 0, 0, 150));
        buttonDeleteReservation.setOpaque(true);
        buttonDeleteReservation.setContentAreaFilled(true);
        buttonDeleteReservation.setBorderPainted(true);
        buttonDeleteReservation.setFont(new Font("Arial", Font.BOLD, 14));
        panelBottom.add(buttonDeleteReservation);

        contentPanel.add(panelTop, BorderLayout.NORTH);
        contentPanel.add(panelBottom, BorderLayout.SOUTH);

        // Alt panel: Mevcut rezervasyonların gösterileceği alan
        textAreaReservations = new JTextArea();
        textAreaReservations.setEditable(false);
        textAreaReservations.setOpaque(false);
        textAreaReservations.setForeground(Color.BLACK);
        contentPanel.add(new JScrollPane(textAreaReservations), BorderLayout.CENTER);

        // Rezervasyon ekleme işlemi
        buttonAddReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addReservation(ID);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonDeleteReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteReservationFromFile(textFieldDeleteID.getText(), textFieldDeleteName.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setContentPane(new JLayeredPane());
        getContentPane().setLayout(new OverlayLayout(getContentPane()));
        getContentPane().add(backgroundPanel, Integer.valueOf(0));
        getContentPane().add(contentPanel, Integer.valueOf(1));

        loadReservations();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    public void deleteReservationFromFile(String reservationId, String guestName) throws IOException {
        String[][] currentReservations = readFileToStringDoubleArray("reservation.txt");
        FileWriter fileWriter = new FileWriter("reservation.txt");
        PrintWriter writer = new PrintWriter(fileWriter);
        for (String[] currentReservation : currentReservations) {
            if (!currentReservation[0].equals(reservationId) || !currentReservation[2].equals(guestName)) {
                writer.println(currentReservation[0] + "," + currentReservation[1] + "," + currentReservation[2] + "," + currentReservation[3]);
            } else {
                // MainApplication aktif masalar güncelleme
                mainApp.removeActiveTable(Integer.parseInt(currentReservation[1]));
            }
        }
        writer.close();
        textFieldDeleteID.setText("");
        textFieldDeleteName.setText("");
        textAreaReservations.setText("");
        loadReservations();
    }

    public static String[][] readFileToStringDoubleArray(String filePath) {
        // Step 1: Determine the number of lines in the file
        int numberOfLines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                numberOfLines++;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file to count lines: " + e.getMessage());
            return new String[0][0]; // Return an empty array in case of error
        }

        // Step 2: Initialize the String[][] array with the determined number of lines
        String[][] dataArray = new String[numberOfLines][];

        // Step 3: Read the file content again and populate the array
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                // Split the line into an array of strings
                String[] data = line.split(","); // Split by comma
                dataArray[index] = data;
                index++;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file to populate the array: " + e.getMessage());
            return new String[0][0]; // Return an empty array in case of error
        }

        return dataArray;
    }

    private String[] getColumnFromTxt(int colum) throws FileNotFoundException {
        Scanner databaseReader = new Scanner(new File("reservation.txt"));
        String[] data;
        String[] returnData = new String[1000];
        int a = 0;
        while (databaseReader.hasNextLine()) {
            data = databaseReader.nextLine().split(",");
            returnData[a] = data[colum];
            a++;
        }
        String[] finalArray = new String[a];
        System.arraycopy(returnData, 0, finalArray, 0, a);
        return finalArray;
    }

    private int getReservationID() throws FileNotFoundException {
        Scanner databaseReader = new Scanner(new File("reservation.txt"));
        int lastId = -1;
        String[] data;

        while (databaseReader.hasNextLine()) {
            data = databaseReader.nextLine().split(",");
            if (data.length > 0 && isNumeric(data[0])) {
                lastId = Integer.parseInt(data[0]);
            }
        }
        return lastId + 1;
    }

    public void writeToDataBaseTxt(JLabel ID) {
        try {
            FileWriter fileWriter = new FileWriter("reservation.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);
            writer.println(ID.getText() + "," + textFieldTableNumber.getText() + "," + textFieldGuestName.getText() + "," + textFieldGuestCount.getText());
            writer.close();
        } catch (IOException e) {
            System.out.println("Database error occurred!");
        }
    }

    private void addReservation(JLabel ID) throws FileNotFoundException {
        String[] tableNumberControl = getColumnFromTxt(1);

        boolean flag = false;
        for (String s : tableNumberControl) {
            if (s.equals(textFieldTableNumber.getText())) {
                JOptionPane.showMessageDialog(null, "Bu masa halihazırda kullanımda!", "Masa Kullanımda!", JOptionPane.INFORMATION_MESSAGE);
                flag = true;
                clearFields(ID);
                break;
            }
        }

        if (!flag) {
            String reservationInfo = "Rezervasyon ID: " + ID.getText()
                    + ", Masa Numarası: " + textFieldTableNumber.getText()
                    + ", Misafir Adı: " + textFieldGuestName.getText()
                    + ", Misafir Sayısı: " + textFieldGuestCount.getText() + "\n";
            textAreaReservations.append(reservationInfo);

            // MainApplication aktif masalar güncelleme
            mainApp.updateActiveTables(Integer.parseInt(textFieldTableNumber.getText()), 0.0); // Başlangıçta 0 TL

            writeToDataBaseTxt(ID);
            clearFields(ID);
        }
    }

    private void clearFields(JLabel ID) {
        ID.setText(String.valueOf(Integer.parseInt(ID.getText()) + 1));
        textFieldTableNumber.setText("");
        textFieldGuestName.setText("");
        textFieldGuestCount.setText("");
    }

    private void loadReservations() {
        try (Scanner scanner = new Scanner(new File("reservation.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 4 && isNumeric(parts[1]) && isNumeric(parts[3])) {
                    String reservationInfo = "Rezervasyon ID: " + parts[0]
                            + ", Masa Numarası: " + parts[1]
                            + ", Misafir Adı: " + parts[2]
                            + ", Misafir Sayısı: " + parts[3] + "\n";
                    textAreaReservations.append(reservationInfo);

                    // MainApplication aktif masalar güncelleme
                    mainApp.updateActiveTables(Integer.parseInt(parts[1]), 0.0); // Başlangıçta 0 TL
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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

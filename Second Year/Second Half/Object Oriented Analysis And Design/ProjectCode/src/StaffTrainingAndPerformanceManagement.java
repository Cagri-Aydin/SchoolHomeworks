import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StaffTrainingAndPerformanceManagement extends JFrame {
    MainApplication mainApp;
    private List<StaffMember> staffMembers;
    private JTextArea textAreaStaffMembers;
    private JTextField textFieldStaffName;
    private JTextField textFieldTrainingModule;
    private JTextField textFieldProgress;
    private JTextField textFieldPerformanceScore;

    public StaffTrainingAndPerformanceManagement(MainApplication app) {
        this.mainApp = app;
        this.staffMembers = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Personel Eğitimi ve Performans Yönetimi");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridLayout(5, 2));
        panelTop.add(new JLabel("Personel Adı:"));
        textFieldStaffName = new JTextField();
        panelTop.add(textFieldStaffName);

        panelTop.add(new JLabel("Eğitim Modülü:"));
        textFieldTrainingModule = new JTextField();
        panelTop.add(textFieldTrainingModule);

        panelTop.add(new JLabel("İlerleme (%):"));
        textFieldProgress = new JTextField();
        panelTop.add(textFieldProgress);

        panelTop.add(new JLabel("Performans Puanı:"));
        textFieldPerformanceScore = new JTextField();
        panelTop.add(textFieldPerformanceScore);

        JButton buttonAddStaff = new JButton("Personel Ekle");
        panelTop.add(buttonAddStaff);

        add(panelTop, BorderLayout.NORTH);

        textAreaStaffMembers = new JTextArea();
        textAreaStaffMembers.setEditable(false);
        add(new JScrollPane(textAreaStaffMembers), BorderLayout.CENTER);

        JButton buttonReviewPerformance = new JButton("Performans İncele");
        add(buttonReviewPerformance, BorderLayout.SOUTH);

        buttonAddStaff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStaffMember();
            }
        });

        buttonReviewPerformance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reviewPerformance();
            }
        });
    }

    private void addStaffMember() {
        String staffName = textFieldStaffName.getText();
        String trainingModule = textFieldTrainingModule.getText();
        int progress = Integer.parseInt(textFieldProgress.getText());
        int performanceScore = Integer.parseInt(textFieldPerformanceScore.getText());

        StaffMember staffMember = new StaffMember(staffName, trainingModule, progress, performanceScore);
        staffMembers.add(staffMember);

        updateStaffList();
        clearFields();
    }

    private void updateStaffList() {
        StringBuilder staffList = new StringBuilder();
        for (StaffMember staffMember : staffMembers) {
            staffList.append(staffMember).append("\n");
        }
        textAreaStaffMembers.setText(staffList.toString());
    }

    private void clearFields() {
        textFieldStaffName.setText("");
        textFieldTrainingModule.setText("");
        textFieldProgress.setText("");
        textFieldPerformanceScore.setText("");
    }

    private void reviewPerformance() {
        StringBuilder performanceReport = new StringBuilder("Performans İnceleme Raporu:\n");
        for (StaffMember staffMember : staffMembers) {
            performanceReport.append(staffMember).append("\n");
        }

        JOptionPane.showMessageDialog(this, performanceReport.toString(), "Performans İnceleme Raporu", JOptionPane.INFORMATION_MESSAGE);
    }


}

class StaffMember {
    private String name;
    private String trainingModule;
    private int progress;
    private int performanceScore;

    public StaffMember(String name, String trainingModule, int progress, int performanceScore) {
        this.name = name;
        this.trainingModule = trainingModule;
        this.progress = progress;
        this.performanceScore = performanceScore;
    }

    @Override
    public String toString() {
        return "Personel Adı: " + name + ", Eğitim Modülü: " + trainingModule + ", İlerleme: " + progress + "%, Performans Puanı: " + performanceScore;
    }
}

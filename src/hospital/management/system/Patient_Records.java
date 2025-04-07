package hospital.management.system;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Patient_Records extends JFrame {
    JTable table;
    JButton backBtn;

    public Patient_Records() {
        setTitle("Patient Records");
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(90, 156, 163)); // Blue background

        // Heading
        JLabel heading = new JLabel("All Patient Records");
        heading.setFont(new Font("Tahoma", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBounds(350, 20, 400, 40);
        add(heading);

        // Panel to hold table
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(50, 80, 880, 360);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                "Patient Information",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Tahoma", Font.BOLD, 16),
                new Color(90, 156, 163)
        ));
        add(panel);

        // Table
        table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 15));
        table.setRowHeight(28);
        table.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Back Button
        backBtn = new JButton("Back");
        backBtn.setBounds(50, 460, 100, 30);
        backBtn.setBackground(new Color(220, 53, 69));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(backBtn);

        backBtn.addActionListener(e -> {
            new Reception();
            dispose();
        });

        showPatientData();
        setVisible(true);
    }

    private void showPatientData() {
        try {
            conn c = new conn();
            String query = "SELECT * FROM patient_info";
            ResultSet rs = c.statement.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID Type", "Patient ID", "Name", "Gender", "Room No", "Disease", "Admission Date"}, 0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("id_type"),
                        rs.getString("patient_id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("room_no"),
                        rs.getString("disease"),
                        rs.getString("admission_date")
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching patient data.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Patient_Records();
    }
}
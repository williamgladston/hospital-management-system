package hospital.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Doctor_Info extends JFrame {
    JTable table;

    public Doctor_Info() {
        setTitle("Doctor Information");
        setBounds(250, 100, 900, 500);
        setLayout(new BorderLayout());

        // Header
        JLabel heading = new JLabel("Doctors On Staff");
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setForeground(Color.WHITE);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setOpaque(true);
        heading.setBackground(new Color(90, 156, 163));
        heading.setPreferredSize(new Dimension(900, 60));
        add(heading, BorderLayout.NORTH);

        // Table
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        showDoctorData();

        // Back Button
        JButton backBtn = new JButton("Back");
        backBtn.setBackground(new Color(90, 156, 163));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backBtn.setPreferredSize(new Dimension(100, 40));
        backBtn.addActionListener(e -> {
            new Reception();
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(90, 156, 163));
        bottom.add(backBtn);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void showDoctorData() {
        try {
            conn c = new conn();
            String query = "SELECT * FROM doctors";
            ResultSet rs = c.statement.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"Doctor ID", "Name", "Specialty", "Contact", "Availability"}, 0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("doctor_id"),
                        rs.getString("name"),
                        rs.getString("specialty"),
                        rs.getString("contact"),
                        rs.getString("availability")
                });
            }

            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Doctor_Info();
    }
}
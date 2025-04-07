package hospital.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointments extends JFrame {
    JTable table;
    JComboBox<String> patientDropdown;
    JButton assignBtn, backBtn;
    DefaultTableModel model;

    public Appointments() {
        setTitle("Appointments");
        setBounds(200, 100, 1000, 600);
        setLayout(null);
        getContentPane().setBackground(new Color(90, 156, 163));

        JLabel heading = new JLabel("Appointments");
        heading.setFont(new Font("Tahoma", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBounds(370, 20, 300, 30);
        add(heading);

        patientDropdown = new JComboBox<>();
        patientDropdown.setBounds(100, 80, 300, 30);
        add(patientDropdown);

        assignBtn = new JButton("Assign Doctor");
        assignBtn.setBounds(420, 80, 150, 30);
        assignBtn.setBackground(Color.WHITE);
        assignBtn.setForeground(Color.BLACK);
        assignBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(assignBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(590, 80, 100, 30);
        backBtn.setBackground(Color.RED);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(backBtn);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 140, 900, 380);
        add(scrollPane);

        model = new DefaultTableModel(new String[]{"Appointment ID", "Patient", "Disease", "Doctor", "Date"}, 0);
        table = new JTable(model);
        scrollPane.setViewportView(table);

        loadPatients();
        loadAppointments();

        assignBtn.addActionListener(e -> assignDoctor());

        backBtn.addActionListener(e -> {
            new Reception();
            dispose();
        });

        setVisible(true);
    }

    private void loadPatients() {
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT patient_id, name FROM patient_info");
            while (rs.next()) {
                patientDropdown.addItem(rs.getString("patient_id") + " - " + rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void assignDoctor() {
        try {
            String selected = (String) patientDropdown.getSelectedItem();
            if (selected == null || selected.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select a patient.");
                return;
            }

            String[] parts = selected.split(" - ");
            String patientId = parts[0];
            String patientName = parts[1];

            conn c = new conn();

            // Get disease
            ResultSet patientRs = c.statement.executeQuery("SELECT disease FROM patient_info WHERE patient_id = '" + patientId + "'");
            String disease = "";
            if (patientRs.next()) {
                disease = patientRs.getString("disease");
            }

            // Fetch available doctor
            ResultSet doctorRs = c.statement.executeQuery("SELECT doctor_id, name FROM doctor_info WHERE status = 'Available' ORDER BY RAND() LIMIT 1");
            if (doctorRs.next()) {
                String doctorId = doctorRs.getString("doctor_id");
                String doctorName = doctorRs.getString("name");

                // Get current date
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                // Insert appointment
                String insertQuery = "INSERT INTO appointments (patient_id, patient_name, disease, doctor_id, doctor_name, appointment_date) " +
                        "VALUES ('" + patientId + "', '" + patientName + "', '" + disease + "', '" + doctorId + "', '" + doctorName + "', '" + date + "')";
                c.statement.executeUpdate(insertQuery);

                // Update doctor status
                c.statement.executeUpdate("UPDATE doctor_info SET status = 'Assigned' WHERE doctor_id = '" + doctorId + "'");

                JOptionPane.showMessageDialog(null, "Doctor " + doctorName + " assigned to " + patientName);
                loadAppointments();

            } else {
                JOptionPane.showMessageDialog(null, "No doctors available!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error assigning doctor.");
        }
    }

    private void loadAppointments() {
        try {
            model.setRowCount(0); // Clear table
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM appointments");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("appointment_id"),
                        rs.getString("patient_name"),
                        rs.getString("disease"),
                        rs.getString("doctor_name"),
                        rs.getDate("appointment_date")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Appointments();
    }
}
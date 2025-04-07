package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Billing extends JFrame {

    JComboBox<String> patientDropdown;
    JTextField daysField, chargeField;
    JTextArea receiptArea;
    JButton generateBtn, backBtn;
    JLabel totalLabel;

    public Billing() {
        setTitle("Billing");
        setSize(850, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(90, 156, 163));

        JLabel heading = new JLabel("Generate Bill");
        heading.setFont(new Font("Tahoma", Font.BOLD, 26));
        heading.setForeground(Color.WHITE);
        heading.setBounds(300, 20, 400, 30);
        add(heading);

        JLabel patientLabel = new JLabel("Select Patient ID:");
        patientLabel.setBounds(100, 80, 200, 25);
        patientLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        patientLabel.setForeground(Color.WHITE);
        add(patientLabel);

        patientDropdown = new JComboBox<>();
        patientDropdown.setBounds(300, 80, 200, 25);
        add(patientDropdown);
        loadPatients();

        JLabel daysLabel = new JLabel("No. of Days:");
        daysLabel.setBounds(100, 130, 200, 25);
        daysLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        daysLabel.setForeground(Color.WHITE);
        add(daysLabel);

        daysField = new JTextField();
        daysField.setBounds(300, 130, 200, 25);
        add(daysField);

        JLabel chargeLabel = new JLabel("Per-Day Charges:");
        chargeLabel.setBounds(100, 180, 200, 25);
        chargeLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        chargeLabel.setForeground(Color.WHITE);
        add(chargeLabel);

        chargeField = new JTextField();
        chargeField.setBounds(300, 180, 200, 25);
        add(chargeField);

        generateBtn = new JButton("Generate Bill");
        generateBtn.setBounds(300, 230, 150, 30);
        generateBtn.setBackground(Color.BLACK);
        generateBtn.setForeground(Color.WHITE);
        add(generateBtn);

        totalLabel = new JLabel("");
        totalLabel.setBounds(300, 270, 400, 25);
        totalLabel.setForeground(Color.YELLOW);
        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(totalLabel);

        receiptArea = new JTextArea();
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        receiptArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(receiptArea);
        scroll.setBounds(100, 310, 630, 230);
        add(scroll);

        backBtn = new JButton("Back");
        backBtn.setBounds(600, 560, 100, 30);
        backBtn.setBackground(Color.RED);
        backBtn.setForeground(Color.WHITE);
        add(backBtn);

        generateBtn.addActionListener(e -> generateBill());
        backBtn.addActionListener(e -> {
            new Reception();
            dispose();
        });

        setVisible(true);
    }

    private void loadPatients() {
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT patient_id FROM patient_info");
            while (rs.next()) {
                patientDropdown.addItem(rs.getString("patient_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateBill() {
        String patientId = (String) patientDropdown.getSelectedItem();
        String daysText = daysField.getText().trim();
        String chargesText = chargeField.getText().trim();

        int days, charges;

        try {
            days = Integer.parseInt(daysText);
            charges = Integer.parseInt(chargesText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Days and Charges.");
            return;
        }

        String name = "", disease = "";
        try {
            conn c = new conn();
            String query = "SELECT name, disease FROM patient_info WHERE patient_id = '" + patientId + "'";
            ResultSet rs = c.statement.executeQuery(query);
            if (rs.next()) {
                name = rs.getString("name");
                disease = rs.getString("disease");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int total = days * charges;
        totalLabel.setText("Total Bill: ₹" + total);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String billDate = sdf.format(new Date());

        // Save to billing table
        try {
            conn c = new conn();
            String insertQuery = "INSERT INTO billing (patient_id, name, disease, days, per_day_charge, total_amount, billing_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = c.connection.prepareStatement(insertQuery);
            pst.setString(1, patientId);
            pst.setString(2, name);
            pst.setString(3, disease);
            pst.setInt(4, days);
            pst.setInt(5, charges);
            pst.setInt(6, total);
            pst.setString(7, billDate);
            pst.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        receiptArea.setText(
                "==============================\n" +
                        "        HOSPITAL BILL         \n" +
                        "==============================\n" +
                        "Patient ID : " + patientId + "\n" +
                        "Name       : " + name + "\n" +
                        "Disease    : " + disease + "\n" +
                        "------------------------------\n" +
                        "Days Stayed: " + days + "\n" +
                        "Charge/Day : ₹" + charges + "\n" +
                        "------------------------------\n" +
                        "Total Bill : ₹" + total + "\n" +
                        "Date       : " + billDate + "\n" +
                        "==============================\n" +
                        "      GET WELL SOON ❤️        \n" +
                        "=============================="
        );
    }

    public static void main(String[] args) {
        new Billing();
    }
}
package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Discharge_Patient extends JFrame {
    Choice patientIdChoice;
    JTextArea infoArea;
    JLabel dischargeTimeLabel;
    JButton dischargeBtn, backBtn;

    public Discharge_Patient() {
        setTitle("Discharge Patient");
        setBounds(300, 100, 750, 550);
        getContentPane().setBackground(new Color(90, 156, 163)); // hospital blue-green
        setLayout(null);

        JLabel heading = new JLabel("Discharge Patient");
        heading.setFont(new Font("Tahoma", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBounds(240, 30, 350, 40);
        add(heading);

        JLabel selectLabel = new JLabel("Select Patient ID:");
        selectLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        selectLabel.setForeground(Color.WHITE);
        selectLabel.setBounds(80, 100, 160, 30);
        add(selectLabel);

        patientIdChoice = new Choice();
        patientIdChoice.setBounds(250, 100, 200, 30);
        add(patientIdChoice);

        infoArea = new JTextArea();
        infoArea.setBounds(80, 150, 580, 200);
        infoArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoArea.setEditable(false);
        infoArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        add(infoArea);

        dischargeTimeLabel = new JLabel("Discharge Time: ");
        dischargeTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        dischargeTimeLabel.setForeground(Color.WHITE);
        dischargeTimeLabel.setBounds(80, 370, 400, 30);
        add(dischargeTimeLabel);

        dischargeBtn = new JButton("Discharge");
        dischargeBtn.setBounds(150, 420, 150, 45);
        dischargeBtn.setBackground(new Color(246, 215, 136));
        dischargeBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        dischargeBtn.setFocusPainted(false);
        add(dischargeBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(350, 420, 150, 45);
        backBtn.setBackground(new Color(255, 102, 102)); // soft red
        backBtn.setForeground(Color.BLACK); // black text
        backBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        backBtn.setFocusPainted(false);
        add(backBtn);

        loadPatientIDs();

        patientIdChoice.addItemListener(e -> displayPatientInfo());

        dischargeBtn.addActionListener(e -> dischargePatient());
        backBtn.addActionListener(e -> {
            new Reception();
            dispose();
        });

        setVisible(true);
    }

    private void loadPatientIDs() {
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT patient_id FROM patient_info");
            while (rs.next()) {
                patientIdChoice.add(rs.getString("patient_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayPatientInfo() {
        String id = patientIdChoice.getSelectedItem();
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM patient_info WHERE patient_id='" + id + "'");
            if (rs.next()) {
                String info = "Name: " + rs.getString("name") +
                        "\nGender: " + rs.getString("gender") +
                        "\nRoom No: " + rs.getString("room_no") +
                        "\nDisease: " + rs.getString("disease") +
                        "\nAdmission Date: " + rs.getDate("admission_date");
                infoArea.setText(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dischargePatient() {
        String id = patientIdChoice.getSelectedItem();
        String room = "";

        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT room_no FROM patient_info WHERE patient_id='" + id + "'");
            if (rs.next()) {
                room = rs.getString("room_no");
            }

            // Capture discharge time
            String dischargeTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            dischargeTimeLabel.setText("Discharge Time: " + dischargeTime);

            // Remove patient and update room
            c.statement.executeUpdate("DELETE FROM patient_info WHERE patient_id='" + id + "'");
            c.statement.executeUpdate("UPDATE rooms SET status='Free' WHERE room_no='" + room + "'");

            JOptionPane.showMessageDialog(null, "Patient discharged successfully!\nRoom marked as Free.");
            dispose();
            new Discharge_Patient();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Discharge_Patient();
    }
}
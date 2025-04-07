package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ADD_PATIENT extends JFrame {

    JComboBox<String> comboBox;
    JTextField textFieldNumber, textFieldName, textFieldROOM, disease;
    JRadioButton r1, r2;
    JButton b1, b2;

    public ADD_PATIENT() {
        setTitle("Add Patient");
        setSize(850, 550);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(90, 156, 163));
        panel.setBounds(20, 20, 800, 480);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(panel);

        JLabel labelID = new JLabel("ID Type");
        labelID.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelID.setForeground(Color.WHITE);
        labelID.setBounds(200, 30, 120, 30);
        panel.add(labelID);

        comboBox = new JComboBox<>(new String[]{"AADHAR", "PAN", "VOTER"});
        comboBox.setBackground(new Color(255, 255, 255));
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        comboBox.setBounds(400, 30, 200, 30);
        panel.add(comboBox);

        JLabel labelID1 = new JLabel("Patient ID");
        labelID1.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelID1.setForeground(Color.WHITE);
        labelID1.setBounds(200, 80, 120, 30);
        panel.add(labelID1);

        textFieldNumber = new JTextField();
        textFieldNumber.setBounds(400, 80, 200, 30);
        panel.add(textFieldNumber);

        JLabel labelName = new JLabel("Name");
        labelName.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelName.setForeground(Color.WHITE);
        labelName.setBounds(200, 120, 120, 30);
        panel.add(labelName);

        textFieldName = new JTextField();
        textFieldName.setBounds(400, 120, 200, 30);
        panel.add(textFieldName);

        JLabel labelGender = new JLabel("Gender");
        labelGender.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelGender.setForeground(Color.WHITE);
        labelGender.setBounds(200, 160, 120, 30);
        panel.add(labelGender);

        r1 = new JRadioButton("Male");
        r2 = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(r1);
        genderGroup.add(r2);

        r1.setBackground(new Color(90, 156, 163));
        r2.setBackground(new Color(90, 156, 163));
        r1.setForeground(Color.WHITE);
        r2.setForeground(Color.WHITE);
        r1.setFont(new Font("Tahoma", Font.BOLD, 16));
        r2.setFont(new Font("Tahoma", Font.BOLD, 16));
        r1.setBounds(400, 160, 80, 30);
        r2.setBounds(490, 160, 100, 30);
        panel.add(r1);
        panel.add(r2);

        JLabel labelROOM = new JLabel("Room No");
        labelROOM.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelROOM.setForeground(Color.WHITE);
        labelROOM.setBounds(200, 200, 120, 30);
        panel.add(labelROOM);

        textFieldROOM = new JTextField();
        textFieldROOM.setBounds(400, 200, 200, 30);
        panel.add(textFieldROOM);

        JLabel labeldisease = new JLabel("Disease");
        labeldisease.setFont(new Font("Tahoma", Font.BOLD, 18));
        labeldisease.setForeground(Color.WHITE);
        labeldisease.setBounds(200, 240, 120, 30);
        panel.add(labeldisease);

        disease = new JTextField();
        disease.setBounds(400, 240, 200, 30);
        panel.add(disease);

        JLabel labelTime = new JLabel("Date");
        labelTime.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelTime.setForeground(Color.WHITE);
        labelTime.setBounds(200, 280, 120, 30);
        panel.add(labelTime);

        JLabel labelDate = new JLabel(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        labelDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelDate.setForeground(Color.WHITE);
        labelDate.setBounds(400, 280, 200, 30);
        panel.add(labelDate);

        b1 = new JButton("Add");
        b1.setBounds(250, 350, 100, 30);
        panel.add(b1);

        b2 = new JButton("Back");
        b2.setBounds(400, 350, 100, 30);
        panel.add(b2);

        // Add ActionListeners
        b1.addActionListener(e -> {
            String idType = (String) comboBox.getSelectedItem();
            String patientId = textFieldNumber.getText();
            String name = textFieldName.getText();
            String gender = r1.isSelected() ? "Male" : "Female";
            String room = textFieldROOM.getText();
            String dis = disease.getText();
            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());

            try {
                conn c = new conn();
                String query = "INSERT INTO patient_info VALUES ('" + idType + "','" + patientId + "','" + name + "','" + gender + "','" + room + "','" + dis + "','" + sqlDate + "')";
                c.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Patient Added Successfully");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding patient. Please check console.");
            }
        });

        b2.addActionListener(e -> {
            setVisible(false);
            new Reception();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ADD_PATIENT();
    }
}
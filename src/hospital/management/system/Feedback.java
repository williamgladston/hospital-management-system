package hospital.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Feedback extends JFrame {

    JComboBox<String> cbPatientID;
    JComboBox<String> cbRating;
    JTextArea taComments;
    JTable table;

    public Feedback() {
        setTitle("Feedback");
        setBounds(300, 100, 900, 550);
        getContentPane().setBackground(new Color(90, 156, 163));
        setLayout(null);

        JLabel heading = new JLabel("Patient Feedback");
        heading.setFont(new Font("Tahoma", Font.BOLD, 26));
        heading.setForeground(Color.WHITE);
        heading.setBounds(300, 20, 300, 40);
        add(heading);

        JLabel lblPatientID = new JLabel("Patient ID:");
        lblPatientID.setBounds(80, 80, 120, 30);
        lblPatientID.setForeground(Color.WHITE);
        lblPatientID.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblPatientID);

        cbPatientID = new JComboBox<>();
        cbPatientID.setBounds(200, 80, 200, 30);
        add(cbPatientID);
        populatePatientIDs();

        JLabel lblRating = new JLabel("Rating (1-5):");
        lblRating.setBounds(80, 130, 120, 30);
        lblRating.setForeground(Color.WHITE);
        lblRating.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblRating);

        cbRating = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        cbRating.setBounds(200, 130, 200, 30);
        add(cbRating);

        JLabel lblComments = new JLabel("Comments:");
        lblComments.setBounds(80, 180, 120, 30);
        lblComments.setForeground(Color.WHITE);
        lblComments.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblComments);

        taComments = new JTextArea();
        taComments.setLineWrap(true);
        taComments.setWrapStyleWord(true);
        JScrollPane commentPane = new JScrollPane(taComments);
        commentPane.setBounds(200, 180, 200, 80);
        add(commentPane);

        JButton btnSubmit = new JButton("Submit Feedback");
        btnSubmit.setBounds(200, 280, 200, 35);
        btnSubmit.setBackground(Color.WHITE);
        btnSubmit.setForeground(Color.BLACK);
        btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(btnSubmit);

        btnSubmit.addActionListener(e -> submitFeedback());

        // Table
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(450, 80, 400, 300);
        add(scrollPane);
        loadFeedback();

        // Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(700, 400, 100, 30);
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(btnBack);

        btnBack.addActionListener(e -> {
            new Reception();
            dispose();
        });

        setVisible(true);
    }

    private void populatePatientIDs() {
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT patient_id FROM patient_info");
            while (rs.next()) {
                cbPatientID.addItem(rs.getString("patient_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitFeedback() {
        String patientId = cbPatientID.getSelectedItem().toString();
        int rating = Integer.parseInt(cbRating.getSelectedItem().toString());
        String comments = taComments.getText();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (comments.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter comments.");
            return;
        }

        try {
            conn c = new conn();
            String query = "INSERT INTO feedback (patient_id, rating, comments, feedback_date) VALUES ('"
                    + patientId + "', " + rating + ", '" + comments + "', '" + date + "')";
            c.statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Feedback Submitted!");
            taComments.setText("");
            loadFeedback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFeedback() {
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM feedback");

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID", "Patient ID", "Rating", "Comments", "Date"}, 0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("patient_id"),
                        rs.getInt("rating"),
                        rs.getString("comments"),
                        rs.getDate("feedback_date")
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Feedback();
    }
}
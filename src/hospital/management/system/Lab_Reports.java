package hospital.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lab_Reports extends JFrame {
    JTable table;
    JComboBox<String> cbPatientID;
    JTextField tfReportType, tfFilePath;
    JButton btnUpload, btnBrowse, btnBack;

    public Lab_Reports() {
        setTitle("Lab Reports");
        setBounds(300, 100, 900, 550);
        getContentPane().setBackground(new Color(90, 156, 163));
        setLayout(null);

        JLabel heading = new JLabel("Manage Lab Reports");
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

        JLabel lblReportType = new JLabel("Report Type:");
        lblReportType.setBounds(80, 130, 120, 30);
        lblReportType.setForeground(Color.WHITE);
        lblReportType.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblReportType);

        tfReportType = new JTextField();
        tfReportType.setBounds(200, 130, 200, 30);
        add(tfReportType);

        JLabel lblFilePath = new JLabel("File Path:");
        lblFilePath.setBounds(80, 180, 120, 30);
        lblFilePath.setForeground(Color.WHITE);
        lblFilePath.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblFilePath);

        tfFilePath = new JTextField();
        tfFilePath.setBounds(200, 180, 200, 30);
        tfFilePath.setEditable(false);
        add(tfFilePath);

        btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(410, 180, 100, 30);
        btnBrowse.setBackground(Color.WHITE);
        btnBrowse.setForeground(Color.BLACK);
        btnBrowse.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(btnBrowse);

        btnBrowse.addActionListener(e -> chooseFile());

        btnUpload = new JButton("Upload Report");
        btnUpload.setBounds(200, 230, 200, 35);
        btnUpload.setBackground(Color.WHITE);
        btnUpload.setForeground(Color.BLACK);
        btnUpload.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(btnUpload);

        btnUpload.addActionListener(e -> uploadReport());

        // Table for reports
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(450, 80, 400, 300);
        add(scrollPane);

        showReports();

        // Back Button
        btnBack = new JButton("Back");
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

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            tfFilePath.setText(selectedFile.getAbsolutePath());
        }
    }

    private void uploadReport() {
        String patientId = cbPatientID.getSelectedItem().toString();
        String reportType = tfReportType.getText();
        String filePath = tfFilePath.getText();
        String reportDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (reportType.isEmpty() || filePath.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields");
            return;
        }

        try {
            conn c = new conn();
            String query = "INSERT INTO lab_reports (patient_id, report_type, file_path, report_date) VALUES ('" +
                    patientId + "', '" + reportType + "', '" + filePath + "', '" + reportDate + "')";
            c.statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Report Uploaded Successfully");
            showReports();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Patient ID not found or database issue");
            e.printStackTrace();
        }
    }

    private void showReports() {
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM lab_reports");

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"Report ID", "Patient ID", "Report Type", "File Path", "Date"}, 0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("report_id"),
                        rs.getString("patient_id"),
                        rs.getString("report_type"),
                        rs.getString("file_path"),
                        rs.getDate("report_date")
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Lab_Reports();
    }
}
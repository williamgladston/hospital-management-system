package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class login extends JFrame implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, cancelButton;

    login() {
        // Set frame properties
        setTitle("Login");
        setSize(450, 250);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel label = new JLabel("Username:");
        JLabel label1 = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");

        // Set bounds
        label.setBounds(40, 30, 100, 30);
        label1.setBounds(40, 80, 100, 30);
        usernameField.setBounds(160, 30, 200, 30);
        passwordField.setBounds(160, 80, 200, 30);
        loginButton.setBounds(160, 130, 100, 30);
        cancelButton.setBounds(270, 130, 100, 30);

        // Set font
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label1.setFont(new Font("Tahoma", Font.BOLD, 16));

        // Add action listeners
        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // Add components to frame
        add(label);
        add(label1);
        add(usernameField);
        add(passwordField);
        add(loginButton);
        add(cancelButton);

        // Show frame
        setVisible(true);
    }

    public static void main(String[] args) {
        new login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            try {
                conn c = new conn();
                String user = usernameField.getText();
                String pass = new String(passwordField.getPassword());

                String q = "SELECT * FROM login WHERE id = '" + user + "' AND pw = '" + pass + "'";
                ResultSet resultSet = c.statement.executeQuery(q);

                if (resultSet.next()) {
                    new Reception(); // You should have a 'test' class defined
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == cancelButton) {
            System.exit(0);
        }
    }
}
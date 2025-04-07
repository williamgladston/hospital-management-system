package hospital.management.system;

import javax.swing.*;
import java.awt.*;

public class Reception extends JFrame {

    Reception() {
        setTitle("Reception");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Blue hospital background
        getContentPane().setBackground(new Color(224, 242, 255)); // light hospital blue

        // Header
        JLabel heading = new JLabel("Hospital Reception Dashboard", SwingConstants.CENTER);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));
        heading.setForeground(new Color(0, 70, 127)); // darker blue
        heading.setBounds(300, 30, 600, 40);
        add(heading);

        // Main Panel
        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(50, 100, 1080, 500);
        panel1.setBackground(new Color(255, 255, 255)); // white background for panel
        panel1.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 70, 127), 2),
                "Main Controls",
                0, 0, new Font("Tahoma", Font.BOLD, 16),
                new Color(0, 70, 127)
        ));
        add(panel1);

        Color btnColor = new Color(197, 226, 255); // soft blue
        Font btnFont = new Font("Tahoma", Font.BOLD, 14);

        String[] btnTexts = {
                "Add New Patient", "Room", "Patient Records",
                "Discharge Patient", "Billing", "Doctor Info",
                "Appointments", "Lab Reports", "Feedback"
        };

        JButton[] buttons = new JButton[btnTexts.length];
        int x = 40, y = 40;

        for (int i = 0; i < btnTexts.length; i++) {
            buttons[i] = new JButton(btnTexts[i]);
            buttons[i].setBounds(x, y, 240, 45);
            buttons[i].setBackground(btnColor);
            buttons[i].setForeground(new Color(0, 50, 100));
            buttons[i].setFont(btnFont);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(BorderFactory.createLineBorder(new Color(0, 100, 180)));
            panel1.add(buttons[i]);

            if ((i + 1) % 3 == 0) {
                x = 40;
                y += 70;
            } else {
                x += 300;
            }

            // Button Action Listener
            String action = btnTexts[i];
            buttons[i].addActionListener(e -> {
                switch (action) {
                    case "Add New Patient":
                        new ADD_PATIENT();
                        break;
                    case "Room":
                        new Room();
                        break;
                    case "Patient Records":
                        new Patient_Records();
                        break;
                    case "Discharge Patient":
                        new Discharge_Patient();
                        break;
                    case "Billing":
                        new Billing();
                        break;
                    case "Doctor Info":
                        new Doctor_Info();
                        break;
                    case "Appointments":
                        new Appointments();
                        break;
                    case "Lab Reports":
                        new Lab_Reports();
                        break;
                    case "Feedback":
                        new Feedback();
                        break;
                }
            });
        }

        // Logout Button
        JButton logoutBtn = new JButton("Log Out");
        logoutBtn.setBounds(900, 430, 130, 40);
        logoutBtn.setBackground(new Color(220, 53, 69)); // soft red
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(btnFont);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 0)));
        panel1.add(logoutBtn);

        logoutBtn.addActionListener(e -> {
            new login();
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Reception();
    }
}
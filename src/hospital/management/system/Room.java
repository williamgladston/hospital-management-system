package hospital.management.system;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Room extends JFrame {

    JTable table;

    public Room() {
        setTitle("Room Management");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(90, 156, 163));
        setLayout(null);

        // Header
        JLabel heading = new JLabel("Room Status Dashboard");
        heading.setFont(new Font("Tahoma", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBounds(300, 20, 400, 40);
        add(heading);

        // Refresh Button
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(800, 30, 100, 30);
        refreshBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
        refreshBtn.setBackground(new Color(246, 215, 136));
        refreshBtn.setFocusPainted(false);
        add(refreshBtn);
        refreshBtn.addActionListener(e -> showRoomData());

        // Table panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(50, 90, 830, 380);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                "Room Availability",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Tahoma", Font.BOLD, 16),
                new Color(90, 156, 163)
        ));
        add(panel);

        // Table setup
        table = new JTable() {
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    String status = (String) getValueAt(row, 1);
                    if ("Occupied".equalsIgnoreCase(status)) {
                        c.setBackground(new Color(255, 204, 204)); // Light red
                    } else {
                        c.setBackground(new Color(204, 255, 204)); // Light green
                    }
                } else {
                    c.setBackground(new Color(184, 207, 229)); // Selected row
                }
                return c;
            }
        };
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.setGridColor(Color.GRAY);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        showRoomData();

        setVisible(true);
    }

    private void showRoomData() {
        try {
            conn c = new conn();
            String query = "SELECT room_no, status FROM rooms";
            ResultSet rs = c.statement.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel(new String[]{"Room No", "Status"}, 0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("room_no"),
                        rs.getString("status")
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading room data!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Room();
    }
}
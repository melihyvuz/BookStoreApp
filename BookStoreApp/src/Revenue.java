import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Revenue extends JFrame {
    public Revenue() {
        setTitle("Total Revenue");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblRevenue = new JLabel("Calculating...");
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblRevenue, gbc);

        JButton deleteAll = new JButton("Delete All Sales");
        gbc.gridy++;
        panel.add(deleteAll, gbc);

        JButton backButton = new JButton("Go Back");
        gbc.gridy++;
        panel.add(backButton, gbc);

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT SUM(b.price * s.quantity) as total FROM sales s JOIN books b ON s.book_id = b.id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double total = rs.getDouble("total");
                lblRevenue.setText("Total Revenue: $" + total);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        deleteAll.addActionListener(e -> {
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM sales");
                stmt.executeUpdate();
                lblRevenue.setText("Total Revenue: $0.0");
                JOptionPane.showMessageDialog(this, "All sales deleted.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> dispose());

        add(panel);
        setVisible(true);
    }
}
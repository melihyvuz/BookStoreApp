import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SellBook extends JFrame {
    public SellBook() {
        setTitle("Sell Book");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField bookIdField = new JTextField(20);
        JTextField qtyField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Book ID:"), gbc);
        gbc.gridx = 1;
        panel.add(bookIdField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        panel.add(qtyField, gbc);

        JButton sellButton = new JButton("Sell");
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(sellButton, gbc);

        JButton backButton = new JButton("Go Back");
        gbc.gridy++;
        panel.add(backButton, gbc);

        sellButton.addActionListener(e -> {
            try {
                int bookId = Integer.parseInt(bookIdField.getText());
                int quantity = Integer.parseInt(qtyField.getText());

                try (Connection conn = DBConnection.getConnection()) {
                    PreparedStatement check = conn.prepareStatement("SELECT COUNT(*) FROM books WHERE id = ?");
                    check.setInt(1, bookId);
                    ResultSet rs = check.executeQuery();
                    if (rs.next() && rs.getInt(1) == 0) {
                        JOptionPane.showMessageDialog(this, "Book ID does not exist.");
                        return;
                    }

                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO sales (book_id, quantity) VALUES (?, ?)");
                    stmt.setInt(1, bookId);
                    stmt.setInt(2, quantity);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Sale recorded!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Book ID and Quantity must be numbers.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> dispose());

        add(panel);
        setVisible(true);
    }
}

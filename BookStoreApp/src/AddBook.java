import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddBook extends JFrame {
    public AddBook() {
        setTitle("Add Book");
        setSize(400, 300);
        setLocationRelativeTo(null); //pencereyi ekranın ortasına getir
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //sadece o sayfayı kapat uygulamayı değil

        JPanel panel = new JPanel(new GridBagLayout()); //esnek bir yerleşim düzeni
        GridBagConstraints gbc = new GridBagConstraints(); //uygulamadaki boşlukları kenarları düzenler
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL; //yatay şekilde yapıyor

        JTextField titleField = new JTextField(20); //başlıkların girilme yeri
        JTextField authorField = new JTextField(20);
        JTextField priceField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        panel.add(priceField, gbc);

        JButton addButton = new JButton("Add");
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        JButton backButton = new JButton("Go Back");
        gbc.gridy++;
        panel.add(backButton, gbc);

        addButton.addActionListener(e -> {
            try {
                double price = Double.parseDouble(priceField.getText()); // fiyatı sayı olarak al
                try (Connection conn = DBConnection.getConnection()) { // veritabanına bağlan
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO books (title, author, price) VALUES (?, ?, ?)");
                    stmt.setString(1, titleField.getText());
                    stmt.setString(2, authorField.getText());
                    stmt.setDouble(3, price);
                    stmt.executeUpdate(); // veriyi ekle
                    JOptionPane.showMessageDialog(this, "Book added successfully!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price must be a number.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> dispose());

        add(panel);
        setVisible(true);
    }
}
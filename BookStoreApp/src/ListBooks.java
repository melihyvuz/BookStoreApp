import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ListBooks extends JFrame {
    public ListBooks() {
        setTitle("List Books");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Title", "Author", "Price"}, 0); //tablodaki gösterilen yerler ve sırası
        JTable table = new JTable(model); //veriyi gösterir
        JScrollPane scrollPane = new JScrollPane(table); //kaydırma çubuğu
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton backButton = new JButton("Go Back");
        JButton deleteButton = new JButton("Delete Selected");
        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        try (Connection conn = DBConnection.getConnection()) { //veriyi bağlar
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) model.getValueAt(selectedRow, 0);
                try (Connection conn = DBConnection.getConnection()) {
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?");
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                    model.removeRow(selectedRow);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        backButton.addActionListener(e -> dispose());

        setVisible(true); //arayüzü ekranda gösterir 
    }
}

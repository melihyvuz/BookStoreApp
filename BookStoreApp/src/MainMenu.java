import javax.swing.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Book Store Management");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JButton addBookBtn = new JButton("Add Book");
        JButton listBooksBtn = new JButton("List Books");
        JButton sellBookBtn = new JButton("Sell Book");
        JButton viewSalesBtn = new JButton("View Sales");
        JButton viewRevenueBtn = new JButton("View Revenue");
        JButton exitBtn = new JButton("Exit");

        addBookBtn.setBounds(100, 20, 200, 30);
        listBooksBtn.setBounds(100, 60, 200, 30);
        sellBookBtn.setBounds(100, 100, 200, 30);
        viewSalesBtn.setBounds(100, 140, 200, 30);
        viewRevenueBtn.setBounds(100, 180, 200, 30);
        exitBtn.setBounds(100, 220, 200, 30);

        add(addBookBtn);
        add(listBooksBtn);
        add(sellBookBtn);
        add(viewSalesBtn);
        add(viewRevenueBtn);
        add(exitBtn);

        addBookBtn.addActionListener(e -> new AddBook());
        listBooksBtn.addActionListener(e -> new ListBooks());
        sellBookBtn.addActionListener(e -> new SellBook());
        viewSalesBtn.addActionListener(e -> new ViewSales());
        viewRevenueBtn.addActionListener(e -> new Revenue());
        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
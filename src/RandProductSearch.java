import javax.swing.*;
import java.awt.*;
import java.io.RandomAccessFile;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JButton searchButton;
    private JButton quitButton;
    private RandomAccessFile file;

    public RandProductSearch() {
        setTitle("Product Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupGUI();
        setupFile();
    }

    private void setupGUI() {
        setLayout(new BorderLayout(10, 10));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());

        searchPanel.add(new JLabel("Search Product Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Results panel
        String[] columns = {"Name", "Description", "ID", "Cost"};
        tableModel = new DefaultTableModel(columns, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // Button panel
        JPanel buttonPanel = new JPanel();
        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> quit());
        buttonPanel.add(quitButton);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(800, 400);
        setLocationRelativeTo(null);
    }

    private void setupFile() {
        try {
            file = new RandomAccessFile("products.dat", "r");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error opening file: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String readFixedString(int length) throws IOException {
        byte[] bytes = new byte[length];
        file.readFully(bytes);
        return new String(bytes).trim();
    }

    private void performSearch() {
        tableModel.setRowCount(0);
        String searchTerm = searchField.getText().toLowerCase();

        try {
            file.seek(0);
            while (file.getFilePointer() < file.length()) {
                // Read fixed-length fields
                String name = readFixedString(Product.NAME_LENGTH);
                String desc = readFixedString(Product.DESC_LENGTH);
                String id = readFixedString(Product.ID_LENGTH);
                double cost = file.readDouble();

                // Add matching records to table
                if (name.toLowerCase().contains(searchTerm)) {
                    tableModel.addRow(new Object[]{name, desc, id, cost});
                }
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "No matching products found.",
                        "Search Results",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error reading file: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void quit() {
        try {
            if (file != null) {
                file.close();
            }
        } catch (IOException e) {
            // Log error or show message
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RandProductSearch().setVisible(true);
        });
    }
}
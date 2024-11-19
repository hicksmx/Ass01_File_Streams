import javax.swing.*;
import java.awt.*;
import java.io.RandomAccessFile;
import java.io.IOException;

public class RandProductMaker extends JFrame {
    private JTextField nameField;
    private JTextField descField;
    private JTextField idField;
    private JTextField costField;
    private JTextField recordCountField;
    private JButton addButton;
    private JButton quitButton;
    private RandomAccessFile file;
    private int recordCount = 0;

    public RandProductMaker() {
        setTitle("Random Product Maker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupGUI();
        setupFile();
        countExistingRecords();
    }

    private void setupGUI() {
        setLayout(new BorderLayout(10, 10));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField(20);
        descField = new JTextField(20);
        idField = new JTextField(20);
        costField = new JTextField(20);
        recordCountField = new JTextField("0");
        recordCountField.setEditable(false);

        inputPanel.add(new JLabel("Name (max 35 chars):"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Description (max 75 chars):"));
        inputPanel.add(descField);
        inputPanel.add(new JLabel("ID (6 chars):"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Cost:"));
        inputPanel.add(costField);
        inputPanel.add(new JLabel("Record Count:"));
        inputPanel.add(recordCountField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Record");
        quitButton = new JButton("Quit");

        addButton.addActionListener(e -> addRecord());
        quitButton.addActionListener(e -> quit());

        buttonPanel.add(addButton);
        buttonPanel.add(quitButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void setupFile() {
        try {
            file = new RandomAccessFile("products.dat", "rw");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error opening file: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void countExistingRecords() {
        try {
            recordCount = (int)(file.length() / Product.RECORD_SIZE);
            recordCountField.setText(String.valueOf(recordCount));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error counting records: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void writeFixedString(String s, int length) throws IOException {
        StringBuilder sb = new StringBuilder(s);
        sb.setLength(length);
        file.writeBytes(sb.toString());
    }

    private void addRecord() {
        try {
            if (validateInputs()) {
                // Position file pointer at the end
                file.seek(file.length());

                // Write each field with fixed length
                writeFixedString(nameField.getText(), Product.NAME_LENGTH);
                writeFixedString(descField.getText(), Product.DESC_LENGTH);
                writeFixedString(idField.getText(), Product.ID_LENGTH);
                file.writeDouble(Double.parseDouble(costField.getText()));

                recordCount++;
                recordCountField.setText(String.valueOf(recordCount));
                clearFields();

                JOptionPane.showMessageDialog(this,
                        "Record added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error writing record: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateInputs() {
        if (nameField.getText().isEmpty() ||
                descField.getText().isEmpty() ||
                idField.getText().isEmpty() ||
                costField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields must be filled out",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (nameField.getText().length() > Product.NAME_LENGTH ||
                descField.getText().length() > Product.DESC_LENGTH ||
                idField.getText().length() > Product.ID_LENGTH) {
            JOptionPane.showMessageDialog(this,
                    "Field lengths exceed maximum allowed",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            double cost = Double.parseDouble(costField.getText());
            if (cost < 0) {
                throw new NumberFormatException("Cost cannot be negative");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid cost value",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void clearFields() {
        nameField.setText("");
        descField.setText("");
        idField.setText("");
        costField.setText("");
        nameField.requestFocus();
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
            new RandProductMaker().setVisible(true);
        });
    }
}
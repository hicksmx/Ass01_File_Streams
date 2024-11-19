import java.io.Serializable;

public class Product implements Serializable {
    public static final int NAME_LENGTH = 35;
    public static final int DESC_LENGTH = 75;
    public static final int ID_LENGTH = 6;
    public static final int RECORD_SIZE =
            (NAME_LENGTH + DESC_LENGTH + ID_LENGTH) * 2 + 8; // *2 for char size, +8 for double

    private String name;
    private String description;
    private String ID;
    private double cost;

    // Original constructors and methods remain...
    public Product(String name, String description, String ID, double cost) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.cost = cost;
    }

    // Getters and setters remain the same...
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getID() { return ID; }
    public double getCost() { return cost; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCost(double cost) { this.cost = cost; }

    // Add methods for random access file handling
    public String getFixedLengthName() {
        return padOrTruncate(name, NAME_LENGTH);
    }

    public String getFixedLengthDescription() {
        return padOrTruncate(description, DESC_LENGTH);
    }

    public String getFixedLengthID() {
        return padOrTruncate(ID, ID_LENGTH);
    }

    private String padOrTruncate(String input, int length) {
        if (input == null) {
            input = "";
        }
        if (input.length() > length) {
            return input.substring(0, length);
        }
        return String.format("%-" + length + "s", input);
    }

    public static Product createFromFixedLengthData(String name, String description,
                                                    String ID, double cost) {
        return new Product(
                name.trim(),
                description.trim(),
                ID.trim(),
                cost
        );
    }

    // Existing toString, toCSV, toJSON, toXML methods remain...
    @Override
    public String toString() {
        return String.format("Product{name='%s', description='%s', ID='%s', cost=%.2f}",
                name, description, ID, cost);
    }

    public String toCSV() {
        return String.format("%s,%s,%s,%.2f", name, description, ID, cost);
    }

    public String toJSON() {
        return String.format(
                "{\"name\":\"%s\",\"description\":\"%s\",\"ID\":\"%s\",\"cost\":%.2f}",
                name, description, ID, cost
        );
    }

    public String toXML() {
        return String.format(
                "<product><name>%s</name><description>%s</description><ID>%s</ID><cost>%.2f</cost></product>",
                name, description, ID, cost
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.cost, cost) == 0 &&
                name.equals(product.name) &&
                description.equals(product.description) &&
                ID.equals(product.ID);
    }

    // Add method to get total record size in bytes
    public static int getRecordSize() {
        return RECORD_SIZE;
    }
}
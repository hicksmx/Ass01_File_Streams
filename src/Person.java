import java.util.Calendar;

/**
 * Represents a Person with basic identifying information
 */
public class Person {
    private String firstName;
    private String lastName;
    private String ID;
    private String title;
    private int YOB;

    /**
     * Full constructor for Person class
     * @param firstName Person's first name
     * @param lastName Person's last name
     * @param ID Person's ID (sequence of digits)
     * @param title Person's title (Mr., Mrs., Ms., etc.)
     * @param YOB Person's year of birth (1940-2010)
     */
    public Person(String firstName, String lastName, String ID, String title, int YOB) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.title = title;
        this.YOB = YOB;
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getID() { return ID; }
    public String getTitle() { return title; }
    public int getYOB() { return YOB; }

    // Setters (except for ID which should never change)
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setTitle(String title) { this.title = title; }
    public void setYOB(int YOB) { this.YOB = YOB; }

    /**
     * Returns the person's full name (firstName + lastName)
     * @return String containing full name
     */
    public String fullName() {
        return firstName + " " + lastName;
    }

    /**
     * Returns the person's formal name (title + fullName)
     * @return String containing formal name
     */
    public String formalName() {
        return title + " " + fullName();
    }

    /**
     * Calculates person's current age based on current year
     * @return String representation of current age
     */
    public String getAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return String.valueOf(currentYear - YOB);
    }

    /**
     * Calculates person's age for a specific year
     * @param year Year to calculate age for
     * @return String representation of age in specified year
     */
    public String getAge(int year) {
        return String.valueOf(year - YOB);
    }

    /**
     * Creates CSV representation of Person object
     * @return CSV formatted string of person data
     */
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%d", firstName, lastName, ID, title, YOB);
    }

    /**
     * Creates JSON representation of Person object
     * @return JSON formatted string of person data
     */
    public String toJSON() {
        return String.format(
                "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"ID\":\"%s\",\"title\":\"%s\",\"YOB\":%d}",
                firstName, lastName, ID, title, YOB
        );
    }

    /**
     * Creates XML representation of Person object
     * @return XML formatted string of person data
     */
    public String toXML() {
        return String.format(
                "<person><firstName>%s</firstName><lastName>%s</lastName><ID>%s</ID><title>%s</title><YOB>%d</YOB></person>",
                firstName, lastName, ID, title, YOB
        );
    }

    @Override
    public String toString() {
        return String.format("Person{firstName='%s', lastName='%s', ID='%s', title='%s', YOB=%d}",
                firstName, lastName, ID, title, YOB);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return YOB == person.YOB &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                ID.equals(person.ID) &&
                title.equals(person.title);
    }
}
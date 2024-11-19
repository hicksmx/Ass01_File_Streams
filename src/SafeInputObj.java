import java.util.Scanner;

/**
 * Object-oriented version of SafeInput utility class
 */
public class SafeInputObj {
    private Scanner pipe;

    /**
     * Default constructor - uses System.in
     */
    public SafeInputObj() {
        pipe = new Scanner(System.in);
    }

    /**
     * Constructor that takes a Scanner parameter
     * @param scanner Scanner to use for input
     */
    public SafeInputObj(Scanner scanner) {
        pipe = scanner;
    }

    /**
     * Get a String which contains at least one character
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public String getNonZeroLenString(String prompt) {
        String retString = "";
        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        } while(retString.length() == 0);

        return retString;
    }

    /**
     * Get an int value within a specified numeric range
     * @param prompt input prompt msg should not include range info
     * @param low low end of inclusive range
     * @param high high end of inclusive range
     * @return int value within the inclusive range
     */
    public int getRangedInt(String prompt, int low, int high) {
        int retVal = 0;
        String trash = "";
        boolean done = false;

        do {
            System.out.print("\n" + prompt + "[" + low + "-" + high + "]: ");
            if(pipe.hasNextInt()) {
                retVal = pipe.nextInt();
                pipe.nextLine();
                if(retVal >= low && retVal <= high) {
                    done = true;
                } else {
                    System.out.println("\nNumber is out of range [" + low + "-" + high + "]: " + retVal);
                }
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter an int: " + trash);
            }
        } while(!done);

        return retVal;
    }

    /**
     * Get an unconstrained double value
     * @param prompt input prompt msg should not contain range info
     * @return an unconstrained double value
     */
    public double getDouble(String prompt) {
        double retVal = 0;
        String trash = "";
        boolean done = false;

        do {
            System.out.print("\n" + prompt + ": ");
            if(pipe.hasNextDouble()) {
                retVal = pipe.nextDouble();
                pipe.nextLine();
                done = true;
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter a double: " + trash);
            }
        } while(!done);

        return retVal;
    }

    /**
     * Get a [Y/N] confirmation from the user
     * @param prompt input prompt msg for user does not need [Y/N]
     * @return true for yes false for no
     */
    public boolean getYNConfirm(String prompt) {
        boolean retVal = true;
        String response = "";
        boolean gotAVal = false;

        do {
            System.out.print("\n" + prompt + " [Y/N] ");
            response = pipe.nextLine();
            if(response.equalsIgnoreCase("Y")) {
                gotAVal = true;
                retVal = true;
            } else if(response.equalsIgnoreCase("N")) {
                gotAVal = true;
                retVal = false;
            } else {
                System.out.println("You must answer [Y/N]! " + response);
            }
        } while(!gotAVal);

        return retVal;
    }
}
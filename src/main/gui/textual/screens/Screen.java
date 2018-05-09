package main.gui.textual.screens;
import main.SystemController;
import main.utility.InputParserUtility;
import java.util.Scanner;
import static main.utility.notifications.Notifications.*;

/**
 * Class that implements a generic screen.
 *
 * @author Manuel Gallina
 */
public class Screen {

    private Scanner scanner;
    private SystemController controller;
    static final String ESCAPE_STRING = "!quit";
    static final String YES = "y";
    static final String NO = "n";
    static final String YN_REGEX = "[yYnN]";

    /**
     * Constructor for the Screen class, initializing a {@link Scanner} and assigning a controller.
     *
     * @param controller The system controller.
     */
    Screen(SystemController controller) {
        scanner = new Scanner(System.in);
        this.controller = controller;
    }

    /**
     * Getter for the {@link Scanner} from which all keyboard input is taken.
     *
     * @return The application scanner.
     */
    Scanner getScanner() {
        return scanner;
    }

    /**
     * Getter for the system controller.
     *
     * @return The system controller.
     */
    public SystemController getController() {
        return controller;
    }

    /**
     * Loops a scanner until the inserted {@code String} is a valid first name (or last name).
     * <p>The parameters and the logic for name validity are defined in the {@code isValidName()} method of the
     * {@link InputParserUtility} class.
     *
     * @return a valid name in the form of a {@code String}.
     */
    String insertName() {
        String name = getScanner().nextLine();

        while(!InputParserUtility.isValidName(name)) {
            System.out.println(ERR_INVALID_NAME);
            name = getScanner().nextLine();
        }

        return name;
    }

    /**
     * Loops a scanner until the inserted {@code String} is a valid year.
     * <p>The parameters and the logic for year validity are defined in the {@code isValidYear()} method of the
     * {@link InputParserUtility} class.
     *
     * @return a valid year in the form of an {@code int}.
     */
    int insertYear() {
        String year = getScanner().nextLine();

        while(!InputParserUtility.isValidYear(year)) {
            System.out.println(ERR_INVALID_YEAR);
            year = getScanner().nextLine();
        }

        return Integer.parseInt(year);
    }

    /**
     * Loops a scanner until the inserted {@code String} represents a valid integer.
     * <p>The parameters and the logic for integer validity are defined in the {@code isValidInteger()} method of the
     * {@link InputParserUtility} class.
     *
     * @return A valid int.
     */
    int insertInteger() {
        String integer = getScanner().nextLine();

        while(!InputParserUtility.isValidInteger(integer)) {
            System.out.println(ERR_MSG_INVALID_INPUT);
            integer = getScanner().nextLine();
        }

        return Integer.parseInt(integer);
    }

    /**
     * Loops a scanner until the inserted {@code String} represents a valid integer between {@code lowerBound} and
     * {@code upperBound}.
     * <p>The parameters and the logic for integer validity are defined in the {@code isValidInteger()} method of the
     * {@link InputParserUtility} class.
     *
     * @param lowerBound The lower bound, inclusive.
     * @param upperBound The upper bound, exclusive.
     * @return A valid int.
     */
    int insertInteger(int lowerBound, int upperBound) {
        String integer = getScanner().nextLine();

        while(!InputParserUtility.isValidInteger(integer, lowerBound, upperBound)) {
            System.out.println(ERR_MSG_INVALID_INPUT);
            integer = getScanner().nextLine();
        }

        return Integer.parseInt(integer);
    }

    /**
     * Loops a scanner until the inserted {@code String} is a valid birth date.
     * <p>The parameters and the logic for date validity are defined in the {@code isValidDate()} method of the
     * {@link InputParserUtility} class.
     *
     * @return a valid birth date in the form of a {@code String}.
     */
    String insertDate() {
        String date = getScanner().nextLine();

        while(!InputParserUtility.isValidDate(date)) {
            System.out.println(ERR_INVALID_DATE);
            date = getScanner().nextLine();
        }

        return date;
    }

    /**
     * Loops a scanner until the inserted {@code String} matches the passed parameter.
     *
     * @param toMatch The {@code String} on which the matching algorithm is based.
     * @return The matching {@code String}.
     */
    String insertString(String toMatch) {
        String out;

        do {
            out = getScanner().nextLine();
        } while(!out.matches(toMatch));

        return out;
    }
}

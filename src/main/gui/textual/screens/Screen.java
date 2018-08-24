package main.gui.textual.screens;
import main.controller.*;
import main.utility.InputParserUtility;
import main.utility.notifications.Notifications;
import org.jetbrains.annotations.NotNull;
import java.util.Scanner;

/**
 * Class that implements a generic screen.
 *
 * @author Manuel Gallina
 */
public class Screen {

    private Scanner scanner;
    private UserController userController;
    private MediaController mediaController;
    private LoanController loanController;
    private FileSystemController fileSystemController;
    static final String ESCAPE_STRING = "!quit";
    static final String YES = "y";
    static final String NO = "n";
    static final String YN_REGEX = "[yYnN]";

    /**
     * Generic constructor for the Screen class, which initializes a Scanner for textual input.
     */
    public Screen() {
        scanner = new Scanner(System.in);
    }

    /**
     * Constructor for the Screen class, initializing a {@link Scanner} and assigning a controller.
     *
     * @param controllers The system controller(s).
     */
    @SafeVarargs
    <C extends Controller> Screen(@NotNull C... controllers) {
        boolean[] controllerSet = new boolean[3];
        for(C controller : controllers) {
            try {
                if(controller instanceof UserController && !controllerSet[0]) {
                    this.userController = (UserController) controller;
                    controllerSet[0] = true;
                }
                else if(controller instanceof MediaController && !controllerSet[1]) {
                    this.mediaController = (MediaController) controller;
                    controllerSet[1] = true;
                }
                else if(controller instanceof LoanController && !controllerSet[2]) {
                    this.loanController = (LoanController) controller;
                    controllerSet[2] = true;
                }
                else if(controller instanceof FileSystemController)
                    this.fileSystemController = (FileSystemController) controller;
            }
            catch(ClassCastException ccEX) {
                ccEX.printStackTrace();
            }
        }

        scanner = new Scanner(System.in);
    }

    /**
     * Getter for the user controller.
     * @return The user controller.
     */
    UserController getUserController() {
        return userController;
    }

//    public void setUserController(UserController userController) {
//        this.userController = userController;
//    }

    /**
     * Getter for the media controller.
     * @return The media controller.
     */
    MediaController getMediaController() {
        return mediaController;
    }

//    public void setMediaController(MediaController mediaController) {
//        this.mediaController = mediaController;
//    }

    /**
     * Getter for the loan controller.
     * @return The loan controller.
     */
    LoanController getLoanController() {
        return loanController;
    }

//    public void setLoanController(LoanController loanController) {
//        this.loanController = loanController;
//    }

//    public void setFileSystemController(FileSystemController fileSystemController) {
//        this.fileSystemController = fileSystemController;
//    }

    /**
     * Getter for the file system controller.
     * @return The file system controller.
     */
    FileSystemController getFileSystemController() {
        return fileSystemController;
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
     * Loops a scanner until the inserted {@code String} is a valid first name (or last name).
     * <p>The parameters and the logic for name validity are defined in the {@code isValidName()} method of the
     * {@link InputParserUtility} class.
     *
     * @return a valid name in the form of a {@code String}.
     */
    String insertName() {
        String name = getScanner().nextLine();

        while(!InputParserUtility.isValidName(name)) {
            System.out.println(Notifications.getMessage("ERR_INVALID_NAME"));
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
            System.out.println(Notifications.getMessage("ERR_INVALID_YEAR"));
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
            System.out.println(Notifications.getMessage("ERR_MSG_INVALID_INPUT"));
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
    @SuppressWarnings("all")
    int insertInteger(int lowerBound, int upperBound) {
        String integer = getScanner().nextLine();

        while(!InputParserUtility.isValidInteger(integer, lowerBound, upperBound)) {
            System.out.println(Notifications.getMessage("ERR_MSG_INVALID_INPUT"));
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
            System.out.println(Notifications.getMessage("ERR_INVALID_DATE"));
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

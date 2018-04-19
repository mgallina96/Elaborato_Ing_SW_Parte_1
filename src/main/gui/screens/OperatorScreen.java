package main.gui.screens;
import main.Controller;
import main.utility.InputParserUtility;
import java.util.logging.Logger;
import static main.utility.Notifications.*;

/**
 * The operator menu screen.
 *
 * @author Manuel Gallina, Giosuè Filippini, Alessandro Polcini
 */
public class OperatorScreen extends Screen {

    private static final String QUIT_STRING = "!quit";
    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Constructor for the OperatorScreen class. It boots up the operator section.
     * @param controller The system controller.
     */
    public OperatorScreen(Controller controller) {
        super(controller);
        boolean exitFromOperatorSection = false;

        while(!exitFromOperatorSection) {
            System.out.printf("%s\n%s\n%s\n", SEPARATOR, PROMPT_OPERATOR_CHOICES, SEPARATOR);

            String command;
            do {
                command = getScanner().nextLine();
            } while(!InputParserUtility.isValidInteger(command, 1, 6));

            switch(Integer.parseInt(command)) {
                case 1:
                    addMedia();
                    break;
                case 2:
                    removeMedia();
                    break;
                case 3:
                    System.out.printf("%s\n%s\n", MSG_MEDIA_LIST, getController().allMediaToString());
                    break;
                case 4:
                    System.out.printf("%s\n%s\n", MSG_USER_LIST, getController().allUsersToString());
                    break;
                case 5:
                    System.out.printf("%s\n", MSG_LOG_OUT);
                    exitFromOperatorSection = true;
                    getController().logout();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Allows the operator to add a media item (book, film, etc.) to the database after filling out all the fields
     * for that particular media item (title, author, genre, etc.).
     *
     */
    private void addMedia() {
        System.out.printf("%s\n%s\n", PROMPT_ADD_MEDIA, SEPARATOR);
        System.out.print(PROMPT_TITLE);
        String title = getScanner().nextLine();

        System.out.print(PROMPT_AUTHOR);
        String author = insertName();

        System.out.print(PROMPT_GENRE);
        String genre = getScanner().nextLine();

        System.out.print(PROMPT_PUBLICATION_YEAR);
        int year = insertYear();

        System.out.print(PROMPT_PUBLISHER_NAME);
        String publisherName = insertName();

        String path = chooseFolder();

        getController().addMediaToDatabase(title, author, genre, year, publisherName, path);

        System.out.println(MSG_ADD_SUCCESSFUL);
    }

    private String chooseFolder() {
        System.out.println(PROMPT_SELECT_PATH);
        //getController().getPathList();

        String path = getScanner().nextLine();
        boolean present = getController().pathIsPresent(path);
        boolean valid = InputParserUtility.isValidPathFormat(path);

        while(!(present && valid)) {
            if(!valid)
                System.out.println(ERR_INVALID_PATH);
            else
                System.out.println(ERR_PATH_NOT_PRESENT);

            path = getScanner().nextLine();

            present = getController().pathIsPresent(path);
            valid = InputParserUtility.isValidPathFormat(path);
        }


        return path;
    }

    /**
     * Allows the operator to search for a media item by inputting keywords to narrow down the search.
     * Once the user has located the media item to remove, this method asks for the numeric "item ID" in order to remove
     * the desired item from the database.
     */
    private void removeMedia() {
        System.out.println(PROMPT_REMOVE_MEDIA);
        String input = getScanner().nextLine();

        if(input.equals(QUIT_STRING)) {
            System.out.println(MSG_ABORT);
            return;
        }

        String output = getController().allFilteredMediaList(input);

        if(output.length() > 0) {
            System.out.printf("%s\n%s\n", MSG_FILTERED_MEDIA_LIST, output);

            System.out.println(PROMPT_REMOVE_MEDIA_ID);
            int id = insertInteger();
            while(!getController().mediaIsPresent(id)) {
                System.out.println(ERR_MEDIA_NOT_PRESENT);
                id = insertInteger();
            }

            getController().removeMediaFromDatabase(id);
            System.out.println(MSG_REMOVE_SUCCESSFUL);
        }
        else {
            System.out.println(ERR_FILTERED_MEDIA_LIST_EMPTY);
        }
    }

    /**
     * Loops a scanner until the inserted {@code String} is a valid person name.
     * <p>The parameters and the logic for name validity are defined in the {@code isValidName()} method of the
     * {@link InputParserUtility} class.
     *
     * @return a valid name in the form of a {@code String}.
     */
    private String insertName() {
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
    private int insertYear() {
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
     * @return a valid int.
     */
    private int insertInteger() {
        String integer = getScanner().nextLine();

        while(!InputParserUtility.isValidInteger(integer)) {
            System.out.println(ERR_MSG_INVALID_INPUT);
            integer = getScanner().nextLine();
        }

        return Integer.parseInt(integer);
    }


}

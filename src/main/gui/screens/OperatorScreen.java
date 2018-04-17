package main.gui.screens;
import main.Controller;
import main.utility.InputParserUtility;
import java.util.logging.Logger;
import static main.utility.Notifications.*;

/**
 * The operator menu screen.
 *
 * @author Manuel Gallina
 */
public class OperatorScreen extends Screen {

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
                    //Aggiungi risorsa
                    addMedia();
                    break;
                case 2:
                    //Rimuovi risorsa
                    removeMedia();
                    break;
                case 3:
                    //Visualizza risorse
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

        getController().addMediaToDatabase(title, author, genre, year, publisherName);
    }

    private void removeMedia() {
        System.out.println(PROMPT_REMOVE_MEDIA);

        getController().removeMediaFromDatabase(1);
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


}

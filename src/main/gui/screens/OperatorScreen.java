package main.gui.screens;

import main.SystemController;
import main.model.database.filesystem.FileSystem;
import main.utility.InputParserUtility;

import static main.utility.notifications.Notifications.*;

/**
 * The operator menu screen.
 *
 * @author Manuel Gallina, Giosuè Filippini, Alessandro Polcini
 * @since version 0.1
 */
public class OperatorScreen extends Screen {

    private static final String QUIT_STRING = "!quit";

    /**
     * Constructor for the OperatorScreen class.
     * <p>
     * Boots up the operator section.
     * @param controller The system controller.
     */
    public OperatorScreen(SystemController controller) {
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
     * Allows the operator to add a media item (book, film, etc.) to the database after filling all the fields
     * (title, author, genre, etc.) for that particular media item.
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
    }

    private String chooseFolder() {
        System.out.printf("%s\n%s\n", SEPARATOR, PROMPT_SELECT_PATH);
        String path;

        int depth = 0;
        int currentID = getController().getRootID();

        do {
            if(depth > 0)
                currentID = insertInteger();
            System.out.println(getController().getFoldersByDepth(depth++, currentID));
        } while(getController().folderHasChildren(currentID));

        path = getController().getPathToString(currentID);

        System.out.printf("%s\"%s\"\n", MSG_ADD_SUCCESSFUL, path);

        return path;
    }

    /**
     * Allows the operator to search for a media item by inputting keywords to narrow down the search.
     * Once the user has located the media item they wish to remove, this method asks the user to insert the numeric ID
     * associated with that media item. If that ID is present in the database and has a valid format, the associated
     * media item is removed from the database.
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

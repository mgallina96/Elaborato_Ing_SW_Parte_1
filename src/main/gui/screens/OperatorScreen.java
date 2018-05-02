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

            switch(insertInteger(1, 6)) {
                case 1:
                    addMedia();
                    break;
                case 2:
                    removeMedia();
                    break;
                case 3:
                    printFilteredMedia();
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

        System.out.printf("%s\n%s\n", SEPARATOR, PROMPT_SELECT_PATH);
        String path = chooseFolder();

        if(!getController().addMediaToDatabase(title, author, genre, year, publisherName, path))
            System.out.printf("%s\n%s\n", ERR_MEDIA_ALREADY_PRESENT, MSG_ABORT);
        else
            System.out.printf("%s\"%s\"\n", MSG_ADD_SUCCESSFUL, path);
    }

    /**
     * Prints the filtered list of {@code Media} items that are located in the chosen folder.
     */
    private void printFilteredMedia() {
        System.out.printf("%s\n%s\n", PROMPT_WHICH_FOLDER, SEPARATOR);
        String path = chooseFolder();

        System.out.printf("%s\"%s\":\n%s\n", MSG_FOLDER_CONTENTS, path, getController().getFolderContents(path));
    }

    /**
     * Allows the operator to choose a path step by step, by printing the selected sub-folders until the desired one is
     * reached.
     *
     * @return A {@code String} representing the path of the desired folder.
     */
    private String chooseFolder() {
        long currentID = getController().getRootID();

        while(getController().folderHasChildren(currentID)) {
            System.out.println(getController().getSubFolders(currentID));
            currentID = insertInteger();
        }

        return getController().getPathToString(currentID);
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

        if(input.equals(ESCAPE_STRING)) {
            System.out.println(MSG_ABORT);
            return;
        }

        String output = getController().allFilteredMediaList(input);

        if(output.length() > 0) {
            System.out.printf("%s\n%s\n", MSG_FILTERED_MEDIA_LIST, output);
            remove();
        }
        else
            System.out.println(ERR_FILTERED_MEDIA_LIST_EMPTY);
    }

    private void remove() {
        System.out.println(PROMPT_REMOVE_MEDIA_ID);

        int id = insertInteger();
        while(!getController().mediaIsPresent(id)) {
            System.out.println(ERR_MEDIA_NOT_PRESENT);
            id = insertInteger();
        }

        System.out.println(PROMPT_REMOVE_CONFIRMATION);

        if(insertString(YN_REGEX).equalsIgnoreCase(YES)) {
            getController().removeMediaFromDatabase(id);
            System.out.println(MSG_REMOVE_SUCCESSFUL);
        }
        else
            System.out.println(MSG_ABORT);
    }

}

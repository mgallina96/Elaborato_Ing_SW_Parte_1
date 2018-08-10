package main.gui.textual.screens;

import main.controller.FileSystemController;
import main.controller.LoanController;
import main.controller.MediaController;
import main.controller.UserController;
import main.utility.notifications.Notifications;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
     * @param userController The user controller.
     */
    public OperatorScreen(UserController userController, MediaController mediaController, LoanController loanController, FileSystemController fileSystemController) {
        super(userController, mediaController, loanController, fileSystemController);
        boolean exitFromOperatorSection = false;

        while(!exitFromOperatorSection) {
            System.out.printf("%s%n%s%n%s%n", Notifications.getMessage("SEPARATOR"), Notifications.getMessage("PROMPT_OPERATOR_CHOICES"), Notifications.getMessage("SEPARATOR"));

            switch(insertInteger(1, 7)) {
                case 1:
                    addMedia();
                    break;
                case 2:
                    if(searchForMedia())
                        remove();
                    break;
                case 3:
                    printFilteredMediaByFolder();
                    break;
                case 4:
                    searchForMedia();
                    break;
                case 5:
                    visualizeData();
                    break;
                case 6:
                    System.out.printf("%s%n", Notifications.getMessage("MSG_LOG_OUT"));
                    exitFromOperatorSection = true;
                    userController.logout();
                    break;
                default:
                    break;
            }
        }
    }

    private void visualizeData() {
        System.out.printf("%s%n%s%n", Notifications.getMessage("PROMPT_DATA_VISUALIZATION_CHOICES"), Notifications.getMessage("SEPARATOR"));

        switch(insertInteger(1, 7)) {
            case 1:
                System.out.printf("%s%n%s%n", Notifications.getMessage("MSG_USER_LIST"), getUserController().allUsersToString());
                break;
            case 2:
                System.out.printf("%s%n%s%n", Notifications.getMessage("MSG_LOAN_LIST_ALL"), getLoanController().allLoansToString());
                break;
            case 3:
                System.out.printf("%s", Notifications.getMessage("MSG_CHOOSE_YEAR"));
                System.out.println(
                        getLoanController().getLoanNumberByYear(insertYear(), new GregorianCalendar().get(Calendar.YEAR))
                );
                break;
            case 4:
                System.out.printf("%s", Notifications.getMessage("MSG_CHOOSE_YEAR"));
                System.out.println(
                        getLoanController().getUserLoanNumberByYear(insertYear(), new GregorianCalendar().get(Calendar.YEAR))
                );
                break;
            case 5:
                System.out.printf("%s", Notifications.getMessage("MSG_CHOOSE_YEAR"));
                System.out.println(
                        getLoanController().getExtensionNumberByYear(insertYear(), new GregorianCalendar().get(Calendar.YEAR))
                );
                break;
            case 6:
                System.out.printf("%s", Notifications.getMessage("MSG_CHOOSE_YEAR"));
                System.out.println(
                        getLoanController().getMostLentMediaByYear(insertYear(), new GregorianCalendar().get(Calendar.YEAR))
                );
                break;
        }
    }

    /**
     * Allows the operator to add a media item (book, film, etc.) to the database after filling all the fields
     * (title, author, genre, etc.) for that particular media item.
     */
    private void addMedia() {
        System.out.printf("%s%n%s%n", Notifications.getMessage("PROMPT_ADD_MEDIA"), Notifications.getMessage("SEPARATOR"));
        System.out.print(Notifications.getMessage("PROMPT_TITLE"));
        String title = getScanner().nextLine();

        System.out.print(Notifications.getMessage("PROMPT_AUTHOR"));
        String author = insertName();

        System.out.print(Notifications.getMessage("PROMPT_GENRE"));
        String genre = getScanner().nextLine();

        System.out.print(Notifications.getMessage("PROMPT_PUBLICATION_YEAR"));
        int year = insertYear();

        System.out.print(Notifications.getMessage("PROMPT_PUBLISHER_NAME"));
        String publisherName = insertName();

        System.out.printf("%s%n%s%n", Notifications.getMessage("SEPARATOR"), Notifications.getMessage("PROMPT_SELECT_PATH"));
        String path = chooseFolder();

        if(!getMediaController().addMediaToDatabase(title, author, genre, year, publisherName, path))
            System.out.printf("%s%n%s%n", Notifications.getMessage("ERR_MEDIA_ALREADY_PRESENT"), Notifications.getMessage("MSG_ABORT"));
        else
            System.out.printf("%s\"%s\"%n", Notifications.getMessage("MSG_ADD_SUCCESSFUL"), path);
    }

    /**
     * Prints the filtered list of {@code Media} items that are located in the chosen folder.
     */
    private void printFilteredMediaByFolder() {
        System.out.printf("%s%n%s%n", Notifications.getMessage("PROMPT_WHICH_FOLDER"), Notifications.getMessage("SEPARATOR"));
        String path = chooseFolder();

        System.out.printf("%s\"%s\":%n%s%n", Notifications.getMessage("MSG_FOLDER_CONTENTS"), path, getMediaController().getFolderContents(path));
    }

    /**
     * Allows the operator to choose a path step by step, by printing the selected sub-folders until the desired one is
     * reached.
     *
     * @return A {@code String} representing the path of the desired folder.
     */
    private String chooseFolder() {
        int currentID = getFileSystemController().getRootID();

        while(getFileSystemController().folderHasChildren(currentID)) {
            System.out.println(getFileSystemController().getSubFolders(currentID));
            currentID = insertInteger();
        }

        return getFileSystemController().getPathToString(currentID);
    }

    /**
     * Allows the operator to search for a media item by inputting keywords to narrow down the search.
     * Once the user has located the media item they wish to remove, this method asks the user to insert the numeric ID
     * associated with that media item. If that ID is present in the database and has a valid format, the associated
     * media item is removed from the database.
     */
    private boolean searchForMedia() {
        System.out.println(Notifications.getMessage("PROMPT_REMOVE_MEDIA"));
        String input = getScanner().nextLine();

        if(input.equals(ESCAPE_STRING)) {
            System.out.println(Notifications.getMessage("MSG_ABORT"));
            return false;
        }

        String output = getMediaController().allFilteredMediaList(input);

        if(output.length() > 0) {
            System.out.printf("%s%n%s%n", Notifications.getMessage("MSG_FILTERED_MEDIA_LIST"), output);
            return true;
        }
        else
            System.out.println(Notifications.getMessage("ERR_FILTERED_MEDIA_LIST_EMPTY"));

        return false;
    }

    private void remove() {
        System.out.println(Notifications.getMessage("PROMPT_REMOVE_MEDIA_ID"));

        int id = insertInteger();
        while(!getMediaController().mediaIsPresent(id)) {
            System.out.println(Notifications.getMessage("ERR_MEDIA_NOT_PRESENT"));
            id = insertInteger();
        }

        System.out.println(Notifications.getMessage("PROMPT_REMOVE_CONFIRMATION"));

        if(insertString(YN_REGEX).equalsIgnoreCase(YES)) {
            getMediaController().removeMediaFromDatabase(id);
            System.out.println(Notifications.getMessage("MSG_REMOVE_SUCCESSFUL"));
        }
        else
            System.out.println(Notifications.getMessage("MSG_ABORT"));
    }
}

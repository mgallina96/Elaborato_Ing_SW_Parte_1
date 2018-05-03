package main;
import java.util.GregorianCalendar;

/**
 * Interface for the system controller.
 *
 * @author Manuel Gallina
 */
public interface SystemController {

    /** Initializes the system controller, which starts the GUI. */
    void init();

    /**
     * Renews the user's subscription and returns a boolean value to tell the program whether the renewal was successful
     * or not.
     * <p>
     * The user must be a customer.
     *
     * @return a {@code boolean} value: {@code true} if the user can renew their subscription, {@code false} otherwise.
     * @throws IllegalArgumentException if the current user is not a customer.
     */
    boolean renewSubscription();

    /** Checks whether a given user is allowed to renew their subscription. */
    boolean canRenew();

    /**
     * Converts the subscription and expiry dates into a {@code String}.
     *
     * @return a {@code String} containing the subscription and expiry dates.
     */
    String dateDetails();

    /** Allows the current user to log out of the system. */
    void logout();

    /**
     * Checks whether a customer is of age or not.
     *
     * @param birthday The birthday to be checked.
     * @return {@code true} if the customer is of age, {@code false} otherwise.
     */
    boolean legalAge(GregorianCalendar birthday);

    /**
     * Returns the amount of days the user has left to renew their subscription.
     *
     * @param username The user's username.
     * @return An {@code integer} value representing the days the user has left.
     */
    int daysLeftToRenew(String username);

    /**
     * Checks whether the given pair <{@code username, password}> can be found in the database. If so, the user with
     * those credentials becomes the "current user".
     *
     * @param username The username.
     * @param password The password.
     * @return {@code true} if the user's credentials are correct and match with an entry in the database,
     * {@code false} otherwise.
     */
    boolean checkUserLogin(String username, String password);

    /**
     * Checks whether the given username matches an entry in the database.
     *
     * @param username The username to be checked.
     * @return A boolean value, {@code true} if the given user can be found in the database, {@code false} otherwise.
     */
    boolean userIsPresent(String username);

    /**
     * Checks if the media with the given ID is present in the database.
     *
     * @param id The ID of the media to be checked.
     * @return {@code true} if the media is present in the database, {@code false} otherwise.
     */
    boolean mediaIsPresent(int id);

    /**
     * Adds a new user to the database and saves all changes made to the database..
     *
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param username The user's username.
     * @param password The user's password.
     * @param birthday The user's birthday.
     */
    boolean addUserToDatabase(String firstName, String lastName, String username, String password, GregorianCalendar birthday);

    /**
     * Adds a new media item to the database and saves all changes made to the database.
     *
     * @param title the title.
     * @param author the author.
     * @param genre the genre.
     * @param publicationYear the publication year.
     * @param publisherName the publisher's name.
     */
    boolean addMediaToDatabase(String title, String author, String genre, int publicationYear, String publisherName, String path);

    //boolean pathIsPresent(String path);

    /**
     * Removes the media element associated with the given ID from the database and saves all changes made to the
     * database.
     *
     * @param id The ID of the media element to be removed.
     */
    void removeMediaFromDatabase(int id);

    /**
     * Getter for the User status (CUSTOMER or OPERATOR).
     *
     * @param username The user's username.
     * @return an {@code int} value, 0 (CUSTOMER) or 1 (OPERATOR).
     */
    int getUserStatus(String username);

    /**
     * Returns a {@code String} that contains all the users in the database.
     *
     * @return the list of all users as a {@code String}.
     */
    String allUsersToString();

/*    /**
     * Returns a {@code String} that contains all media items in the database.
     *
     * @return the list of all media items as a {@code String}.
     */
//    String allMediaToString();

    /**
     * Returns a {@code String} that contains all the media items that match a certain filter.
     *
     * @return the list of all filtered media items as a {@code String}.
     */
    String allFilteredMediaList(String filter);

    /**
     * Checks whether the folder associated to the given ID has children.
     *
     * @param folderID The ID associated to the folder to be checked.
     * @return A boolean value: {@code true} if the given folder has children, {@code false} otherwise.
     */
    boolean folderHasChildren(int folderID);

    /**
     * Searches the file system for the folder with the given ID and returns its children in the format of a {@code
     * String}.
     *
     * @param parentID The ID of the desired parent folder.
     * @return A {@code String} containing all sub-folders of the desired parent folder.
     */
    String getSubFolders(int parentID);

    /**
     * Getter for the ROOT folder ID.
     *
     * @return The ID's {@code Long} value.
     */
    int getRootID();

    /**
     * Returns the path of the folder associated with the given ID.
     *
     * @param folderID The folder ID.
     * @return A {@code String} containing the full path of that folder.
     */
    String getPathToString(int folderID);

    /**
     * Returns the contents of the folder that matches the given path.
     *
     * @param folderPath The path to look for.
     * @return A {@code String} with all the contents of that folder.
     */
    String getFolderContents(String folderPath);

}

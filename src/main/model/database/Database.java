package main.model.database;
import main.model.media.Media;
import main.model.user.User;

/**
 * Interface whose primary task is to control and manage the interaction between different kinds of databases
 * (user database and media database for now).
 *
 * @author Manuel Gallina, Alessandro Polcini
 */
public interface Database {

    /**
     * Adds a new user to the database.
     *
     * @param toAdd The {@code User} to be added to the database.
     */
    void add(User toAdd);

    /**
     * Adds a new piece of media to the database.
     *
     * @param toAdd The {@code Media} item to be added to the database.
     */
    void add(Media toAdd);

    /**
     * Returns the given user (if present).
     *
     * @param toFetch The user to be found and returned.
     * @return The user or {@code null} if that user can't be found in the database.
     */
    User fetch(User toFetch);

    /**
     * Returns the given media item (if present).
     *
     * @param toFetch The media item to be found and returned.
     * @return The piece of media or {@code null} if that piece of media can't be found in the database.
     */
    Media fetch(Media toFetch);

    /**
     * Returns a {@code String} that contains a brief description for every user in the database, according to the
     * {@code toString} method found in the {@link User} class.
     *
     * @return The list of all users as a {@code String}.
     */
    String getUserListString();

    /**
     * Returns a {@code String} that contains a brief description for every piece of media in the database, according
     * to the {@code toString} method found in the {@link Media} class.
     *
     * @return The list of all media items as a {@code String}.
     */
    String getMediaListString();

    /**
     * Returns a list that contains a brief description for every piece of media in the database that matches a
     * specific input. The logic for the filtering can be found in the {@code getFilteredMediaList} method of the
     * {@link MediaDatabase} class.
     *
     * @param filter The filter to apply.
     * @return The filtered list of media items.
     */
    String getFilteredMediaList(String filter);

    /**
     * Sets the current user who just logged in.
     *
     * @param currentUser The logged-in user to set.
     */
    void setCurrentUser(User currentUser);

    /**
     * Removes the current user browsing the program by setting its value to {@code null}: this means that no user is
     * active in the system at that particular moment.
     */
    void removeCurrentUser();

    /**
     * Getter for the current user.
     *
     * @return the current user who just logged in.
     */
    User getCurrentUser();

    /**
     * Checks whether the given user is present in the database.
     *
     * @param toFind The user to be found.
     * @return A boolean value: {@code true} if the user is present in the database, {@code false} otherwise.
     */
    boolean isPresent(User toFind);

    /**
     * Checks whether the given piece of media is present in the database.
     *
     * @param toFind The media item to be found.
     * @return A boolean value: {@code true} if the media item is present in the database, {@code false} otherwise.
     */
    boolean isPresent(Media toFind);

    /**
     * Saves:
     * <p>
     * - a {@code HashMap} containing all subscribed users;<p>
     * - a {@code HashMap} containing all media files;<p>
     * - an {@code integer} to keep track of the media item IDs
     * <p>
     * to a {@code .ser} file.
     *
     */
    void saveDatabase();

    void remove(Media toRemove);

}

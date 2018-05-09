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
     * Adds a new user to the database and saves the user database afterwards.
     *
     * @param toAdd The {@code User} to be added to the database.
     */
    void add(User toAdd);

    /**
     * Adds a new piece of media to the database and saves the media database afterwards.
     *
     * @param toAdd The {@code Media} item to be added to the database.
     * @param path The path the {@code Media} item is stored in.
     */
    void add(Media toAdd, String path);

    /**
     * Adds a new entry to the loan database containing both the current user and the media item that has just been lent.
     *
     * @param toLend The media to be lent to that user.
     */
    void add(Media toLend);

    /**
     * Removes a media item from the database.
     *
     * @param toRemove The {@code Media} item to remove.
     */
    void remove(Media toRemove);

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
     * Returns a {@code String} that contains a brief description for every loan that has been granted to each user
     * in the database.
     *
     * @return The list of all loans as a {@code String}.
     */
    String getLoanListString();

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
     * Checks whether the given media item has the same details (title, author, genre, etc.) as another media item
     * in the database.
     *
     * @param toFind The media item to be checked.
     * @return {@code true} if there is a match, {@code false} otherwise.
     */
    boolean isMatchingMedia(Media toFind);

    /**
     * Returns the contents of the folder that matches the given path.
     *
     * @param folderPath The path to look for.
     * @return A {@code String} with all the contents of that folder.
     */
    String getFolderContents(String folderPath);

}

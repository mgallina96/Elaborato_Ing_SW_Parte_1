package main;
import main.utility.exceptions.UserNotFoundException;
import main.utility.exceptions.WrongPasswordException;

import java.util.GregorianCalendar;

/**
 * Interface for the system controller.
 *
 * @author Manuel Gallina
 */
public interface SystemController {

    /** Initializes the system controller, which starts the GUI. */
    void init();

}

/**
 * Interface whose primary task is to control and manage the interaction between different kinds of databases
 * (user database and media database for now).
 *
 * @author Manuel Gallina, Alessandro Polcini
 */
/**
 * Adds a new user to the database and saves the user database afterwards.
 *
 * @param toAdd The {@code User} to be added to the database.
 */
/**
 * Adds a new piece of media to the database and saves the media database afterwards.
 *
 * @param toAdd The {@code Media} item to be added to the database.
 * @param path The path the {@code Media} item is stored in.
 */
/**
 * Adds a new entry to the loan database containing both the current user and the media item that has just been lent.
 *
 * @param toLend The media to be lent to that user.
 */
/**
 * Removes a media item from the database.
 *
 * @param toRemove The {@code Media} item to remove.
 */
/**
 * Returns the given user (if present).
 *
 * @param toFetch The user to be found and returned.
 * @return The user or {@code null} if that user can't be found in the database.
 */
/**
 * Returns the given media item (if present).
 *
 * @param toFetch The media item to be found and returned.
 * @return The piece of media or {@code null} if that piece of media can't be found in the database.
 */
/**
 *
 * @param media
 * @return
 */
/**
 * Returns a {@code String} that contains a brief description for every user in the database, according to the
 * {@code toString} method found in the {@link main.model.user.User} class.
 *
 * @return The list of all users as a {@code String}.
 */
/**
 * Returns a {@code String} that contains a brief description for every loan that has been granted to each user
 * in the database.
 *
 * @return The list of all loans as a {@code String}.
 */
/**
 * Returns a list that contains a brief description for every piece of media in the database that matches a
 * specific input. The logic for the filtering can be found in the {@code getFilteredMediaList} method of the
 * {@link main.model.database.MediaDatabase} class.
 *
 * @param filter The filter to apply.
 * @return The filtered list of media items.
 */
/**
 * Sets the current user who just logged in.
 *
 * @param currentUser The logged-in user to set.
 */
/**
 * Removes the current user browsing the program by setting its value to {@code null}: this means that no user is
 * active in the system at that particular moment.
 */
/**
 * Getter for the current user.
 *
 * @return the current user who just logged in.
 */
/**
 * Checks whether the given user is present in the database.
 *
 * @param toFind The user to be found.
 * @return A boolean value: {@code true} if the user is present in the database, {@code false} otherwise.
 */
/**
 * Checks whether the given piece of media is present in the database.
 *
 * @param toFind The media item to be found.
 * @return A boolean value: {@code true} if the media item is present in the database, {@code false} otherwise.
 */
/**
 * Checks whether the given media item has the same details (title, author, genre, etc.) as another media item
 * in the database.
 *
 * @param toFind The media item to be checked.
 * @return {@code true} if there is a match, {@code false} otherwise.
 */
/**
 * Returns the contents of the folder that matches the given path.
 *
 * @param folderPath The path to look for.
 * @return A {@code String} with all the contents of that folder.
 */
package main.model.database;
import main.model.media.Media;
import main.model.user.User;
import main.model.user.UserStatus;
import main.utility.Notifications;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database singleton class. This class manages a simple Database in form of a {@link HashMap}.
 * <p>
 * This class provides methods for adding elements to or removing elements from the database, searching through the
 * database for a single element and checking whether a given element belongs to the database.
 *
 */
public class DatabaseManager implements Serializable, Database {

    /**
     * Unique serial ID for the {@link DatabaseManager} class. DO NOT CHANGE, otherwise the database can't be read properly.
     */
    private static final long serialVersionUID = -5681383377098150051L;

    private UserDatabase userDatabase = UserDatabase.getInstance();
    private MediaDatabase mediaDatabase = MediaDatabase.getInstance();
    private static final String DATABASE_FILE_NAME = "Biblioteca SMARTINATOR - Database.ser";
    private static DatabaseManager database;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    //Singleton Database constructor, private to prevent instantiation.
    private DatabaseManager() {
        loadDatabase();
    }

    /**
     * Getter/initializer for the Singleton Database class.
     * <p>
     * This method builds a single instance of Database the first time it is called. Calling it more than once doesn't
     * change anything.
     *
     * @return either a new {@code Database} instance, if the {@code Database} has not been initialized yet, or the
     * already initialized {@code Database} object.
     */
    public static DatabaseManager getInstance() {
        if(database == null)
            database = new DatabaseManager();

        return database;
    }

    /**
     * Adds a new user to the database.
     *
     * @param toAdd The {@code User} who is to be added to the database.
     */
    public void add(User toAdd) {
        userDatabase.addUser(toAdd);
    }

    public void add(Media toAdd) {
        mediaDatabase.addMedia(toAdd);
    }

    /**
     * Removes an already existing user from the database.
     *
     * @param toRemove The {@code User} who is to be removed.
     */
    public void remove(User toRemove) {
        userDatabase.removeUser(toRemove);
    }

    @Override
    public void remove(Media toRemove) {
        mediaDatabase.removeMedia(toRemove);
    }

    /**
     * Checks whether the given user is present in the database.
     *
     * @param toFind The user to be found.
     * @return {@code true} if the user is present in the database, {@code false} otherwise.
     */
    @Override
    public boolean isPresent(User toFind) {
        return userDatabase.isPresent(toFind);
    }

    @Override
    public boolean isPresent(Media toFind) {
        return mediaDatabase.isPresent(toFind);
    }

    /**
     * Returns the given user (if present).
     *
     * @param toFetch The user to be found.
     * @return the user or {@code null} if that user can't be found in the database.
     */
    @Override
    public User fetch(User toFetch) {
        return userDatabase.fetchUser(toFetch);
    }

    @Override
    public Media fetch(Media toFetch) {
        return mediaDatabase.fetch(toFetch);
    }

    /**
     * Sets the current user who just logged in.
     *
     * @param currentUser The logged-in user to set.
     */
    public void setCurrentUser(User currentUser) {
        userDatabase.setCurrentUser(currentUser);
    }

    /**
     * Getter for the current user.
     *
     * @return the current user who just logged in.
     */
    public User getCurrentUser() {
        return userDatabase.getCurrentUser();
    }

    /**
     * Returns a {@code String} that contains all the users in the database.
     *
     * @return the list of all users as a {@code String}.
     */
    public String getUserListString() {
        return userDatabase.getUserListString();
    }

    @Override
    public String getMediaListString() {
        return mediaDatabase.getMediaListString();
    }

    /**
     * Sets the current user to {@code null}: this means that no user is active in the system at that moment.
     */
    public void removeCurrentUser() { userDatabase.removeCurrentUser(); }

    /**
     * Loads an existing database of users into this class' HashMap.
     */
    private void loadDatabase() {
        try {
            FileInputStream fileIn = new FileInputStream(DATABASE_FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            userDatabase.setUserList((HashMap<String, User>) in.readObject());
            mediaDatabase.setMediaList((HashMap<String, Media>) in.readObject());

            in.close();
            fileIn.close();
        }
        catch(FileNotFoundException FNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_FILE_NOT_FOUND);
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_LOADING_DATABASE);
        }
        catch(ClassNotFoundException CNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_DATABASE_CLASS_NOT_FOUND);
        }
    }

    /**
     * Saves the {@code HashMap} containing all subscribed users to a {@code .ser} file.
     *
     */
    public void saveDatabase() {
        try {
            FileOutputStream fileOut = new FileOutputStream(DATABASE_FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(userDatabase.getUserList());
            out.writeObject(mediaDatabase.getMediaList());

            out.close();
            fileOut.close();
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_SAVING_DATABASE, IOEx);
        }
    }
}

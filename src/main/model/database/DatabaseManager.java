package main.model.database;
import main.model.media.Media;
import main.model.user.User;
import main.utility.Notifications;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code DatabaseManager} class, which manages two different databases and provides methods for saving them to (and
 * loading them from) a .ser serializable file.
 * <p>
 * This class' methods are conformed to the {@link Database} interface's methods through interface implementation.
 *
 * @author Manuel Gallina, Alessandro Polcini
 */
public class DatabaseManager implements Serializable, Database {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5681383377098150051L;
    private static final String DATABASE_FILE_NAME = "Biblioteca SMARTINATOR - Database.ser";

    private static DatabaseManager database;
    private UserDatabase userDatabase;
    private MediaDatabase mediaDatabase;
    private Logger logger;

    //Singleton Database constructor, private to prevent instantiation.
    private DatabaseManager() {
        this.userDatabase = UserDatabase.getInstance();
        this.mediaDatabase = MediaDatabase.getInstance();
        this.logger = Logger.getLogger(this.getClass().getName());
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

    @Override
    public void add(User toAdd) {
        userDatabase.addUser(toAdd);
    }

    @Override
    public void add(Media toAdd) {
        mediaDatabase.addMedia(toAdd);
    }

    @Override
    public void remove(Media toRemove) {
        mediaDatabase.removeMedia(toRemove);
    }

    @Override
    public boolean isPresent(User toFind) {
        return userDatabase.isPresent(toFind);
    }

    @Override
    public boolean isPresent(Media toFind) {
        return mediaDatabase.isPresent(toFind);
    }

    @Override
    public User fetch(User toFetch) {
        return userDatabase.fetchUser(toFetch);
    }

    @Override
    public Media fetch(Media toFetch) {
        return mediaDatabase.fetch(toFetch);
    }

    @Override
    public void setCurrentUser(User currentUser) {
        userDatabase.setCurrentUser(currentUser);
    }

    @Override
    public User getCurrentUser() {
        return userDatabase.getCurrentUser();
    }

    @Override
    public String getUserListString() {
        return userDatabase.getUserListString();
    }

    @Override
    public String getMediaListString() {
        return mediaDatabase.getMediaListString();
    }

    @Override
    public void removeCurrentUser() {
        userDatabase.removeCurrentUser();
    }

    @Override
    public void saveDatabase() {
        try {
            FileOutputStream fileOut = new FileOutputStream(DATABASE_FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(userDatabase.getUserList());
            out.writeObject(mediaDatabase.getMediaList());
            out.writeObject(Integer.toString(MediaDatabase.getCounter()));

            out.close();
            fileOut.close();
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_SAVING_DATABASE, IOEx);
        }
    }

    /**
     * Opens a .ser serializable file and loads its contents into this program.<p>
     * This method loads a {@code HashMap} containing all subscribed users and a {@code HashMap} containing all
     * registered media items into the {@link UserDatabase} and the {@link MediaDatabase} respectively.
     */
    private void loadDatabase() {
        try {
            FileInputStream fileIn = new FileInputStream(DATABASE_FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            userDatabase.setUserList((HashMap<String, User>) in.readObject());
            mediaDatabase.setMediaList((HashMap<Integer, Media>) in.readObject());
            MediaDatabase.setCounter(Integer.parseInt((String)in.readObject()));

            in.close();
            fileIn.close();
        }
        catch(FileNotFoundException FNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_FILE_NOT_FOUND);
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_LOADING_DATABASE, IOEx);
        }
        catch(ClassNotFoundException CNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_DATABASE_CLASS_NOT_FOUND);
        }
    }
}

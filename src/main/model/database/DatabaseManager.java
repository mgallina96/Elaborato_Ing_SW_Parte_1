package main.model.database;
import main.model.media.Media;
import main.model.user.User;
import main.utility.notifications.Notifications;

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
    private static final String DATABASE_FILE_PATH = "application_resources\\Biblioteca SMARTINATOR - Database.ser";

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
     * @return Either a new {@code Database} instance, if the {@code Database} has not been initialized yet, or the
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
        saveDatabase();
    }

    @Override
    public void add(Media toAdd, String path) {
        mediaDatabase.addMedia(toAdd, path);
        saveDatabase();
    }

    @Override
    public void remove(Media toRemove) {
        mediaDatabase.removeMedia(toRemove);
        saveDatabase();
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
    public String getFilteredMediaList(String filter) {
        return mediaDatabase.getFilteredMediaList(filter);
    }

    @Override
    public String getFolderContents(String folderPath) {
        return mediaDatabase.getFolderContents(folderPath);
    }

    @Override
    public void removeCurrentUser() {
        userDatabase.removeCurrentUser();
    }

    /**
     * Saves:
     * <p>
     * - a {@code HashMap} containing all subscribed users;<p>
     * - a {@code HashMap} containing all media files;<p>
     * - an {@code integer} to keep track of the media item IDs
     * <p>
     * to a {@code .ser} file.
     */
    private void saveDatabase() {
        try {
            //to increase serializing speed
            RandomAccessFile raf = new RandomAccessFile(DATABASE_FILE_PATH, "rw");

            FileOutputStream fileOut = new FileOutputStream(raf.getFD());
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
     * This method loads:
     * <p>- a {@code HashMap} containing all subscribed users into the {@link UserDatabase} class;
     * <p>- a {@code HashMap} containing all registered media items into the {@link MediaDatabase} class;
     * <p>- an {@code integer} to keep track of the media item IDs into the {@link MediaDatabase} class.
     */
    @SuppressWarnings("unchecked")
    private void loadDatabase() {
        try {
            FileInputStream fileIn = new FileInputStream(DATABASE_FILE_PATH);
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
            logger.log(Level.SEVERE, Notifications.ERR_LOADING_DATABASE);
        }
        catch(ClassNotFoundException CNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_DATABASE_CLASS_NOT_FOUND);
        }
    }
}

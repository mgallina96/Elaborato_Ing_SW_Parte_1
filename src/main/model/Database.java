package main.model;
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
public class Database implements Serializable {

    /**
     * Unique serial ID for the {@link Database} class. DO NOT CHANGE, otherwise the database can't be read properly.
     */
    private static final long serialVersionUID = -5681383377098150051L;

    //A default admin user, added to the database whenever this class is instantiated.
    private static final User ADMIN = new User("admin", "admin");
    public static final String DATABASE_FILE_NAME = "Biblioteca SMARTINATOR - Database.ser";
    private static Database database;
    private User currentUser;
    private HashMap<String, User> userList;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    //Singleton Database constructor, private to prevent instantiation.
    private Database() {
        this.userList = new HashMap<>();
        loadDatabase();

        //Adds the admin to the database, if the admin hasn't been added yet.
        if(!isPresent(ADMIN)) {
            ADMIN.setUserStatus(UserStatus.OPERATOR);
            this.addUser(ADMIN);
        }
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
    public static Database getInstance() {
        if(database == null)
            database = new Database();

        return database;
    }

    /**
     * Adds a new user to the database.
     *
     * @param toAdd The {@code User} who is to be added to the database.
     */
    public void addUser(User toAdd) {
        userList.put(toAdd.getUsername(), toAdd);
    }

    /**
     * Removes an already existing user from the database.
     *
     * @param toRemove The {@code User} who is to be removed.
     */
    public void removeUser(User toRemove) {
        if(isPresent(toRemove))
            userList.remove(toRemove.getUsername());
    }

    /**
     * Returns the list of users in form of a HashMap.
     *
     * @return the actual database.
     */
    public HashMap<String, User> getUserList() {
        return userList;
    }

    /**
     * Checks whether the given user is present in the database.
     *
     * @param toFind The user to be found.
     * @return {@code true} if the user is present in the database, {@code false} otherwise.
     */
    public boolean isPresent(User toFind) {
        return userList.containsKey(toFind.getUsername());
    }

    /**
     * Returns the given user (if present).
     *
     * @param toFind The user to be found.
     * @return the user or {@code null} if that user can't be found in the database.
     */
    public User fetchUser(User toFind) {
        return isPresent(toFind) ? userList.get(toFind.getUsername()) : null;
    }

    /**
     * Loads an existing database of users into this class' HashMap.
     */
    private void loadDatabase() {
        try {
            FileInputStream fileIn = new FileInputStream(DATABASE_FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            userList = (HashMap<String, User>)in.readObject();

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
     * Saves the {@code HashMap} that contains all subscribed users to a {@code .ser} file.
     */
    public void saveDatabase() {
        try {
            FileOutputStream fileOut = new FileOutputStream(DATABASE_FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(userList);

            out.close();
            fileOut.close();
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_SAVING_DATABASE);
        }
    }

    /**
     * Sets the current user who just logged in.
     * @param currentUser The logged-in user to set.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = fetchUser(currentUser);
    }

    /**
     * Getter for the current user.
     *
     * @return the current user who just logged in.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Returns a {@code String} that contains all the users in the database.
     *
     * @return the list of all users as a {@code String}.
     */
    public String allUsersToString() {
        StringBuilder allUsers = new StringBuilder();

        for(User u : userList.values()) {
            allUsers.append("\t- ");
            allUsers.append(u.toString());
        }

        return allUsers.toString();
    }

    /**
     * Sets null the current user, it means that there is no active user in the system.
     */
    public void removeCurrentUser() {
        currentUser = null;
    }
}

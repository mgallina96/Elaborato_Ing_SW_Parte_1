package main.model.database;
import main.model.user.Customer;
import main.model.user.User;
import main.model.user.UserStatus;
import main.utility.notifications.Notifications;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database concerned with saving, removing, finding, fetching and generally managing all kinds of users and details
 * about them.
 *
 * @author Manuel Gallina, Alessandro Polcini
 */
public class UserDatabase implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5681387677098150051L;

    private static final String USER_DATABASE_FILE_PATH = "resources\\data\\Biblioteca SMARTINATOR - User Database.ser";
    //A default admin user, added to the database whenever this class is instantiated.
    private static final User ADMIN = new User("admin", "admin");
    private static UserDatabase userDatabase;
    private User currentUser;
    private HashMap<String, User> userList;
    private Logger logger;

    //Singleton Database constructor, private to prevent instantiation.
    private UserDatabase() {
        this.userList = new HashMap<>();
        logger = Logger.getLogger(this.getClass().getName());
        loadUserDatabase();

        //Adds the admin to the database, if the admin hasn't been added yet.
        if(!isPresent(ADMIN)) {
            ADMIN.setUserStatus(UserStatus.OPERATOR);
            this.addUser(ADMIN);
        }

        sweep();
    }

    static UserDatabase getInstance() {
        if(userDatabase == null)
            userDatabase = new UserDatabase();

        return userDatabase;
    }

    void addUser(User toAdd) {
        userList.put(toAdd.getUsername(), toAdd);
    }

    boolean isPresent(User toFind) {
        return userList.containsKey(toFind.getUsername());
    }

    User fetchUser(User toFetch) {
        return isPresent(toFetch) ? userList.get(toFetch.getUsername()) : null;
    }

    void setCurrentUser(User currentUser) {
        this.currentUser = fetchUser(currentUser);
    }

    User getCurrentUser() {
        return currentUser;
    }

    void removeCurrentUser() {
        currentUser = null;
    }

    String getUserListString() {
        StringBuilder allUsers = new StringBuilder();

        for(User u : userList.values()) {
            allUsers.append("\t- ");
            allUsers.append(u.toString());
        }

        return allUsers.toString();
    }

    HashMap<String, User> getUserList() {
        return userList;
    }

    void setUserList(HashMap<String, User> userList) {
        this.userList = userList;
    }

    /**
     * Opens a .ser serializable file and loads its contents into this {@link UserDatabase} class.<p>
     * This method loads a {@code HashMap} containing all subscribed users.
     */
    @SuppressWarnings("unchecked")
    void loadUserDatabase() {
        try {
            FileInputStream fileIn = new FileInputStream(USER_DATABASE_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            this.userList = (HashMap<String, User>) in.readObject();

            in.close();
            fileIn.close();
        }
        catch(FileNotFoundException FNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_FILE_NOT_FOUND + this.getClass().getName());
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_LOADING_DATABASE + this.getClass().getName(), IOEx);
        }
        catch(ClassNotFoundException CNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_CLASS_NOT_FOUND + this.getClass().getName());
        }
    }

    /**
     * Sweeps the user database removing all users whose subscription has expired.
     */
    private void sweep() {
        userList.values().stream()
                .filter(s -> s instanceof Customer)
                .filter(s -> ((Customer)s).subscriptionHasExpired())
                .forEach(s -> userList.remove(s.getUsername()));
    }

    static String getPath() {
        return USER_DATABASE_FILE_PATH;
    }
}

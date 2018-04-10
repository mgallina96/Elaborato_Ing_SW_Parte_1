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
 * @author Manuel Gallina
 */
class UserDatabase implements Serializable {

    /**
     * Unique serial ID for the {@link UserDatabase} class. DO NOT CHANGE, otherwise the database can't be read properly.
     */
    private static final long serialVersionUID = -5681387677098150051L;

    //A default admin user, added to the database whenever this class is instantiated.
    private static final User ADMIN = new User("admin", "admin");
    private static UserDatabase userDatabase;
    private User currentUser;
    private HashMap<String, User> userList;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    //Singleton Database constructor, private to prevent instantiation.
    private UserDatabase() {
        this.userList = new HashMap<>();

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
    public static UserDatabase getInstance() {
        if(userDatabase == null)
            userDatabase = new UserDatabase();

        return userDatabase;
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
     * @param toFetch The user to be found.
     * @return the user or {@code null} if that user can't be found in the database.
     */
    public User fetchUser(User toFetch) {
        return isPresent(toFetch) ? userList.get(toFetch.getUsername()) : null;
    }

    /**
     * Sets the current user who just logged in.
     *
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
     * Sets the current user to {@code null}: this means that no user is active in the system at that moment.
     */
    public void removeCurrentUser() {
        currentUser = null;
    }

    /**
     * Returns a {@code String} that contains all the users in the database.
     *
     * @return the list of all users as a {@code String}.
     */
    public String getUserListString() {
        StringBuilder allUsers = new StringBuilder();

        for(User u : userList.values()) {
            allUsers.append("\t- ");
            allUsers.append(u.toString());
        }

        return allUsers.toString();
    }

    public HashMap<String, User> getUserList() {
        return userList;
    }

    public void setUserList(HashMap<String, User> userList) {
        this.userList = userList;
    }
}

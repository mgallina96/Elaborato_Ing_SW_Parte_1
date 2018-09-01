package main.model.database;
import main.model.user.Customer;
import main.model.user.User;
import main.model.user.UserStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import static main.model.database.DatabaseIO.loadUserDatabase;

/**
 * Database concerned with saving, removing, finding, fetching and generally managing all kinds of users and details
 * about them.
 *
 * @author Manuel Gallina, Alessandro Polcini
 */
public class UserDatabase implements Serializable, Database {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5681387677098150051L;

    //A default admin user, added to the database whenever this class is instantiated.
    private static final User ADMIN = new User("admin", "admin");
    private static UserDatabase userDatabase;
    private User currentUser;
    private HashMap<String, User> userList;

    //UserDatabase initializer.
    private UserDatabase() {
        this.userList = new HashMap<>();

        //Adds the admin to the database, if the admin hasn't been added yet.
        if(!isPresent(ADMIN)) {
            ADMIN.setUserStatus(UserStatus.OPERATOR);
            this.addUser(ADMIN);
        }

        sweep();
    }

    /**
     * Getter/initializer for the {@code UserDatabase} singleton class.
     * @return The {@code UserDatabase} instance.
     */
    public static UserDatabase getInstance() {
        if(userDatabase == null) {
            userDatabase = new UserDatabase();
            userDatabase = loadUserDatabase();
        }

        return userDatabase;
    }

    /**
     * Adds a new user to the database.
     * @param toAdd The user to be added.
     */
    public void addUser(User toAdd) {
        userList.put(toAdd.getUsername(), toAdd);
    }

    /**
     * Removes a user from the database.
     * @param toRemove The user to be removed.
     */
    public void removeUser(User toRemove) {
        userList.remove(toRemove.getUsername());
    }

    /**
     * Checks whether a given user is present in the database.
     *
     * @param toFind The user to be found.
     * @return {@code true} if the user has been found, {@code false otherwise}.
     */
    public boolean isPresent(User toFind) {
        return userList.containsKey(toFind.getUsername());
    }

    /**
     * Fetches a given user from the database.
     *
     * @param toFetch The user to be fetched.
     * @return The user (if found, {@code null} otherwise).
     */
    public User fetchUser(User toFetch) {
        return isPresent(toFetch) ? userList.get(toFetch.getUsername()) : null;
    }

    /**
     * Sets the given user as the "current user". Logically equivalent to logging in.
     * @param currentUser The user who will become the "current user".
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = fetchUser(currentUser);
    }

    /**
     * Getter for the current user.
     * @return The current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Removes the current user, setting its value to {@code null}. Logically equivalent to logging out.
     */
    public void removeCurrentUser() {
        currentUser = null;
    }

    /**
     * Builds a {@code String} containing all the users in the database.
     * @return The {@code String} containing all users.
     */
    public String getUserListString() {
        StringBuilder allUsers = new StringBuilder();

        for(User u : userList.values()) {
            allUsers.append("\t- ");
            allUsers.append(u.toString());
        }

        return allUsers.toString();
    }

    //sweeps the user database removing all users whose subscription has expired.
    private void sweep() {
        GregorianCalendar today = new GregorianCalendar();
        ArrayList<String> toRemove = new ArrayList<>();

        userList.values().stream()
                .filter(u -> u instanceof Customer)
                .filter(c -> today.after(((Customer)c).getExpiryDateGregorian()))
                .forEach(c -> toRemove.add(c.getUsername()));

        for(String s : toRemove)
            userList.remove(s);
    }

}

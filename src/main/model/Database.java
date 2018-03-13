package main.model;

import main.model.user.User;

import java.util.HashMap;

/**
 * Database singleton class. This class manages a simple Database in form of a {@link HashMap}.
 * <p>
 * This class provides methods for adding elements to or removing elements from the database, searching through the
 * database for a single element and checking whether a given element belongs to the database.
 *
 */
public class Database {

    //A default admin user, added to the database whenever this class is instantiated.
    private static final User ADMIN = new User("admin", "admin");
    private static Database database;
    private HashMap<String, User> userList = new HashMap<>();

    //Singleton Database constructor, private to prevent instantiation.
    private Database() {
        this.addUser(ADMIN);
    }

    /**
     * Getter/initializer for the Singleton Database class.
     * <p>
     * This method builds a single instance of Database the first time it is called. Calling it more than once doesn't
     * change anything.
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
     * @param toAdd the {@code User} who is to be added to the database.
     */
    public void addUser(User toAdd) {
        userList.put(toAdd.getUsername(), toAdd);
    }

    /**
     * Removes an already existing user from the database.
     * @param toRemove the {@code User} who is to be removed.
     */
    public void removeUser(User toRemove) {
        if(isPresent(toRemove))
            userList.remove(toRemove.getUsername());
    }

    /**
     * Returns the list of users in form of a HashMap.
     * @return the actual database.
     */
    public HashMap<String, User> getUserList() {
        return userList;
    }

    /**
     * Checks whether the given user is present in the database.
     * @param toFind the user to be found.
     * @return {@code true} if the user is present in the database, {@code false} otherwise.
     */
    public boolean isPresent(User toFind) {
        return userList.containsKey(toFind.getUsername());
    }

    /**
     * Returns the given user (if present).
     * @param toFind the user to be found.
     * @return the user or {@code null} if that user can't be found in the database.
     */
    public User fetchUser(User toFind) {
        return isPresent(toFind) ? userList.get(toFind.getUsername()) : null;
    }

    /**
     * Loads an existing database into this class' HashMap.
     */
    public void loadDatabase() {

    }

    /**
     * Saves this database to a {@code .ser} file
     */
    public void saveDatabase() {

    }
}

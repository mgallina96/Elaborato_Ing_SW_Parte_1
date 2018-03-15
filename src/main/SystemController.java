package main;
import main.gui.GuiManager;
import main.gui.screens.StartScreen;
import main.model.user.Customer;
import main.model.Database;
import main.model.user.User;
import main.model.user.UserStatus;

import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Controller class that manages all the interactions between the graphic user interface
 * and the logic model of the program.
 *
 * @author Manuel Gallina
 * @version 0.1
 * @since version 0.1 - 12/03/2018
 */
public class SystemController {
    private static SystemController instance;
    private GuiManager guiManager;
    private Database database;

    //Singleton constructor, private to prevent instantiation.
    private SystemController() {
        database = Database.getInstance();
        guiManager = new StartScreen(this);
    }

    /**
     * Returns the system controller.
     *
     * @return The system controller.
     */
    public static SystemController getInstance() {
        if(instance == null)
            instance = new SystemController();
        return instance;
    }

    /** Initializes the system controller which starts the GUI. */
    public void init() {
        guiManager.start();
    }

    /**
     * Checks if the given username and password match with a registered user.
     *
     * @param username The username.
     * @param password The password.
     * @return {@code true} if the user credentials are correct, {@code false} otherwise.
     */
    public boolean checkUserLogin(String username, String password) {
        User toCheck = new User(username, password);
        if(database.isPresent(toCheck)){
            database.setCurrentUser(toCheck);
            return true;
        }
        return false;
    }

    /**
     * Returns a {@code String} that contains all the users in the database.
     *
     * @return the list of all users as a {@code String}.
     */
    public String allUsersToString() {
        return database.allUsersToString();
    }

    /**
     * Adds a new user to the database.
     *
     * @param firstName the user's first name.
     * @param lastName the user's last name.
     * @param username the user's username.
     * @param password the user's password.
     * @param birthday the user's birthday.
     */
    public void addUserToDatabase(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        database.addUser(new Customer(firstName, lastName, username, password, birthday));
    }

    /**
     * Checks whether a customer is of age or not.
     *
     * @param birthday the birthday to be checked.
     * @return {@code true} if the customer is of age, {@code false} otherwise.
     */
    public boolean legalAge(GregorianCalendar birthday) {
        return new Customer(
                "",
                "",
                "",
                "",
                birthday
        ).isOfAge();
    }

    /**
     * Getter for the User status (OPERATOR or CUSTOMER).
     *
     * @param username the user's username.
     * @return the user status as an {@code enum}.
     */
    public UserStatus getUserStatus(String username) {
        return database.fetchUser(new User(username)).getUserStatus();
    }

    /**
     * Renews the user's subscription.
     * <p>
     * The user must be a customer.
     *
     * @throws IllegalArgumentException if the current user is not a customer.
     */
    public void renewSubscription() throws IllegalArgumentException {
        if(database.getCurrentUser() instanceof  Customer)
            ((Customer)database.getCurrentUser()).renewSubscription();
        else
            throw new IllegalArgumentException();
    }

    /** Saves the database and possible changes made to it. */
    public void saveDatabase() {
        database.saveDatabase();
    }
}

package main;
import main.gui.GuiManager;
import main.gui.TextualView;
import main.model.user.Customer;
import main.model.Database;
import main.model.user.User;
import main.model.user.UserStatus;

import java.util.GregorianCalendar;

/**
 * Controller class that manages any kind of interaction between the graphical user interface
 * and the logic (model) of the program.
 *
 * @author Manuel Gallina
 * @version 0.1
 * @since version 0.1 - 12/03/2018
 */
public class Controller implements SystemController {

    private static Controller instance;
    private GuiManager guiManager;
    private Database database;

    //Singleton constructor, private to prevent instantiation.
    private Controller() {
        database = Database.getInstance();
        guiManager = new TextualView(this);
    }

    /**
     * Getter/setter for the system controller.
     *
     * @return The system controller.
     */
    public static Controller getInstance() {
        if(instance == null)
            instance = new Controller();

        return instance;
    }

    /**
     * Initializes the system controller, which starts the GUI.
     */
    public void init() {
        guiManager.mainScreen();
    }

    /**
     * Checks whether the given pair <{@code username, password}> can be found in the database. If so, the user with
     * those credentials becomes the "current user".
     *
     * @param username The username.
     * @param password The password.
     * @return {@code true} if the user's credentials are correct and match with an entry in the database,
     * {@code false} otherwise.
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
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param username The user's username.
     * @param password The user's password.
     * @param birthday The user's birthday.
     */
    public void addUserToDatabase(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        database.addUser(new Customer(firstName, lastName, username, password, birthday));
    }

    /**
     * Checks whether a customer is of age or not.
     *
     * @param birthday The birthday to be checked.
     * @return {@code true} if the customer is of age, {@code false} otherwise.
     */
    public boolean legalAge(GregorianCalendar birthday) {
        return new Customer("", "", "", "", birthday).isOfAge();
    }

    /**
     * Getter for the User status (OPERATOR or CUSTOMER).
     * @param username The user's username.
     * @return an {@code enum} value, OPERATOR or CUSTOMER.
     */
    public UserStatus getUserStatus(String username) {
        return database.fetchUser(new User(username)).getUserStatus();
    }

    /**
     * Sets the current user.
     * @param username The user's username.
     */
    private void setCurrentUser(String username) {
        database.setCurrentUser(new User(username));
    }

    /**
     * Renews the user's subscription.
     * <p>
     * The user must be a customer.
     *
     * @throws IllegalArgumentException if the current user is not a customer.
     */
    public void renewSubscription() throws IllegalArgumentException {
        if(database.getCurrentUser() instanceof Customer)
            ((Customer)database.getCurrentUser()).renewSubscription();
        else
            throw new IllegalArgumentException();
    }

    /**
     * Saves the database and possible changes made to it.
     */
    public void saveDatabase() {
        database.saveDatabase();
    }

    /**
     * Checks whether a given user is allowed to renew his/her subscription.
     */
    public boolean canRenew() {
        return (database.getCurrentUser() instanceof Customer) && ((Customer)database.getCurrentUser()).canRenew();
    }

    /**
     * Converts the subscription and expiry dates into a {@code String}.
     *
     * @return a {@code String} containing the subscription and expiry dates.
     */
    public String dateDetails() {
        User u = database.getCurrentUser();

        return (u instanceof Customer) ?
                String.format("Reminder:\n\tYou subscribed on %s\n\tYour subscription expires on %s\n\tYou're not " +
                                "allowed to renew your subscription until 10 days before the expiry date.",
                        ((Customer)u).getSubscriptionDate().toZonedDateTime().toString().substring(0, 10),
                        ((Customer)u).getExpiryDate().toZonedDateTime().toString().substring(0, 10)) :
                "";
    }

    /**
     * Allows the current user to log out of the system.
     */
    public void logout() {
        database.removeCurrentUser();
    }
}

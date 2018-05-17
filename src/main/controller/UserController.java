package main.controller;
import main.utility.exceptions.UserNotFoundException;
import main.utility.exceptions.WrongPasswordException;
import java.util.GregorianCalendar;

/**
 * Interface for the User controller, which provides methods specific to user management.
 *
 * @author Manuel Gallina, Giosuè Filippini, Alessandro Polcini
 */
public interface UserController extends Controller {

    /**
     * Renews the user's subscription and returns a boolean value to tell the program whether the renewal was successful
     * or not.
     * <p>
     * The user must be a customer.
     *
     * @return a {@code boolean} value: {@code true} if the user can renew their subscription, {@code false} otherwise.
     * @throws IllegalArgumentException if the current user is not a customer.
     */
    boolean renewSubscription();

    /**
     * Checks whether the given pair <{@code username, password}> can be found in the database. If so, the user with
     * those credentials becomes the "current user".
     *
     * @param username The username.
     * @param password The password.
     * @return {@code true} if the user's credentials are correct and match with an entry in the database,
     * {@code false} otherwise.
     *
     * @throws UserNotFoundException if the user is not present in the database.
     * @throws WrongPasswordException if the password is wrong.
     */
    boolean checkUserLogin(String username, String password) throws UserNotFoundException, WrongPasswordException;

    /** Logs the current user out of the system. */
    void logout();

    /**
     * Checks whether the given username matches an entry in the database.
     *
     * @param username The username to be checked.
     * @return A boolean value, {@code true} if the given user can be found in the database, {@code false} otherwise.
     */
    boolean userIsPresent(String username);

    /**
     * Adds a new user to the database.
     *
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param username The user's username.
     * @param password The user's password.
     * @param birthday The user's birthday.
     */
    boolean addUserToDatabase(String firstName, String lastName, String username, String password, GregorianCalendar birthday);

    /**
     * Getter for the User status (CUSTOMER or OPERATOR).
     *
     * @param username The user's username.
     * @return an {@code int} value, 0 (CUSTOMER) or 1 (OPERATOR).
     */
    int getUserStatus(String username);

    /**
     * Returns a {@code String} containing all the users in the database.
     *
     * @return the list of all users as a {@code String}.
     */
    String allUsersToString();

    /**
     * Converts the subscription and expiry dates into a {@code String}.
     *
     * @return a {@code String} containing the subscription and expiry dates.
     */
    String dateDetails();

    /**
     * Returns the amount of days the user has left to renew their subscription.
     *
     * @param username The user's username.
     * @return An {@code integer} value representing the days the user has left.
     */
    int daysLeftToRenew(String username) throws UserNotFoundException;


}

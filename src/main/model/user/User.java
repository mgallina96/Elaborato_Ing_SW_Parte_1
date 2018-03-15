package main.model.user;
import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * The {@code User} class, superclass of {@link Customer} and {@link Operator}. This class contains fields and methods
 * that both customers and operators have in common, like personal details (and getters/setters for those details).
 */
public class User implements Serializable {
    private static final long serialVersionUID = -5681383377098150051L;

    private static final String DEFAULT_FIRST_NAME = "null";
    private static final String DEFAULT_LAST_NAME = "null";
    private static final String DEFAULT_PASSWORD = "null";
    private static final GregorianCalendar DEFAULT_BIRTHDAY = new GregorianCalendar();

    private UserStatus userStatus;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private GregorianCalendar birthday;

    /**
     * The least specific user constructor (mainly used to quickly initialize a user for database matching).
     * This constructor only sets the {@code username} parameter.
     * <p>All the other parameters are initialized to their default state.
     *
     * @param username The user's unique username.
     */
    public User(String username) {
        this.username = username;
        defaultInit();
        this.password = DEFAULT_PASSWORD;
    }

    /**
     * User constructor which only sets the {@code username} and {@code password} parameters.
     * <p>All the other parameters are initialized to their default state.
     *
     * @param username The user's unique username.
     * @param password The user's password.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        defaultInit();
    }

    /**
     * The most complete user constructor, having all possible parameters for a user.
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param username The user's unique username.
     * @param password The user's password.
     * @param birthday The user's birth date.
     */
    public User(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
    }

    /**
     * Initializer for a default user for whom the fields {@code firstName}, {@code lastName} and {@code birthday}
     * have not been set.
     */
    private void defaultInit() {
        this.firstName = DEFAULT_FIRST_NAME;
        this.lastName = DEFAULT_LAST_NAME;
        this.birthday = DEFAULT_BIRTHDAY;
    }

    /**
     * Getter for the user's unique username.
     *
     * @return the username in form of a {@code String}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the user's username.
     *
     * @param username The user's unique username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the user's first name.
     *
     * @return the first name in form of a {@code String}.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the user's first name.
     *
     * @param firstName The user's first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the user's last name.
     *
     * @return the last name in form of a {@code String}.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for the user's last name.
     *
     * @param lastName The user's last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for the user's unique password.
     *
     * @return the password in form of a {@code String}.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the user's password.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the user's birth date.
     *
     * @return the birth date in {@code GregorianCalendar} form.
     */
    public GregorianCalendar getBirthday() {
        return birthday;
    }

    /**
     * Setter for the user's birth date.
     *
     * @param birthday The birth date in {@code GregorianCalendar} form.
     */
    public void setBirthday(GregorianCalendar birthday) {
        this.birthday = birthday;
    }

    /**
     * Returns the User status.
     *
     * @return {@code true} if the User is a Customer, {@code false} if the User is an Operator.
     */
    public UserStatus getUserStatus() {
        return userStatus;
    }

    /**
     * Setter for the enum-linked {@link UserStatus} object. As of version 0.1 the possible states are CUSTOMER or
     * OPERATOR.
     *
     * @param userStatus The status to set.
     */
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * Converts the user's public data into a readable {@code String}.
     *
     * @return the user's details, in form of a {@code String}.
     */
    public String toString() {
        return String.format("First name: %s\t|\tLast name: %s\t|\tUsername: %s\t|\tBirthday = %s\n",
                firstName, lastName, username, birthday.toZonedDateTime().toString().substring(0, 10));
    }

}

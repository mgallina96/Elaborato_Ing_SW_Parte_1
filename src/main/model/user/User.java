package main.model.user;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The {@code User} class, superclass of {@link Customer} and {@link Operator}. This class contains fields and methods
 * that both customers and operators have in common, like personal details (and getters/setters for those details).
 */
public class User implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5681383377098150051L;

    private static final String DEFAULT_FIRST_NAME = "Default";
    private static final String DEFAULT_LAST_NAME = "Default";
    private static final String DEFAULT_PASSWORD = "Default";
    private static final GregorianCalendar DEFAULT_BIRTHDAY = new GregorianCalendar(1970, Calendar.JANUARY, 1);

    private UserStatus userStatus;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private GregorianCalendar birthday;

    /**
     * The least specific user constructor (mainly used to quickly initialize a user for database matching).
     * This constructor only sets the {@code username} parameter.
     * <p>
     * All the other parameters are initialized to their default state.
     *
     * @param username The user's unique username.
     */
    public User(String username) {
        this.username = username;
        this.password = DEFAULT_PASSWORD;
        defaultInit();
    }

    /**
     * User constructor which only sets the {@code username} and {@code password} parameters.
     * <p>
     * All the other parameters are initialized to their default state.
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
     *
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
    @SuppressWarnings("unused")
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username The username to set.
     */
    @SuppressWarnings("unused")
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the user's first name.
     *
     * @return the first name in form of a {@code String}.
     */
    @SuppressWarnings("unused")
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the user's first name.
     *
     * @param firstName The user's first name to set.
     */
    @SuppressWarnings("unused")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the user's last name.
     *
     * @return the last name in form of a {@code String}.
     */
    @SuppressWarnings("unused")
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for the user's last name.
     *
     * @param lastName The user's last name to set.
     */
    @SuppressWarnings("unused")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for the user's unique password.
     *
     * @return the password in form of a {@code String}.
     */
    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the user's password.
     *
     * @param password The password to set.
     */
    @SuppressWarnings("unused")
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the user's birth date.
     *
     * @return the birth date in {@code GregorianCalendar} form.
     */
    @SuppressWarnings("unused")
    GregorianCalendar getBirthday() {
        return birthday;
    }

    /**
     * Setter for the user's birth date.
     *
     * @param birthday The birth date in {@code GregorianCalendar} form.
     */
    @SuppressWarnings("unused")
    public void setBirthday(GregorianCalendar birthday) {
        this.birthday = birthday;
    }

    /**
     * Returns the User status.
     *
     * @return {@code true} if the User is a Customer, {@code false} if the User is an Operator.
     */
    @SuppressWarnings("unused")
    public UserStatus getUserStatus() {
        return userStatus;
    }

    /**
     * Setter for the enum-linked {@link UserStatus} object. As of version 0.1 the possible states are CUSTOMER or
     * OPERATOR.
     *
     * @param userStatus The status to set.
     */
    @SuppressWarnings("unused")
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

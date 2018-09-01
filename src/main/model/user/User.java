package main.model.user;
import main.utility.InputParserUtility;

import java.io.Serializable;
import java.util.GregorianCalendar;

import static main.model.user.UserConstants.*;

/**
 * The {@code User} class, superclass of {@link Customer} and {@link Operator}. This class contains fields and methods
 * that both customers and operators have in common, like personal details (and getters/setters for those details).
 */
public class User implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5681383377098150051L;

    private UserStatus userStatus;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private GregorianCalendar birthDate;

    /**
     * The least specific user constructor (mainly used to quickly initialize a user for database matching).
     * This constructor only sets the {@code username} parameter.
     * <p>
     * All the other parameters are initialized to their default state.
     *
     * @param username The user's unique username.
     */
    public User(String username) {
        this(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, username, DEFAULT_PASSWORD, DEFAULT_BIRTH_DATE);
    }

    /**
     * Constructor that builds a new {@code Customer} object using the given parameters.
     *
     * @param birthDate The customer's birth date, in {@code GregorianCalendar} form.
     */
    public User(GregorianCalendar birthDate) {
        this(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_USERNAME, DEFAULT_PASSWORD, birthDate);
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
        this(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, username, password, DEFAULT_BIRTH_DATE);
    }

    /**
     * The most complete user constructor, having all possible parameters for a user.
     *
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param username The user's unique username.
     * @param password The user's password.
     * @param birthDate The user's birth date.
     */
    public User(String firstName, String lastName, String username, String password, GregorianCalendar birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
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
     * Sets the user's username.
     *
     * @param username The username to set.
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
    public GregorianCalendar getBirthDate() {
        return birthDate;
    }

    /**
     * Setter for the user's birth date.
     *
     * @param birthDate The birth date in {@code GregorianCalendar} form.
     */
    public void setBirthDate(GregorianCalendar birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Returns the User status.
     *
     * @return The value {@code CUSTOMER} if the User is a Customer, {@code OPERATOR} if the User is an Operator.
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
        return String.format("First name: %s\t|\tLast name: %s\t|\tUsername: %s\t|\tBirth date = %s%n",
                firstName, lastName, username, InputParserUtility.toEuropeanFormat(birthDate));
    }
}

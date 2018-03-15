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

    public User(String username) {
        this.username = username;
        defaultInit();
        this.password = DEFAULT_PASSWORD;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        defaultInit();
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GregorianCalendar getBirthday() {
        return birthday;
    }

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

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String toString() {
        return String.format("First name: %s | Last name: %s | Username: %s | Birthday = %s\n", firstName, lastName, username, birthday.toZonedDateTime().toString().substring(0, 10));
    }

}

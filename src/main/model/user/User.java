package main.model.user;
import java.util.GregorianCalendar;

public class User {
    private static final String DEFAULT_FIRST_NAME = "null";
    private static final String DEFAULT_LAST_NAME = "null";
    private static final GregorianCalendar DEFAULT_BIRTHDAY = new GregorianCalendar();

    private boolean isCustomer;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private GregorianCalendar birthday;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        this.firstName = DEFAULT_FIRST_NAME;
        this.lastName = DEFAULT_LAST_NAME;
        this.birthday = DEFAULT_BIRTHDAY;
    }

    public User(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
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

    public boolean isCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    public String toString() {
        return String.format("First name: %s | Last name: %s | Username: %s | Birthday = %s", firstName, lastName, username, birthday.toZonedDateTime().toString().substring(0, 10));
    }

}

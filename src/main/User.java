package main;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class User {

    private static final long OF_AGE = 568024668000L;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private GregorianCalendar birthday;
    private GregorianCalendar subscriptionDate;
    private GregorianCalendar expiryDate;
    boolean isExpired;
    boolean isUser;

    public User(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        subscriptionDate = new GregorianCalendar();
        expiryDate = (GregorianCalendar)(subscriptionDate.clone());
        expiryDate.add(Calendar.YEAR, 5);
        System.out.println(subscriptionDate.toZonedDateTime());
        System.out.println(expiryDate.toZonedDateTime());
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public GregorianCalendar getBirthday() {
        return birthday;
    }

    public GregorianCalendar getSubscriptionDate() {
        return subscriptionDate;
    }

    public GregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    public boolean isOfAge() {
        return subscriptionDate.getTimeInMillis() - birthday.getTimeInMillis() >= OF_AGE;
    }

    public String toString() {
        return String.format("First name: %s | Last name: %s | Username: %s | Birthday = %s\n", firstName, lastName, username, birthday.toZonedDateTime());
    }
}

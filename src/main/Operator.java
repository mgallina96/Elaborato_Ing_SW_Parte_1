package main;

import java.util.GregorianCalendar;

public class Operator {

    private boolean isUser = false;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private GregorianCalendar birthday;

    public Operator(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
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

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public String toString() {
        return String.format("First name: %s | Last name: %s | Username: %s | Birthday = %s\n", firstName, lastName, username, birthday.toZonedDateTime());
    }

}

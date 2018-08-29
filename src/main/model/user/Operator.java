package main.model.user;
import java.util.GregorianCalendar;

/**
 * The {@code Operator} class, a subclass of {@link User} equipped with privileged methods specific to the Operator.
 */
public class Operator extends User {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = 6873316295925654746L;

    /**
     * Constructor for the {@code Operator} class.
     *
     * @param firstName The operator's first name.
     * @param lastName The operator's last name.
     * @param username The operator's username.
     * @param password The operator's password.
     * @param birthDate The operator's birth date.
     */
    public Operator(String firstName, String lastName, String username, String password, GregorianCalendar birthDate) {
        super(firstName, lastName, username, password, birthDate);
        super.setUserStatus(UserStatus.OPERATOR);
    }
}

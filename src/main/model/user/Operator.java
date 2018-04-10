package main.model.user;
import java.util.GregorianCalendar;

/**
 * The {@code Operator} class, a subclass of {@link User} equipped with privileged methods specific to the Operator.
 */
public class Operator extends User {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = 6873316295925654746L;

    public Operator(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        super(firstName, lastName, username, password, birthday);
        super.setUserStatus(UserStatus.OPERATOR);
    }

    /**
     *
     */
    public void addMediaToDatabase() {

    }

    /**
     *
     */
    public void removeMediaFromDatabase() {

    }

    /**
     *
     */
    public void viewMediaList() {

    }

}

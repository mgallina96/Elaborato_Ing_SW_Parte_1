package main.model.user;
import java.util.GregorianCalendar;

/**
 * The {@code Operator} class, a subclass of {@code User} equipped with privileged methods specific to the Operator.
 */
public class Operator extends User {

    public Operator(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        super(firstName, lastName, username, password, birthday);
        super.setCustomer(false);
    }

    /**
     * Views the list of all enrolled customers.
     */
    public void viewCustomerList() {

    }

}

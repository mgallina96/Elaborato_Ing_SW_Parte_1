package main.model.user;

import java.util.GregorianCalendar;

public class Operator extends User {

    public Operator(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        super(firstName, lastName, username, password, birthday);
        super.setCustomer(false);
    }

    public void viewCustomerList() {

    }

}

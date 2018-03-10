package main;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class User extends Operator {

    private static final long OF_AGE = 568024668000L;
    private GregorianCalendar subscriptionDate;
    private GregorianCalendar expiryDate;
    boolean isExpired;

    public User(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        super(firstName, lastName, username, password, birthday);
        super.setUser(true);

        subscriptionDate = new GregorianCalendar();
        expiryDate = (GregorianCalendar)(subscriptionDate.clone());
        expiryDate.add(Calendar.YEAR, 5);
    }

    public GregorianCalendar getSubscriptionDate() {
        return subscriptionDate;
    }

    public GregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    public boolean isOfAge() {
        return subscriptionDate.getTimeInMillis() - super.getBirthday().getTimeInMillis() >= OF_AGE;
    }

}

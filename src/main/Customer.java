package main;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Customer extends User {

    private static final long OF_AGE = 568024668000L;
    private static final int EXPIRY_TIME_IN_YEARS = 5;
    private GregorianCalendar subscriptionDate;
    private GregorianCalendar expiryDate;
    private boolean hasExpired;

    public Customer(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        super(firstName, lastName, username, password, birthday);
        super.setCustomer(true);

        subscriptionDate = new GregorianCalendar();
        expiryDate = (GregorianCalendar)(subscriptionDate.clone());
        expiryDate.add(Calendar.YEAR, EXPIRY_TIME_IN_YEARS);
        hasExpired = false;
    }

    public boolean isOfAge() {
        return subscriptionDate.getTimeInMillis() - super.getBirthday().getTimeInMillis() >= OF_AGE;
    }

    public boolean hasExpired() {
        return expiryDate.after(new GregorianCalendar());
    }

    public GregorianCalendar getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(GregorianCalendar subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public GregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    public void renewSubscription() {
        if(hasExpired)
            expiryDate.add(Calendar.YEAR, EXPIRY_TIME_IN_YEARS);
    }
}

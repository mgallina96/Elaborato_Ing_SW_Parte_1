package main;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Customer extends User {

    private static final int LEGAL_AGE_IN_YEARS = 18;
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
        GregorianCalendar subscription = (GregorianCalendar)subscriptionDate.clone();
        GregorianCalendar correctedBirthday = (GregorianCalendar)super.getBirthday().clone();
        correctedBirthday.add(Calendar.YEAR, LEGAL_AGE_IN_YEARS);

        return correctedBirthday.before(subscription);
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

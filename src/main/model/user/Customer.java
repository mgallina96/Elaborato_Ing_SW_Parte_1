package main.model.user;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The {@code Customer} class, a subclass of {@link User} equipped with:
 * <p>- customer-specific fields like the date of subscription, the expiry date of said subscription and constants
 * that define the time after which the subscription expires and the legal age of the customer.
 * <p>- methods that provide various features, like an age-checking
 * function, an expiry and validity check and a subscription renewal.
 */
public class Customer extends User {

    private static final int LEGAL_AGE_IN_YEARS = 18;
    private static final int EXPIRY_TIME_IN_YEARS = 5;
    private GregorianCalendar subscriptionDate;
    private GregorianCalendar expiryDate;
    private boolean hasExpired;

    /**
     * Constructor that builds a new {@code Customer} object using the given parameters.
     * @param firstName the customer's first name.
     * @param lastName the customer's last name.
     * @param username the customer's username.
     * @param password the customer's password.
     * @param birthday the customer's birthday, in {@code GregorianCalendar} form.
     */
    public Customer(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        super(firstName, lastName, username, password, birthday);
        super.setCustomer(true);

        subscriptionDate = new GregorianCalendar();
        expiryDate = (GregorianCalendar)(subscriptionDate.clone());
        expiryDate.add(Calendar.YEAR, EXPIRY_TIME_IN_YEARS);
        hasExpired = false;
    }

    /**
     * Quick initialization of a {@code Customer} object, where only the username and password are specified.
     * @param username the customer's username.
     * @param password the customer's password.
     */
    public Customer(String username, String password) {
        super(username, password);
    }

    /**
     * Checks whether the customer is of age. The legal age parameter is defined as a static final integer in the
     * {@link Customer} class and may vary according to law or country.
     * <p> The legal age in Italy is 18, so this is the value this check is going to be based on.
     * @return {@code true} if the customer is of legal age, {@code false} otherwise.
     */
    public boolean isOfAge() {
        GregorianCalendar subscription = (GregorianCalendar)subscriptionDate.clone();
        GregorianCalendar correctedBirthday = (GregorianCalendar)super.getBirthday().clone();
        correctedBirthday.add(Calendar.YEAR, LEGAL_AGE_IN_YEARS);

        return correctedBirthday.before(subscription);
    }

    /**
     * Checks whether the customer's subscription has expired or not.
     * @return {@code true} if the customer's subscription has expired, {@code false} otherwise.
     */
    public boolean hasExpired() {
        return expiryDate.after(new GregorianCalendar());
    }

    /**
     * The getter for the exact date and time the customer first signed up.
     * @return the subscription date in {@code GregorianCalendar} form.
     */
    public GregorianCalendar getSubscriptionDate() {
        return subscriptionDate;
    }

    /**
     * The getter for the date when the customer's subscription expires.
     * @return the expiry date in {@code GregorianCalendar} form.
     */
    public GregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    /**
     * Renews the subscription by x years, where x is a static final integer value defined in the {@link Customer}
     * class. This value is equal to 5 now.
     */
    public void renewSubscription() {
        if(hasExpired)
            expiryDate.add(Calendar.YEAR, EXPIRY_TIME_IN_YEARS);
    }
}

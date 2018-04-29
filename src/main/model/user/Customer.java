package main.model.user;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The {@code Customer} class, a subclass of {@link User} equipped with:
 * <p>- customer-specific fields like the date of subscription, the expiry date of said subscription and constants
 * that define the time after which the subscription expires and the legal age of the customer.
 * <p>- methods that provide various features, like an age-checking function, an expiry and validity check and a
 * subscription renewal function.
 *
 * @author Manuel Gallina, Giosuè Filippini, Alessandro Polcini
 */
public class Customer extends User {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = 562339799965662315L;

    private static final int RENEWAL_BOUNDARY_IN_DAYS = 10;
    private static final int LEGAL_AGE_IN_YEARS = 18;
    private static final int EXPIRY_TIME_IN_YEARS = 5;

    private GregorianCalendar subscriptionDate;
    private GregorianCalendar expiryDate;

    /**
     * Constructor that builds a new {@code Customer} object using the given parameters.
     *
     * @param firstName The customer's first name.
     * @param lastName The customer's last name.
     * @param username The customer's username.
     * @param password The customer's password.
     * @param birthday The customer's birthday, in {@code GregorianCalendar} form.
     */
    public Customer(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        super(firstName, lastName, username, password, birthday);
        super.setUserStatus(UserStatus.CUSTOMER);

        subscriptionDate = new GregorianCalendar();
        expiryDate = (GregorianCalendar)(subscriptionDate.clone());
        expiryDate.add(Calendar.YEAR, EXPIRY_TIME_IN_YEARS);
    }

    /**
     * Constructor that builds a new {@code Customer} object using the given parameters.
     *
     * For testing purposes only.
     *
     * @param firstName The customer's first name.
     * @param lastName The customer's last name.
     * @param username The customer's username.
     * @param password The customer's password.
     * @param birthday The customer's birthday, in {@code GregorianCalendar} form.
     * @param subscriptionDate The customer's subscription date, in {@code GregorianCalendar} form.
     */
    Customer(String firstName, String lastName, String username, String password, GregorianCalendar birthday, GregorianCalendar subscriptionDate) {
        super(firstName, lastName, username, password, birthday);
        super.setUserStatus(UserStatus.CUSTOMER);

        this.subscriptionDate = subscriptionDate;
        expiryDate = (GregorianCalendar)(subscriptionDate.clone());
        expiryDate.add(Calendar.YEAR, EXPIRY_TIME_IN_YEARS);
    }

    /**
     * Quick initialization of a {@code Customer} object, where only the username and password are specified.
     *
     * @param username The customer's username.
     * @param password The customer's password.
     */
    public Customer(String username, String password) {
        super(username, password);
    }

    /**
     * Checks whether the customer is of age. The legal age parameter is defined as a static final integer in the
     * {@link Customer} class and may vary according to law or country.
     * <p> The legal age in Italy is 18, so this is the value this check is going to be based on.
     *
     * @return {@code true} if the customer is of legal age, {@code false} otherwise.
     */
    public boolean isOfAge() {
        GregorianCalendar subscription = (GregorianCalendar)subscriptionDate.clone();
        GregorianCalendar correctedBirthday = (GregorianCalendar)super.getBirthday().clone();
        correctedBirthday.add(Calendar.YEAR, LEGAL_AGE_IN_YEARS);

        return correctedBirthday.before(subscription);
    }

    /**
     * Checks whether the current date is within x days of the expiry date, where x is an integer parameter defined
     * in the {@link Customer} class. If so, the customer is allowed to renew his/her subscription.
     *
     * @return {@code true} if the customer is allowed to renew his/her subscription, {@code false} otherwise.
     */
    public boolean canRenew() {
        GregorianCalendar expiryDateMinus10 = (GregorianCalendar)expiryDate.clone();
        expiryDateMinus10.add(Calendar.DATE, -RENEWAL_BOUNDARY_IN_DAYS);

        return !hasExpired() && (new GregorianCalendar()).after(expiryDateMinus10);
    }

    /**
     * Checks whether the user's subscription has expired.
     *
     * @return A boolean value: {@code true} if the user's subscription has expired, {@code false} otherwise.
     */
    public boolean hasExpired() {
        return (new GregorianCalendar()).after(expiryDate);
    }

    /**
     * The getter for the exact date and time the customer first signed up.
     *
     * @return the subscription date in {@code GregorianCalendar} form.
     */
    public GregorianCalendar getSubscriptionDate() {
        return subscriptionDate;
    }

    /**
     * The getter for the date when the customer's subscription expires.
     *
     * @return the expiry date in {@code GregorianCalendar} form.
     */
    public GregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    /**
     * Renews the subscription by x years, where x is a static final integer value defined in the {@link Customer}
     * class. This value is equal to 5 now.
     */
    public boolean renewSubscription() {
        if(canRenew()) {
            expiryDate.add(Calendar.YEAR, EXPIRY_TIME_IN_YEARS);
            return true;
        }

        return false;
    }

    /**
     * Returns the amount of days the user has left to renew their subscription.
     *
     * @return An {@code integer} value representing the days the user has left.
     */
    public int daysLeftToRenew() {
        return (int)Math.abs(ChronoUnit.DAYS.between(new GregorianCalendar().toInstant(), expiryDate.toInstant()));
    }
}

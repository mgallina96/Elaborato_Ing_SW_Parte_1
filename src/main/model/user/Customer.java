package main.model.user;

import main.utility.InputParserUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static main.utility.GlobalParameters.EXPIRY_TIME_IN_YEARS;

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

    private GregorianCalendar subscriptionDate;
    private GregorianCalendar expiryDate;
    private ArrayList<GregorianCalendar> renewalDates;

    /**
     * Constructor that builds a new {@code Customer} object using the given parameters.
     *
     * @param firstName The customer's first name.
     * @param lastName The customer's last name.
     * @param username The customer's username.
     * @param password The customer's password.
     * @param birthDate The customer's birth date, in {@code GregorianCalendar} form.
     */
    public Customer(String firstName, String lastName, String username, String password, GregorianCalendar birthDate) {
        this(firstName, lastName, username, password, birthDate, new GregorianCalendar());
    }

    /**
     * Constructor that builds a new {@code Customer} object using the given parameters.
     *
     * @param firstName The customer's first name.
     * @param lastName The customer's last name.
     * @param username The customer's username.
     * @param password The customer's password.
     * @param birthDate The customer's birth date, in {@code GregorianCalendar} form.
     * @param subscriptionDate The customer's subscription date, in {@code GregorianCalendar} form.
     */
    private Customer(String firstName, String lastName, String username, String password, GregorianCalendar birthDate, GregorianCalendar subscriptionDate) {
        super(firstName, lastName, username, password, birthDate);
        super.setUserStatus(UserStatus.CUSTOMER);

        this.renewalDates = new ArrayList<>();
        this.subscriptionDate = subscriptionDate;
        expiryDate = (GregorianCalendar)(subscriptionDate.clone());
        expiryDate.add(Calendar.YEAR, EXPIRY_TIME_IN_YEARS);
    }

    /**
     * The getter for the exact date and time the customer first signed up.
     *
     * @return the subscription date in {@code GregorianCalendar} form.
     */
    public GregorianCalendar getSubscriptionDateGregorian() {
        return subscriptionDate;
    }

    /**
     * The getter for the exact date and time the customer first signed up.
     *
     * @return the subscription date in form of a {@code String}.
     */
    public String getSubscriptionDate() {
        return subscriptionDate.toZonedDateTime().toString().substring(0, 10);
    }

    /**
     * The getter for the last renewal date of the subscription.
     *
     * @return the last renewal date in form of a {@code String}.
     */
    public String getLastRenewalDate() {
        return renewalDates.get(renewalDates.size() - 1).toZonedDateTime().toString().substring(0, 10);
    }

    /**
     * The getter for the date when the customer's subscription expires.
     *
     * @return the expiry date in {@code GregorianCalendar} form.
     */
    public GregorianCalendar getExpiryDateGregorian() {
        return expiryDate;
    }

    /**
     * The getter for the date when the customer's subscription expires.
     *
     * @return the expiry date in form of a {@code String}.
     */
    public String getExpiryDate() {
        return expiryDate.toZonedDateTime().toString().substring(0, 10);
    }

    /**
     * Returns the list of renewals.
     *
     * @return the list of renewals.
     */
    public ArrayList<GregorianCalendar> getRenewalDate() {
        return renewalDates;
    }

    /**
     * Renews the subscription by x years, where x is a static final integer value defined in the {@link Customer}
     * class. This value is equal to 5 now.
     */
    public void renewSubscription() {
        expiryDate = new GregorianCalendar();
        expiryDate.add(Calendar.YEAR, EXPIRY_TIME_IN_YEARS);
        renewalDates.add(new GregorianCalendar());
    }

    /**
     * Converts the customer's public data into a readable {@code String}.
     *
     * @return The customer's details, in form of a {@code String}.
     */
    public String toString() {
        return String.format("First name: %s\t|\tLast name: %s\t|\tUsername: %s\t|\tBirth date = %s\t|\tSubscription date = %s%n",
                getFirstName(), getLastName(), getUsername(),
                InputParserUtility.toEuropeanFormat(getBirthDate()),
                InputParserUtility.toEuropeanFormat(subscriptionDate));
    }
}

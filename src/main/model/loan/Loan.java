package main.model.loan;

import main.model.media.Media;
import main.model.user.User;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The Loan class, which associates a user with a media item they borrowed.
 *
 * @author Alessandro Polcini, Giosuè Filippini
 */
public class Loan implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -4699383317018250651L;

    private User user;
    private Media media;
    private int extensionRestrictionInDays;
    private GregorianCalendar loanDate;
    private GregorianCalendar loanExpiry;
    private GregorianCalendar extensionDate;
    private boolean canBeExtended;
    private boolean isActive;

    /**
     * Constructor for the Loan class. Binds a user and a media item together to form a {@code Loan} object.
     *
     * @param user The user borrowing the {@code Media} item.
     * @param media The media being borrowed by the {@code User}.
     */
    public Loan(User user, Media media) {
        this.user = user;
        this.media = media;
        this.isActive = true;
        this.canBeExtended = true;

        this.extensionRestrictionInDays = this.media.getExtensionRestriction();
        this.loanDate = new GregorianCalendar();
        this.loanExpiry = new GregorianCalendar();
        this.loanExpiry.add(Calendar.DATE, this.media.getMediaLoanValidityPeriod());
        this.extensionDate = null;
    }

    /**
     * Checks whether the given loan has expired.
     * @return {@code true} if the loan has expired, {@code false} otherwise.
     */
    public boolean hasExpired() {
        return new GregorianCalendar().after(loanExpiry);
    }

    /**
     * Getter for the date in which this loan was granted.
     * @return The loan date, as a {@code GregorianCalendar} object.
     */
    public GregorianCalendar getLoanDate() {
        return loanDate;
    }

    /**
     * Extends a loan by x days, where x is a variable defined in the {@link Media} class or its subclasses.
     */
    public void extend() {
        loanExpiry.add(Calendar.DATE, media.getMediaLoanValidityPeriod());
        extensionDate = new GregorianCalendar();
        canBeExtended = false;
    }

    /**
     * Getter for the expiry date for this loan.
     * @return The expiry date, as a {@code GregorianCalendar} object.
     */
    public GregorianCalendar getLoanExpiry() {
        return loanExpiry;
    }

    /**
     * Getter for the extension restriction (the number of days before this loan expires, before which a loan extension
     * cannot be made).
     * @return The loan extension restriction in days.
     */
    public int getExtensionRestrictionInDays() {
        return extensionRestrictionInDays;
    }

    /**
     * Getter for the user associated with this loan.
     * @return The user associated with this loan.
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter for the media associated with this loan.
     * @return The media associated with this loan.
     */
    public Media getMedia() {
        return media;
    }

    /**
     * Builds a string with general information about this loan.
     * @return A visual representation of this loan.
     */
    public String toString() {
        return String.format("Loan -> Media item: %s\t\tUser: %s\t\tYear: %d%n", media.toString(), user.toString(), loanDate.get(Calendar.YEAR));
    }

    /**
     * Builds a string with essential information about this loan.
     * @return A visual representation of this loan.
     */
    public String toEssentialString() {
        return String.format("Media item: %s\t|\tLoan date: %s%n", media.getBareItemDetails(), loanDate.toZonedDateTime().toString().substring(0, 10));
    }

    /**
     * Getter for the extension dates: all dates in which this loan was extended.
     * @return An {@code ArrayList} containing all extension dates.
     */
    public GregorianCalendar getExtensionDate() {
        return extensionDate;
    }

    /**
     * Getter for the {@code active} parameter.
     * @return {@code true} if the loan is active, {@code false} otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the status of this loan to active.
     * @param active Pass {@code true} as a parameter if you want to set this loan to active, {@code false} if you want
     *               to set it to inactive.
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Getter for the {@code canBeExtended} boolean parameter.
     * @return {@code true} if the loan can be extended, {@code false} otherwise.
     */
    public boolean canBeExtended(){
        return canBeExtended;
    }

    /**
     * For testing purposes only.
     * @param expiryDate The date to be set.
     */
    public void setLoanExpiry(GregorianCalendar expiryDate) {
        this.loanExpiry = expiryDate;
    }

}

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

    private static final int EXPIRY_TIME_IN_DAYS = 30;
    private User user;
    private Media media;
    private int extensionRestrictionInDays;
    private GregorianCalendar loanDate;
    private GregorianCalendar loanExpiry;

    public Loan(User user, Media media) {
        this.user = user;
        this.media = media;

        this.extensionRestrictionInDays = this.media.getExtensionRestriction();
        this.loanDate = new GregorianCalendar();
        this.loanExpiry = new GregorianCalendar();
        this.loanExpiry.add(Calendar.DATE, EXPIRY_TIME_IN_DAYS);
    }

    public boolean hasExpired() {
        return (new GregorianCalendar().after(loanExpiry));
    }

    public boolean canBeExtended() {
        GregorianCalendar correctedLoanExpiry = (GregorianCalendar)loanExpiry.clone();
        correctedLoanExpiry.add(Calendar.DATE, -extensionRestrictionInDays);

        return !hasExpired() && (new GregorianCalendar()).after(correctedLoanExpiry);
    }

    public GregorianCalendar getLoanDate() {
        return loanDate;
    }

    public GregorianCalendar getLoanExpiry() {
        return loanExpiry;
    }

    public int getExtensionRestrictionInDays() {
        return extensionRestrictionInDays;
    }

    public User getUser() {
        return user;
    }

    public Media getMedia() {
        return media;
    }

    public String toString() {
        return String.format("Loan -> Media item: " + media + "\tUser: " + user);
    }
}

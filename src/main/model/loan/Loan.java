package main.model.loan;

import main.model.media.Media;
import main.model.user.User;

import java.io.Serializable;
import java.util.ArrayList;
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
    private ArrayList<GregorianCalendar> extensionDates;
    private boolean isActive;

    public Loan(User user, Media media) {
        this.user = user;
        this.media = media;
        this.isActive = true;

        this.extensionRestrictionInDays = this.media.getExtensionRestriction();
        this.loanDate = new GregorianCalendar();
        this.loanExpiry = new GregorianCalendar();
        this.loanExpiry.add(Calendar.DATE, EXPIRY_TIME_IN_DAYS);
        this.extensionDates = new ArrayList<>();
    }

    public boolean hasExpired() {
        return (new GregorianCalendar().after(loanExpiry));
    }

    public GregorianCalendar getLoanDate() {
        return loanDate;
    }

    public void extend() {
        if(media.isAvailable()) {
            loanExpiry.add(Calendar.DATE, media.getExtensionRestriction());
            extensionDates.add(new GregorianCalendar());
        }
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
        return String.format("Loan -> Media item: %s\t\tUser: %s\t\tYear: %d%n", media.toString(), user.toString(), loanDate.get(Calendar.YEAR));
    }

    public String toEssentialString() {
        return String.format("Media item: %s\t|\tLoan date: %s%n", media.getBareItemDetails(), loanDate.toZonedDateTime().toString().substring(0, 10));
    }

    public ArrayList<GregorianCalendar> getExtensionDates() {
        return extensionDates;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

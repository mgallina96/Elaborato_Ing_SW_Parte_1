package main.model.database.loan;
import main.model.media.Media;
import main.model.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Loan implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -4699383317018250651L;

    private static final int EXPIRY_TIME_IN_DAYS = 30;
    private User user;
    private Media media;
    private ArrayList<Media> lentMediaItems;
    private int extentionConstraintInDays;
    private GregorianCalendar loanDate;
    private GregorianCalendar loanExpiry;

    public Loan(User user, Media media) {
        this.user = user;
        this.media = media;

        this.extentionConstraintInDays = this.media.getExtentionConstraint();
        this.loanDate = new GregorianCalendar();
        this.loanExpiry = new GregorianCalendar();
        this.loanExpiry.add(Calendar.DATE, EXPIRY_TIME_IN_DAYS);
    }

    public boolean hasExpired() {
        return (new GregorianCalendar().after(loanExpiry));
    }

    public boolean canBeExtended() {
        GregorianCalendar correctedLoanExpiry = (GregorianCalendar)loanExpiry.clone();
        correctedLoanExpiry.add(Calendar.DATE, -extentionConstraintInDays);

        return !hasExpired() && (new GregorianCalendar()).after(correctedLoanExpiry);
    }

    public void addLoan(Media media) {

    }

    public GregorianCalendar getLoanDate() {
        return loanDate;
    }

    public GregorianCalendar getLoanExpiry() {
        return loanExpiry;
    }

    public int getExtentionConstraintInDays() {
        return extentionConstraintInDays;
    }

    public User getUser() {
        return user;
    }

    public Media getMedia() {
        return media;
    }
}

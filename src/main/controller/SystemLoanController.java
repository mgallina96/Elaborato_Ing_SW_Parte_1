package main.controller;
import main.model.database.LoanDatabase;
import main.model.database.MediaDatabase;
import main.model.database.UserDatabase;
import main.model.loan.Loan;
import main.model.media.Media;
import main.model.user.User;
import main.utility.exceptions.ExtensionDateOutOfBoundsException;
import main.utility.exceptions.ExtensionLimitReachedException;
import main.utility.exceptions.LoanNotFoundException;
import main.utility.notifications.Notifications;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Logger;

import static main.model.database.DatabaseIO.saveDatabase;
import static main.utility.GlobalParameters.*;

/**
 * Controller for loan management and operations.
 *
 * @author Giosuè Filippini, Manuel Gallina, Alessandro Polcini
 */
public class SystemLoanController implements LoanController {

    private static SystemLoanController instance;
    private LoanDatabase loanDatabase;
    private UserDatabase userDatabase;
    private MediaDatabase mediaDatabase;
    private Logger logger;

    private SystemLoanController() {
        loanDatabase = LoanDatabase.getInstance();
        userDatabase = UserDatabase.getInstance();
        mediaDatabase = MediaDatabase.getInstance();

        for(int ID : loanDatabase.sweep())
            mediaDatabase.fetch(new Media(ID)).giveBack();

        //saving sweep changes
        saveDatabase(USER_DATABASE_FILE_PATH, userDatabase);
        saveDatabase(MEDIA_DATABASE_FILE_PATH, mediaDatabase);
        saveDatabase(LOAN_DATABASE_FILE_PATH, loanDatabase);

        logger = Logger.getLogger(this.getClass().getName());
    }

    //returns the instance of this controller.
    public static SystemLoanController getInstance() {
        if(instance == null)
            instance = new SystemLoanController();

        return instance;
    }

    @Override
    public boolean addLoanToDatabase(int mediaID) {
        Media toLend = mediaDatabase.fetch(new Media(mediaID));

        if(toLend.isAvailable()) {
            loanDatabase.addLoan(userDatabase.getCurrentUser(), toLend);
            saveDatabase(MEDIA_DATABASE_FILE_PATH, mediaDatabase);
            saveDatabase(USER_DATABASE_FILE_PATH, userDatabase);
            saveDatabase(LOAN_DATABASE_FILE_PATH, loanDatabase);
            return true;
        }

        return false;
    }

    @Override
    public boolean canBorrow(int mediaID) {
        int counter = 0;
        Media media = mediaDatabase.fetch(new Media(mediaID));

        try {
            for(Loan l : loanDatabase.getUserLoans(userDatabase.getCurrentUser()))
                if(l.getMedia().getType().equals(media.getType()))
                    counter++;
        }
        catch(Exception ex) {
            return true;
        }

        return counter < media.getLoanLimit();
    }

    @Override
    public void extendLoan(int mediaID) throws LoanNotFoundException,
            ExtensionDateOutOfBoundsException, ExtensionLimitReachedException {

        Loan toBeExtended = loanDatabase.fetchLoan(userDatabase.getCurrentUser(), mediaID);

        if(toBeExtended.canBeExtended()) {
            GregorianCalendar correctedLoanExpiry = (GregorianCalendar)toBeExtended.getLoanExpiry().clone();
            correctedLoanExpiry.add(Calendar.DATE, -toBeExtended.getExtensionRestrictionInDays());

            if(new GregorianCalendar().after(correctedLoanExpiry)) {
                toBeExtended.extend();
                saveDatabase(LOAN_DATABASE_FILE_PATH, loanDatabase);
            }
            else
                throw new ExtensionDateOutOfBoundsException();
        }
        else
            throw new ExtensionLimitReachedException();
    }

    @Override
    public String allLoansToString() {
        return loanDatabase.getLoanListString();
    }

    @Override
    public String currentUserLoansToString() {
        return loanDatabase.getUserLoansToString(userDatabase.getCurrentUser());
    }

    @Override
    public String getLoanNumberByYear(int from, int to) {
        StringBuilder loansByYear = new StringBuilder();
        loansByYear.append(Notifications.getMessage("MSG_LOANS_IN_YEAR")).append("\n");

        for(int year = from; year <= to; year++)
            loansByYear.append(year).append(": ").append(loanDatabase.getLoanNumberByYear(year)).append("\n");

        return loansByYear.toString();
    }

    @Override
    public String getUserLoanNumberByYear(int from, int to) {
        StringBuilder loansByYear = new StringBuilder();
        loansByYear.append(Notifications.getMessage("MSG_USER_LOANS_IN_YEAR")).append("\n");

        for(int year = from; year <= to; year++) {
            loansByYear.append(year).append(":\n");

            HashMap<String, Integer> userLoansByYear = loanDatabase.getUserLoanNumberByYear(year);
            for(String username : userLoansByYear.keySet())
                loansByYear.append("\t").append(username).append(": ").append(userLoansByYear.get(username)).append("\n");
        }

        return loansByYear.toString();
    }

    @Override
    public String getExtensionNumberByYear(int from, int to) {
        StringBuilder extensionsByYear = new StringBuilder();
        extensionsByYear.append(Notifications.getMessage("MSG_EXTENSIONS_IN_YEAR")).append("\n");

        for(int year = from; year <= to; year++)
            extensionsByYear.append(year).append(": ").append(loanDatabase.getExtensionNumberByYear(year)).append("\n");

        return extensionsByYear.toString();
    }

    @Override
    public String getMostLentMediaByYear(int from, int to){
        StringBuilder mostLentMedia = new StringBuilder();
        mostLentMedia.append(Notifications.getMessage("MSG_MOST_LENT_MEDIA_IN_YEAR")).append("\n");

        for(int year = from; year <= to; year++) {
            if(loanDatabase.getMostLentMediaByYear(year) == null) {
                mostLentMedia.append(year).append(": ").append("/").append("\n");
            }
            else {
                Media m = mediaDatabase.fetch(new Media(loanDatabase.getMostLentMediaByYear(year)));
                mostLentMedia.append(year).append(": ").append(m.getBareItemDetails()).append("\t-\t").append(m.getType()).append("\n");
            }
        }

        return mostLentMedia.toString();
    }
}

package main.model.database;

import main.model.loan.Loan;
import main.model.media.Media;
import main.model.user.User;
import main.utility.exceptions.UserNotFoundException;
import main.utility.notifications.Notifications;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.utility.GlobalParameters.LOAN_DATABASE_FILE_PATH;


/**
 * Database which contains a record of all loans.
 */
public class LoanDatabase implements Serializable, Database {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -2599493317418350651L;

    private HashMap<String, ArrayList<Loan>> loans;
    private static LoanDatabase loanDatabase;
    private transient Logger logger;

    //Singleton Database constructor, private to prevent instantiation.
    private LoanDatabase() {
        this.loans = new HashMap<>();
        logger = Logger.getLogger(this.getClass().getName());

        sweep();
    }

    public static LoanDatabase getInstance() {
        if(loanDatabase == null)
            loanDatabase = new LoanDatabase();

        return loanDatabase;
    }

    public void addLoan(User user, Media media) {
        media.lend();

        if(loans.get(user.getUsername()) == null) {
            ArrayList<Loan> firstLoan = new ArrayList<>();
            firstLoan.add(new Loan(user, media));
            loans.put(user.getUsername(), firstLoan);
        }
        else
            loans.get(user.getUsername()).add(new Loan(user, media));
    }

    public String getLoanListString() {
        StringBuilder loanList = new StringBuilder();

        loans.forEach((u, l) -> {
            loanList.append("User <").append(u).append(">\n");
            l.forEach(loan -> loanList
                    .append("\t- ").append(loan.getMedia().getType())
                    .append(" - ").append(loan.getMedia().getBareItemDetails())
                    .append("\n"));
        });

        return loanList.toString();
    }

    public String getUserLoansToString(User user) {
        try {
            StringBuilder userLoans = new StringBuilder();
            getUserLoans(user)
                    .forEach(s -> userLoans.append(s.getMedia()));

            return userLoans.toString();

        }
        catch(NullPointerException npEx) {
            return "";
        }
        catch(UserNotFoundException unfEx) {
            return "";
        }
    }

    public ArrayList<Loan> getUserLoans(User user) throws UserNotFoundException {
        try {
            return this.loans.get(user.getUsername());
        }
        catch(NullPointerException npEx) {
            throw new UserNotFoundException();
        }
    }

    public int getLoanNumberByYear(int year) {
        int counter = 0;

        for(ArrayList<Loan> loanValues : loans.values()) {
            for(Loan l : loanValues)
                if(l.getLoanDate().get(Calendar.YEAR) == year)
                    counter++;
        }

        return counter;
    }

    public HashMap<String, Integer> getUserLoanNumberByYear(int year) {
       HashMap<String, Integer> userLoanByYear = new HashMap<>();

        for(String username : loans.keySet()) {
            int counter = 0;
            for (Loan loan : loans.get(username))
                if (loan.getLoanDate().get(Calendar.YEAR) == year)
                    counter++;
            userLoanByYear.put(username, counter);

        }

        return userLoanByYear;
    }

    public int getExtensionNumberByYear(int year) {
        int counter = 0;

        for(ArrayList<Loan> loanValues : loans.values()) {
            for(Loan l : loanValues)
                for(GregorianCalendar extension : l.getExtensionDates())
                    if(extension.get(Calendar.YEAR) == year)
                        counter++;
        }

        return counter;
    }

    public Integer getMostLentMediaByYear(int year){
        HashMap<Integer, Integer> mediaCounter = new HashMap<>();
        for(ArrayList<Loan> loanValues : loans.values())
            for(Loan l: loanValues){
                if(l.getLoanDate().get(Calendar.YEAR) == year) {
                    mediaCounter.computeIfPresent(l.getMedia().getIdentifier(), (k, v) -> v + 1);
                    mediaCounter.putIfAbsent(l.getMedia().getIdentifier(), 1);
                }
            }

        Integer maxId = 0;
        for(Integer id : mediaCounter.keySet())
            maxId = id;

        for(Integer id : mediaCounter.keySet())
            if(mediaCounter.get(id) > mediaCounter.get(maxId))
                maxId = id;

        return maxId == 0 ? null : maxId;
    }

    private static void setLoanDatabase(LoanDatabase loanDatabase) {
        LoanDatabase.loanDatabase = loanDatabase;
    }

    public HashMap<String, ArrayList<Loan>> getLoansList() {
        return loans;
    }

    /**
     * Opens a .ser serializable file and loads its contents into this {@link LoanDatabase} class.<p>
     * This method loads a {@code HashMap} containing a record of all loans.
     */
    @SuppressWarnings("unchecked")
    private void loadLoanDatabase() {
        try (
            FileInputStream fileIn = new FileInputStream(LOAN_DATABASE_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn)
        ) {
            setLoanDatabase((LoanDatabase)in.readObject());
        }
        catch(FileNotFoundException fnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_FILE_NOT_FOUND") + this.getClass().getName());
        }
        catch(IOException ioEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_LOADING_DATABASE") + this.getClass().getName());
        }
        catch(ClassNotFoundException cnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_CLASS_NOT_FOUND") + this.getClass().getName());
        }
    }

    private void sweep() {
        for(ArrayList<Loan> al : loans.values())
            for(Loan l : al) {
                if(l.hasExpired()) {
                    l.getMedia().giveBack();
                    l.setActive(false);
                }
            }
    }

    static String getPath() {
        return LOAN_DATABASE_FILE_PATH;
    }
}

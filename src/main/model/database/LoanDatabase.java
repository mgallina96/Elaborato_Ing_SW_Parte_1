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

    //LoanDatabase initializer.
    private LoanDatabase() {
        this.loans = new HashMap<>();

        sweep();
    }

    /**
     * Getter/initializer for the {@code LoanDatabase} singleton class.
     * @return The {@code LoanDatabase} instance.
     */
    public static LoanDatabase getInstance() {
        if(loanDatabase == null)
            loanDatabase = new LoanDatabase();

        return loanDatabase;
    }

    /**
     * Adds a {@link Loan} object to the database.
     *
     * @param user The user who requested the loan.
     * @param media The media item that has been lent.
     */
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

    /**
     * Builds a {@code String} with the list of all loans sorted and grouped by user.
     * @return A {@code String} containing all loans by user.
     */
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

    /**
     * Builds a {@code String} containing the loans of a given user.
     *
     * @param user The user whose loans are to be shown.
     * @return A {@code String} containing the {@code user}'s loans.
     */
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

    /**
     * Getter for the {@code ArrayList} containing the loans of a given user.
     *
     * @param user The user.
     * @return The {@code ArrayList} containing the {@code user}'s loans.
     * @throws UserNotFoundException If the user is not present in the database.
     */
    public ArrayList<Loan> getUserLoans(User user) throws UserNotFoundException {
        try {
            return this.loans.get(user.getUsername());
        }
        catch(NullPointerException npEx) {
            throw new UserNotFoundException();
        }
    }

    /**
     * Calculates the general number of loans that have been granted in a given year.
     *
     * @param year The year.
     * @return An {@code integer} representing the general number of loans that have been granted in the given year.
     */
    public int getLoanNumberByYear(int year) {
        int counter = 0;

        for(ArrayList<Loan> loanValues : loans.values()) {
            for(Loan l : loanValues)
                if(l.getLoanDate().get(Calendar.YEAR) == year)
                    counter++;
        }

        return counter;
    }

    /**
     * Calculates the specific number of loans that have been granted to each user, in a given {@code year}.
     *
     * @param year The year.
     * @return A {@code HashMap} containing the number of loans by user for a given {@code year}.
     */
    public HashMap<String, Integer> getUserLoanNumberByYear(int year) {
       HashMap<String, Integer> userLoanByYear = new HashMap<>();

        for(String username : loans.keySet()) {
            int counter = 0;
            for(Loan loan : loans.get(username))
                if(loan.getLoanDate().get(Calendar.YEAR) == year)
                    counter++;
            userLoanByYear.put(username, counter);

        }

        return userLoanByYear;
    }

    /**
     * Calculates the specific number of loan extensions that occurred in a given {@code year}.
     *
     * @param year The year.
     * @return An {@code integer} representing the number of loan extensions in the given {@code year}.
     */
    public int getExtensionNumberByYear(int year) {
        int counter = 0;

        for (ArrayList<Loan> loanValues : loans.values()) {
            for (Loan l : loanValues) {
                GregorianCalendar extension = l.getExtensionDates();
                if (extension.get(Calendar.YEAR) == year)
                    counter++;
            }
        }
        return counter;
    }

    /**
     * Finds the ID of the most borrowed media item in a given {@code year}.
     *
     * @param year The year.
     * @return An {@code Integer} representing the ID of the most borrowed media item in the given {@code year}.
     */
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

    /**
     * Opens a .ser serializable file and loads its contents into this {@link LoanDatabase} class.<p>
     * This method loads a {@code HashMap} containing a record of all loans.
     */
    @SuppressWarnings("unchecked")
/*    private void loadLoanDatabase() {
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
*/
    //sweeps the database, looking for invalid entries and deleting them.
    private void sweep() {
        for(ArrayList<Loan> al : loans.values())
            for(Loan l : al) {
                if(l.hasExpired()) {
                    l.getMedia().giveBack();
                    l.setActive(false);
                }
            }
    }

    /**
     * Getter for the path where the loan database is located.
     * @return The Loan Database path.
     */
    static String getPath() {
        return LOAN_DATABASE_FILE_PATH;
    }
}

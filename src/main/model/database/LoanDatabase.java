package main.model.database;
import com.sun.xml.internal.bind.v2.model.core.ID;
import main.model.loan.Loan;
import main.model.media.Media;
import main.model.user.User;
import main.utility.notifications.Notifications;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database which contains a record of all loans.
 */
public class LoanDatabase implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -2599493317418350651L;

    private static final String LOAN_DATABASE_FILE_PATH = "application_resources\\Biblioteca SMARTINATOR - Loan Database.ser";
    private HashMap<String, ArrayList<Loan>> loans;
    private static LoanDatabase loanDatabase;
    private Logger logger;

    //Singleton Database constructor, private to prevent instantiation.
    private LoanDatabase() {
        this.loans = new HashMap<>();
        logger = Logger.getLogger(this.getClass().getName());
        loadLoanDatabase();
    }

    static LoanDatabase getInstance() {
        if(loanDatabase == null)
            loanDatabase = new LoanDatabase();

        return loanDatabase;
    }
    
    public String getUserLoans(User user) {
        StringBuilder userList = new StringBuilder();

        loans.get(user.getUsername())
                .forEach(loans -> userList.append(loans.getMedia().toString()));
        
        return userList.toString();
    }

    void addLoan(User user, Media media) {
        media.lend();
        loans.get(user.getUsername()).add(new Loan(user, media));
    }

    HashMap<String, ArrayList<Loan>> getLoansList() {
        return loans;
    }
    
    void setLoanList(HashMap<String, ArrayList<Loan>> loans) {
        this.loans = loans;
    }

    /**
     * Opens a .ser serializable file and loads its contents into this {@link LoanDatabase} class.<p>
     * This method loads a {@code HashMap} containing a record of all loans.
     */
    @SuppressWarnings("unchecked")
    void loadLoanDatabase() {
        try {
            FileInputStream fileIn = new FileInputStream(LOAN_DATABASE_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            this.loans = ((HashMap<String, ArrayList<Loan>>) in.readObject());

            in.close();
            fileIn.close();
        }
        catch(FileNotFoundException FNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_FILE_NOT_FOUND + this.getClass().getName());
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_LOADING_DATABASE + this.getClass().getName(), IOEx);
        }
        catch(ClassNotFoundException CNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_CLASS_NOT_FOUND + this.getClass().getName());
        }
    }

    /**
     * Saves the loan database in the form of a HashMap object.
     */
    void saveLoanDatabase() {
        //TODO eliminare ripetizione codice
        try {
            //to increase serializing speed
            RandomAccessFile raf = new RandomAccessFile(LOAN_DATABASE_FILE_PATH, "rw");

            FileOutputStream fileOut = new FileOutputStream(raf.getFD());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(loans);

            out.close();
            fileOut.close();
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_SAVING_DATABASE + this.getClass().getName(), IOEx);
        }
    }

    private void sweep() {
        for(ArrayList<Loan> al : loans.values())
            for(Loan l : al)
                if(l.hasExpired()) {
                    l.getMedia().giveBack();
                    al.remove(l);
                }
    }

}

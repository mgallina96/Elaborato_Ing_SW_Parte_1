package main.model.database.loan;
import main.model.database.filesystem.Folder;
import main.model.media.Media;
import main.model.user.User;
import main.utility.notifications.Notifications;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoanSystem implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -2599493317418350651L;

    private static final String LOAN_SYSTEM_FILE_PATH = "application_resources\\Biblioteca SMARTINATOR - Loan System.ser";
    private static final int MAX_LENT_MEDIA_ITEMS = 3;
    private HashMap<String, ArrayList<Loan>> loans;
    private static LoanSystem loanSystem;
    private Logger logger;

    //Singleton Database constructor, private to prevent instantiation.
    private LoanSystem() {
        this.loans = new HashMap<>();
        logger = Logger.getLogger(this.getClass().getName());
        loadLoanSystem();

        sweep();
    }

    static LoanSystem getInstance() {
        if(loanSystem == null)
            loanSystem = new LoanSystem();

        return loanSystem;
    }
    
    public String getUserLoans(User user) {
        StringBuilder userList = new StringBuilder();

        loans.get(user.getUsername())
                .forEach(loans -> userList.append(loans.getMedia().toString()));
        
        return userList.toString();
    }
    
    public boolean addLoan(User user, Media media) {
        String username = user.getUsername();
        
        if(!loans.containsKey(username)) {
            ArrayList<Loan> a = new ArrayList<>();
            a.add(new Loan(user, media));
            loans.put(username, a);
        }
        else {
            if(loans.get(username).size() > MAX_LENT_MEDIA_ITEMS)
                return false;
            else
                loans.get(username).add(new Loan(user, media));
        }

        saveLoanSystem();
        return true;
    }

    HashMap<String, ArrayList<Loan>> getLoansList() {
        return loans;
    }
    
    void setLoanList(HashMap<String, ArrayList<Loan>> loans) {
        this.loans = loans;
    }

    @SuppressWarnings("unchecked")
    private void loadLoanSystem() {
        try {
            FileInputStream fileIn = new FileInputStream(LOAN_SYSTEM_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            this.loans = ((HashMap<String, ArrayList<Loan>>) in.readObject());

            in.close();
            fileIn.close();
        }
        catch(FileNotFoundException FNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_FILE_NOT_FOUND);
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_LOADING_FILESYSTEM, IOEx);
        }
        catch(ClassNotFoundException CNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_FILESYSTEM_CLASS_NOT_FOUND);
        }
    }

    /**
     * Saves the File System in the form of a HashMap object.
     */
    private void saveLoanSystem() {
        try {
            //to increase serializing speed
            RandomAccessFile raf = new RandomAccessFile(LOAN_SYSTEM_FILE_PATH, "rw");

            FileOutputStream fileOut = new FileOutputStream(raf.getFD());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(loans);

            out.close();
            fileOut.close();
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_SAVING_DATABASE, IOEx);
        }
    }

    private void sweep() {
        for(ArrayList<Loan> al : loans.values())
            for(Loan l : al)
                if(l.hasExpired())
                    al.remove(l);
    }
}

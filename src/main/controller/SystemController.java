package main.controller;
import main.model.database.Database;
import main.model.database.LoanDatabase;
import main.model.database.MediaDatabase;
import main.model.database.UserDatabase;
import main.model.loan.Loan;
import main.model.media.Book;
import main.model.media.Media;
import main.model.user.Customer;
import main.model.user.User;
import main.utility.exceptions.UserNotFoundException;
import main.utility.exceptions.WrongPasswordException;
import main.utility.notifications.Notifications;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.utility.GlobalParameters.*;
import static main.utility.notifications.Notifications.ERR_NO_LOANS_IN_YEAR;

/**
 * Controller class that manages any kind of interaction between the graphical user interface
 * and the logic (model) of the program.
 *
 * @author Manuel Gallina
 * @since version 0.1 - 12/03/2018
 */
public class SystemController implements UserController, MediaController, LoanController {

    private static SystemController instance;

    private UserDatabase userDatabase;
    private MediaDatabase mediaDatabase;
    private LoanDatabase loanDatabase;
    private Logger logger;

    //Singleton constructor, private to prevent instantiation.
    private SystemController() {
        userDatabase = UserDatabase.getInstance();
        loadDatabase(USER_DATABASE_FILE_PATH);
        mediaDatabase = MediaDatabase.getInstance();
        loadDatabase(MEDIA_DATABASE_FILE_PATH);
        loanDatabase = LoanDatabase.getInstance();
        loadDatabase(LOAN_DATABASE_FILE_PATH);

        logger = Logger.getLogger(this.getClass().getName());
    }

    //Returns the instance of the controller.
    public static SystemController getInstance() {
        if(instance == null)
            instance = new SystemController();

        return instance;
    }

    @Override
    public boolean checkUserLogin(String username, String password) throws UserNotFoundException, WrongPasswordException {
        User toCheck = userDatabase.fetchUser(new User(username));

        if(toCheck == null)
            throw new UserNotFoundException();

        if(toCheck.getPassword().equals(password)){
            userDatabase.setCurrentUser(toCheck);
            return true;
        } else
            throw new WrongPasswordException();

    }

    public boolean userIsPresent(String username) {
        return userDatabase.isPresent(new User(username));
    }

    public boolean mediaIsPresent(int id) {
        return mediaDatabase.isPresent(new Media(id));
    }

    public boolean addUserToDatabase(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        Customer c = new Customer(firstName, lastName, username, password, birthday);

        if(!userDatabase.isPresent(c)) {
            userDatabase.addUser(c);
            saveDatabase(USER_DATABASE_FILE_PATH, userDatabase);
            return true;
        }

        return false;
    }

    public boolean addMediaToDatabase(String title, String author, String genre, int publicationYear, String publisherName, String path) {
        Book b = new Book(title, author, genre, publicationYear, publisherName);

        if(!mediaDatabase.isMatchingMedia(b)) {
            mediaDatabase.addMedia(b, path);
            saveDatabase(MEDIA_DATABASE_FILE_PATH, mediaDatabase);
            return true;
        }

        return false;
    }

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

    public void removeMediaFromDatabase(int id) {
        mediaDatabase.removeMedia(new Media(id));
    }

    public int getUserStatus(String username) {
        if(username == null)
            return -1;

        switch(userDatabase.fetchUser(new User(username)).getUserStatus()) {
            case CUSTOMER:
                return 0;
            case OPERATOR:
                return 1;
            default:
                return -1;
        }
    }

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
    public boolean canBeExtended(int mediaID) {
        try {
            for(Loan l : loanDatabase.getUserLoans(userDatabase.getCurrentUser())) {
                if(l.getMedia().getIdentifier() == mediaID) {
                    GregorianCalendar correctedLoanExpiry = (GregorianCalendar) l.getLoanExpiry().clone();
                    correctedLoanExpiry.add(Calendar.DATE, -l.getExtensionRestrictionInDays());

                    return (new GregorianCalendar()).after(correctedLoanExpiry);
                }
            }
        }
        catch(Exception ex) {
            return true;
        }

        return false;
    }

    public void extendLoan(int mediaID) {
        try {
            for(Loan l : loanDatabase.getUserLoans(userDatabase.getCurrentUser())) {
                if(l.getMedia().getIdentifier() == mediaID) {
                    l.extend();
                    break;
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public int daysLeftToRenew(String username) throws UserNotFoundException {
        try {
            User user = userDatabase.fetchUser(new User(username));

            int days = (int)Math.abs(ChronoUnit.DAYS.between(
                    new GregorianCalendar().toInstant(),
                    ((Customer)user).getExpiryDate().toInstant()));

            if(days <= RENEWAL_BOUNDARY_IN_DAYS)
                return days;
        }
        catch(ClassCastException ccEx) {
            return 0;
        }
        catch(NullPointerException npEx) {
            throw new UserNotFoundException();
        }

        return 0;
    }

    public boolean renewSubscription() {
        try {
            Customer customer = (Customer) userDatabase.getCurrentUser();

            GregorianCalendar correctedExpiryDate = (GregorianCalendar) (customer.getExpiryDate().clone());
            correctedExpiryDate.add(Calendar.DATE, -RENEWAL_BOUNDARY_IN_DAYS);

            if (new GregorianCalendar().after(correctedExpiryDate)) {
                customer.renewSubscription();
                return true;
            }
        }
        catch(ClassCastException ccEx) {
            throw new IllegalArgumentException();
        }

        return false;
    }

    public String dateDetails() {
        User u = userDatabase.getCurrentUser();
        return (u instanceof Customer) ?
                String.format("Reminder:\n\tYou subscribed on %s\n\tYour subscription expires on %s\n\tYou're not " +
                                "allowed to renew your subscription until 10 days before the expiry date.",
                        ((Customer)u).getSubscriptionDate().toZonedDateTime().toString().substring(0, 10),
                        ((Customer)u).getExpiryDate().toZonedDateTime().toString().substring(0, 10)) :
                "";
    }

    public void logout() {
        userDatabase.removeCurrentUser();
    }

    public String allUsersToString() {
        return userDatabase.getUserListString();
    }

    public String allLoansToString() {
        return loanDatabase.getLoanListString();
    }

    @Override
    public String currentUserLoansToString() {
        return loanDatabase.getUserLoansToString(userDatabase.getCurrentUser());
    }

    public String allFilteredMediaList(String filter) {
        return mediaDatabase.getFilteredMediaList(filter);
    }

    public String getFolderContents(String folderPath) {
        return mediaDatabase.getFolderContents(folderPath);
    }

    private <D extends Database> void saveDatabase(String path, @NotNull D database) {
        try (
                //to increase serializing speed
                RandomAccessFile raf = new RandomAccessFile(path, "rw");
                FileOutputStream fileOut = new FileOutputStream(raf.getFD());
                ObjectOutputStream out = new ObjectOutputStream(fileOut)
        ) {
            out.writeObject(database);
        }
        catch(IOException ioEx) {
            logger.log(Level.SEVERE, Notifications.ERR_SAVING_DATABASE + database.getClass().getName());
        }
    }

    /**
     * Opens a .ser serializable file and loads its contents into this {@link UserDatabase} class.<p>
     * This method loads a {@code HashMap} containing all subscribed users.
     */
    @SuppressWarnings("unchecked")
    private void loadDatabase(String path) {
        try (
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn)
        ) {
            Object database = in.readObject();

            if(database instanceof UserDatabase)
                userDatabase = (UserDatabase)database;
            else if(database instanceof MediaDatabase)
                mediaDatabase = (MediaDatabase)database;
            else if(database instanceof LoanDatabase)
                loanDatabase = (LoanDatabase)database;
        }
        catch(FileNotFoundException fnfEx) {
            logger.log(Level.SEVERE, Notifications.ERR_FILE_NOT_FOUND + this.getClass().getName());
        }
        catch(IOException ioEx) {
            logger.log(Level.SEVERE, Notifications.ERR_LOADING_DATABASE + this.getClass().getName());
        }
        catch(ClassNotFoundException cnfEx) {
            logger.log(Level.SEVERE, Notifications.ERR_CLASS_NOT_FOUND + this.getClass().getName());
        }
    }

    public String getLoansByYear(int year) {
        StringBuilder loansByYear = new StringBuilder();
        int len;

        for(ArrayList<Loan> loans : loanDatabase.getLoansList().values()) {
            len = loans.size();
            Loan currentLoan = loans.get(0);
            if(currentLoan.getLoanDate().get(Calendar.YEAR) == year) {
                loansByYear.append("User <").append(currentLoan.getUser().getUsername()).append(">\n");
                loansByYear.append("\t").append(currentLoan.toEssentialString());
            }

            for(int i = 1; i < len; i++) {
                currentLoan = loans.get(i);
                if(currentLoan.getLoanDate().get(Calendar.YEAR) == year) {
                    loansByYear.append("\t").append(currentLoan.toEssentialString());
                }
            }
        }

        return loansByYear.length() > 0 ? loansByYear.toString() : (ERR_NO_LOANS_IN_YEAR + year);
    }
}

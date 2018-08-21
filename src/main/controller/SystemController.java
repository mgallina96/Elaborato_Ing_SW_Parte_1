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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.utility.GlobalParameters.*;

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
        }
        else
            throw new WrongPasswordException();

    }

    @Override
    public boolean userIsPresent(String username) {
        return userDatabase.isPresent(new User(username));
    }

    @Override
    public boolean mediaIsPresent(int id) {
        return mediaDatabase.isPresent(new Media(id));
    }

    @Override
    public boolean addUserToDatabase(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        Customer c = new Customer(firstName, lastName, username, password, birthday);

        if(!userDatabase.isPresent(c)) {
            userDatabase.addUser(c);
            saveDatabase(USER_DATABASE_FILE_PATH, userDatabase);
            return true;
        }

        return false;
    }

    @Override
    public boolean addMediaToDatabase(String title, String author, String genre, int publicationYear, String publisherName, String path) {
        Book b = new Book(title, author, genre, publicationYear, publisherName);

        if(!mediaDatabase.isMatchingMedia(b)) {
            mediaDatabase.addMedia(b, path);
            saveDatabase(MEDIA_DATABASE_FILE_PATH, mediaDatabase);
            return true;
        }

        return false;
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
    public void removeMediaFromDatabase(int id) {
        mediaDatabase.removeMedia(new Media(id));
    }

    @Override
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

    @Override
    public void extendLoan(int mediaID) {
        try {
            for(Loan l : loanDatabase.getUserLoans(userDatabase.getCurrentUser())) {
                if(l.getMedia().getIdentifier() == mediaID) {
                    if(l.isValidExtension()) {
                        l.extend();
                        break;
                    }
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int daysLeftToRenew(String username) throws UserNotFoundException {
        try {
            User user = userDatabase.fetchUser(new User(username));

            int days = (int)Math.abs(ChronoUnit.DAYS.between(
                    new GregorianCalendar().toInstant(),
                    ((Customer)user).getExpiryDateGregorian().toInstant()));

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

    @Override
    public boolean renewSubscription() {
        try {
            Customer customer = (Customer) userDatabase.getCurrentUser();

            GregorianCalendar correctedExpiryDate = (GregorianCalendar) (customer.getExpiryDateGregorian().clone());
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

    @Override
    public String dateDetails() {
        User u = userDatabase.getCurrentUser();
        return (u instanceof Customer) ?
                String.format("Reminder:\n\tYou subscribed on %s\n\tYour subscription expires on %s\n\tYou're not " +
                                "allowed to renew your subscription until 10 days before the expiry date.",
                        ((Customer)u).getSubscriptionDate(),
                        ((Customer)u).getExpiryDate()) :
                "";
    }

    @Override
    public void logout() {
        userDatabase.removeCurrentUser();
    }

    @Override
    public String allUsersToString() {
        return userDatabase.getUserListString();
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
    public String allFilteredMediaList(String filter) {
        return mediaDatabase.getFilteredMediaList(filter);
    }

    @Override
    public String getFolderContents(String folderPath) {
        return mediaDatabase.getFolderContents(folderPath);
    }

    @Override
    public String getLoanNumberByYear(int from, int to) {
        StringBuilder loansByYear = new StringBuilder();

        loansByYear
                .append(Notifications.getMessage("MSG_LOANS_IN_YEAR"))
                .append("\n");

        for(int year = from; year <= to; year++)
            loansByYear
                    .append(year)
                    .append(": ")
                    .append(loanDatabase.getLoanNumberByYear(year))
                    .append("\n");

        return loansByYear.toString();
    }

    @Override
    public String getUserLoanNumberByYear(int from, int to) {
        StringBuilder loansByYear = new StringBuilder();

        loansByYear
                .append(Notifications.getMessage("MSG_USER_LOANS_IN_YEAR"))
                .append("\n");

        for(int year = from; year <= to; year++) {
            loansByYear
                    .append(year)
                    .append(":\n");

            HashMap<String, Integer> userLoansByYear = loanDatabase.getUserLoanNumberByYear(year);
            for(String username : userLoansByYear.keySet())
                loansByYear
                    .append("\t")
                    .append(username)
                    .append(": ")
                    .append(userLoansByYear.get(username))
                    .append("\n");
        }

        return loansByYear.toString();
    }

    @Override
    public String getExtensionNumberByYear(int from, int to) {
        StringBuilder extensionsByYear = new StringBuilder();

        extensionsByYear.append(Notifications.getMessage("MSG_EXTENSIONS_IN_YEAR")).append("\n");

        for(int year = from; year <= to; year++)
            extensionsByYear
                    .append(year)
                    .append(": ")
                    .append(loanDatabase.getExtensionNumberByYear(year))
                    .append("\n");

        return extensionsByYear.toString();
    }

    @Override
    public String getMostLentMediaByYear(int from, int to){
        StringBuilder mostLentMedia = new StringBuilder();
        mostLentMedia.append(Notifications.getMessage("MSG_MOST_LENT_MEDIA_IN_YEAR")).append("\n");

        for(int year = from; year <= to; year++) {
            if(loanDatabase.getMostLentMediaByYear(year) != null) {
                Media m = mediaDatabase.fetch(new Media(loanDatabase.getMostLentMediaByYear(year)));
                mostLentMedia
                        .append(year)
                        .append(": ")
                        .append(m.getBareItemDetails())
                        .append("\t-\t")
                        .append(m.getType())
                        .append("\n");
            }
        }

        return mostLentMedia.toString();
    }

    @Override
    public String getCurrentUserName() {
        return userDatabase.getCurrentUser().getUsername();
    }

    //saves a generic HashMap (database) to a serializable .ser file.
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
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_SAVING_DATABASE") + database.getClass().getName());
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
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_FILE_NOT_FOUND") + this.getClass().getName());
        }
        catch(IOException ioEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_LOADING_DATABASE") + this.getClass().getName());
        }
        catch(ClassNotFoundException cnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_CLASS_NOT_FOUND") + this.getClass().getName());
        }
    }

}

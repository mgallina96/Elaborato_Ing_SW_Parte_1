package main.controller;
import main.model.database.*;
import main.model.loan.Loan;
import main.utility.exceptions.UserNotFoundException;
import main.utility.exceptions.WrongPasswordException;
import main.gui.GuiManager;
import main.model.media.Book;
import main.model.media.Media;
import main.model.user.Customer;
import main.model.user.User;
import main.utility.notifications.Notifications;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.time.temporal.ChronoUnit;
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

    private GuiManager guiManager;
    private UserDatabase userDatabase;
    private MediaDatabase mediaDatabase;
    private LoanDatabase loanDatabase;
    private Logger logger;

    //Singleton constructor, private to prevent instantiation.
    private SystemController() {
        userDatabase = UserDatabase.getInstance();
        mediaDatabase = MediaDatabase.getInstance();
        loanDatabase = LoanDatabase.getInstance();

        logger = Logger.getLogger(this.getClass().getName());

        //guiManager = new TextualView(this);
//        guiManager = new GraphicView(this);
    }

    //Returns the instance of the controller.
    public static SystemController getInstance() {
        if(instance == null)
            instance = new SystemController();

        return instance;
    }

    public void init() {
        guiManager.mainScreen();
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

    public String allUsersToString() {
        return userDatabase.getUserListString();
    }

    public String allLoansToString() {
        return loanDatabase.getLoanListString();
    }

    public String allFilteredMediaList(String filter) {
        return mediaDatabase.getFilteredMediaList(filter);
    }

    public boolean addUserToDatabase(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        Customer c = new Customer(firstName, lastName, username, password, birthday);

        if(!userDatabase.isPresent(c)) {
            userDatabase.addUser(c);
            return true;
        }

        return false;
    }

    public boolean addMediaToDatabase(String title, String author, String genre, int publicationYear, String publisherName, String path) {
        Book b = new Book(title, author, genre, publicationYear, publisherName);

        if(!mediaDatabase.isMatchingMedia(b)) {
            mediaDatabase.addMedia(b, path);
            return true;
        }

        return false;
    }

    public boolean addLoanToDatabase(int mediaID) {
        Media toLend = mediaDatabase.fetch(new Media(mediaID));

        if(toLend.isAvailable()) {
            loanDatabase.addLoan(userDatabase.getCurrentUser(), toLend);
            mediaDatabase.saveMediaDatabase();
            saveHashMap(USER_DATABASE_FILE_PATH, userDatabase.getUserList());
            saveHashMap(LOAN_DATABASE_FILE_PATH, loanDatabase.getLoansList());
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
        catch(Exception e) {
            return true;
        }

        return counter < media.getLoanLimit();
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
        catch(ClassCastException CCEx) {
            return 0;
        }
        catch(NullPointerException NPEx) {
            throw new UserNotFoundException();
        }

        return 0;
    }

    public boolean renewSubscription() throws IllegalArgumentException {
        if(userDatabase.getCurrentUser() instanceof Customer)
            return ((Customer)userDatabase.getCurrentUser()).renewSubscription();
        else
            throw new IllegalArgumentException();
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

    public String getFolderContents(String folderPath) {
        return mediaDatabase.getFolderContents(folderPath);
    }

    private void saveHashMap(String path, HashMap h) {
        try {
            //to increase serializing speed
            RandomAccessFile raf = new RandomAccessFile(path, "rw");

            FileOutputStream fileOut = new FileOutputStream(raf.getFD());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(h);

            out.close();
            fileOut.close();
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_SAVING_DATABASE + this.getClass().getName(), IOEx);
        }
    }

}

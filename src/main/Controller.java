package main;
import main.gui.graphic.GraphicView;
import main.model.loan.Loan;
import main.model.user.Operator;
import main.model.user.UserConstants;
import main.utility.exceptions.UserNotFoundException;
import main.utility.exceptions.WrongPasswordException;
import main.gui.GuiManager;
import main.gui.textual.TextualView;
import main.model.database.Database;
import main.model.database.DatabaseManager;
import main.model.database.filesystem.FileSystem;
import main.model.media.Book;
import main.model.media.Media;
import main.model.user.Customer;
import main.model.user.User;

import java.time.temporal.ChronoUnit;
import java.util.GregorianCalendar;

import static main.utility.GlobalParameters.RENEWAL_BOUNDARY_IN_DAYS;

/**
 * Controller class that manages any kind of interaction between the graphical user interface
 * and the logic (model) of the program.
 *
 * @author Manuel Gallina
 * @since version 0.1 - 12/03/2018
 */
public class Controller implements SystemController {

    private static Controller instance;

    private GuiManager guiManager;
    private Database database;
    private FileSystem fileSystem;

    //Singleton constructor, private to prevent instantiation.
    private Controller() {
        database = DatabaseManager.getInstance();
        fileSystem = FileSystem.getInstance();
        guiManager = new TextualView(this);
        //guiManager = new GraphicView(this);
    }

    //Returns the instance of the controller.
    static Controller getInstance() {
        if(instance == null)
            instance = new Controller();

        return instance;
    }

    @Override
    public void init() {
        guiManager.mainScreen();
    }

    @Override
    public boolean checkUserLogin(String username, String password) throws UserNotFoundException, WrongPasswordException {
        User toCheck = database.fetch(new User(username));

        if(toCheck == null)
            throw new UserNotFoundException();

        if(toCheck.getPassword().equals(password)){
            database.setCurrentUser(toCheck);
            return true;
        } else
            throw new WrongPasswordException();

    }

    @Override
    public boolean userIsPresent(String username) {
        return database.isPresent(new User(username));
    }

    @Override
    public boolean mediaIsPresent(int id) {
        return database.isPresent(new Media(id));
    }

    @Override
    public String allUsersToString() {
        return database.getUserListString();
    }

    @Override
    public String allLoansToString() {
        return database.getLoanListString();
    }

    @Override
    public String allFilteredMediaList(String filter) {
        return database.getFilteredMediaList(filter);
    }

    @Override
    public boolean addUserToDatabase(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        Customer c = new Customer(firstName, lastName, username, password, birthday);

        if(!database.isPresent(c)) {
            database.add(c);
            return true;
        }

        return false;
    }

    @Override
    public boolean addMediaToDatabase(String title, String author, String genre, int publicationYear, String publisherName, String path) {
        Book b = new Book(title, author, genre, publicationYear, publisherName);

        if(!database.isMatchingMedia(b)) {
            database.add(b, path);
            return true;
        }

        return false;
    }

    @Override
    public boolean addLoanToDatabase(int mediaID) {
        Media media = database.fetch(new Media(mediaID));

        if(media.isAvailable()) {
            database.add(media);
            return true;
        }

        return false;
    }

    @Override
    public void removeMediaFromDatabase(int id) {
        database.remove(new Media(id));
    }

    @Override
    public int getUserStatus(String username) {
        if(username == null)
            return -1;

        switch(database.fetch(new User(username)).getUserStatus()) {
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
        Media media = database.fetch(new Media(mediaID));

        try {
            for(Loan l : database.getUserLoans(database.getCurrentUser()))
                if(l.getMedia().getType().equals(media.getType()))
                    counter++;
        }
        catch(Exception e) {
            return true;
        }

        return counter < media.getLoanLimit();
    }

    @Override
    public int daysLeftToRenew(String username) throws UserNotFoundException {
        try {
            User user = database.fetch(new User(username));

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

    @Override
    public boolean renewSubscription() throws IllegalArgumentException {
        if(database.getCurrentUser() instanceof Customer)
            return ((Customer)database.getCurrentUser()).renewSubscription();
        else
            throw new IllegalArgumentException();
    }

    @Override
    public String dateDetails() {
        User u = database.getCurrentUser();
        return (u instanceof Customer) ?
                String.format("Reminder:\n\tYou subscribed on %s\n\tYour subscription expires on %s\n\tYou're not " +
                                "allowed to renew your subscription until 10 days before the expiry date.",
                        ((Customer)u).getSubscriptionDate().toZonedDateTime().toString().substring(0, 10),
                        ((Customer)u).getExpiryDate().toZonedDateTime().toString().substring(0, 10)) :
                "";
    }

    @Override
    public void logout() {
        database.removeCurrentUser();
    }

    @Override
    public String getFolderContents(String folderPath) {
        return database.getFolderContents(folderPath);
    }

    @Override
    public boolean folderHasChildren(int folderID) {
        return !fileSystem.getFileSystem().get(folderID).getChildren().isEmpty();
    }

    @Override
    public String getSubFolders(int parentID) {
        return fileSystem.getSubFolders(parentID);
    }

    @Override
    public int getRootID() {
        return fileSystem.getRootID();
    }

    @Override
    public String getPathToString(int folderID) {
        return fileSystem.getFileSystem().get(folderID).getFolderPath();
    }
}

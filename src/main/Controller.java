package main;

import main.gui.GuiManager;
import main.gui.TextualView;
import main.model.database.Database;
import main.model.database.DatabaseManager;
import main.model.media.Book;
import main.model.media.Media;
import main.model.user.Customer;
import main.model.user.User;
import main.model.user.UserStatus;

import java.util.GregorianCalendar;

/**
 * Controller class that manages any kind of interaction between the graphical user interface
 * and the logic (model) of the program.
 *
 * @author Manuel Gallina
 * @version 0.1
 * @since version 0.1 - 12/03/2018
 */
public class Controller implements SystemController {
    private static Controller instance;

    private GuiManager guiManager;
    private Database database;

    //Singleton constructor, private to prevent instantiation.
    private Controller() {
        database = DatabaseManager.getInstance();
        guiManager = new TextualView(this);
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
    public boolean checkUserLogin(String username, String password) {
        User toCheck = database.fetch(new User(username));

        if(toCheck != null && toCheck.getPassword().equals(password)){
            database.setCurrentUser(toCheck);
            return true;
        }

        return false;
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
    public String allMediaToString() {
        return database.getMediaListString();
    }

    @Override
    public String allFilteredMediaList(String filter) {
        return database.getFilteredMediaList(filter);
    }

    @Override
    public void addUserToDatabase(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        database.add(new Customer(firstName, lastName, username, password, birthday));
    }

    @Override
    public void addMediaToDatabase(String title, String author, String genre, int publicationYear, String publisherName) {
        database.add(new Book(title, author, genre, publicationYear, publisherName));
    }

    @Override
    public void removeMediaFromDatabase(int id) {
        database.remove(new Media(id));
    }

    @Override
    public boolean legalAge(GregorianCalendar birthday) {
        return new Customer("", "", "", "", birthday).isOfAge();
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
    public boolean canRenew() {
        User currentUser = database.getCurrentUser();
        return (currentUser instanceof Customer) && ((Customer)currentUser).canRenew();
    }

    @Override
    public int daysLeftToRenew(String username) {
        User user = database.fetch(new User(username));
        return (user instanceof Customer) ? ((Customer)user).daysLeftToRenew() : 999999999;
    }

    @Override
    public boolean renewSubscription() throws IllegalArgumentException {
        if(database.getCurrentUser() instanceof Customer)
            return ((Customer)database.getCurrentUser()).renewSubscription();
        else
            throw new IllegalArgumentException();
    }

    @Override
    public void saveDatabase() {
        database.saveDatabase();
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
}

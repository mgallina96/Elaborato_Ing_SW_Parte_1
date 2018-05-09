package main.model.database;
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
 * The {@code DatabaseManager} class, which manages two different databases and provides methods for saving them to (and
 * loading them from) a .ser serializable file.
 * <p>
 * This class' methods are conformed to the {@link Database} interface's methods through interface implementation.
 *
 * @author Manuel Gallina, Alessandro Polcini
 */
public class DatabaseManager implements Serializable, Database {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5681383377098150051L;

    private static DatabaseManager database;
    private UserDatabase userDatabase;
    private MediaDatabase mediaDatabase;
    private LoanDatabase loanDatabase;

    private Logger logger;

    //Singleton Database constructor, private to prevent instantiation.
    private DatabaseManager() {
        this.userDatabase = UserDatabase.getInstance();
        this.mediaDatabase = MediaDatabase.getInstance();
        this.loanDatabase = LoanDatabase.getInstance();
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Getter/initializer for the Singleton Database class.
     * <p>
     * This method builds a single instance of Database the first time it is called. Calling it more than once doesn't
     * change anything.
     *
     * @return Either a new {@code Database} instance, if the {@code Database} has not been initialized yet, or the
     * already initialized {@code Database} object.
     */
    public static DatabaseManager getInstance() {
        if(database == null)
            database = new DatabaseManager();

        return database;
    }

    @Override
    public void add(User toAdd) {
        userDatabase.addUser(toAdd);
        userDatabase.saveUserDatabase();
    }

    @Override
    public void add(Media toAdd, String path) {
        mediaDatabase.addMedia(toAdd, path);
        mediaDatabase.saveMediaDatabase();
    }

    @Override
    public void add(Media toLend) {
        loanDatabase.addLoan(userDatabase.getCurrentUser(), toLend);
        //mediaDatabase.fetch(toLend).lend();
        loanDatabase.saveLoanDatabase();
    }

    @Override
    public void remove(Media toRemove) {
        mediaDatabase.removeMedia(toRemove);
        mediaDatabase.saveMediaDatabase();
    }

    @Override
    public boolean isPresent(User toFind) {
        return userDatabase.isPresent(toFind);
    }

    @Override
    public boolean isPresent(Media toFind) {
        return mediaDatabase.isPresent(toFind);
    }

    @Override
    public boolean isMatchingMedia(Media toFind) {
        return mediaDatabase.isMatchingMedia(toFind);
    }

    @Override
    public User fetch(User toFetch) {
        return userDatabase.fetchUser(toFetch);
    }

    @Override
    public Media fetch(Media toFetch) {
        return mediaDatabase.fetch(toFetch);
    }

    @Override
    public void setCurrentUser(User currentUser) {
        userDatabase.setCurrentUser(currentUser);
    }

    @Override
    public User getCurrentUser() {
        return userDatabase.getCurrentUser();
    }

    @Override
    public String getUserListString() {
        return userDatabase.getUserListString();
    }

    @Override
    public String getFilteredMediaList(String filter) {
        return mediaDatabase.getFilteredMediaList(filter);
    }

    @Override
    public String getFolderContents(String folderPath) {
        return mediaDatabase.getFolderContents(folderPath);
    }

    @Override
    public void removeCurrentUser() {
        userDatabase.removeCurrentUser();
    }
}

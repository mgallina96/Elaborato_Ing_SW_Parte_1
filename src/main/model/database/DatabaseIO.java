package main.model.database;
import main.model.user.User;
import main.utility.notifications.Notifications;
import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.utility.GlobalParameters.LOAN_DATABASE_FILE_PATH;
import static main.utility.GlobalParameters.MEDIA_DATABASE_FILE_PATH;
import static main.utility.GlobalParameters.USER_DATABASE_FILE_PATH;

public class DatabaseIO {

    private static final String CLASS_NAME = "DatabaseIO";
    private static Logger logger = Logger.getLogger(CLASS_NAME);

    private DatabaseIO() {}

    //saves a generic HashMap (database) to a serializable .ser file.
    public static <D extends Database> void saveDatabase(String path, @NotNull D database) {
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
     * This method loads a {@code HashMap} containing all media items.
     * @return The media database.
     */
    @SuppressWarnings("unchecked")
    public static MediaDatabase loadMediaDatabase() {
        try (
                FileInputStream fileIn = new FileInputStream(MEDIA_DATABASE_FILE_PATH);
                ObjectInputStream in = new ObjectInputStream(fileIn)
        ) {
            return (MediaDatabase)in.readObject();
        }
        catch(FileNotFoundException fnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_FILE_NOT_FOUND") + CLASS_NAME);
        }
        catch(IOException ioEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_LOADING_DATABASE") + CLASS_NAME);
        }
        catch(ClassNotFoundException cnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_CLASS_NOT_FOUND") + CLASS_NAME);
        }

        return null;
    }

    /**
     * This method loads a {@code HashMap} containing all users.
     * @return The user database.
     */
    @SuppressWarnings("unchecked")
    public static UserDatabase loadUserDatabase() {
        try (
                FileInputStream fileIn = new FileInputStream(USER_DATABASE_FILE_PATH);
                ObjectInputStream in = new ObjectInputStream(fileIn)
        ) {
            return (UserDatabase)in.readObject();
        }
        catch(FileNotFoundException fnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_FILE_NOT_FOUND") + CLASS_NAME);
        }
        catch(IOException ioEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_LOADING_DATABASE") + CLASS_NAME);
        }
        catch(ClassNotFoundException cnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_CLASS_NOT_FOUND") + CLASS_NAME);
        }

        return null;
    }

    /**
     * This method loads a {@code HashMap} containing all loans.
     * @return The loan database.
     */
    @SuppressWarnings("unchecked")
    public static LoanDatabase loadLoanDatabase() {
        try (
                FileInputStream fileIn = new FileInputStream(LOAN_DATABASE_FILE_PATH);
                ObjectInputStream in = new ObjectInputStream(fileIn)
        ) {
            return (LoanDatabase)in.readObject();
        }
        catch(FileNotFoundException fnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_FILE_NOT_FOUND") + CLASS_NAME);
        }
        catch(IOException ioEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_LOADING_DATABASE") + CLASS_NAME);
        }
        catch(ClassNotFoundException cnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_CLASS_NOT_FOUND") + CLASS_NAME);
        }

        return null;
    }

}

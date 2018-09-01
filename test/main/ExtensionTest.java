package main;
import main.controller.SystemLoanController;
import main.controller.SystemUserController;
import main.model.database.DatabaseIO;
import main.model.database.LoanDatabase;
import main.model.database.MediaDatabase;
import main.model.database.UserDatabase;
import main.model.media.Book;
import main.model.media.Media;
import main.model.user.User;
import main.utility.InputParserUtility;
import main.utility.exceptions.ExtensionDateOutOfBoundsException;
import main.utility.exceptions.ExtensionLimitReachedException;
import main.utility.exceptions.LoanNotFoundException;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static main.utility.GlobalParameters.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtensionTest {

    SystemLoanController slc = SystemLoanController.getInstance();
    private User testUser1 = new User("Franchello", "Cavalli", "franchellocavalli111", "1234", new GregorianCalendar(1976, Calendar.JANUARY, 12));
    private Media testBook1 = new Book("La morte", "Dario Platino", "Horror", 1998, "AncoMarzio editore", 10);

    @Test
    void ExtensionTest() {
        signUpAndLogin();
        addMedia();

        assertTrue(slc.canBorrow(testBook1.getIdentifier()));
        assertTrue(slc.addLoanToDatabase(testBook1.getIdentifier()));

        //errore: il prestito non esiste perchè quel media non esiste
        assertEquals(1, extend(Integer.MAX_VALUE));

        //errore: il prestito esiste ma è troppo presto per estenderlo
        assertEquals(2, extend(testBook1.getIdentifier()));

        //-----cambio data in modo che sia possibile estendere il prestito-----
        GregorianCalendar newDate = new GregorianCalendar();
        newDate.add(Calendar.DATE, 2);
        //---------------------------------------------------------------------

        try {
            LoanDatabase.getInstance().fetchLoan(testUser1, testBook1.getIdentifier()).setLoanExpiry(newDate);
        }
        catch(LoanNotFoundException lnfEx) {

        }

        //adesso si può. Prestito esteso a buon fine
        assertEquals(0, extend(testBook1.getIdentifier()));

        //ora il prestito non può più essere esteso, perchè l'operazione è già stata fatta una volta
        assertEquals(3, extend(testBook1.getIdentifier()));

        removeAndSave();
    }

    int extend(int ID) {
        try {
            slc.extendLoan(ID);
        }
        catch(LoanNotFoundException e) {
            return 1;
        }
        catch(ExtensionDateOutOfBoundsException e) {
            return 2;
        }
        catch(ExtensionLimitReachedException e) {
            return 3;
        }

        return 0;
    }

    void addMedia() {
        MediaDatabase.getInstance().addMedia(testBook1, "root\\Libri\\Horror\\");

    }

    void signUpAndLogin() {
        SystemUserController suc = SystemUserController.getInstance();

        try {
            assertTrue(suc.addUserToDatabase(
                    testUser1.getFirstName(),
                    testUser1.getLastName(),
                    testUser1.getUsername(),
                    testUser1.getPassword(),
                    InputParserUtility.toEuropeanFormat(testUser1.getBirthDate()))
            );

            assertTrue(suc.checkUserLogin(testUser1.getUsername(), testUser1.getPassword()));
        }
        catch(Exception e) {

        }
    }

    void removeAndSave() {
        MediaDatabase.getInstance().actuallyRemoveMedia(testBook1);
        LoanDatabase.getInstance().removeUserAndLoans(testUser1);
        UserDatabase.getInstance().removeUser(testUser1);

        DatabaseIO.saveDatabase(USER_DATABASE_FILE_PATH, UserDatabase.getInstance());
        DatabaseIO.saveDatabase(MEDIA_DATABASE_FILE_PATH, MediaDatabase.getInstance());
        DatabaseIO.saveDatabase(LOAN_DATABASE_FILE_PATH, LoanDatabase.getInstance());
    }

}

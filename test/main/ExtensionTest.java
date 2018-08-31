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
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static main.utility.GlobalParameters.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtensionTest {

    private User testUser1 = new User("Franchello", "Cavalli", "franchellocavalli111", "1234", new GregorianCalendar(1976, Calendar.JANUARY, 12));
    private Media testBook1 = new Book("La morte", "Dario Platino", "Horror", 1998, "AncoMarzio editore", 10);

    @Test
    void ExtensionTest() {
        signUpAndLogin();
        addMedia();

        SystemLoanController slc = SystemLoanController.getInstance();

        assertTrue(slc.canBorrow(testBook1.getIdentifier()));
        assertTrue(slc.addLoanToDatabase(testBook1.getIdentifier()));

        //non può perchè non esiste
        assertEquals(-1, slc.canBeExtended(Integer.MAX_VALUE));

        //non può perché è troppo presto
        assertEquals(1, slc.canBeExtended(testBook1.getIdentifier()));

        //cambio data in modo che sia possibile estendere
        GregorianCalendar newDate = new GregorianCalendar();
        newDate.add(Calendar.DATE, 2);
        //-----------------------------------------------

        try {
            LoanDatabase.getInstance().fetchLoan(testUser1, testBook1.getIdentifier()).setLoanExpiry(newDate);
            //adesso può
            assertEquals(0, slc.canBeExtended(testBook1.getIdentifier()));

            //esteso a buon fine
            assertTrue(slc.extendLoan(testBook1.getIdentifier()));

            //ora non può più essere esteso
            assertFalse(slc.extendLoan(testBook1.getIdentifier()));
        }
        catch(Exception e) {

        }

        removeAndSave();
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
                    InputParserUtility.convertDateToEuropeanFormat(testUser1.getBirthDate()))
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

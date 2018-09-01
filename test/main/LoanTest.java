package main;
import main.controller.SystemLoanController;
import main.controller.SystemUserController;
import main.model.database.DatabaseIO;
import main.model.database.LoanDatabase;
import main.model.database.MediaDatabase;
import main.model.database.UserDatabase;
import main.model.media.Book;
import main.model.media.Film;
import main.model.media.Media;
import main.model.user.User;
import main.utility.InputParserUtility;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static main.utility.GlobalParameters.LOAN_DATABASE_FILE_PATH;
import static main.utility.GlobalParameters.MEDIA_DATABASE_FILE_PATH;
import static main.utility.GlobalParameters.USER_DATABASE_FILE_PATH;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoanTest {

    private User testUser1 = new User("Samuele", "Grandi", "samuelegrandi10", "1234", new GregorianCalendar(1996, Calendar.DECEMBER, 12));

    private Media testBook1 = new Book("Il cavallo pazzo", "Ben Spice", "Horror", 1998, "AncoMarzio editore", 10);
    private Media testBook2 = new Book("Il mulo da soma giallo", "Will Turner", "Noir", 1969, "TullioOstilio editore", 2);

    private Media testFilm1 = new Film("Il pony del deserto", "Jack Johnson", "Animali", 1979, "CamilloBenso production", 10);
    private Media testFilm2 = new Film("Lo stallone nel Grand Canyon", "Paul McGregor", "Educativo", 1983, "Garibaldi&1000 production", 2);

    @Test
    void loanTest() {
        signUpAndLogin();
        addMedia();

        SystemLoanController slc = SystemLoanController.getInstance();

        assertTrue(slc.canBorrow(testBook1.getIdentifier()));
        assertTrue(slc.addLoanToDatabase(testBook1.getIdentifier()));

        assertTrue(slc.canBorrow(testBook2.getIdentifier()));
        assertTrue(slc.addLoanToDatabase(testBook2.getIdentifier()));

        assertTrue(slc.canBorrow(testBook2.getIdentifier()));
        assertTrue(slc.addLoanToDatabase(testBook2.getIdentifier()));

        //tetto massimo di libri raggiunto dall'utente
        assertFalse(slc.canBorrow(testBook1.getIdentifier()));
        assertFalse(slc.canBorrow(testBook2.getIdentifier()));

        //però il media 1 è ancora disponibile
        assertTrue(testBook1.isAvailable());

        //ma il media 2 non è più disponibile
        assertFalse(testBook2.isAvailable());

        removeAndSave();
    }

    void addMedia() {
        MediaDatabase.getInstance().addMedia(testBook1, "root\\Libri\\Horror\\");
        MediaDatabase.getInstance().addMedia(testBook2, "root\\Libri\\Noir\\");
        MediaDatabase.getInstance().addMedia(testFilm1, "root\\Film\\Animali\\");
        MediaDatabase.getInstance().addMedia(testFilm2, "root\\Film\\Educativo\\");

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
        catch(Exception e) {}
    }

    void removeAndSave() {
        MediaDatabase.getInstance().actuallyRemoveMedia(testBook1, testBook2, testFilm1, testFilm2);
        LoanDatabase.getInstance().removeUserAndLoans(testUser1);
        UserDatabase.getInstance().removeUser(testUser1);

        DatabaseIO.saveDatabase(USER_DATABASE_FILE_PATH, UserDatabase.getInstance());
        DatabaseIO.saveDatabase(MEDIA_DATABASE_FILE_PATH, MediaDatabase.getInstance());
        DatabaseIO.saveDatabase(LOAN_DATABASE_FILE_PATH, LoanDatabase.getInstance());
    }

}

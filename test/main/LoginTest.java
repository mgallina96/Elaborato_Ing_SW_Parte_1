package main;
import main.controller.SystemUserController;
import main.model.database.DatabaseIO;
import main.model.database.UserDatabase;
import main.model.user.User;
import main.utility.InputParserUtility;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static main.utility.GlobalParameters.USER_DATABASE_FILE_PATH;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    private SystemUserController sc = SystemUserController.getInstance();
    private User testUser1 = new User("Giulio", "Alberti", "giulioalberti44", "1234", new GregorianCalendar(1976, Calendar.JANUARY, 12));
    private User testUser2 = new User("Mario", "Franchi", "mariofranchi", "11111", new GregorianCalendar(2008, Calendar.MARCH, 6));

    @Test
    void login() {
        signUp();

        try {
            //coppia username GIUSTO - password GIUSTA
            assertTrue(sc.checkUserLogin(testUser1.getUsername(), "1234"));
            //coppia username GIUSTO - password SBAGLIATA
            assertFalse(sc.checkUserLogin(testUser1.getUsername(), "9999"));
            //coppia username SBAGLIATO - password GIUSTA
            assertFalse(sc.checkUserLogin("mariodemarchi45", "1234"));
            //coppia username SBAGLIATO - password SBAGLIATA
            assertFalse(sc.checkUserLogin("mariodemarchi45", "9999"));

            //coppia username GIUSTO - password GIUSTA, MA l'utente non è nel database
            assertFalse(sc.checkUserLogin(testUser2.getUsername(), testUser2.getPassword()));

        }
        catch(Exception e) {

        }

        //rimozione e salvataggio
        UserDatabase.getInstance().removeUser(testUser1);
        DatabaseIO.saveDatabase(USER_DATABASE_FILE_PATH, UserDatabase.getInstance());

    }

    void signUp() {
        try {
            sc.addUserToDatabase(
                    testUser1.getFirstName(),
                    testUser1.getLastName(),
                    testUser1.getUsername(),
                    testUser1.getPassword(),
                    InputParserUtility.toEuropeanFormat(testUser1.getBirthDate()));
        }
        catch(Exception e) {

        }
    }

}

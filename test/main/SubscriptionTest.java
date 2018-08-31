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

class SubscriptionTest {

    private User testUser1 = new User("Giulio", "Alberti", "giulioalberti44", "1234", new GregorianCalendar(1976, Calendar.JANUARY, 12));
    private User testUser2 = new User("Mario", "Franchi", "mariofranchi", "11111", new GregorianCalendar(2008, Calendar.MARCH, 6));
    private User testUser3 = new User("Aldo", "Franchi", "aldofranchi", "22222", new GregorianCalendar(2121, Calendar.JANUARY, 146));

    @Test
    void subscription() {
        SystemUserController sc = SystemUserController.getInstance();

        assertFalse(UserDatabase.getInstance().isPresent(testUser1));
        assertFalse(UserDatabase.getInstance().isPresent(testUser2));
        assertFalse(UserDatabase.getInstance().isPresent(testUser3));


        try {
            //dovrebbe essere vero perchè l'utente è maggiorenne
            assertTrue(sc.addUserToDatabase(
                    testUser1.getFirstName(),
                    testUser1.getLastName(),
                    testUser1.getUsername(),
                    testUser1.getPassword(),
                    InputParserUtility.convertDateToEuropeanFormat(testUser1.getBirthDate())
            ));

            //dovrebbe essere falso perchè l'utente è minorenne
            assertFalse(sc.addUserToDatabase(
                    testUser2.getFirstName(),
                    testUser2.getLastName(),
                    testUser2.getUsername(),
                    testUser2.getPassword(),
                    InputParserUtility.convertDateToEuropeanFormat(testUser2.getBirthDate())));

            //dovrebbe essere falso percè l'utente non è ancora nato
            assertFalse(sc.addUserToDatabase(
                    testUser3.getFirstName(),
                    testUser3.getLastName(),
                    testUser3.getUsername(),
                    testUser3.getPassword(),
                    InputParserUtility.convertDateToEuropeanFormat(testUser3.getBirthDate())));
        }
        catch(Exception e) {

        }

        assertTrue(UserDatabase.getInstance().isPresent(testUser1));
        assertFalse(UserDatabase.getInstance().isPresent(testUser2));
        assertFalse(UserDatabase.getInstance().isPresent(testUser3));

        //rimozione e salvataggio
        UserDatabase.getInstance().removeUser(testUser1);
        DatabaseIO.saveDatabase(USER_DATABASE_FILE_PATH, UserDatabase.getInstance());
    }

}
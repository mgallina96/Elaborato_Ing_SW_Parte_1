package main;
import main.controller.SystemController;
import main.model.database.UserDatabase;
import main.model.user.User;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTest {
    private User testUser1 = new User("Giorgia", "Franchi", "giorgiafranchi33", "44444", new GregorianCalendar(1970, Calendar.JANUARY, 1));
    private User testUser2 = new User("Mario", "Franchi", "mariofranchi", "11111", new GregorianCalendar(2018, Calendar.JANUARY, 1));
    private User testUser3 = new User("Aldo", "Franchi", "aldofranchi", "22222", new GregorianCalendar(2121, Calendar.JANUARY, 146));

    @Test
    void subscription() {
        assertTrue(SystemController.getInstance().addUserToDatabase(testUser1.getFirstName(), testUser1.getLastName(), testUser1.getUsername(), testUser1.getPassword(), testUser1.getBirthday()));
        assertFalse(SystemController.getInstance().addUserToDatabase(testUser2.getFirstName(), testUser2.getLastName(), testUser2.getUsername(), testUser2.getPassword(), testUser2.getBirthday()));
        assertFalse(SystemController.getInstance().addUserToDatabase(testUser3.getFirstName(), testUser3.getLastName(), testUser3.getUsername(), testUser3.getPassword(), testUser3.getBirthday()));

        assertTrue(UserDatabase.getInstance().isPresent(testUser1));
        assertFalse(UserDatabase.getInstance().isPresent(testUser2));
        assertFalse(UserDatabase.getInstance().isPresent(testUser3));
    }

}
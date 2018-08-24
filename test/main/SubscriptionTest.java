package main;
import main.model.user.User;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.GregorianCalendar;

class SubscriptionTest {
    private User testUser1 = new User("Giorgia", "Franchi", "giorgiafranchi33", "44444", new GregorianCalendar(1970, Calendar.JANUARY, 1));
    private User testUser2 = new User("Mario", "Franchi", "mariofranchi", "11111", new GregorianCalendar(2018, Calendar.JANUARY, 1));
    private User testUser3 = new User("Aldo", "Franchi", "aldofranchi", "22222", new GregorianCalendar(2121, Calendar.JANUARY, 146));

    @Test
    void subscription() {

    }

}
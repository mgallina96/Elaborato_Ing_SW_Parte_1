package main.model.user;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Manuel Gallina
 */
class UserConstants {
    static final String DEFAULT_FIRST_NAME = "Default";
    static final String DEFAULT_LAST_NAME = "Default";
    static final String DEFAULT_USERNAME = "Default";
    static final String DEFAULT_PASSWORD = "Default";
    static final GregorianCalendar DEFAULT_BIRTHDAY = new GregorianCalendar(1970, Calendar.JANUARY, 1);

    private UserConstants() {}
}

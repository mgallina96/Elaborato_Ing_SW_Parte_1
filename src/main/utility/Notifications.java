package main.utility;

/**
 * Utility class that stores useful messages, warnings and notifications for this particular program.
 *
 * @author Alessandro Polcini
 */
public class Notifications {

    //MESSAGES
    public static final String MSG_BIBLIO_NAME = "BIBLIOTECA SMARTin4t0r 3.0";
    public static final String MSG_SIGN_UP_SUCCESSFUL = "Sign up successful. Welcome!";
    public static final String MSG_EXIT_WITHOUT_SAVING = "Exiting without saving...";

    //PROMPTS
    public static final String PROMPT_FIRST_NAME = "First name (please insert a valid first name): ";
    public static final String PROMPT_LAST_NAME = "Last name (please insert a valid last name): ";
    public static final String PROMPT_USERNAME = "Username: ";
    public static final String PROMPT_PASSWORD = "Password: ";
    public static final String PROMPT_BIRTHDAY = "Birthday (accepted format = DD/MM/YYYY): ";
    public static final String PROMPT_PRESENT_USER_MULTIPLE_CHOICE = "This user is already present in our database.\n(1) EXIT WITHOUT SAVING\t|\t(2) CHANGE FIELDS\t|\t(3) RENEW SUBSCRIPTION";
    public static final String PROMPT_BIBLIO_INITIAL_CHOICES = "(1) LOGIN\t|\t(2) SIGN UP";
    public static final String PROMPT_OPERATOR_CHOICES = "(1) SHOW ALL SUBSCRIBED USERS";
    public static final String PROMPT_SIGN_UP_CONFIRMATION = "Are you sure you want to submit this form? (y/n)";
    public static final String PROMPT_LOGIN_SCREEN = "Welcome back! Log in to " + MSG_BIBLIO_NAME;
    public static final String PROMPT_SIGN_UP_SCREEN = "Hi there! Sign up for " + MSG_BIBLIO_NAME;
    public static final String PROMPT_MODIFY_FIELDS = "Please modify the wrong fields.";

    //ERRORS
    public static final String ERR_NOT_OF_AGE = "Warning: user is underage. Please re-fill this form.";
    public static final String ERR_INVALID_NAME = "It looks like this name has an invalid format, please re-insert a valid name.";
    public static final String ERR_LOGIN_FAILED = "Login failed: wrong username or password.";
    public static final String ERR_SIGN_UP_FAILED = "Sign up failed.";

    //general useful messages
    public static final String SEPARATOR = "-------------------------------------------------------------------------------------------------------------";


}

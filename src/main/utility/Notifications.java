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
    public static final String MSG_GOODBYE = "Exiting " + MSG_BIBLIO_NAME + "... Goodbye!";
    public static final String MSG_LOG_OUT = "Logging out...";
    public static final String MSG_USER_LIST = "Here is the list of all subscribed users:";
    public static final String MSG_MEDIA_LIST = "Here is the list of all media items:";
    public static final String MSG_FILTERED_MEDIA_LIST = "Results:";
    public static final String MSG_REMOVE_SUCCESSFUL = "Media item removed successfully.";
    public static final String MSG_OPERATOR_MENU = "Welcome, operator!";
    public static final String MSG_CUSTOMER_MENU = "Welcome, customer!";

    //PROMPTS
    public static final String PROMPT_FIRST_NAME = "First name (please insert a valid first name): ";
    public static final String PROMPT_LAST_NAME = "Last name (please insert a valid last name): ";
    public static final String PROMPT_USERNAME = "Username: ";
    public static final String PROMPT_PASSWORD = "Password: ";
    public static final String PROMPT_BIRTHDAY = "Birthday (accepted format = DD/MM/YYYY): ";
    public static final String PROMPT_PRESENT_USER_MULTIPLE_CHOICE = "(1) EXIT WITHOUT SAVING\t|\t(2) CHANGE FIELDS\t|\t(3) RENEW SUBSCRIPTION";
    public static final String PROMPT_BIBLIO_INITIAL_CHOICES = "(1) LOGIN\t|\t(2) SIGN UP\t|\t(3) EXIT";
    public static final String PROMPT_OPERATOR_CHOICES = "(1) ADD A MEDIA ITEM\t|\t(2) REMOVE A MEDIA ITEM\t|\t(3) SHOW ALL MEDIA ITEMS\t|\t(4) SHOW ALL SUBSCRIBED USERS\t|\t(5) LOGOUT";
    public static final String PROMPT_CUSTOMER_CHOICES = "(1) RENEW YOUR SUBSCRIPTION!\t|\t(2) LOGOUT";
    public static final String PROMPT_SIGN_UP_CONFIRMATION = "Are you sure you want to submit this form? (y/n)";
    public static final String PROMPT_LOGIN_SCREEN = "Welcome back! Log in to " + MSG_BIBLIO_NAME;
    public static final String PROMPT_SIGN_UP_SCREEN = "Hi there! Sign up for " + MSG_BIBLIO_NAME;
    public static final String PROMPT_MODIFY_FIELDS = "Please modify the wrong fields.";
    public static final String PROMPT_RETRY_LOGGING_IN = "Please retry logging in with correct credentials.";
    public static final String PROMPT_ADD_MEDIA = "Please add a media item.";
    public static final String PROMPT_TITLE = "Title: ";
    public static final String PROMPT_AUTHOR = "Author: ";
    public static final String PROMPT_GENRE = "Genre: ";
    public static final String PROMPT_PUBLICATION_YEAR = "Publication year: ";
    public static final String PROMPT_PUBLISHER_NAME = "Publisher: ";
    public static final String PROMPT_REMOVE_MEDIA = "Please insert one or more keywords for the media item you wish to remove: ";

    //ERRORS
    public static final String ERR_NOT_OF_AGE = "Warning: user is underage. Please re-fill this form.";
    public static final String ERR_INVALID_NAME = "Looks like this name has an invalid format, please re-insert a valid name.";
    public static final String ERR_INVALID_DATE = "This birth date has an invalid format. Please re-insert a valid date.";
    public static final String ERR_INVALID_YEAR = "This year has an invalid format. Please re-insert a valid year.";
    public static final String ERR_LOGIN_FAILED = "Login failed: wrong username or password.";
    public static final String ERR_SIGN_UP_FAILED = "Sign up failed.";
    public static final String ERR_SIGN_UP_ABORTED = "Sign up aborted.";
    public static final String ERR_SAVING_DATABASE = "An error occurred while saving the database to a .ser file.";
    public static final String ERR_LOADING_DATABASE = "An error occurred while loading the database.";
    public static final String ERR_DATABASE_CLASS_NOT_FOUND = "Database class not found.";
    public static final String ERR_FILE_NOT_FOUND = "Error: file not found.";
    public static final String ERR_MSG_INVALID_INPUT = "Invalid input.";
    public static final String ERR_USER_ALREADY_PRESENT = "This user is already present in our database.";
    public static final String ERR_CANNOT_RENEW = "It's too early to renew your subscription.";
    public static final String PROMPT_REMOVE_MEDIA_ID = "Please choose the ID for the item you wish to remove.";

    //generic useful messages
    public static final String SEPARATOR = "--------------------------------------------------------------------------------------------------------------------------------------";


}

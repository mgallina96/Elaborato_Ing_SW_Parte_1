package main.gui.textual.screens;
import main.SystemController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static main.utility.notifications.Notifications.*;

/**
 * The login screen.
 *
 * @author Manuel Gallina
 * @author Giosuè Filippini
 * @since version 0.1
 */
public class LoginScreen extends Screen {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Constructor for the {@code LoginScreen} class.
     *
     * @param controller The system controller.
     */
    public LoginScreen(SystemController controller) {
        super(controller);
    }


    /**
     * Builds the actual login screen. This method also keeps track of the user that just logged in by returning a
     * {@code String} with their username.
     *
     * @return The username of the user who just logged in.
     */
    public String login() {
        System.out.printf("%s\n%s\n", PROMPT_LOGIN_SCREEN, SEPARATOR);

        while(true) {
            String username;
            String password;

            System.out.printf("[%s %s]\n", ESCAPE_STRING, MSG_ESCAPE_STRING_MESSAGE);

            try {
                username = inputRequest(PROMPT_USERNAME);
                password = inputRequest(PROMPT_PASSWORD);
            }
            catch(InterruptedException e) {
                System.out.printf("%s\n\n", MSG_EXIT_LOGIN);
                break; //returns a null value in this case.
            }

            if(getController().checkUserLogin(username, password)) {
                if(getController().canRenew())
                    System.out.printf("%s %s %s %s.\n",
                            PROMPT_EXPIRY_IMMINENT, MSG_REMINDER_DAYS_LEFT, getController().daysLeftToRenew(username), MSG_DAYS);

                return username;
            }
            else {
                logger.log(Level.SEVERE, ERR_LOGIN_FAILED);
                System.out.printf("%s\n%s\n", PROMPT_RETRY_LOGGING_IN, SEPARATOR);
            }
        }

        return null;
    }

    /**
     * Asks for an input and acquires it. If the user types in {@code "!quit"}, the login section closes.
     */
    private String inputRequest(String prompt) throws InterruptedException {
        String input;

        System.out.print(prompt);
        input = getScanner().next();
        getScanner().nextLine();

        if(input.equals(ESCAPE_STRING))
            throw new InterruptedException();

        return input;
    }

}

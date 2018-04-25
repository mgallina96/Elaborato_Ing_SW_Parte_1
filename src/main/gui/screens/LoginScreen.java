package main.gui.screens;
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

    private static final String ESCAPE_STRING = "!quit";
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

            System.out.printf("[insert %s at any time to exit this section]\n", ESCAPE_STRING);

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
                    System.out.printf("%s Days you have left to renew your subscription: %s days.\n",
                            PROMPT_EXPIRY_IMMINENT, getController().daysLeftToRenew(username));

                return username;
            }
            else {
                logger.log(Level.SEVERE, ERR_LOGIN_FAILED);
                System.out.printf("%s\n%s\n", PROMPT_RETRY_LOGGING_IN, SEPARATOR);
            }
        }

        return null;
    }
}

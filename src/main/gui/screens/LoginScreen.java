package main.gui.screens;
import main.SystemController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static main.utility.Notifications.*;

/**
 * The login screen.
 *
 * @author Manuel Gallina
 * @author Giosuè Filippini
 */
public class LoginScreen extends Screen {

    private static final String ESCAPE_STRING_REGEX = "[qQ]([uU][iI][tT])?";
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
     * Asks for an input and acquires it. If the user types in one of the possible escape strings, the application
     * closes.
     * <p>
     * Accepted escape strings: {@code "quit"} or {@code "q"} (case insensitive).
     */
    private String inputRequest(String prompt) throws InterruptedException {
        String input;

        System.out.print(prompt);
        input = getScanner().next();
        getScanner().nextLine();

        if(input.matches(ESCAPE_STRING_REGEX))
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

            try {
                username = inputRequest(PROMPT_USERNAME);
                password = inputRequest(PROMPT_PASSWORD);
            }
            catch(InterruptedException e) {
                break; //returns a null value in this case.
            }

            if(getController().checkUserLogin(username, password)) {
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

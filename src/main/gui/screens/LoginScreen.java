package main.gui.screens;

import main.SystemController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static main.utility.Notifications.*;

/**
 * The login screen.
 *
 * @author Manuel Gallina
 */
public class LoginScreen extends Screen{
    private static final String ESCAPE_STRING = "quit";

    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Builds the login screen.
     *
     * @param controller The system controller.
     */
    public LoginScreen(SystemController controller) {
        super(controller);

        System.out.println(PROMPT_LOGIN_SCREEN);

        while (true){
            String username;
            String password;

            try {
                username = inputRequest(PROMPT_USERNAME);
                password = inputRequest(PROMPT_PASSWORD);
            } catch (InterruptedException e) {
                new StartScreen(controller).start();
                break;
            }

            if(controller.checkUserLogin(username, password)) {
                switch (controller.getUserStatus(username)) {
                    case OPERATOR:
                        new OperatorScreen(controller);
                        break;
                    case CUSTOMER:
                        new CustomerScreen(controller);
                        break;
                }
                break;
            } else
                logger.log(Level.SEVERE, ERR_LOGIN_FAILED);
        }
    }

    // Asks for an input and acquires it. If the user writes the escape string the application closes.
    private String inputRequest(String prompt) throws InterruptedException {
        String input;

        System.out.print(prompt);
        input = getScanner().next();
        getScanner().nextLine();

        if(input.equalsIgnoreCase(ESCAPE_STRING))
            throw new InterruptedException();

        return input;
    }
}

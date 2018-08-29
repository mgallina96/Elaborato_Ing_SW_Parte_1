package main.gui.textual.screens;
import main.controller.UserController;
import main.utility.exceptions.UserNotFoundException;
import main.utility.exceptions.WrongPasswordException;
import main.utility.notifications.Notifications;

/**
 * The login screen.
 *
 * @author Manuel Gallina, Giosuè Filippini
 * @since version 0.1
 */
public class LoginScreen extends Screen {

    /**
     * Constructor for the {@code LoginScreen} class.
     *
     * @param userController The system controller.
     */
    public LoginScreen(UserController userController) {
        super(userController);
    }


    /**
     * Builds the actual login screen. This method also keeps track of the user that just logged in by returning a
     * {@code String} with their username.
     *
     * @return The username of the user who just logged in.
     */
    public String login() {
        System.out.printf("%s%s%n%s%n",
                Notifications.getMessage("PROMPT_LOGIN_SCREEN"),
                Notifications.getMessage("MSG_LIBRARY_NAME"),
                Notifications.getMessage("SEPARATOR"));

        while(true) {
            String username;
            String password;

            System.out.printf("[%s %s]%n", ESCAPE_STRING, Notifications.getMessage("MSG_ESCAPE_STRING_MESSAGE"));

            try {
                username = inputRequest(Notifications.getMessage("PROMPT_USERNAME"));
                password = inputRequest(Notifications.getMessage("PROMPT_PASSWORD"));

                if(getUserController().checkUserLogin(username, password)) {
                    int days = getUserController().daysLeftToRenew(username);

                    if(days > 0)
                        System.out.printf("%s%s %s %s.%n",
                                Notifications.getMessage("PROMPT_EXPIRY_IMMINENT"), Notifications.getMessage("MSG_REMINDER_DAYS_LEFT"), days, Notifications.getMessage("MSG_DAYS"));

                    return username;
                } else {
                    System.out.printf("%s%n%s%n", Notifications.getMessage("PROMPT_RETRY_LOGGING_IN"), Notifications.getMessage("SEPARATOR"));
                }
            }
            catch(InterruptedException e) {
                System.out.printf("%s%n%n", Notifications.getMessage("MSG_EXIT_LOGIN"));
                break; //returns a null value in this case.
            }
            catch(UserNotFoundException e) {
                System.out.println(Notifications.getMessage("ERR_USER_NOT_PRESENT"));
            }
            catch(WrongPasswordException e) {
                System.out.println(Notifications.getMessage("ERR_WRONG_PASSWORD"));
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

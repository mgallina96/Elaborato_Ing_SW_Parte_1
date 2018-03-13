package main.gui;
import main.SystemController;
import main.utility.InputParserUtility;
import static main.utility.Notifications.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main GUI class which sets up the graphical appearance and manages any kind of interaction with the user.
 *
 * @author Manuel Gallina
 * @version 0.1
 * @since version 0.1 - 08/03/2018
 */
public class View {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private static final String operatorChoices = "(1) SHOW SUBSCRIBED USER";
    private SystemController controller;
    private Scanner scanner;

    /**
     * The constructor for the View class.
     *
     * @param controller The system controller.
     */
    public View(SystemController controller) {
        this.controller = controller;
        scanner = new Scanner(System.in);
    }

    /**
     * Builds the first screen of the user interface which is the login screen.
     */
    public void start() {
        System.out.printf("%s\n\n%s\n\n> ", MSG_BIBLIO_NAME, MSG_BIBLIO_INITIAL_CHOICES);

        String command;
        do {
            command = scanner.nextLine();
        } while(!InputParserUtility.isValidInteger(command, 1, 3));

        switch(Integer.parseInt(command)) {
            case 1:
                login();
                break;
            case 2:
                signUp();
                break;
            default:
                break;
        }

        scanner.close();
    }

    /**
     * Boots up the section from which the user can sign up.
     */
    private void signUp() {
        boolean valid;
        System.out.println(PROMPT_SIGN_UP_SCREEN + "\n" + SEPARATOR);

        controller.addUserToDatabase("Ale", "Polcio", "polcio", "asd", InputParserUtility.toGregorianDate("14/10/1996"));

        do {
            valid = true;

            System.out.print(PROMPT_FIRST_NAME);
            String firstName = insertName();
            System.out.print(PROMPT_LAST_NAME);
            String lastName = insertName();
            System.out.print(PROMPT_USERNAME);
            String username = scanner.nextLine();
            System.out.print(PROMPT_PASSWORD);
            String password = scanner.nextLine();
            System.out.print(PROMPT_BIRTHDAY);
            String birthday = scanner.nextLine();

            System.out.println(SEPARATOR + "\n" + PROMPT_SIGN_UP_CONFIRMATION + "\n" + SEPARATOR);
            String choice;

            do {
                choice = scanner.nextLine();
            } while(choice.matches("[^ynYN]"));

            if(choice.matches("[yY]")) {

                if(!controller.legalAge(InputParserUtility.toGregorianDate(birthday))) {
                    System.out.println(ERR_NOT_OF_AGE);
                    continue;
                }

                if(!controller.checkUserOccurrence(username, password)) {
                    controller.addUserToDatabase(firstName, lastName, username, password, InputParserUtility.toGregorianDate(birthday));
                    System.out.println(MSG_SIGN_UP_SUCCESSFUL);
                    break;
                }
                else {
                    System.out.println(PROMPT_PRESENT_USER_MULTIPLE_CHOICE);
                    String input;

                    do {
                        input = scanner.nextLine();
                    } while(!InputParserUtility.isValidInteger(input, 1, 4));

                    switch(Integer.parseInt(input)) {
                        case 1:
                            System.out.println(MSG_EXIT_WITHOUT_SAVING);
                            valid = false;
                            break;
                        case 2:
                            System.out.println(PROMPT_MODIFY_FIELDS);
                            break;
                        case 3:
                            renewSubscription();
                            valid = false;
                            break;
                    }
                }
            }
            else {
                System.out.println(ERR_SIGN_UP_FAILED + " " + MSG_EXIT_WITHOUT_SAVING);
                break;
            }

        } while(valid);

    }

    /**
     * Boots up the login screen.
     */
    private void login() {
        System.out.println(PROMPT_LOGIN_SCREEN);

        while(true) {
            String username;
            String password;

            System.out.print(PROMPT_USERNAME);
            username = scanner.next();
            scanner.nextLine();

            System.out.print(PROMPT_PASSWORD);
            password = scanner.next();
            scanner.nextLine();

            if(controller.checkUserOccurrence(username, password))
                break;

            logger.log(Level.SEVERE, ERR_LOGIN_FAILED);
        }
    }

    private void renewSubscription() {

    }

    private String insertName() {
        String name = scanner.nextLine();
        boolean valid = InputParserUtility.isValidName(name);

        while(!valid) {
            System.out.println(ERR_INVALID_NAME);
            name = scanner.nextLine();
            valid = InputParserUtility.isValidName(name);
        }

        return name;
    }

    /**
     * Boots up the operator menu.
     */
    public void operatorMenu() {
        System.out.printf("%s\n\n", operatorChoices);

        String command;
        do {
            command = scanner.nextLine();
        } while(!InputParserUtility.isValidInteger(command, 1, 3));

        switch(Integer.parseInt(command)) {
            case 1:
                showUsers();
                break;
            default:
                break;
        }

        scanner.close();
    }

    /**
     * Shows the full list of subscribed users.
     */
    private void showUsers() {
        System.out.println(controller.allUsersToString());
    }

}

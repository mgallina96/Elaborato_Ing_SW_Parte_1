package main.gui;
import static main.utility.Notifications.*;

import com.sun.org.apache.xpath.internal.SourceTree;
import main.SystemController;
import main.utility.InputParserUtility;
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
        boolean exit = false;

        while(!exit) {
            System.out.printf("%s\n%s\n%s\n%s\n> ", MSG_BIBLIO_NAME, SEPARATOR, PROMPT_BIBLIO_INITIAL_CHOICES, SEPARATOR);

            String command;
            do {
                command = scanner.nextLine();
            } while(!InputParserUtility.isValidInteger(command, 1, 4));

            switch(Integer.parseInt(command)) {
                case 1:
                    login();
                    break;
                case 2:
                    signUp();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    break;
            }
        }

        System.out.printf("%s\n", MSG_GOODBYE);
        scanner.close();
    }

    /**
     * Boots up the section from which the user can sign up.
     */
    private void signUp() {
        boolean valid;
        System.out.printf("%s\n", PROMPT_SIGN_UP_SCREEN);

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
                String birthday = insertDate();
            System.out.printf("%s\n%s\n%s\n", SEPARATOR, PROMPT_SIGN_UP_CONFIRMATION, SEPARATOR);

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
                    controller.saveDatabase();
                    System.out.printf("%s\n", MSG_SIGN_UP_SUCCESSFUL);
                    break;
                }
                else {
                    System.out.printf("%s\n%s\n%s\n%s\n", ERR_USER_ALREADY_PRESENT, SEPARATOR, PROMPT_PRESENT_USER_MULTIPLE_CHOICE, SEPARATOR);
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
                            controller.renewSubscription();
                            valid = false;
                            break;
                    }
                }
            }
            else {
                System.out.printf("%s %s\n\n", ERR_SIGN_UP_ABORTED, MSG_EXIT_WITHOUT_SAVING);
                break;
            }

        } while(valid);

    }

    /**
     * Boots up the login screen.
     */
    private void login() {
        System.out.printf("\n%s\n", PROMPT_LOGIN_SCREEN);

        while(true) {
            String username;
            String password;

            System.out.print(PROMPT_USERNAME);
            username = scanner.nextLine();

            System.out.print(PROMPT_PASSWORD);
            password = scanner.nextLine();

            if(controller.checkUserOccurrence(username, password)) {
                switch(controller.getUserStatus(username)) {
                    case OPERATOR:
                        operatorMenu();
                        break;
                    case CUSTOMER:
                        customerMenu();
                        break;
                    default:
                        break;
                }

                break;
            }

            logger.log(Level.SEVERE, ERR_LOGIN_FAILED);
        }
    }

    private void customerMenu() {
        boolean exit = false;

        while(!exit) {
            System.out.printf("\n%s\n%s\n%s\n%s\n", MSG_CUSTOMER_MENU, SEPARATOR, PROMPT_CUSTOMER_CHOICES, SEPARATOR);

            String command;
            do {
                command = scanner.nextLine();
            } while(!InputParserUtility.isValidInteger(command, 1, 3));

            switch(Integer.parseInt(command)) {
                case 1:
                    controller.renewSubscription();
                    break;
                case 2:
                    exit = true;
                    System.out.printf("%s\n\n", MSG_LOG_OUT);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Boots up the operator menu.
     */
    private void operatorMenu() {
        boolean exit = false;

        while(!exit) {
            System.out.printf("\n%s\n%s\n%s\n%s\n", MSG_OPERATOR_MENU, SEPARATOR, PROMPT_OPERATOR_CHOICES, SEPARATOR);

            String command;
            do {
                command = scanner.nextLine();
            } while(!InputParserUtility.isValidInteger(command, 1, 3));

            switch(Integer.parseInt(command)) {
                case 1:
                    showUsers();
                    break;
                case 2:
                    exit = true;
                    System.out.printf("%s\n\n", MSG_LOG_OUT);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Loops a scanner until the inserted {@code String} is a valid first name (or last name).
     * <p>The parameters and the logic for name validity are defined in the {@code isValidName()} method of the
     * {@link InputParserUtility} class.
     *
     * @return a valid name in the form of a {@code String}.
     */
    private String insertName() {
        String name = scanner.nextLine();

        while(!InputParserUtility.isValidName(name)) {
            System.out.println(ERR_INVALID_NAME);
            name = scanner.nextLine();
        }

        return name;
    }

    /**
     * Loops a scanner until the inserted {@code String} is a valid date.
     * <p>The parameters and the logic for date validity are defined in the {@code isValidDate()} method of the
     * {@link InputParserUtility} class.
     *
     * @return a valid date in the form of a {@code String}.
     */
    private String insertDate() {
        String date = scanner.nextLine();

        while(!InputParserUtility.isValidDate(date)) {
            System.out.println(ERR_INVALID_DATE);
            date = scanner.nextLine();
        }

        return date;
    }

    /**
     * Shows the full list of subscribed users.
     */
    private void showUsers() {
        System.out.printf("%s\n%s%s\n", MSG_USER_LIST, controller.allUsersToString(), SEPARATOR);
    }

}

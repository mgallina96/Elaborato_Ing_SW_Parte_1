package main.gui;
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
    private static final String biblioName = "BIBLIOTECA SMARTin4t0r 3.0";
    private static final String choices = "(1) LOGIN\t|\t(2) SIGN IN";
    private static final String LOGIN_FAILED = "Wrong username or password.\n";
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
        System.out.printf("%s\n\n%s\n\n>", biblioName, choices);

        String command;
        do {
            command = scanner.nextLine();
        } while(!InputParserUtility.isValidInteger(command, 1, 3));

        switch(Integer.parseInt(command)) {
            case 1: login();
                break;
            case 2: signIn();
                break;
            default:
                break;
        }

        scanner.close();
    }

    /**
     * Boots up the section from which the user can sign in.
     */
    private void signIn() {
        //TODO
    }

    /**
     * Boots up the login screen.
     */
    private void login() {
        while(true) {
            String username;
            String password;

            System.out.print("Username: ");
            username = scanner.next();
            scanner.nextLine();

            System.out.print("Password: ");
            password = scanner.next();
            scanner.nextLine();

            if(controller.checkUser(username, password))
                break;

            logger.log(Level.SEVERE, LOGIN_FAILED);
        }
    }
}

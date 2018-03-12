package main.gui;

import main.SystemController;

import java.util.Scanner;

/**
 * Main GUI class.
 *
 * It builds the graphic appearance and manages the interaction with the user.
 *
 * @author Manuel Gallina
 * @version 0.1
 * @since version 0.1 - 08/03/2018
 */
public class View {
    private SystemController controller;
    private Scanner scanner;

    /**
     * Constructor.
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
        System.out.print("BIBLIOTECA SMARTin4t0r 3.0\n\n" +
                "(1) LOGIN\t|\t(2) SIGN IN\n\n" +
                "> ");

        int command = 0;

        do {
            if(scanner.hasNextInt())
                command = scanner.nextInt();
            else
                scanner.nextLine();
        } while (command < 1 || command > 2);

        switch (command) {
            case 1:
                login();
                break;
            case 2:
                signIn();
                break;
            default:
                break;
        }

        scanner.close();
    }

    private void signIn() {
        //TODO
    }

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

            if (controller.checkUser(username, password))
                break;
            System.out.println("Username o password errati\n");
        }

        //TODO: profileScreen();
    }
}

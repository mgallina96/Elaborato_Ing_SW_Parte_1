package main.gui.screens;
import main.SystemController;
import main.gui.GuiManager;
import main.utility.InputParserUtility;

import java.util.Scanner;
import java.util.logging.Logger;

import static main.utility.Notifications.MSG_BIBLIO_NAME;
import static main.utility.Notifications.PROMPT_BIBLIO_INITIAL_CHOICES;

/**
 * The start screen of the application.
 *
 * @author Manuel Gallina
 * @version 0.1
 * @since version 0.1 - 08/03/2018
 */
public class StartScreen implements GuiManager {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private SystemController controller;
    private Scanner scanner;

    /**
     * The constructor for the GuiManager class.
     *
     * @param controller The system controller.
     */
    public StartScreen(SystemController controller) {
        this.controller = controller;
        scanner = new Scanner(System.in);
    }

    /** Builds the first screen of the user interface which is the login screen. */
    @Override
    public void start() {
        System.out.printf("%s\n\n%s\n\n> ", MSG_BIBLIO_NAME, PROMPT_BIBLIO_INITIAL_CHOICES);

        String command;
        do {
            command = scanner.nextLine();
        } while(!InputParserUtility.isValidInteger(command, 1, 3));

        switch(Integer.parseInt(command)) {
            case 1:
                new LoginScreen(controller);
                break;
            case 2:
                new SignUpScreen(controller);
                break;
            default:
                break;
        }
        scanner.close();
    }
}

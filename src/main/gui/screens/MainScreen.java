package main.gui.screens;
import main.Controller;
import main.utility.InputParserUtility;

import java.util.logging.Logger;
import static main.utility.Notifications.*;

/**
 * The starting screen of the application.
 *
 * @author Manuel Gallina
 * @version 0.1
 * @since version 0.1 - 08/03/2018
 */
public class MainScreen extends Screen {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * The constructor for the GUI manager class.
     *
     * @param controller The system controller.
     */
    public MainScreen(Controller controller) {
        super(controller);
    }

    /**
     * Builds the actual main screen. It allows the user to make a choice and then returns it for further use
     * within the application.
     * @return The choice the user made.
     */
    public int menuChoices() {
        System.out.printf("%s\n%s\n%s\n%s\n> ", MSG_BIBLIO_NAME, SEPARATOR, PROMPT_BIBLIO_INITIAL_CHOICES, SEPARATOR);

        String command;
        do {
            command = getScanner().nextLine();
        } while(!InputParserUtility.isValidInteger(command, 1, 4));

        return Integer.parseInt(command);
    }
}
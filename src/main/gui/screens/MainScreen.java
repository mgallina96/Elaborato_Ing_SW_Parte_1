package main.gui.screens;
import main.SystemController;
import main.gui.GuiManager;
import main.utility.InputParserUtility;
import java.util.Scanner;
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
    public MainScreen(SystemController controller) {
        super(controller);
    }

    /**
     * Builds the actual main screen. It allows the user to make a choice and then returns it for further use
     * within the application.
     * @return The choice the user made.
     */
    public int menuChoices() {
        System.out.printf("%s\n\n%s\n\n> ", MSG_BIBLIO_NAME, PROMPT_BIBLIO_INITIAL_CHOICES);

        String command;
        do {
            command = getScanner().nextLine();
        } while(!InputParserUtility.isValidInteger(command, 1, 3));

        return Integer.parseInt(command);
    }
}

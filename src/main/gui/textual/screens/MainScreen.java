package main.gui.textual.screens;
import main.RobaInutileConDocumentazioneUtile;
import main.utility.InputParserUtility;

import static main.utility.notifications.Notifications.*;

/**
 * The starting screen of the application.
 *
 * @author Manuel Gallina
 * @since version 0.1
 */
public class MainScreen extends Screen {

    /**
     * Builds the actual main screen and the main choices for navigating this application.
     * <p>
     * The user is allowed to make a choice, which this method then returns (as an {@code int} value) for further use
     * within the application.
     *
     * @return The choice the user made.
     */
    public int menuChoices() {
        System.out.printf("%s\n%s\n%s\n%s\n> ", MSG_BIBLIO_NAME, SEPARATOR, PROMPT_BIBLIO_INITIAL_CHOICES, SEPARATOR);

        return insertInteger(1, 4);
    }
}

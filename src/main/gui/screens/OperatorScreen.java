package main.gui.screens;
import main.SystemController;
import main.utility.InputParserUtility;
import java.util.logging.Logger;
import static main.utility.Notifications.*;

/**
 * The operator menu screen.
 *
 * @author Manuel Gallina
 */
public class OperatorScreen extends Screen {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Constructor for the OperatorScreen class. It boots up the operator section.
     * @param controller The system controller.
     */
    public OperatorScreen(SystemController controller) {
        super(controller);

        System.out.printf("%s\n\n", PROMPT_OPERATOR_CHOICES);

        String command;
        do {
            command = getScanner().nextLine();
        } while(!InputParserUtility.isValidInteger(command, 1, 3));

        switch(Integer.parseInt(command)) {
            case 1:
                System.out.printf("%s\n%s\n", MSG_USER_LIST, controller.allUsersToString());
                break;
            case 2:
                controller.logout();
                break;
            default:
                break;
        }
    }
}

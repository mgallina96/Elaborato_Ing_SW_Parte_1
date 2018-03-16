package main.gui.screens;
import main.SystemController;
import main.utility.InputParserUtility;
import static main.utility.Notifications.*;

/**
 * The customer menu screen.
 *
 * @author Manuel Gallina
 */
public class CustomerScreen extends Screen {

    public CustomerScreen(SystemController controller) {
        super(controller);
        System.out.printf("%s\n\n", PROMPT_CUSTOMER_CHOICES);

        String command;
        do {
            command = getScanner().nextLine();
        } while(!InputParserUtility.isValidInteger(command, 1, 3));

        switch (Integer.parseInt(command)) {
            case 1:
                controller.renewSubscription();
                break;
            case 2:
                controller.logout();
                new StartScreen(controller);
                break;
            default:
                break;
        }
    }
}

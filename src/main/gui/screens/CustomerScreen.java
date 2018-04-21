package main.gui.screens;
import main.SystemController;
import main.utility.InputParserUtility;

import java.util.logging.Logger;

import static main.utility.Notifications.*;

/**
 * The customer menu screen.
 *
 * @since version 0.1
 */
public class CustomerScreen extends Screen {
    /**
     * Constructor for the {@code CustomerScreen} class. This constructor boots up the customer section.
     * @param controller The system controller.
     */
    public CustomerScreen(SystemController controller) {
        super(controller);
        boolean exitFromCustomerSection = false;

        while(!exitFromCustomerSection) {
            System.out.printf("%s\n%s\n%s\n", SEPARATOR, PROMPT_CUSTOMER_CHOICES, SEPARATOR);

            String command;
            do {
                command = getScanner().nextLine();
            } while(!InputParserUtility.isValidInteger(command, 1, 3));

            switch(Integer.parseInt(command)) {
                case 1:
                    if(!getController().renewSubscription())
                        System.out.printf("%s\n%s\n", ERR_CANNOT_RENEW, getController().dateDetails());
                    break;
                case 2:
                    System.out.printf("%s\n", MSG_LOG_OUT);
                    exitFromCustomerSection = true;
                    getController().logout();
                    break;
                default:
                    break;
            }
        }
    }
}

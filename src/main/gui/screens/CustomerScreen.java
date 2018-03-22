package main.gui.screens;
import main.Controller;
import main.utility.InputParserUtility;
import static main.utility.Notifications.*;

/**
 * The customer menu screen.
 */
public class CustomerScreen extends Screen {

    /**
     * Constructor for the CustomerScreen class. It boots up the customer section.
     * @param controller The system controller.
     */
    public CustomerScreen(Controller controller) {
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
                    getController().renewSubscription();
                    if(!getController().canRenew())
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

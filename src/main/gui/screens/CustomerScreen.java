package main.gui.screens;
import main.SystemController;
import main.utility.InputParserUtility;

import static main.utility.notifications.Notifications.*;

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

            switch(insertInteger(1,6)) {
                case 1:
                    if(!getController().renewSubscription())
                        System.out.printf("%s\n%s\n", ERR_CANNOT_RENEW, getController().dateDetails());
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    searchForMedia();
                    break;
                case 5:
                    System.out.printf("%s\n", MSG_LOG_OUT);
                    exitFromCustomerSection = true;
                    getController().logout();
                    break;
                default:
                    break;
            }
        }
    }

    private void searchForMedia() {
        System.out.println(PROMPT_SEARCH_FOR_MEDIA);
        String input = getScanner().nextLine();

        if(input.equals(ESCAPE_STRING)) {
            System.out.println(MSG_ABORT);
            return;
        }

        String output = getController().allFilteredMediaList(input);

        if(output.length() > 0)
            System.out.printf("%s\n%s\n", MSG_FILTERED_MEDIA_LIST, output);
        else
            System.out.println(ERR_FILTERED_MEDIA_LIST_EMPTY);
    }
}

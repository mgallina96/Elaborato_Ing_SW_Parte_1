package main.gui.textual.screens;
import main.SystemController;

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
                case 2: //borrow stuff
                    if(true)
                        System.out.println("aaasasa");
                    if(searchForMedia(PROMPT_SEARCH_FOR_MEDIA_TO_BORROW))
                        borrowMedia();
                    break;
                case 3: //extend stuff
                    break;
                case 4:
                    searchForMedia(PROMPT_SEARCH_FOR_MEDIA);
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

    private boolean searchForMedia(String prompt) {
        System.out.println(prompt);
        String input = getScanner().nextLine();

        if(input.equals(ESCAPE_STRING)) {
            System.out.println(MSG_ABORT);
            return false;
        }

        String output = getController().allFilteredMediaList(input);

        if(output.length() > 0)
            System.out.printf("%s\n%s\n", MSG_FILTERED_MEDIA_LIST, output);
        else {
            System.out.println(ERR_FILTERED_MEDIA_LIST_EMPTY);
            return false;
        }

        return true;
    }

    private void borrowMedia() {
        int id = insertInteger();
        while(!getController().mediaIsPresent(id)) {
            System.out.println(ERR_MEDIA_NOT_PRESENT);
            id = insertInteger();
        }

        System.out.println(PROMPT_BORROW_CONFIRMATION);

        if(insertString(YN_REGEX).equalsIgnoreCase(YES)) {
            switch(getController().addLoanToDatabase(id)) {
                case 0: System.out.println(MSG_BORROW_SUCCESSFUL);
                    break;
                case 1: System.out.println(ERR_BORROW_FAILED);
                    break;
                case -1: System.out.println(ERR_BORROW_FAILED);
                    break;
                default:
                    break;
            }
        }
        else
            System.out.println(MSG_ABORT);
    }
}

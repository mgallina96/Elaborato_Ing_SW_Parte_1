package main.gui.textual.screens;
import main.controller.LoanController;
import main.controller.MediaController;
import main.controller.UserController;

import static main.utility.notifications.Notifications.*;

/**
 * The customer menu screen.
 *
 * @since version 0.1
 */
public class CustomerScreen extends Screen {

    /**
     * Constructor for the {@code CustomerScreen} class. This constructor boots up the customer section.
     *
     * @param userController The user controller.
     * @param mediaController The media controller.
     * @param loanController The loan controller.
     */
    public CustomerScreen(UserController userController, MediaController mediaController, LoanController loanController) {
        super(userController, mediaController, loanController);
        boolean exitFromCustomerSection = false;

        while(!exitFromCustomerSection) {
            System.out.printf("%s\n%s\n%s\n", SEPARATOR, PROMPT_CUSTOMER_CHOICES, SEPARATOR);

            switch(insertInteger(1,6)) {
                case 1:
                    if(!userController.renewSubscription())
                        System.out.printf("%s\n%s\n", ERR_CANNOT_RENEW, userController.dateDetails());
                    break;
                case 2:
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
                    userController.logout();
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

        String output = getMediaController().allFilteredMediaList(input);

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
        while(!getMediaController().mediaIsPresent(id)) {
            System.out.println(ERR_MEDIA_NOT_PRESENT);
            id = insertInteger();
        }

        System.out.println(PROMPT_BORROW_CONFIRMATION);

        if(insertString(YN_REGEX).equalsIgnoreCase(YES)) {
            if(getLoanController().canBorrow(id)) {
                if(getLoanController().addLoanToDatabase(id))
                    System.out.println(MSG_BORROW_SUCCESSFUL);
                else
                    System.out.println(ERR_MEDIA_NOT_AVAILABLE);
            }
            else
                System.out.println(ERR_CANNOT_BORROW);
        }
        else
            System.out.println(MSG_ABORT);
    }
}

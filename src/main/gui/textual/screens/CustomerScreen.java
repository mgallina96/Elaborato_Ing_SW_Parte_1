package main.gui.textual.screens;
import main.controller.LoanController;
import main.controller.MediaController;
import main.controller.UserController;
import main.utility.notifications.Notifications;

import static main.utility.GlobalParameters.RENEWAL_BOUNDARY_IN_DAYS;

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
            System.out.printf("%s%n%s\t\t\t\t\t\t%s%s%n%s%n",
                    Notifications.getMessage("SEPARATOR"),
                    Notifications.getMessage("PROMPT_CUSTOMER_CHOICES"),
                    Notifications.getMessage("MSG_LOGGED_IN_AS"), getUserController().getCurrentUserName(),
                    Notifications.getMessage("SEPARATOR"));

            switch(insertInteger(1,6)) {
                case 1:
                    if(userController.renewSubscription())
                        System.out.println(Notifications.getMessage("MSG_SUBSCRIPTION_RENEWAL_SUCCESSFUL"));
                    else {
                        System.out.printf("%s%n", Notifications.getMessage("ERR_CANNOT_RENEW"));
                        String[] dates = userController.dateDetails();
                        System.out.printf("%s%s%s%s%s%s%s%d %s %s%n",
                                Notifications.getMessage("MSG_SUBSCRIPTION_REMINDER"), dates[0],
                                Notifications.getMessage("MSG_LATEST_RENEWAL_REMINDER"), dates[1],
                                Notifications.getMessage("MSG_EXPIRY_REMINDER"), dates[2],
                                Notifications.getMessage("MSG_RENEWAL_INFO"), RENEWAL_BOUNDARY_IN_DAYS,
                                Notifications.getMessage("MSG_DAYS"), Notifications.getMessage("MSG_RENEWAL_INFO_END_OF_MESSAGE"));
                    }
                    break;
                case 2:
                    if(searchForMedia(Notifications.getMessage("PROMPT_SEARCH_FOR_MEDIA_TO_BORROW")))
                        borrowMedia();
                    break;
                case 3:
                    if(searchForLoans())
                        extendLoan();
                    break;
                case 4:
                    searchForMedia(Notifications.getMessage("PROMPT_SEARCH_FOR_MEDIA"));
                    break;
                case 5:
                    System.out.printf("%s%n", Notifications.getMessage("MSG_LOG_OUT"));
                    exitFromCustomerSection = true;
                    userController.logout();
                    break;
                default:
                    break;
            }
        }
    }

    //finds all the current user's loans.
    private boolean searchForLoans() {
        String output = getLoanController().currentUserLoansToString();

        if(output.length() > 0)
            System.out.printf("%s%n%s%n", Notifications.getMessage("MSG_LOAN_LIST_SINGLE"), output);
        else {
            System.out.println(Notifications.getMessage("ERR_LOAN_LIST_EMPTY"));
            return false;
        }

        return true;
    }

    //allows the user to search for media items.
    private boolean searchForMedia(String prompt) {
        System.out.println(prompt);
        String input = getScanner().nextLine();

        if(input.equals(ESCAPE_STRING)) {
            System.out.println(Notifications.getMessage("MSG_ABORT"));
            return false;
        }

        String output = getMediaController().allFilteredMediaList(input);

        if(output.length() > 0)
            System.out.printf("%s%n%s%n", Notifications.getMessage("MSG_FILTERED_MEDIA_LIST"), output);
        else {
            System.out.println(Notifications.getMessage("ERR_FILTERED_MEDIA_LIST_EMPTY"));
            return false;
        }

        return true;
    }

    //allows the user to borrow a media item.
    private void borrowMedia() {
        int id = insertInteger();
        while(!getMediaController().mediaIsPresent(id)) {
            System.out.println(Notifications.getMessage("ERR_MEDIA_NOT_PRESENT"));
            id = insertInteger();
        }

        System.out.println(Notifications.getMessage("PROMPT_BORROW_CONFIRMATION"));

        if(insertString(YN_REGEX).equalsIgnoreCase(YES)) {
            if(getLoanController().canBorrow(id)) {
                if(getLoanController().addLoanToDatabase(id))
                    System.out.println(Notifications.getMessage("MSG_BORROW_SUCCESSFUL"));
                else
                    System.out.println(Notifications.getMessage("ERR_MEDIA_NOT_AVAILABLE"));
            }
            else
                System.out.println(Notifications.getMessage("ERR_LOAN_UNAVAILABLE"));
        }
        else
            System.out.println(Notifications.getMessage("MSG_ABORT"));
    }

    //allows the user to extend their loan.
    private void extendLoan() {
        System.out.println(Notifications.getMessage("PROMPT_CHOOSE_MEDIA_TO_EXTEND"));

        int id = insertInteger();
        while(!getMediaController().mediaIsPresent(id)) {
            System.out.println(Notifications.getMessage("ERR_MEDIA_NOT_PRESENT"));
            id = insertInteger();
        }

        int loanOutcome = getLoanController().canBeExtended(id);

        if(loanOutcome == 0) {
            if(getLoanController().extendLoan(id))
                System.out.println(Notifications.getMessage("MSG_EXTEND_SUCCESSFUL"));
            else
                System.out.println(Notifications.getMessage("ERR_ALREADY_EXTENDED"));
        }

        switch(loanOutcome) {
            case 1:
                System.out.println(Notifications.getMessage("ERR_TOO_EARLY_TO_EXTEND"));
                break;
            case -1:
                System.out.println(Notifications.getMessage("ERR_LOAN_DOES_NOT_EXIST"));
                break;
            default:
                break;
        }
    }
}

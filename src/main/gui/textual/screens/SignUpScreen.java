package main.gui.textual.screens;
import main.controller.UserController;
import main.utility.InputParserUtility;
import main.utility.exceptions.IllegalDateFormatException;
import main.utility.notifications.Notifications;
import static main.utility.GlobalParameters.LEGAL_AGE_IN_YEARS;

/**
 * The sign up screen.
 *
 * @author Alessandro Polcini
 */
public class SignUpScreen extends Screen {

    /**
     * Boots up the section from which the user can sign up.
     *
     * @param userController The system user controller.
     */
    public SignUpScreen(UserController userController) {
        super(userController);
        System.out.printf("%s%n%s%n", Notifications.getMessage("PROMPT_SIGN_UP_SCREEN"), Notifications.getMessage("SEPARATOR"));

        int status;
        do {
            status = signUp();
        } while(status == 1);
    }

    //sign up screen
    private int signUp() {
        String[] details;

        try {
            details = fillDetails();
        }
        catch(InterruptedException iEx) {
            System.out.printf("%s %s%n", Notifications.getMessage("ERR_SIGN_UP_ABORTED"), Notifications.getMessage("MSG_EXIT_WITHOUT_SAVING"));
            return -1;
        }

        try {
            if(!InputParserUtility.isOfAge(details[4], LEGAL_AGE_IN_YEARS)) {
                System.out.println(Notifications.getMessage("ERR_NOT_OF_AGE"));
                return 1;
            }
        }
        catch (IllegalDateFormatException idfEx) {
            idfEx.printStackTrace();
        }

        if(getUserController().addUserToDatabase(details[0], details[1], details[2], details[3], InputParserUtility.toGregorianDate(details[4]))) {
            System.out.println(Notifications.getMessage("MSG_SIGN_UP_SUCCESSFUL"));
            return 0;
        }
        else
            return userAlreadyPresent();
    }

    //to insert sign-up details
    private String[] fillDetails() throws InterruptedException {
        System.out.print(Notifications.getMessage("PROMPT_FIRST_NAME"));
        String firstName = insertName();

        System.out.print(Notifications.getMessage("PROMPT_LAST_NAME"));
        String lastName = insertName();

        System.out.print(Notifications.getMessage("PROMPT_USERNAME"));
        String username = getScanner().nextLine();

        System.out.print(Notifications.getMessage("PROMPT_PASSWORD"));
        String password = getScanner().nextLine();

        System.out.print(Notifications.getMessage("PROMPT_BIRTHDAY"));
        String birthday = insertDate();

        System.out.printf("%s%n%s%n%s%n", Notifications.getMessage("SEPARATOR"), Notifications.getMessage("PROMPT_SIGN_UP_CONFIRMATION"), Notifications.getMessage("SEPARATOR"));

        if(insertString(YN_REGEX).equalsIgnoreCase(NO))
            throw new InterruptedException();

        return new String[]{firstName, lastName, username, password, birthday};
    }

    //routine that gets executed if the user is trying to sign up but is already present in the database
    private int userAlreadyPresent() {
        System.out.printf("%s%n%s%n%s%n%s%n", Notifications.getMessage("ERR_USER_ALREADY_PRESENT"), Notifications.getMessage("SEPARATOR"), Notifications.getMessage("PROMPT_PRESENT_USER_MULTIPLE_CHOICE"), Notifications.getMessage("SEPARATOR"));

        switch(insertInteger(1, 3)) {
            case 1:
                System.out.printf("%s %s%n", Notifications.getMessage("MSG_EXIT_WITHOUT_SAVING"), Notifications.getMessage("MSG_MOVE_TO_LOGIN"));
                return -1;
            case 2:
                System.out.println(Notifications.getMessage("PROMPT_MODIFY_FIELDS"));
                return 1;
            default:
                return 0;
        }
    }

}

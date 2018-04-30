package main.gui.screens;
import main.SystemController;
import main.utility.InputParserUtility;

import static main.utility.notifications.Notifications.*;

/**
 * The sign up screen.
 *
 * @author Alessandro Polcini
 */
public class SignUpScreen extends Screen {

    /**
     * Boots up the section from which the user can sign up.
     *
     * @param controller The system controller.
     */
    public SignUpScreen(SystemController controller) {
        super(controller);
        System.out.printf("%s\n%s\n", PROMPT_SIGN_UP_SCREEN, SEPARATOR);

        int status;
        do {
            status = signUp();
        } while(status == 1);
    }

    private int signUp() {
        String[] details;

        try {
            details = fillDetails();
        }
        catch(InterruptedException IEx) {
            System.out.printf("%s %s\n", ERR_SIGN_UP_ABORTED, MSG_EXIT_WITHOUT_SAVING);
            return -1;
        }

        if(!getController().legalAge(InputParserUtility.toGregorianDate(details[4]))) {
            System.out.println(ERR_NOT_OF_AGE);
            return 1;
        }
        if(!getController().userIsPresent(details[2])) {
            getController().addUserToDatabase(details[0], details[1], details[2], details[3], InputParserUtility.toGregorianDate(details[4]));
            System.out.println(MSG_SIGN_UP_SUCCESSFUL);
            return 0;
        }
        else
            return userAlreadyPresent();
    }

    private String[] fillDetails() throws InterruptedException {
        System.out.print(PROMPT_FIRST_NAME);
        String firstName = insertName();

        System.out.print(PROMPT_LAST_NAME);
        String lastName = insertName();

        System.out.print(PROMPT_USERNAME);
        String username = getScanner().nextLine();

        System.out.print(PROMPT_PASSWORD);
        String password = getScanner().nextLine();

        System.out.print(PROMPT_BIRTHDAY);
        String birthday = insertDate();

        System.out.printf("%s\n%s\n%s\n", SEPARATOR, PROMPT_SIGN_UP_CONFIRMATION, SEPARATOR);

        if(insertString(YN_REGEX).equalsIgnoreCase(NO))
            throw new InterruptedException();

        return new String[]{firstName, lastName, username, password, birthday};
    }

    private int userAlreadyPresent() {
        System.out.printf("%s\n%s\n%s\n%s\n", ERR_USER_ALREADY_PRESENT, SEPARATOR, PROMPT_PRESENT_USER_MULTIPLE_CHOICE, SEPARATOR);

        switch(insertInteger(1, 3)) {
            case 1:
                System.out.printf("%s %s\n", MSG_EXIT_WITHOUT_SAVING, MSG_MOVE_TO_LOGIN);
                return -1;
            case 2:
                System.out.println(PROMPT_MODIFY_FIELDS);
                return 1;
            default:
                return 0;
        }
    }

}

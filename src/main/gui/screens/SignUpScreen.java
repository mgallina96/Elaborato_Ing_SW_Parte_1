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

        boolean valid;
        System.out.printf("%s\n%s\n", PROMPT_SIGN_UP_SCREEN, SEPARATOR);

        //TODO Cambiare il rinnovo con lo spostamento alla sezione login

        do {
            valid = true;

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

            if(insertString(YN_REGEX).equalsIgnoreCase(YES)) {
                if(!getController().legalAge(InputParserUtility.toGregorianDate(birthday))) {
                    System.out.println(ERR_NOT_OF_AGE);
                    continue;
                }

                if(!getController().checkUserLogin(username, password)) {
                    getController().addUserToDatabase(firstName, lastName, username, password, InputParserUtility.toGregorianDate(birthday));
                    System.out.println(MSG_SIGN_UP_SUCCESSFUL);
                    break;
                }
                else {
                    System.out.printf("%s\n%s\n%s\n%s\n", ERR_USER_ALREADY_PRESENT, SEPARATOR, PROMPT_PRESENT_USER_MULTIPLE_CHOICE, SEPARATOR);

                    switch(insertInteger(1, 4)) {
                        case 1:
                            System.out.println(MSG_EXIT_WITHOUT_SAVING);
                            valid = false;
                            break;
                        case 2:
                            System.out.println(PROMPT_MODIFY_FIELDS);
                            break;
                        case 3:
                            getController().renewSubscription();
                            valid = false;
                            break;
                    }
                }
            }
            else {
                System.out.printf("%s %s\n", ERR_SIGN_UP_ABORTED, MSG_EXIT_WITHOUT_SAVING);
                break;
            }

        } while(valid);
    }

}

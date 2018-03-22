package main.gui.screens;
import main.Controller;
import main.utility.InputParserUtility;
import static main.utility.Notifications.*;

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
    public SignUpScreen(Controller controller) {
        super(controller);

        boolean valid;
        System.out.printf("%s\n%s\n", PROMPT_SIGN_UP_SCREEN, SEPARATOR);

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

            String choice;
            do {
                choice = getScanner().nextLine();
            } while(choice.matches("[^ynYN]"));

            if(choice.matches("[yY]")) {
                if(!getController().legalAge(InputParserUtility.toGregorianDate(birthday))) {
                    System.out.println(ERR_NOT_OF_AGE);
                    continue;
                }

                if(!getController().checkUserLogin(username, password)) {
                    getController().addUserToDatabase(firstName, lastName, username, password, InputParserUtility.toGregorianDate(birthday));
                    getController().saveDatabase();
                    System.out.println(MSG_SIGN_UP_SUCCESSFUL);
                    break;
                }
                else {
                    System.out.println(PROMPT_PRESENT_USER_MULTIPLE_CHOICE);
                    String input;

                    do {
                        input = getScanner().nextLine();
                    } while(!InputParserUtility.isValidInteger(input, 1, 4));

                    switch(Integer.parseInt(input)) {
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

    /**
     * Loops a scanner until the inserted {@code String} is a valid first name (or last name).
     * <p>The parameters and the logic for name validity are defined in the {@code isValidName()} method of the
     * {@link InputParserUtility} class.
     *
     * @return a valid name in the form of a {@code String}.
     */
    private String insertName() {
        String name = getScanner().nextLine();

        while(!InputParserUtility.isValidName(name)) {
            System.out.println(ERR_INVALID_NAME);
            name = getScanner().nextLine();
        }

        return name;
    }

    /**
     * Loops a scanner until the inserted {@code String} is a valid birth date.
     * <p>The parameters and the logic for date validity are defined in the {@code isValidDate()} method of the
     * {@link InputParserUtility} class.
     *
     * @return a valid birth date in the form of a {@code String}.
     */
    private String insertDate() {
        String date = getScanner().nextLine();

        while(!InputParserUtility.isValidDate(date)) {
            System.out.println(ERR_INVALID_DATE);
            date = getScanner().nextLine();
        }

        return date;
    }
}

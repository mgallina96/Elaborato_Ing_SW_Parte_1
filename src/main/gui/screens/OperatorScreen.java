package main.gui.screens;
import main.Controller;
import main.utility.InputParserUtility;
import java.util.logging.Logger;
import static main.utility.Notifications.*;

/**
 * The operator menu screen.
 *
 * @author Manuel Gallina
 */
public class OperatorScreen extends Screen {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Constructor for the OperatorScreen class. It boots up the operator section.
     * @param controller The system controller.
     */
    public OperatorScreen(Controller controller) {
        super(controller);
        boolean exitFromOperatorSection = false;

        while(!exitFromOperatorSection) {
            System.out.printf("%s\n%s\n%s\n", SEPARATOR, PROMPT_OPERATOR_CHOICES, SEPARATOR);

            String command;
            do {
                command = getScanner().nextLine();
            } while(!InputParserUtility.isValidInteger(command, 1, 6));

            switch(Integer.parseInt(command)) {
                case 1:
                    //Aggiungi risorsa
                    addMedia();
                    break;
                case 2:
                    //Rimuovi risorsa
                    removeMedia();
                    break;
                case 3:
                    //Visualizza risorse
                    System.out.printf("%s\n%s\n", MSG_MEDIA_LIST, getController().allMediaToString());
                    break;
                case 4:
                    System.out.printf("%s\n%s\n", MSG_USER_LIST, getController().allUsersToString());
                    break;
                case 5:
                    System.out.printf("%s\n", MSG_LOG_OUT);
                    exitFromOperatorSection = true;
                    getController().logout();
                    break;
                default:
                    break;
            }
        }
    }

    private void addMedia() {
        System.out.println();
        getController().addMediaToDatabase("","","",0,"");
    }

    private void removeMedia() {
        System.out.print("");
        getController().removeMediaFromDatabase(1);
    }
}

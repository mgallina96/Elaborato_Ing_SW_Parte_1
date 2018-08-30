package main;
import main.controller.*;
import main.gui.GuiManager;
import main.gui.textual.TextualView;
import main.model.user.User;
import main.utility.notifications.Notifications;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The entry point of the application.
 *
 * @author Manuel Gallina, Giosuè Filippini, Alessandro Polcini
 * @version 0.1
 * @since version 0.1 - 03/03/2018
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final Controller USER_CONTROLLER = SystemUserController.getInstance();
    private static final Controller MEDIA_CONTROLLER = SystemMediaController.getInstance();
    private static final Controller LOAN_CONTROLLER = SystemLoanController.getInstance();
    private static final FileSystemController FILE_SYSTEM_CONTROLLER = FileSystemManager.getInstance();

    public static void main(String[] args) {
        Notifications.setLanguage(Notifications.LANGUAGE_ITA);
        LOGGER.setLevel(Level.ALL);

        GuiManager textualView = new TextualView(USER_CONTROLLER, MEDIA_CONTROLLER, LOAN_CONTROLLER, FILE_SYSTEM_CONTROLLER);
        textualView.mainScreen();
//      GuiManager graphicalView = new GraphicView(CONTROLLER, FILE_SYSTEM_CONTROLLER);
//      graphicalView.mainScreen();
    }
}

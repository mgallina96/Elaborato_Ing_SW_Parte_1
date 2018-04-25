package main;
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

    public static void main(String[] args) {
        Notifications.setLanguage(Notifications.ITALIAN);
        LOGGER.setLevel(Level.ALL);
        SystemController controller = Controller.getInstance();
        controller.init();
    }
}

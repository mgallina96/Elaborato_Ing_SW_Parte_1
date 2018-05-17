package main;
import main.controller.Controller;
import main.controller.FileSystemController;
import main.controller.FileSystemManager;
import main.controller.SystemController;
import main.gui.GuiManager;
import main.gui.textual.TextualView;
import main.utility.notifications.Notifications;

import java.io.File;
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
    private static final Notifications NOTIFICATIONS = Notifications.getInstance();
    private static final Controller CONTROLLER = SystemController.getInstance();
    private static final FileSystemController FILE_SYSTEM_CONTROLLER = FileSystemManager.getInstance();

    public static void main(String[] args) {
        NOTIFICATIONS.setLanguage(Notifications.ENGLISH);

        LOGGER.setLevel(Level.ALL);

        //GuiManager guiManager = new TextualView()
    }
}

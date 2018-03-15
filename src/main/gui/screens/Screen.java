package main.gui.screens;
import main.SystemController;
import java.util.Scanner;

/**
 * Class implementing a generic screen.
 *
 * @author Manuel Gallina
 */
public class Screen {

    private Scanner scanner;
    private SystemController controller;

    /**
     * Constructor for the Screen class, initializing a {@link Scanner} and assigning a controller.
     *
     * @param controller The system controller.
     */
    public Screen(SystemController controller) {
        scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public SystemController getController() {
        return controller;
    }
}

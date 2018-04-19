package main.gui.screens;

import main.SystemController;

import java.util.Scanner;

/**
 * Class that implements a generic screen.
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
    Screen(SystemController controller) {
        scanner = new Scanner(System.in);
        this.controller = controller;
    }

    /**
     * Getter for the {@link Scanner} from which all keyboard input is taken.
     *
     * @return The application scanner.
     */
    Scanner getScanner() {
        return scanner;
    }

    /**
     * Getter for the system controller.
     *
     * @return The system controller.
     */
    public SystemController getController() {
        return controller;
    }
}

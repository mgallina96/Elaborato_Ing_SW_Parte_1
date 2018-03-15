package main.gui.screens;

import main.SystemController;

import java.util.Scanner;

/**
 * Generic screen.
 *
 * @author Manuel Gallina
 */
public class Screen {
    private Scanner scanner;
    private SystemController controller;

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

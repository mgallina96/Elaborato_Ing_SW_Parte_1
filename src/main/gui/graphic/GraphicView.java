package main.gui.graphic;

import main.controller.*;
import main.gui.GuiManager;
import main.gui.graphic.screens.start.LoginScreen;

/**
 * Class that manages a graphic GUI for the application, loading the different sections when they are needed and
 * handling the interactions between them.
 *
 * @author Manuel Gallina
 */
public class GraphicView implements GuiManager {
    private Controller controller;
    private FileSystemController fileSystemController;

    public GraphicView(Controller controller, FileSystemController fileSystemController) {
        this.controller = controller;
        this.fileSystemController = fileSystemController;
        Resources.init();
    }

    @Override
    public void mainScreen() {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.init((UserController) controller);
    }

    @Override
    public void loginScreen() {
    }

    @Override
    public void signUpScreen() {

    }

    @Override
    public void operatorScreen() {

    }

    @Override
    public void customerScreen() {

    }

    public Controller getController() {
        return controller;
    }

    public FileSystemController getFileSystemController() {
        return fileSystemController;
    }
}

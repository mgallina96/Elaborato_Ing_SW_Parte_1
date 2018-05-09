package main.gui.graphic;

import main.SystemController;
import main.gui.GuiManager;
import main.gui.graphic.screens.LoginScreen;

/**
 * Class that manages a graphic GUI for the application, loading the different sections when they are needed and
 * handling the interactions between them.
 *
 * @author Manuel Gallina
 */
public class GraphicView implements GuiManager {
    private SystemController controller;

    public GraphicView(SystemController controller) {
        this.controller = controller;
        Resources.init();
    }

    @Override
    public void mainScreen() {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.init(controller);
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

    public SystemController getController() {
        return controller;
    }
}

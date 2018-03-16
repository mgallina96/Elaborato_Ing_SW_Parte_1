package main.gui;

import main.SystemController;
import main.gui.screens.CustomerScreen;
import main.gui.screens.LoginScreen;
import main.gui.screens.MainScreen;
import main.gui.screens.SignUpScreen;
import main.gui.screens.OperatorScreen;

/**
 * Class for the  textual representation of the application. It creates the different screen and manage the interaction between them.
 *
 * @author Giosuè Filippini
 */
public class TextualView implements GuiManager {

    private SystemController controller;

    /**
     * TextualView constructor.
     * @param controller The system controller.
     */
    public TextualView(SystemController controller) {
        this.controller = controller;
    }

    /**
     * Builds the main screen.
     */
    @Override
    public void mainScreen() {
        MainScreen mainScreen = new MainScreen(controller);

        switch(mainScreen.takeChoice()) {
            case 1:
                loginScreen();
                break;
            case 2:
                signUpScreen();
                break;
            default:
                break;
        }
    }

    /**
     * Builds the login screen.
     */
    @Override
    public void loginScreen() {
        LoginScreen loginScreen = new LoginScreen(controller);

        switch(controller.getUserStatus(loginScreen.login())) {
            case OPERATOR:
                operatorScreen();
                break;
            case CUSTOMER:
                customerScreen();
                break;
            default: // in case of null value: it means that the username or the password is not valid.
                mainScreen();
        }

    }

    /**
     * Builds the sign up screen.
     */
    @Override
    public void signUpScreen() {
        new SignUpScreen(controller);
    }

    /**
     * Builds the operator screen
     */
    @Override
    public void operatorScreen() {
        new OperatorScreen(controller);
    }

    /**
     * Builds the customer screen
     */
    @Override
    public void customerScreen() {
        new CustomerScreen(controller);
    }
}

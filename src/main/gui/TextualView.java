package main.gui;
import main.SystemController;
import main.gui.screens.CustomerScreen;
import main.gui.screens.LoginScreen;
import main.gui.screens.MainScreen;
import main.gui.screens.SignUpScreen;
import main.gui.screens.OperatorScreen;

/**
 * Class that manages a textual GUI for the application, loading the different sections when they are needed and
 * handling the interactions between them.
 *
 * @author Giosuè Filippini
 */
public class TextualView implements GuiManager {

    private SystemController controller;

    /**
     * Constructor for the TextualView class.
     *
     * @param controller The system controller.
     */
    public TextualView(SystemController controller) {
        this.controller = controller;
    }

    /**
     * Loads the main screen.
     */
    @Override
    public void mainScreen() {
        MainScreen mainScreen = new MainScreen(controller);

        switch(mainScreen.menuChoices()) {
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
     * Loads the login screen.
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
            default: //in case a null value is passed: this means that either the username or the password isn't valid.
                mainScreen();
        }

    }

    /**
     * Loads the sign up screen.
     */
    @Override
    public void signUpScreen() {
        new SignUpScreen(controller);
    }

    /**
     * Loads the operator screen
     */
    @Override
    public void operatorScreen() {
        new OperatorScreen(controller);
    }

    /**
     * Loads the customer screen
     */
    @Override
    public void customerScreen() {
        new CustomerScreen(controller);
    }
}

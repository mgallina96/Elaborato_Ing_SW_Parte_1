package main.gui;
import main.SystemController;
import main.gui.screens.*;

import static main.utility.Notifications.MSG_GOODBYE;

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

    @Override
    public void signUpScreen() {
        new SignUpScreen(controller);
    }

    @Override
    public void operatorScreen() {
        new OperatorScreen(controller);
    }

    @Override
    public void customerScreen() {
        new CustomerScreen(controller);
    }

    @Override
    public void mainScreen() {
        MainScreen mainScreen = new MainScreen(controller);
        boolean exit = false;

        while(!exit) {
            switch(mainScreen.menuChoices()) {
                case 1:
                    loginScreen();
                    break;
                case 2:
                    signUpScreen();
                    break;
                case 3:
                    System.out.printf("%s\n", MSG_GOODBYE);
                    exit = true;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void loginScreen() {
        LoginScreen loginScreen = new LoginScreen(controller);

        switch(controller.getUserStatus(loginScreen.login())) {
            case 0:
                customerScreen();
                break;
            case 1:
                operatorScreen();
                break;
            default: //in case ths user status is still a null value (either the username or the password isn't valid).
                mainScreen();
        }
    }
}

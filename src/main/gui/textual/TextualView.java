package main.gui.textual;
import main.controller.*;
import main.gui.GuiManager;
import main.gui.textual.screens.*;
import main.utility.notifications.Notifications;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

/**
 * Class that manages a textual GUI for the application, loading the different sections when they are needed and
 * handling the interactions between them.
 *
 * @author Giosuè Filippini
 */
public class TextualView implements GuiManager {
    private UserController userController;
    private MediaController mediaController;
    private LoanController loanController;
    private FileSystemController fileSystemController;

    /**
     * Constructor for the TextualView class.
     *
     * @param controllers The system controllers.
     * @param <C> Objects of the Controller type.
     */
    @SafeVarargs
    public <C extends Controller> TextualView(@NotNull C... controllers) {
        boolean[] controllerSet = new boolean[3];
        for(C controller : controllers) {
            try {
                if(controller instanceof UserController && !controllerSet[0]) {
                    this.userController = (UserController) controller;
                    controllerSet[0] = true;
                }
                else if(controller instanceof MediaController && !controllerSet[1]) {
                    this.mediaController = (MediaController) controller;
                    controllerSet[1] = true;
                }
                else if(controller instanceof LoanController && !controllerSet[2]) {
                    this.loanController = (LoanController) controller;
                    controllerSet[2] = true;
                }
                else if(controller instanceof FileSystemController)
                    this.fileSystemController = (FileSystemController) controller;
            }
            catch(ClassCastException ccEX) {
                ccEX.printStackTrace();
            }
        }
    }

    @Override
    public void signUpScreen() {
        new SignUpScreen(userController);
    }

    @Override
    public void operatorScreen() {
        new OperatorScreen(userController, mediaController, loanController, fileSystemController);
    }

    @Override
    public void customerScreen() {
        new CustomerScreen(userController, mediaController, loanController);
    }

    @Override
    public void mainScreen() {
        MainScreen mainScreen = new MainScreen();
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
                    settings();
                    break;
                case 4:
                    System.out.printf("%s%n", Notifications.getMessage("MSG_GOODBYE"));
                    exit = true;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void loginScreen() {
        LoginScreen loginScreen = new LoginScreen(userController);

        switch(userController.getUserStatus(loginScreen.login())) {
            case 0:
                customerScreen();
                break;
            case 1:
                operatorScreen();
                break;
            default: //in case ths user status is still a null value (either the username or the password isn't valid).
                break;
        }
    }

    private void settings() {
        new SettingsScreen();
    }
}

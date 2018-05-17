package main.gui.textual;
import main.*;
import main.gui.GuiManager;
import main.gui.textual.screens.*;
import main.model.database.filesystem.FileSystem;

import static main.utility.notifications.Notifications.MSG_GOODBYE;

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
     * @param controller The system controller.
     */
    public TextualView(UserController userController, MediaController mediaController, LoanController loanController, FileSystemController fileSystemController) {
        this.userController = userController;
        this.mediaController = mediaController;
        this.loanController = loanController;
        this.fileSystemController = fileSystemController;
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
}

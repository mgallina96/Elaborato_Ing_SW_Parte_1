package main.gui;

/**
 * Main GUI class which sets up the graphical appearance and manages any kind of interaction with the user.
 * It also serves as the interface for communication between the view module and the controller.
 *
 * @author Manuel Gallina
 * @author Giosuè Filippini
 */
public interface GuiManager {

    /** Loads the main screen. */
    void mainScreen();

    /** Loads the login screen. */
    void loginScreen();

    /** Loads the sign up screen. */
    void signUpScreen();

    /** Loads the operator screen. */
    void operatorScreen();

    /** Loads the customer screen. */
    void customerScreen();
}

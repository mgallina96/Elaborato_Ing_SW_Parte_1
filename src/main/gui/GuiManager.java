package main.gui;

/**
 * Main GUI class which sets up the graphical appearance and manages any kind of interaction with the user.
 * It also serves as the interface for communication between the view module and the controller.
 *
 * @author Manuel Gallina
 * @author Giosuè Filippini
 */
public interface GuiManager {

    void mainScreen();

    void loginScreen();

    void signUpScreen();

    void operatorScreen();

    void customerScreen();
}

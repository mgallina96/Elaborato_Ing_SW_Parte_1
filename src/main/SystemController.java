package main;

import main.gui.View;
import main.model.user.Customer;
import main.model.Database;

/**
 * Controller class that manages all the interactions between the graphic user interface
 * and the logic model of the program.
 *
 * @author Manuel Gallina
 * @version 0.1
 * @since version 0.1 - 12/03/2018
 */
public class SystemController {
    private static SystemController instance;

    private View view;
    private Database database;

    //Singleton constructor
    private SystemController() {
        database = Database.getInstance();
        view = new View(this);
    }

    /**
     * Returns the system controller.
     *
     * @return The system controller.
     */
    public static SystemController getInstance() {
        if (instance == null)
            instance = new SystemController();
        return instance;
    }

    /**
     * Initializes the system controller which starts the gui.
     */
    public void init() {
        view.start();
    }

    /**
     * Checks if the given username and password match with a registered user.
     *
     * @param username The username.
     * @param password The password.
     * @return True if the user credentials are correct, false otherwise.
     */
    public boolean checkUser(String username, String password) {
        return database.isPresent(new Customer(username, password));
    }
}

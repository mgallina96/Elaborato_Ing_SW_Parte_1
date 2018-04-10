package main;

import main.model.database.Database;
import main.model.database.DatabaseManager;
import main.model.user.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the system controller.
 *
 * @author Manuel Gallina
 */
class ControllerTest {
    private static final Database DATABASE = DatabaseManager.getInstance();
    private static final Controller CONTROLLER = Controller.getInstance();

    @BeforeEach
    void setUp() {
        DATABASE.add(new Customer("Mario", "12345"));
    }

    @Test
    void checkUser() {
        String correctUsername = "Mario";
        String correctPassword = "12345";
        String wrongUsername = "Paolo";
        String wrongPassword = "54321";

        Assertions.assertTrue(CONTROLLER.checkUserLogin(correctUsername, correctPassword));
        Assertions.assertFalse(CONTROLLER.checkUserLogin(wrongUsername, wrongPassword));
    }
}
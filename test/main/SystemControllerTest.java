package main;

import main.model.Database;
import main.model.user.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the system controller.
 *
 * @author Manuel Gallina
 */
class SystemControllerTest {
    private static final Database DATABASE = Database.getInstance();
    private static final SystemController CONTROLLER = SystemController.getInstance();

    @BeforeEach
    void setUp() {
        DATABASE.addUser(new Customer("Mario", "12345"));
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
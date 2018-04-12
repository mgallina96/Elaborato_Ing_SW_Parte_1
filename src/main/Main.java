package main;

import main.model.media.Book;

/**
 * The entry point of the application.
 *
 * @author Manuel Gallina, Giosuè Filippini, Alessandro Polcini
 * @version 0.1
 * @since version 0.1 - 03/03/2018
 */
public class Main {

    public static void main(String[] args) {
        SystemController controller = Controller.getInstance();
        ((Controller) controller).init();
    }
}

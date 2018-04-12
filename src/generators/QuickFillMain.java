package generators;

import main.model.database.DatabaseManager;
import main.model.media.Media;
import main.model.user.User;

/**
 * Created by Alessandro on 12/04/18.
 */
public class QuickFillMain {

    //quanto volete riempire il database, viene riempito con (2^FILLING_LEVEL)^2 utenti e libri.
    public static final byte FILLING_LEVEL = 5;

    public static void main(String[] args) {

        DatabaseManager d = DatabaseManager.getInstance();
        Generator generator = new Generator(FILLING_LEVEL);

        for(User u : generator.getUsers())
            d.add(u);
        for(Media m : generator.getBooks())
            d.add(m);

        d.saveDatabase();
    }

}

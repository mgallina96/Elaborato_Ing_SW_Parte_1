package generators;

import main.model.database.DatabaseManager;
import main.model.database.filesystem.FileSystem;
import main.model.database.filesystem.Folder;
import main.model.media.Media;
import main.model.user.User;

/**
 * Created by Alessandro on 12/04/18.
 */
public class QuickFillMain {

    //di quanto volete riempire il database? Viene riempito con (2^FILLING_LEVEL)^2 utenti e libri.
    private static final byte FILLING_LEVEL = 6;

    public static void main(String[] args) {
/*
        DatabaseManager d = DatabaseManager.getInstance();
        Generator generator = new Generator(FILLING_LEVEL);

        for(User u : generator.getUsers())
            d.add(u);
        for(Media m : generator.getBooks())
            d.add(m, "");

        //d.saveDatabase();*/

        FileSystem fs = FileSystem.getInstance();

        System.out.println(
                fs.getAllPathsToString()
        );


    }
}

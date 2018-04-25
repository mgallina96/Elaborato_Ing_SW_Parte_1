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

        //fs.saveFileSystem();

        System.out.println(
                fs.getAllPaths()
        );


        /*

        Da mettere nel costruttore del FileSystem. Utile per riempire il filesystem (per ora)

        fileSystem.put(2, new Folder("Libri", ROOT,  2));
        fileSystem.put(3, new Folder("Film", ROOT, 3));

        fileSystem.put(4, new Folder("Horror", fileSystem.get(2),  4));
        fileSystem.put(5, new Folder("Gialli", fileSystem.get(2), 5));
        fileSystem.put(6, new Folder("Avventura", fileSystem.get(2), 6));
        fileSystem.put(7, new Folder("Romanzo Storico", fileSystem.get(2), 7));

        fileSystem.put(8, new Folder("Thriller", fileSystem.get(3), 8));
        fileSystem.put(9, new Folder("Fantascienza", fileSystem.get(3), 9));
        fileSystem.put(10, new Folder("Azione", fileSystem.get(3), 10));
        fileSystem.put(11, new Folder("Supereroi", fileSystem.get(3), 11));

        fileSystem.put(12, new Folder("Anni 70",  fileSystem.get(10), 12));
        fileSystem.put(13, new Folder("Anni 80",  fileSystem.get(10), 13));
        fileSystem.put(14, new Folder("Anni 90",  fileSystem.get(10), 14));
        fileSystem.put(15, new Folder("Anni 10",  fileSystem.get(7), 15));
        fileSystem.put(16, new Folder("Anni 20",  fileSystem.get(7), 16));

         */


    }
}

package generators;
import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;
import main.model.database.DatabaseManager;
import main.model.database.LoanDatabase;
import main.model.database.UserDatabase;
import main.model.database.filesystem.FileSystem;
import main.model.database.filesystem.Folder;
import main.model.media.Book;
import main.model.media.Film;
import main.model.media.Media;
import main.model.user.User;
import main.utility.notifications.Notifications;

import java.util.ArrayList;
import java.util.HashMap;

import static generators.Generator.COMMON_MEDIA_PATH;

/**
 * Class QuickFillMain, to quickly fill all of the databases (FileSystem, User database, Media database and Loan
 * database).
 * @author Alessandro Polcini.
 */
public class QuickFillMain {

    //di quanto volete riempire il database? Viene riempito con (2^FILLING_LEVEL)^2 utenti e libri.
    private static final byte FILLING_LEVEL = 6;

    public static void main(String[] args) {
        //fillFileSystem();
        //fillDatabase();
        //printFileSystem();
    }

    private static void printFileSystem() {
        FileSystem fs = FileSystem.getInstance();

        System.out.println("Tree structure:");
        System.out.println(fs.treeToString(fs.getFileSystem().get(fs.getRootID()), 0) + "\n");

        System.out.println("All paths:");
        System.out.println(fs.getAllPaths() + "\n");

        System.out.println("Robe:");
        fs.getFileSystem().values().forEach(s -> System.out.println(s.getName() + "\t" + s.getChildren().size()));
    }

    private static void fillFileSystem() {
        FileSystem fs = FileSystem.getInstance();
        HashMap<Integer, Folder> fileSystem = fs.getFileSystem();
        Folder ROOT = fileSystem.get(fs.getRootID());

        fs.addFolder("Libri", ROOT);
        fs.addFolder("Film", ROOT);

        String[] genres = PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "generi_libro.txt");

        for(String g : genres)
            fs.addFolder(g, fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        for(String g : genres)
            fs.addFolder(g, fs.getFolder(ROOT.getChildren().get(1).getFolderId()));

        fs.saveFileSystem();
    }

    private static void fillDatabase() {
        Notifications notifications = Notifications.getInstance();
        notifications.setLanguage(Notifications.ENGLISH);

        DatabaseManager d = DatabaseManager.getInstance();
        Generator generator = new Generator(FILLING_LEVEL);

        for(User u : generator.getUsers())
            d.add(u);
        for(Media m : generator.getBooks())
            if(m instanceof Book)
                d.add(m, "root\\Libri\\" + ((Book)m).getGenre() + "\\");
        for(Media f : generator.getFilms())
            if(f instanceof Film)
                d.add(f, "root\\Film\\" + ((Film)f).getGenre() + "\\");

        //these methods need to be made (temporarily) public
//        d.getMediaDatabase().saveMediaDatabase();
//        d.saveHashMap("resources\\data\\Biblioteca SMARTINATOR - User Database.ser", d.getUserList());
//        d.saveHashMap("resources\\data\\Biblioteca SMARTINATOR - Loan Database.ser", d.getLoansList());
    }


}

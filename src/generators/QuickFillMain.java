package generators;
import generators.randomwords.PoolLoader;
import main.controller.SystemController;
import main.model.database.LoanDatabase;
import main.model.database.MediaDatabase;
import main.model.database.UserDatabase;
import main.model.database.filesystem.FileSystem;
import main.model.database.filesystem.Folder;
import main.model.media.Book;
import main.model.media.Film;
import main.model.media.Media;
import main.model.user.User;
import main.utility.notifications.Notifications;

import java.util.HashMap;

import static generators.Generator.COMMON_BOOK_PATH;
import static generators.Generator.COMMON_FILM_PATH;
import static generators.Generator.COMMON_MEDIA_PATH;

/**
 * The QuickFillMain Class, whose job is to quickly fill all of the databases (FileSystem, User database, Media database
 * and Loan database).
 *
 * @author Alessandro Polcini.
 */
public class QuickFillMain {

    private static final String COMMON_DATABASE_PATH = "resources\\data\\";
    private static final int HOW_MANY_INSTANCES = 1500;

    public static void main(String[] args) {
        Notifications.setLanguage(Notifications.LANGUAGE_ENG);

        //fillFileSystem();
        //fillDatabase();
        //printFileSystem();
    }

    private static void printFileSystem() {
        FileSystem fs = FileSystem.getInstance();

        System.out.println("Tree structure:");
        System.out.println(fs.treeToString(fs.getFolderStructure().get(fs.getRootID()), 0) + "\n");

        System.out.println("All paths:");
        System.out.println(fs.getAllPaths() + "\n");

        System.out.println("Robe:");
        fs.getFolderStructure().values().forEach(s -> System.out.println(s.getName() + "\t" + s.getChildren().size()));
    }

    private static void fillFileSystem() {
        FileSystem fs = FileSystem.getInstance();
        HashMap<Integer, Folder> fileSystem = fs.getFolderStructure();
        Folder ROOT = fileSystem.get(fs.getRootID());

        fs.addFolder("Libri", ROOT);
        fs.addFolder("Film", ROOT);

        String[] book_genres = PoolLoader.fromTXTFile(COMMON_BOOK_PATH + "book_genres.txt");
        String[] film_genres = PoolLoader.fromTXTFile(COMMON_FILM_PATH + "film_genres.txt");

        for(String g : book_genres)
            fs.addFolder(g, fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        for(String g : film_genres)
            fs.addFolder(g, fs.getFolder(ROOT.getChildren().get(1).getFolderId()));

        fs.saveFileSystem();
    }

    private static void fillDatabase() {
        SystemController sc = SystemController.getInstance();
        UserDatabase userDatabase = UserDatabase.getInstance();
        MediaDatabase mediaDatabase = MediaDatabase.getInstance();
        LoanDatabase loanDatabase = LoanDatabase.getInstance();

        Generator generator = new Generator(HOW_MANY_INSTANCES);

        for(User u : generator.getUsers())
            userDatabase.addUser(u);
        for(Media m : generator.getBooks())
            if(m instanceof Book)
                mediaDatabase.addMedia(m, "root\\Libri\\" + ((Book)m).getGenre() + "\\");
        for(Media f : generator.getFilms())
            if(f instanceof Film)
                mediaDatabase.addMedia(f, "root\\Film\\" + ((Film)f).getGenre() + "\\");

        //this method needs to be made (temporarily) public
        //sc.saveDatabase(COMMON_DATABASE_PATH + "Biblioteca SMARTINATOR - Media Database.ser", mediaDatabase);
        //sc.saveDatabase(COMMON_DATABASE_PATH + "Biblioteca SMARTINATOR - User Database.ser", userDatabase);
        //sc.saveDatabase(COMMON_DATABASE_PATH + "Biblioteca SMARTINATOR - Loan Database.ser", loanDatabase);
    }


}

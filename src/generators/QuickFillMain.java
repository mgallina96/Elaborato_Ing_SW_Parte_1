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

        SystemController sc = SystemController.getInstance();
        UserDatabase userDatabase = UserDatabase.getInstance();
        MediaDatabase mediaDatabase = MediaDatabase.getInstance();
        LoanDatabase loanDatabase = LoanDatabase.getInstance();

        Generator generator = new Generator(FILLING_LEVEL);

        for(User u : generator.getUsers())
            userDatabase.addUser(u);
        for(Media m : generator.getBooks())
            if(m instanceof Book)
                mediaDatabase.addMedia(m, "root\\Libri\\" + ((Book)m).getGenre() + "\\");
        for(Media f : generator.getFilms())
            if(f instanceof Film)
                mediaDatabase.addMedia(f, "root\\Film\\" + ((Film)f).getGenre() + "\\");

        //this method needs to be made (temporarily) public
        //sc.saveDatabase("resources\\data\\Biblioteca SMARTINATOR - Media Database.ser", mediaDatabase);
        //sc.saveDatabase("resources\\data\\Biblioteca SMARTINATOR - User Database.ser", userDatabase);
        //sc.saveDatabase("resources\\data\\Biblioteca SMARTINATOR - Loan Database.ser", loanDatabase);
    }


}

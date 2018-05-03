package generators;
import main.model.database.DatabaseManager;
import main.model.database.filesystem.FileSystem;
import main.model.database.filesystem.Folder;
import main.model.media.Book;
import main.model.media.Media;
import main.model.user.User;
import main.utility.notifications.Notifications;
import java.util.HashMap;

/**
 * Created by Alessandro on 12/04/18.
 */
public class QuickFillMain {

    //di quanto volete riempire il database? Viene riempito con (2^FILLING_LEVEL)^2 utenti e libri.
    private static final byte FILLING_LEVEL = 6;

    public static void main(String[] args) {
        //printFileSystem();
        fillDatabase();
        //fillFileSystem();
    }

    private static void printFileSystem() {
        FileSystem fs = FileSystem.getInstance();

        System.out.println("Tree structure:");
        System.out.println(fs.tree(fs.getFileSystem().get(fs.getRootID()), 0) + "\n");

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

        fs.addFolder("Animali", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Fumetto", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Scienza", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Geografia", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Avventura", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Viaggi", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Didattica", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Fisica", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Automobili", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Arte", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Tecnica", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Romanzo epico", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Raccolta di favole", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Raccolta di poesie", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Religioso", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Salute e benessere", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Giardinaggio", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Spirituale", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Matematica", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Horror", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Romantico", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Giallo", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Fantasy", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Noir", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Thriller", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Fantascienza", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Commedia", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Erotica", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Musica", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Raccolta di fiabe", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Per bambini", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Fotografia", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Biografia", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Romanzo storico", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));
        fs.addFolder("Comico", fs.getFolder(ROOT.getChildren().get(0).getFolderId()));

        fs.addFolder("Documentario", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Avventura", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Azione", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Supereroi", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Horror", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Romantico", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Giallo", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Fantasy", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Noir", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Thriller", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Fantascienza", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Erotica", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Romanzo storico", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));
        fs.addFolder("Comico", fs.getFolder(ROOT.getChildren().get(1).getFolderId()));

        fs.saveFileSystem();
    }

    private static void fillDatabase() {
        Notifications.setLanguage(Notifications.ENGLISH);

        DatabaseManager d = DatabaseManager.getInstance();
        Generator generator = new Generator(FILLING_LEVEL);

        for(User u : generator.getUsers())
            d.add(u);
        for(Media m : generator.getBooks()) {
            if(m instanceof Book)
                d.add(m, "root\\Libri\\" + ((Book)m).getGenre() + "\\");
        }

        //needs to be made (temporarily) public in DatabaseManager class
        //d.saveDatabase();
    }


}

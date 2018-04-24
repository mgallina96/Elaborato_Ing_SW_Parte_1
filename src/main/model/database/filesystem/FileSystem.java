package main.model.database.filesystem;
import main.utility.Notifications;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Manuel Gallina
 */
public class FileSystem implements Serializable {

    private static final String FILESYSTEM_FILE_PATH = "application_resources\\Biblioteca SMARTINATOR - File System.ser";
    private static final Folder ROOT = new Folder("root");
    private static FileSystem instance;
    private String allPaths;
    private Logger logger;
    private HashMap<Integer, Folder> fileSystem;

    private FileSystem() {
        this.fileSystem = new HashMap<>();
        this.logger = Logger.getLogger(this.getClass().getName());

        loadFileSystem();
        this.allPaths = allPathsToString();

        if(!fileSystem.containsValue(ROOT))
            this.fileSystem.put(1, ROOT);
    }

    public static FileSystem getInstance() {
        if(instance == null)
            instance = new FileSystem();

        return instance;
    }

    public boolean isPresent(String path) {
        return allPaths.contains(path);
    }

    public String getAllPaths() {
        return allPaths;
    }

    private String allPathsToString() {
        StringBuilder allPaths = new StringBuilder();

        for(Folder f : fileSystem.values()) {
            ArrayList<String> tmp = new ArrayList<>();

            while(f.getParent() != f) {
                tmp.add(f.getName() + "\\");
                f = f.getParent();
            }

            Collections.reverse(tmp);
            tmp.forEach(allPaths::append);
            allPaths.append("\n");
        }

        return allPaths.toString().trim();
    }

    @SuppressWarnings("unchecked")
    private void loadFileSystem() {
        try {
            FileInputStream fileIn = new FileInputStream(FILESYSTEM_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            this.fileSystem = ((HashMap<Integer, Folder>) in.readObject());

            in.close();
            fileIn.close();
        }
        catch(FileNotFoundException FNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_FILE_NOT_FOUND);
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_LOADING_FILESYSTEM, IOEx);
        }
        catch(ClassNotFoundException CNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_FILESYSTEM_CLASS_NOT_FOUND);
        }
    }

    public void saveFileSystem() {
        try {
            //to increase serializing speed
            RandomAccessFile raf = new RandomAccessFile(FILESYSTEM_FILE_PATH, "rw");

            FileOutputStream fileOut = new FileOutputStream(raf.getFD());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(fileSystem);

            out.close();
            fileOut.close();
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_SAVING_DATABASE, IOEx);
        }
    }

    public static Folder getROOT() {
        return ROOT;
    }
}

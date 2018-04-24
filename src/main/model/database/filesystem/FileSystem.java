package main.model.database.filesystem;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Manuel Gallina
 */
public class FileSystem {

    private static final Folder ROOT = new Folder("root");
    private static FileSystem instance;
    private String allPaths;

    private HashMap<Integer, Folder> fileSystem;

    private FileSystem() {
        this.fileSystem = new HashMap<>();
        this.fileSystem.put(1, ROOT);

        this.fileSystem.put(2, new Folder(ROOT, "B", 2));
        this.fileSystem.put(5, new Folder(ROOT, "C", 5));
        this.fileSystem.put(4, new Folder(fileSystem.get(2), "D", 4));
        this.fileSystem.put(9, new Folder(fileSystem.get(2), "E", 9));
        this.fileSystem.put(19, new Folder(fileSystem.get(2), "F", 19));
        this.fileSystem.put(10, new Folder(fileSystem.get(5), "G", 10));
        this.fileSystem.put(21, new Folder(fileSystem.get(5), "H", 21));
    }

    public static FileSystem getInstance() {
        if(instance == null)
            instance = new FileSystem();

        return instance;
    }

    public String getAllPathsToString() {
        StringBuilder allPaths = new StringBuilder();

        for(Folder f : fileSystem.values()) {
            StringBuilder tmp = new StringBuilder();
            while(f.getParent() != f) {
                tmp.append("\\");
                tmp.append(f.getName());
                f = f.getParent();
            }
            tmp.reverse();
            allPaths.append(tmp);
            allPaths.append("\n");
        }

        return allPaths.toString();
    }


    public static Folder getROOT() {
        return ROOT;
    }
}

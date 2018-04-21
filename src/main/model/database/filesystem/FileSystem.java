package main.model.database.filesystem;

import java.util.HashMap;

/**
 * @author Manuel Gallina
 */
public class FileSystem {
    private static final Folder ROOT = new Folder("root");

    private static FileSystem instance;

    private HashMap<Integer, Folder> fileSystem;

    private FileSystem() {
        this.fileSystem = new HashMap<>();
        this.fileSystem.put(1, ROOT);
    }

    public static FileSystem getInstance() {
        if(instance == null)
            instance = new FileSystem();
        return instance;
    }

    public String toString() {
        return null;
    }
}

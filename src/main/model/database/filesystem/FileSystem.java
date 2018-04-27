package main.model.database.filesystem;

import main.utility.notifications.Notifications;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The file system for this application.
 * <p>
 * Manages the general folder structure and provides methods for checking path validity, checking path presence,
 * returning sub-folders.
 *
 * @author Manuel Gallina, Giosuè Filippini, Alessandro Polcini
 */
public class FileSystem implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = 7970305636210332068L;

    private static final String FILESYSTEM_FILE_PATH = "application_resources\\Biblioteca SMARTINATOR - File System.ser";
    private static final Folder ROOT = new Folder("root");
    private final long ROOT_ID = 1L;

    private static FileSystem instance;
    private String allPaths;
    private Logger logger;
    private HashMap<Long, Folder> fileSystem;

    @SuppressWarnings("all")
    private FileSystem() {
        this.fileSystem = new HashMap<>();
        this.logger = Logger.getLogger(this.getClass().getName());

        loadFileSystem();
        //please DON'T move the following declaration, as the paths can only be resolved AFTER the File System has been loaded.
        this.allPaths = allPathsToString();

        if(!fileSystem.containsKey(ROOT_ID))
            this.fileSystem.put(ROOT_ID, ROOT);
    }

    public static FileSystem getInstance() {
        if(instance == null)
            instance = new FileSystem();

        return instance;
    }

    /**
     * Checks whether the given path is present in the File System.
     *
     * @param path The path to be checked.
     * @return A boolean value, {@code true} if the path is present, {@code false} otherwise.
     */
    public boolean isPresent(String path) {
        return allPaths.contains(path);
    }

    /**
     * Returns all the present paths in the form of a {@code String}.
     *
     * @return A {@code String} containing all paths.
     */
    public String getAllPaths() {
        return allPaths;
    }

    public HashMap<Long, Folder> getFileSystem() {
        return fileSystem;
    }

    /**
     * Getter for the ID of the ROOT folder.
     *
     * @return The ROOT ID's {@code Long} value.
     */
    public long getRootID() {
        return ROOT_ID;
    }

    private String allPathsToString() {
        StringBuilder allPaths = new StringBuilder();

        fileSystem.values().stream()
                .filter(f -> f.getParent() != f)
                .forEach(f -> allPaths.append(f.getFolderPath()).append("\n"));

        return allPaths.toString().trim();
    }

    /**
     * Returns all sub-folders of a desired folder in the form of a {@code String}.
     *
     * @param parentID The ID associated to the parent whose sub-folders are to be visualized.
     * @return A {@code String} containing all sub-folders.
     */
    public String getSubFolders(long parentID) {
        StringBuilder folders = new StringBuilder();

        fileSystem.get(parentID).getChildren()
                .forEach(f -> folders.append(f.getFolderId()).append(".\t").append(f.getName()).append("\n"));

        return folders.toString().trim();
    }

    /**
     * Recursive function that builds a tree structure starting from a folder.
     * A HashMap with specific key values is needed for this method to work correctly.
     * The key values have to follow a binary pattern.
     *
     * @param root The root folder to start from.
     * @param depth The depth of the current folder.
     * @return A string containing the full tree.
     */
    private String tree(Folder root, int depth) {
        StringBuilder treeToString = new StringBuilder();

        for(int i = 0; i < depth; i++)
            treeToString.append("|\t");
        treeToString.append(root.getName()).append("\n");

        Folder timesTwo = fileSystem.get(root.getFolderId() * 2);
        Folder timesTwoPlusOne = fileSystem.get(root.getFolderId() * 2 + 1);

        if(timesTwo != null) {
            depth++;
            treeToString.append(tree(timesTwo, depth));
            depth--;
        }
        if(timesTwoPlusOne != null) {
            treeToString.append(tree(timesTwoPlusOne, depth));
        }

        return treeToString.toString().trim();
    }

    @SuppressWarnings("unchecked")
    private void loadFileSystem() {
        try {
            FileInputStream fileIn = new FileInputStream(FILESYSTEM_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            this.fileSystem = ((HashMap<Long, Folder>) in.readObject());

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

    /**
     * Saves the File System in the form of a HashMap object.
     */
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
}

package main.model.database.filesystem;

import main.utility.notifications.Notifications;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.utility.GlobalParameters.FILESYSTEM_FILE_PATH;

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

    private static final Folder ROOT = new Folder("root");
    private static final int ROOT_ID = 0;

    private static int folderCounter;
    private static FileSystem instance;

    private HashMap<Integer, Folder> folderStructure;
    private String allPaths;

    private transient Logger logger;

    // Private constructor for the unique instance of file system.
    private FileSystem() {
        this.folderStructure = new HashMap<>();
        this.logger = Logger.getLogger(this.getClass().getName());

        loadFileSystem();
        //please DON'T move the following declaration, as the paths can only be resolved AFTER the File System has been loaded.
        this.allPaths = allPathsToString();

        if(!folderStructure.containsKey(ROOT_ID)) {
            this.folderStructure.put(ROOT_ID, ROOT);
            folderCounter++;
        }
    }

    /**
     * Getter/Setter for the FileSystem instance.
     *
     * @return The FileSystem instance.
     */
    public static FileSystem getInstance() {
        if(instance == null)
            instance = new FileSystem();

        return instance;
    }

    /**
     * Adds a folder to the file system.
     *
     * @param name The name of the folder to be added.
     * @param parent Its parent folder.
     */
    public void addFolder(String name, Folder parent) {
        folderStructure.put(folderCounter, new Folder(name, parent, folderCounter));
        incrementFolderCounter();
    }

    /**
     * Returns the folder having the given ID.
     *
     * @param folderID The ID of the requested folder.
     * @return The folder having the given ID.
     */
    public Folder getFolder(int folderID) {
        return folderStructure.get(folderID);
    }

    /**
     * Returns all the present paths in the form of a {@code String}.
     *
     * @return A {@code String} containing all paths.
     */
    public String getAllPaths() {
        return allPaths;
    }

    /**
     * Returns the file system data structure as a hash map where the keys are the folders IDs and the values are the
     * actual {@code Folder} objects.
     *
     * @return The file system.
     */
    public HashMap<Integer, Folder> getFolderStructure() {
        return folderStructure;
    }

    /**
     * Getter for the ID of the ROOT folder.
     *
     * @return The ROOT ID's {@code Long} value.
     */
    public int getRootID() {
        return ROOT_ID;
    }

    /**
     * Returns all sub-folders of a desired folder in the form of a {@code String}.
     *
     * @param parentID The ID associated to the parent whose sub-folders are to be visualized.
     * @return A {@code String} containing all sub-folders.
     */
    public String getSubFolders(int parentID) {
        StringBuilder folders = new StringBuilder();

        folderStructure.get(parentID).getChildren()
                .forEach(f -> folders.append(f.getFolderId()).append(".\t").append(f.getName()).append("\n"));

        return folders.toString().trim();
    }

    /**
     * Recursive function that prints to string a tree structure starting from a folder.
     *
     * @param root The root folder to start from.
     * @param depth The depth of the current folder.
     * @return A string containing the full tree.
     */
    public String treeToString(Folder root, int depth) {
        StringBuilder folders = new StringBuilder();

        for(int i = 0; i < depth; i++)
            folders.append("\t");
        folders.append(root.getName()).append("\n");

        if(!root.getChildren().isEmpty()) {
            depth++;
            for(Folder f : root.getChildren())
                folders.append(treeToString(f, depth));
        }

        return folders.toString();
    }

    private static void setFolderCounter(int folderCounter) {
        FileSystem.folderCounter = folderCounter;
    }

    private static void incrementFolderCounter() {
        folderCounter++;
    }

    /**
     * Opens a .ser serializable file and loads its contents into this {@link FileSystem} class.<p>
     * This method loads:
     * <p>- a {@code HashMap} containing all folders;
     * <p>- an {@code integer} to keep track of the ID of the most recently added folder.
     */
    @SuppressWarnings("unchecked")
    private void loadFileSystem() {
        try(
            FileInputStream fileIn = new FileInputStream(FILESYSTEM_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn)
        ) {
            this.folderStructure = ((HashMap<Integer, Folder>) in.readObject());
            setFolderCounter(Integer.parseInt((String)in.readObject()));
        }
        catch(FileNotFoundException fnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_FILE_NOT_FOUND ") + this.getClass().getName());
        }
        catch(IOException ioEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_LOADING_FILESYSTEM"));
        }
        catch(ClassNotFoundException cnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_CLASS_NOT_FOUND ") + this.getClass().getName());
        }
    }

    /** Saves the File System in the form of a HashMap object. */
    public void saveFileSystem() {
        try(
            //to increase serializing speed
            RandomAccessFile raf = new RandomAccessFile(FILESYSTEM_FILE_PATH, "rw");
            FileOutputStream fileOut = new FileOutputStream(raf.getFD());
            ObjectOutputStream out = new ObjectOutputStream(fileOut)
        ) {
            out.writeObject(folderStructure);
            out.writeObject(Integer.toString(folderCounter));
        }
        catch(IOException ioEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_SAVING_DATABASE ") + this.getClass().getName());
        }
    }

    private String allPathsToString() {
        StringBuilder pathBuilder = new StringBuilder();

        folderStructure.values().stream()
                .filter(f -> f.getParent() != f)
                .forEach(f -> pathBuilder.append(f.getFolderPath()).append("\n"));

        return pathBuilder.toString().trim();
    }
}

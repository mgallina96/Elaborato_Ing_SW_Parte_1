package main.model.database.filesystem;

import main.utility.notifications.Notifications;

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

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = 7970305636210332068L;

    private static final String FILESYSTEM_FILE_PATH = "application_resources\\Biblioteca SMARTINATOR - File System.ser";
    static final Folder ROOT = new Folder("root");
    private static FileSystem instance;
    private String allPaths;
    private Logger logger;
    private HashMap<Integer, Folder> fileSystem;

    @SuppressWarnings("all")
    private FileSystem() {
        this.fileSystem = new HashMap<>();
        this.logger = Logger.getLogger(this.getClass().getName());

        loadFileSystem();
        //please don't FUCKING move the following declaration.
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

    public String pathToString(int folderID) {
        Folder folder = fileSystem.get(folderID);

        StringBuilder path = new StringBuilder();
        ArrayList<String> tmp = new ArrayList<>();

        while(folder.getParent() != folder) {
            tmp.add(folder.getName() + "\\");
            folder = folder.getParent();
        }

        Collections.reverse(tmp);
        tmp.forEach(path::append);

        return path.toString();
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
        setChildrenStatus();

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

    public String getFoldersByDepth(int depth, int parentID) {
       StringBuilder folders = new StringBuilder();

        for(Folder folder : fileSystem.values()) {
            if(folder.getParent().getFolderId() == parentID && folder.getDepth() == depth) {
                folders.append(folder.getFolderId());
                folders.append(".\t");
                folders.append(folder.getName());
                folders.append("\n");
            }
        }

        return folders.toString().trim();
    }

    private void setChildrenStatus() {
        for(Folder f : fileSystem.values())
            for(Folder f1 : fileSystem.values()) {
                if(f.getParent() == f1) {
                    f1.setHasChildren(true);
                    break;
                }
            }
    }

    public HashMap<Integer, Folder> getFileSystem() {
        return fileSystem;
    }

    public static Folder getROOT() {
        return ROOT;
    }
}

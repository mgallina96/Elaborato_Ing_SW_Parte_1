package main.controller;
import main.model.database.filesystem.FileSystem;

public class FileSystemManager implements FileSystemController {

    private static FileSystemManager fileSystemManager;
    private FileSystem fileSystem;

    //Singleton constructor, private to prevent instantiation.
    private FileSystemManager() {
        fileSystem = FileSystem.getInstance();
    }

    public static FileSystemManager getInstance() {
        return (fileSystemManager == null) ? (fileSystemManager = new FileSystemManager()) : fileSystemManager;
    }

    public static FileSystemManager getFileSystemManager() {
        return fileSystemManager;
    }

    public boolean folderHasChildren(int folderID) {
        return !fileSystem.getFileSystem().get(folderID).getChildren().isEmpty();
    }

    public String getSubFolders(int parentID) {
        return fileSystem.getSubFolders(parentID);
    }

    public int getRootID() {
        return fileSystem.getRootID();
    }

    public String getPathToString(int folderID) {
        return fileSystem.getFileSystem().get(folderID).getFolderPath();
    }

}

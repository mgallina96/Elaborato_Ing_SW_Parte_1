package main;
import main.model.database.filesystem.FileSystem;

public class FileSystemManager implements FileSystemController {

    private FileSystem fileSystem;

    //Singleton constructor, private to prevent instantiation.
    private FileSystemManager() {
        fileSystem = FileSystem.getInstance();
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

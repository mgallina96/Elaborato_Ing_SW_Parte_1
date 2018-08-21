package main.controller;
import main.model.database.filesystem.FileSystem;

/**
 * Controller for the FileSystem.
 */
public class FileSystemManager implements FileSystemController {

    private static FileSystemManager fileSystemManager;
    private FileSystem fileSystem;

    //Singleton constructor, private to prevent instantiation.
    private FileSystemManager() {
        fileSystem = FileSystem.getInstance();
    }

    /**
     * Getter/initializer for the {@link FileSystemManager} singleton class.
     *
     * @return The instance of the File System Manager.
     */
    public static FileSystemManager getInstance() {
        if(fileSystemManager == null)
            fileSystemManager = new FileSystemManager();

        return fileSystemManager;
    }

    @Override
    public boolean folderHasChildren(int folderID) {
        return !fileSystem.getFolderStructure().get(folderID).getChildren().isEmpty();
    }

    @Override
    public String getSubFolders(int parentID) {
        return fileSystem.getSubFolders(parentID);
    }

    @Override
    public int getRootID() {
        return fileSystem.getRootID();
    }

    @Override
    public String getPathToString(int folderID) {
        return fileSystem.getFolderStructure().get(folderID).getFolderPath();
    }

}

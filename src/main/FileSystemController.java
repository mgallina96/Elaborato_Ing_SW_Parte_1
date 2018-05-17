package main;

public interface FileSystemController extends Controller {

    /**
     * Checks whether the folder associated to the given ID has children.
     *
     * @param folderID The ID associated to the folder to be checked.
     * @return A boolean value: {@code true} if the given folder has children, {@code false} otherwise.
     */
    boolean folderHasChildren(int folderID);

    /**
     * Searches the file system for the folder with the given ID and returns its children in the format of a {@code
     * String}.
     *
     * @param parentID The ID of the desired parent folder.
     * @return A {@code String} containing all sub-folders of the desired parent folder.
     */
    String getSubFolders(int parentID);

    /**
     * Getter for the ROOT folder ID.
     *
     * @return The ID's {@code Long} value.
     */
    int getRootID();

    /**
     * Returns the path of the folder associated with the given ID.
     *
     * @param folderID The folder ID.
     * @return A {@code String} containing the full path of that folder.
     */
    String getPathToString(int folderID);
}

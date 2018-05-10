package main.model.database.filesystem;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The {@code Folder} class, which manages a single folder entity by storing its most important parameters: parent,
 * children, path, unique ID and depth.
 *
 * @author Manuel Gallina, Giosuè Filippini, Alessandro Polcini
 */
public class Folder implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = 7970305636210332068L;

    private Folder parent;
    private ArrayList<Folder> children;
    private String name;
    private String folderPath;
    private int folderId;
    private int depth;

    /**
     * Default constructor for the ROOT folder only. The ROOT folder is the first element in the folder structure, whose
     * children array contains all the other folders (either directly or indirectly). The parent of ROOT is ROOT itself.
     *
     * @param name The name of the folder.
     */
    Folder(String name) {
        this.name = name;
        this.folderPath = this.name + "\\";
        this.parent = this;
        this.folderId = 1;
        this.depth = 0;
        this.children = new ArrayList<>();
    }

    /**
     * Constructor for a common non-ROOT folder. Its depth is initialized as the parent's depth +1 and its path can be
     * resolved by appending its name to the parent path.<p>
     * As the folder is created, it gets added to its parent's {@code ArrayList} of children.
     *
     * @param name The name of the folder.
     * @param parent The parent of the folder.
     */
    Folder(String name, Folder parent, int ID) {
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.folderId = ID;

        this.parent.addChild(this);
        this.depth = this.parent.getDepth() + 1;
        this.folderPath = this.parent.getFolderPath() + this.name + "\\";
    }

    /**
     * Getter for the folder path.
     *
     * @return A {@code String} containing the path of this folder.
     */
    public String getFolderPath() {
        return folderPath;
    }

    public String getName() {
        return name;
    }

    public Folder getParent() {
        return parent;
    }

    /**
     * Getter for the children of this folder.
     *
     * @return An {@code ArrayList} containing this folder's children.
     */
    public ArrayList<Folder> getChildren() {
        return children;
    }

    /**
     * Returns the depth of the folder.
     *
     * @return The depth of the folder.
     */
    public int getDepth() {
        return depth;
    }

    public int getFolderId() {
        return folderId;
    }

    private void addChild(Folder toAdd) {
        children.add(toAdd);
    }
}

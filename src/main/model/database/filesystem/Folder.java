package main.model.database.filesystem;

import java.io.Serializable;

import static main.model.database.filesystem.FileSystem.ROOT;

/**
 * @author Manuel Gallina
 */
public class Folder implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = 7970305636210332068L;

    private Folder parent;
    private boolean hasChildren;
    private String name;
    private int folderId;
    private int depth;

    Folder(String name) {
        this.name = name;
        this.parent = this;
        this.folderId = 1;
        this.depth = -1;
        this.hasChildren = true;
    }

    public Folder(String name, Folder parent, int ID) {
        this.name = name;
        this.parent = parent;
        this.folderId = ID;
        this.hasChildren = false;
        resolveDepth();
    }

    private void resolveDepth() {
        this.depth = 0;
        Folder tmpParent = this.parent;

        while(tmpParent != ROOT) {
            this.depth++;
            tmpParent = tmpParent.parent;
        }
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean hasChildren() {
        return hasChildren;
    }

    public int getDepth() {
        return depth;
    }

    public Folder getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public int getFolderId() {
        return folderId;
    }

    /*public void setFolderId(int folderId) {
        this.folderId = folderId;
    }*/
}

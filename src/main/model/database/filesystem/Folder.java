package main.model.database.filesystem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Manuel Gallina
 */
public class Folder {

    private Folder parent;
    private ArrayList<Folder> children;
    private String name;
    private int folderId;

    public Folder(String name) {
        this.parent = this;
        this.children = new ArrayList<>();
        this.name = name;
        this.folderId = 1;
    }

    public Folder(Folder parent, String name) {
        this.parent = parent;
        this.children = new ArrayList<>();
        this.name = name;
    }

    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }

    public ArrayList<Folder> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }
}

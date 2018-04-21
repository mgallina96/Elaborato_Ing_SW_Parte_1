package main.model.database.filesystem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Manuel Gallina
 */
public class Folder implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = 562139798965262215L;

    private Folder parent;
    private ArrayList<Folder> children;
    private String name;

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
}

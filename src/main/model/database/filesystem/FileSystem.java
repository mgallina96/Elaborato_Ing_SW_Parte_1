package main.model.database.filesystem;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Manuel Gallina
 */
public class FileSystem {

    //private static final Folder ROOT = new Folder("root");
    private static final Folder ROOT = new Folder("root", 1);
    private static FileSystem instance;
    private String allPaths;

    private HashMap<Integer, Folder> fileSystem;

    private FileSystem() {
        this.fileSystem = new HashMap<>();
        this.fileSystem.put(ROOT.getFolderId(), ROOT);
    }

    public static FileSystem getInstance() {
        if(instance == null)
            instance = new FileSystem();

        return instance;
    }

    public String childrenToString(ArrayList<Folder> f) {
        if(f.isEmpty()) {
            return "";
        }

        Folder head = f.get(0);
        ArrayList<Folder> tail = tail(f);

        return head.getName() + "\n- " + childrenToString(head.getChildren()) + childrenToString(tail);

    }

    private ArrayList<Folder> tail(ArrayList<Folder> a) {
        ArrayList<Folder> tail = new ArrayList<>();
        int len = a.size();
        for(int i = 1; i < len; i++)
            tail.add(a.get(i));

        return tail;
    }

    private void allPathsToString(Folder f) {

    }

}

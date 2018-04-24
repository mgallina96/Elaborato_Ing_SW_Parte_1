package main.model.database;
import main.model.media.Media;
import java.io.Serializable;
import java.util.HashMap;
import java.io.*;
import static main.utility.Notifications.*;
import java.util.logging.Logger;

/**
 * Database concerned with saving, removing, finding, fetching and generally managing all kinds of media items.
 *
 * @author Manuel Gallina, Alessandro Polcini
 */
class MediaDatabase implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5687383377098150051L;
    private static final String FILE_SYSTEM_PATH = "application_resources\\Biblioteca SMARTINATOR - File System.txt";

    private static MediaDatabase mediaDatabase;
    private static int counter;

    private HashMap<Integer, Media> mediaList;
    private String fileSystem;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    //Singleton Database constructor, private to prevent instantiation.
    private MediaDatabase() {
        this.mediaList = new HashMap<>();
        loadFileSystem();
    }

    static MediaDatabase getInstance() {
        if(mediaDatabase == null)
            mediaDatabase = new MediaDatabase();

        return mediaDatabase;
    }

    void addMedia(Media toAdd, String path) {
        toAdd.setIdentifier(counter++);
        toAdd.setPath(resolvePath(path));
        mediaList.put(toAdd.getIdentifier(), toAdd);
    }

    void removeMedia(Media toRemove) {
        mediaList.remove(toRemove.getIdentifier());
    }

    boolean isPresent(Media toFind) {
        return mediaList.containsKey(toFind.getIdentifier());
    }

    boolean isPresent(String path) {
        return fileSystem.contains(path);
    }

    Media fetch(Media toFetch) {
        return mediaList.get(toFetch.getIdentifier());
    }

    String getMediaListString() {
        StringBuilder allMedia = new StringBuilder();

        for(Media m : mediaList.values()) {
            allMedia.append("\t- ");
            allMedia.append(m.toString());
        }

        return allMedia.toString();
    }

    String getFilteredMediaList(String filter) {
        StringBuilder filteredMedia = new StringBuilder();
        filter = filter.replaceAll("(\\W+ )+", "|").toLowerCase();
        String regex = ".*(" + filter + ").*";

        mediaList.values().stream()
                .filter(s -> (s.getBareItemDetails().toLowerCase()).matches(regex))
                .forEach(s -> filteredMedia.append(s.toString()));

        return filteredMedia.toString();
    }

    void setFileSystem(String fileSystem) {
        this.fileSystem = fileSystem;
    }

    public String getFileSystem() {
        return fileSystem;
    }

    void setMediaList(HashMap<Integer, Media> mediaList) {
        this.mediaList = mediaList;
    }

    HashMap<Integer, Media> getMediaList() {
        return mediaList;
    }

    static int getCounter() {
        return counter;
    }

    static void setCounter(int counter) {
        MediaDatabase.counter = counter;
    }

    private void loadFileSystem() {
        File file = new File(FILE_SYSTEM_PATH);
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader in = new BufferedReader(new FileReader(file));

            String line;
            while((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

            in.close();
        }
        catch(Exception e) {
            System.out.println(ERR_LOADING_FILESYSTEM);
        }

        fileSystem = resolveDirectories(sb.toString());
    }

    private String[] resolvePath(String p) {
        p += "\\";
        int len = p.length();
        int dim = 0;

        for(int i = 0; i < len; i++)
            if(p.charAt(i) == '\\')
                dim++;

        String[] path = new String[dim];
        int counter = 0;

        for(int i = 0; i < p.length(); i++)
            if(p.charAt(i) == '\\') {
                path[counter++] = p.substring(0, i);
                p = p.substring(i + 1);
                i = 0;
            }

        return path;
    }

    private String resolveDirectories(String s) {
        String line = "";

        if(s.charAt(0) == ' ')
            return resolveDirectories(s);

        return "";
    }


}

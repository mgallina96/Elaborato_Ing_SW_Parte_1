package main.model.database;
import main.model.media.Media;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Database concerned with saving, removing, finding, fetching and generally managing all kinds of media items.
 *
 * @author Manuel Gallina, Alessandro Polcini
 */
class MediaDatabase implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5687383377098150051L;

    private static MediaDatabase mediaDatabase;
    private static int counter;

    private HashMap<Integer, Media> mediaList;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    //Singleton Database constructor, private to prevent instantiation.
    private MediaDatabase() {
        this.mediaList = new HashMap<>();
    }

    static MediaDatabase getInstance() {
        if(mediaDatabase == null)
            mediaDatabase = new MediaDatabase();

        return mediaDatabase;
    }

    void addMedia(Media toAdd) {
        toAdd.setIdentifier(counter++);
        mediaList.put(toAdd.getIdentifier(), toAdd);
    }

    void removeMedia(Media toRemove) {
        mediaList.remove(toRemove.getIdentifier());
    }

    boolean isPresent(Media toFind) {
        return mediaList.containsKey(toFind.getIdentifier());
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
        filter = filter.replaceAll("(\\W+ )+", "|");
        String regex = ".*(" + filter + ").*";

        mediaList.values().stream()
                .filter(s -> s.getBareItemDetails().matches(regex))
                .forEach(s -> filteredMedia.append(s.toString()));

        return filteredMedia.toString();
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
}

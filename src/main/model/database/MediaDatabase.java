package main.model.database;
import main.model.media.Media;
import java.io.*;
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
    private HashMap<String, Media> mediaList;
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
        Media m = new Media("");
        m.setIdentifier(mediaList.size());
    }

    void removeMedia(Media toRemove) {

    }

    boolean isPresent(Media toFind) {
        return false;
    }

    Media fetch(Media toFetch) {
        return null;
    }

    String getMediaListString() {
        StringBuilder allMedia = new StringBuilder();

        for(Media m : mediaList.values()) {
            allMedia.append("\t- ");
            allMedia.append(m.toString());
        }

        return allMedia.toString();
    }

    void setMediaList(HashMap<String, Media> mediaList) {
        this.mediaList = mediaList;
    }

    HashMap<String, Media> getMediaList() {
        return mediaList;
    }
}

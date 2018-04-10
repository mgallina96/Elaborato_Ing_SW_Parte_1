package main.model.database;

import main.model.media.Media;
import main.model.user.User;
import main.model.user.UserStatus;
import main.utility.Notifications;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Manuel Gallina
 */
class MediaDatabase implements Serializable {

    /**
     * Unique serial ID for the {@link MediaDatabase} class. DO NOT CHANGE, otherwise the database can't be read properly.
     */
    private static final long serialVersionUID = -5687383377098150051L;

    private static MediaDatabase mediaDatabase;
    private User currentUser;
    private HashMap<String, Media> mediaList;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    //Singleton Database constructor, private to prevent instantiation.
    private MediaDatabase() {
        this.mediaList = new HashMap<>();
    }

    /**
     * Getter/initializer for the Singleton Database class.
     * <p>
     * This method builds a single instance of Database the first time it is called. Calling it more than once doesn't
     * change anything.
     *
     * @return either a new {@code Database} instance, if the {@code Database} has not been initialized yet, or the
     * already initialized {@code Database} object.
     */
    public static MediaDatabase getInstance() {
        if(mediaDatabase == null)
            mediaDatabase = new MediaDatabase();

        return mediaDatabase;
    }

    /**
     * Adds a new media file to the database.
     *
     * @param toAdd The {@code Media} that is to be added to the database.
     */
    public void addMedia(Media toAdd) {
    }

    /**
     * Removes an already existing media file from the database.
     *
     * @param toRemove The {@code Media} file to be removed.
     */
    public void removeMedia(Media toRemove) {

    }

    /**
     * Checks whether the given media file is present in the database.
     *
     * @param toFind The media to find.
     * @return {@code true} if the media file is present in the database, {@code false} otherwise.
     */
    public boolean isPresent(Media toFind) {
        return false;
    }

    /**
     * Returns the given media file (if present).
     *
     * @param toFetch The media file to be found.
     * @return the media file or {@code null} if that file can't be found in the database.
     */
    public Media fetch(Media toFetch) {
        return null;
    }

    /**
     * Returns a {@code String} that contains all the users in the database.
     *
     * @return the list of all users as a {@code String}.
     */
    public String getMediaListString() {
        StringBuilder allMedia = new StringBuilder();

        for(Media m : mediaList.values()) {
            allMedia.append("\t- ");
            allMedia.append(m.toString());
        }

        return allMedia.toString();
    }

    public void setMediaList(HashMap<String, Media> mediaList) {
        this.mediaList = mediaList;
    }

    public HashMap<String, Media> getMediaList() {
        return mediaList;
    }
}

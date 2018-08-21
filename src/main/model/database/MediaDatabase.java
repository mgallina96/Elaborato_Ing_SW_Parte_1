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
public class MediaDatabase implements Serializable, Database {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5687383377098150051L;

    private static MediaDatabase mediaDatabase;
    private int counter;
    private HashMap<Integer, Media> mediaList;

    //MediaDatabase initializer.
    private MediaDatabase() {
        this.mediaList = new HashMap<>();
    }

    /**
     * Getter/initializer for the {@code MediaDatabase} singleton class.
     * @return The {@code MediaDatabase} instance.
     */
    public static MediaDatabase getInstance() {
        if(mediaDatabase == null)
            mediaDatabase = new MediaDatabase();

        return mediaDatabase;
    }

    /**
     * Adds a given media item to the database.
     *
     * @param toAdd The media to be added.
     * @param path The file system path for the given media item.
     */
    public void addMedia(Media toAdd, String path) {
        toAdd.setIdentifier(counter++);

        toAdd.setPath(path);
        mediaList.put(toAdd.getIdentifier(), toAdd);
    }

    /**
     * Removes a given media from the database.
     * @param toRemove The media to be removed.
     */
    public void removeMedia(Media toRemove) {
        mediaList.get(toRemove.getIdentifier()).setUnavailable();
    }

    /**
     * Checks whether a given media item is present in the database. Mainly used during the borrowing operations.
     *
     * @param toFind The media to be found.
     * @return A {@code boolean} value: {@code true} if the media item is present, {@code false} otherwise.
     */
    public boolean isPresent(Media toFind) {
        return mediaList.containsKey(toFind.getIdentifier());
    }

    /**
     * An alternate version of the {@code isPresent} method. This method is used when adding a media item to the
     * database, in order to prevent the operator from adding a duplicate media item (with the same title, author and
     * details as another media item in the database).
     *
     * @param toFind The media item to be found.
     * @return A {@code boolean} value: {@code true} if this media item matches another media item in the database,
     *                                  {@code false} otherwise.
     */
    public boolean isMatchingMedia(Media toFind) {
        for(Media m : mediaList.values())
            if((m.getBareItemDetails()).equalsIgnoreCase(toFind.getBareItemDetails()))
                return true;

        return false;
    }

    /**
     * Fetches a media item from the database.
     *
     * @param toFetch The media item to be fetched.
     * @return The media item.
     */
    public Media fetch(Media toFetch) {
        return mediaList.get(toFetch.getIdentifier());
    }

    /**
     * Builds a {@code String} containing all media items whose title (or detail such as author name, genre, year)
     * matches a given {@code filter}.
     *
     * @param filter The filter that will be applied to the search.
     * @return The {@code String} of all media items matching the {@code filter}.
     */
    public String getFilteredMediaList(String filter) {
        StringBuilder filteredMedia = new StringBuilder();
        filter = filter.replaceAll("(\\W+ )+", "|").toLowerCase();
        String regex = ".*(" + filter + ").*";

        mediaList.values().stream()
                .filter(s -> (s.getBareItemDetails().toLowerCase()).matches(regex))
                .forEach(s -> filteredMedia.append(s.toString()));

        return filteredMedia.toString();
    }

    /**
     * Builds a {@code String} containing all media items in a given folder.
     *
     * @param folderPath The path of the folder whose contents are to be shown.
     * @return The {@code String} containing the contents of the given folder.
     */
    public String getFolderContents(String folderPath) {
        StringBuilder folderContents = new StringBuilder();

        mediaList.values().stream()
                .filter(m -> m.getPath().equals(folderPath))
                .forEach(m -> folderContents.append(m.toString()));

        return folderContents.toString();
    }

    /**
     * Opens a .ser serializable file and loads its contents into this {@link MediaDatabase} class.<p>
     * This method loads a {@code HashMap} containing all media items.
     */
/*    @SuppressWarnings("unchecked")
    private void loadMediaDatabase() {
        try (
            FileInputStream fileIn = new FileInputStream(MEDIA_DATABASE_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn)
        ) {
            setMediaDatabase((MediaDatabase)in.readObject());
        }
        catch(FileNotFoundException fnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_FILE_NOT_FOUND") + this.getClass().getName());
        }
        catch(IOException ioEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_LOADING_DATABASE") + this.getClass().getName());
        }
        catch(ClassNotFoundException cnfEx) {
            logger.log(Level.SEVERE, Notifications.getMessage("ERR_CLASS_NOT_FOUND") + this.getClass().getName());
        }
    }
    */
}
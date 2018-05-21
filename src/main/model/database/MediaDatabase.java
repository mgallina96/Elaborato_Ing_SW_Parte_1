package main.model.database;

import main.model.media.Media;
import main.utility.notifications.Notifications;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.utility.GlobalParameters.MEDIA_DATABASE_FILE_PATH;

/**
 * Database concerned with saving, removing, finding, fetching and generally managing all kinds of media items.
 *
 * @author Manuel Gallina, Alessandro Polcini
 */
public class MediaDatabase implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5687383377098150051L;

    private static MediaDatabase mediaDatabase;
    private static int counter;

    private HashMap<Integer, Media> mediaList;
    private Logger logger;

    //Singleton Database constructor, private to prevent instantiation.
    private MediaDatabase() {
        this.mediaList = new HashMap<>();
        logger = Logger.getLogger(this.getClass().getName());
        loadMediaDatabase();
    }

    public static MediaDatabase getInstance() {
        return (mediaDatabase == null) ? (mediaDatabase = new MediaDatabase()) : mediaDatabase;
    }

    public void addMedia(Media toAdd, String path) {
        toAdd.setIdentifier(counter++);
        toAdd.setPath(path);
        mediaList.put(toAdd.getIdentifier(), toAdd);
    }

    public void removeMedia(Media toRemove) {
        mediaList.remove(toRemove.getIdentifier());
    }

    public boolean isMatchingMedia(Media toFind) {
        for(Media m : mediaList.values())
            if((m.getBareItemDetails()).equalsIgnoreCase(toFind.getBareItemDetails()))
                return true;

        return false;
    }

    public boolean isPresent(Media toFind) {
        return mediaList.containsKey(toFind.getIdentifier());
    }

    public Media fetch(Media toFetch) {
        return mediaList.get(toFetch.getIdentifier());
    }

    public String getMediaListString() {
        StringBuilder allMedia = new StringBuilder();

        for(Media m : mediaList.values())
            allMedia.append("\t- ").append(m.toString());

        return allMedia.toString();
    }

    public String getFilteredMediaList(String filter) {
        StringBuilder filteredMedia = new StringBuilder();
        filter = filter.replaceAll("(\\W+ )+", "|").toLowerCase();
        String regex = ".*(" + filter + ").*";

        mediaList.values().stream()
                .filter(s -> (s.getBareItemDetails().toLowerCase()).matches(regex))
                .forEach(s -> filteredMedia.append(s.toString()));

        return filteredMedia.toString();
    }

    public String getFolderContents(String folderPath) {
        StringBuilder folderContents = new StringBuilder();

        mediaList.values().stream()
                .filter(m -> m.getPath().equals(folderPath))
                .forEach(m -> folderContents.append(m.toString()));

        return folderContents.toString();
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

    /**
     * Opens a .ser serializable file and loads its contents into this {@link MediaDatabase} class.<p>
     * This method loads a {@code HashMap} containing all media items.
     */
    @SuppressWarnings("unchecked")
    void loadMediaDatabase() {
        try {
            FileInputStream fileIn = new FileInputStream(MEDIA_DATABASE_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            this.mediaList = (HashMap<Integer, Media>) in.readObject();
            counter = Integer.parseInt((String)in.readObject());

            in.close();
            fileIn.close();
        }
        catch(FileNotFoundException FNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_FILE_NOT_FOUND + this.getClass().getName());
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_LOADING_DATABASE + this.getClass().getName(), IOEx);
        }
        catch(ClassNotFoundException CNFEx) {
            logger.log(Level.SEVERE, Notifications.ERR_CLASS_NOT_FOUND + this.getClass().getName());
        }
    }

    /**
     * Saves the File System in the form of a HashMap object.
     */
    public void saveMediaDatabase() {
        try {
            //to increase serializing speed
            RandomAccessFile raf = new RandomAccessFile(MEDIA_DATABASE_FILE_PATH, "rw");

            FileOutputStream fileOut = new FileOutputStream(raf.getFD());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(mediaList);
            out.writeObject(Integer.toString(MediaDatabase.getCounter()));

            out.close();
            fileOut.close();
        }
        catch(IOException IOEx) {
            logger.log(Level.SEVERE, Notifications.ERR_SAVING_DATABASE + this.getClass().getName(), IOEx);
        }
    }
}

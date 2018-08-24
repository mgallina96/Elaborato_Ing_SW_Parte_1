package main.controller;
import main.model.database.MediaDatabase;
import main.model.media.Book;
import main.model.media.Film;
import main.model.media.Media;
import java.util.logging.Logger;
import static main.model.database.DatabaseIO.loadMediaDatabase;
import static main.model.database.DatabaseIO.saveDatabase;
import static main.utility.GlobalParameters.MEDIA_DATABASE_FILE_PATH;

public class SystemMediaController implements MediaController {

    private static SystemMediaController instance;
    private MediaDatabase mediaDatabase;

    private SystemMediaController() {
        mediaDatabase = MediaDatabase.getInstance();
    }

    //returns the instance of this controller.
    public static SystemMediaController getInstance() {
        if(instance == null)
            instance = new SystemMediaController();

        return instance;
    }

    @Override
    public boolean mediaIsPresent(int id) {
        return mediaDatabase.isPresent(new Media(id));
    }

    @Override
    public boolean addMediaToDatabase(String title, String author, String genre, int publicationYear, String publisherName, String path, String type) {
        Media m = null;

        switch(type) {
            case "BOOK":
                m = new Book(title, author, genre, publicationYear, publisherName);
                break;
            case "FILM":
                m = new Film(title, author, genre, publicationYear, publisherName);
                break;
            default:
                break;
        }

        if(!mediaDatabase.isMatchingMedia(m)) {
            mediaDatabase.addMedia(m, path);
            saveDatabase(MEDIA_DATABASE_FILE_PATH, mediaDatabase);
            return true;
        }

        return false;
    }

    @Override
    public void removeMediaFromDatabase(int id) {
        mediaDatabase.removeMedia(new Media(id));
        saveDatabase(MEDIA_DATABASE_FILE_PATH, mediaDatabase);
    }

    @Override
    public String allFilteredMediaList(String filter) {
        return mediaDatabase.getFilteredMediaList(filter);
    }

    @Override
    public String getFolderContents(String folderPath) {
        return mediaDatabase.getFolderContents(folderPath);
    }

}

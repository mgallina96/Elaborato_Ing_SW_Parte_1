package main;

public interface MediaController {

    /**
     * Checks if the media with the given ID is present in the database.
     *
     * @param id The ID of the media to be checked.
     * @return {@code true} if the media is present in the database, {@code false} otherwise.
     */
    boolean mediaIsPresent(int id);

    /**
     * Adds a new media item to the database.
     *
     * @param title the title.
     * @param author the author.
     * @param genre the genre.
     * @param publicationYear the publication year.
     * @param publisherName the publisher's name.
     */
    boolean addMediaToDatabase(String title, String author, String genre, int publicationYear, String publisherName, String path);

    /**
     * Removes the media element with the given ID from the database.
     *
     * @param id The ID of the media element to be removed.
     */
    void removeMediaFromDatabase(int id);

    /**
     * Returns a {@code String} that contains all the media items that match a certain filter.
     *
     * @return the list of all filtered media items as a {@code String}.
     */
    String allFilteredMediaList(String filter);

    /**
     * Returns the contents of the folder that matches the given path.
     *
     * @param folderPath The path to look for.
     * @return A {@code String} with all the contents of that folder.
     */
    String getFolderContents(String folderPath);
}

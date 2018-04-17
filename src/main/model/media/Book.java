package main.model.media;

import java.io.Serializable;

/**
 * The {@code Book} class, subclass of the {@link Media} class.
 * <p>
 * This class represents a book through parameters like its title, its publication year, its author's name, its
 * publisher's name, etc.
 *
 * @author Alessandro Polcini
 */
public class Book extends Media implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -1281383374398165051L;

    private static final String ID = "Book";
    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    private String publisherName;

    //non proprio necessari, ma carini da avere
    private String description;
    private String subtitle;
    private String language;
    private int pageCount;
    private boolean belongsToSeries;
    private int volumeNumber;

    public Book(String title, String author, String genre, int publicationYear, String publisherName) {
        super(title);
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.publisherName = publisherName;

        super.setBareItemDetails(title, author, genre, publicationYear, publisherName);
    }

    public void setDetails(String description, String subtitle, String language, int pageCount, boolean belongsToSeries, int volumeNumber) {
        this.description = description;
        this.subtitle = subtitle;
        this.language = language;
        this.pageCount = pageCount;
        this.belongsToSeries = belongsToSeries;
        this.volumeNumber = this.belongsToSeries ? volumeNumber : 0;
    }
/*
    public void setPath(String[] path) {
        super.setPath(path);
    }
*/

    @Override
    public String getBareItemDetails() {
        return super.getBareItemDetails();
    }

    public String toString() {
        return String.format("Item ID: %d\t|\tTitle: %s\t|\tAuthor: %s\t|\tPublication year: %d\t|\tGenre: %s\t|\tPublisher name: %s\n",
                getIdentifier(), title, author, publicationYear, genre, publisherName);
    }

    public String allDetailsToString() {
        return String.format("%s\t|\tSubtitle: %s\t|\tLanguage: %s\t|\tPage count: %d%s\nDescription: %s\n",
                toString(), subtitle, language, pageCount, (belongsToSeries ? ("|\tVolume number: " + volumeNumber) : ""), description);
    }

}

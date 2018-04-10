package main.model.media;

/**
 * The {@code Book} class, subclass of the {@link Media} class.
 * <p>
 * This class represents a book through parameters like its title, its publication year, its author's name, its
 * publisher's name, etc.
 *
 * @author Alessandro Polcini
 */
public class Book extends Media {

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
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.publisherName = publisherName;
    }

    public String toString() {
        return String.format("Title: %s\t|\tAuthor: %s\t|\tPublication year: %d\t|\tGenre: %s\t|\tPublisher name: %s",
                title, author, publicationYear, genre, publisherName);
    }

}

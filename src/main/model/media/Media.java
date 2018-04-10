package main.model.media;
import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * The {@code Media} class, which contains fields and methods that can be applied to any generic piece of media, such
 * as books, movies, magazines, tv series, video games, etc.
 *
 * @author Alessandro Polcini
 */
public class Media implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5681380379098150051L;

    private String identifier;
    private GregorianCalendar dateAdded;

    public Media() {
        dateAdded = new GregorianCalendar();
    }

    public void setDateAdded(GregorianCalendar dateAdded) {
        this.dateAdded = dateAdded;
    }

    public GregorianCalendar getDateAdded() {
        return dateAdded;
    }

    public String toString() {
        return "";
    }
}

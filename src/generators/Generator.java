package generators;

import generators.mediagenerator.BookGenerator;
import generators.mediagenerator.FilmGenerator;
import generators.usergenerator.UserGenerator;
import main.model.media.Media;
import main.model.user.User;

import java.util.ArrayList;

/**
 * Generator class. Fills three distinct ArrayLists: one for randomly generated users, one for randomly generated books
 * and one for randomly generated films.
 *
 * @author Alessandro Polcini
 */
public class Generator {

    private static final double CONSTANT = 1.4;
    public static final String COMMON_MEDIA_PATH = "resources\\media\\";
    public static final String COMMON_BOOK_PATH = COMMON_MEDIA_PATH + "books\\";
    public static final String COMMON_FILM_PATH = COMMON_MEDIA_PATH + "films\\";
    public static final String COMMON_USER_PATH = "resources\\users\\";
    private ArrayList<User> users;
    private ArrayList<Media> books;
    private ArrayList<Media> films;

    /**
     * Constructor for the generator class.
     * @param howMany The number of users, books and films to be generated.
     */
    Generator(int howMany) {
        users = UserGenerator.generateUsers(howMany);
        books = BookGenerator.generateBooks((int)((double)howMany / CONSTANT));
        films = FilmGenerator.generateFilm((int)((double)howMany / CONSTANT));
    }

    /**
     * Getter for the ArrayList that contains random users.
     * @return The ArrayList containing random users.
     */
    ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Getter for the ArrayList that contains random books.
     * @return The ArrayList containing random books.
     */
    ArrayList<Media> getBooks() {
        return books;
    }

    /**
     * Getter for the ArrayList that contains random films.
     * @return The ArrayList containing random films.
     */
    ArrayList<Media> getFilms() {
        return films;
    }
}

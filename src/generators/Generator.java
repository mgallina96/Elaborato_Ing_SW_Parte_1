package generators;

import generators.mediagenerator.BookGenerator;
import generators.mediagenerator.FilmGenerator;
import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;
import main.model.media.Media;
import main.model.user.User;

import java.util.ArrayList;

/**
 * Created by Alessandro on 12/04/18.
 */
public class Generator {

    public static final String COMMON_MEDIA_PATH = "resources\\media\\";
    public static final String COMMON_USER_PATH = "resources\\users\\";
    private byte fillingLevel;
    private ArrayList<User> users;
    private ArrayList<Media> books;
    private ArrayList<Media> films;

    public Generator(byte fillingLevel) {
        this.fillingLevel = fillingLevel;
        int i = (int)Math.pow(2, this.fillingLevel);
        int howMany = i * i;

        users = UserGenerator.generateUsers(howMany);
        books = BookGenerator.generateBooks((int)(howMany / 1.4));
        films = FilmGenerator.generateFilm((int)(howMany / 1.4));
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Media> getBooks() {
        return books;
    }

    public ArrayList<Media> getFilms() {
        return films;
    }
}

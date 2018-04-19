package generators;

import generators.bookgenerator.BookGenerator;
import main.model.media.Media;
import main.model.user.User;

import java.util.ArrayList;

/**
 * Created by Alessandro on 12/04/18.
 */
public class Generator {

    private byte fillingLevel;
    private ArrayList<User> users;
    private ArrayList<Media> books;

    public Generator(byte fillingLevel) {
        this.fillingLevel = fillingLevel;
        int i = (int)Math.pow(2, this.fillingLevel);
        int howMany = i * i;

        users = UserGenerator.generateUsers(howMany);
        books = BookGenerator.generateBooks(howMany);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Media> getBooks() {
        return books;
    }
}

package generators;
import main.model.media.Media;
import main.model.user.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Alessandro on 12/04/18.
 */
public class Generator {

    private ArrayList<String> dictionary;
    private ArrayList<String> firstNames;
    private ArrayList<String> lastNames;
    private byte fillingLevel;
    private ArrayList<User> users;
    private ArrayList<Media> books;

    public Generator(byte fillingLevel) {
        this.fillingLevel = fillingLevel;
        int i = (int)Math.pow(2, this.fillingLevel);
        int howMany = i * i;

        firstNames = initializeArrayList("test_resources\\users\\nomi_italiani.txt");
        lastNames = initializeArrayList("test_resources\\users\\cognomi_italiani.txt");
        dictionary = initializeArrayList("test_resources\\media\\dizionario_italiano.txt");

        users = UserGenerator.generateUsers(howMany, firstNames, lastNames);
        books = BookGenerator.generateBooks(howMany, firstNames, lastNames, dictionary);
    }

    private ArrayList<String> initializeArrayList(String path) {
        ArrayList<String> a = new ArrayList<>();

        File f = new File(path);

        try {
            Scanner s = new Scanner(f);

            while(s.hasNextLine()) {
                a.add(s.nextLine());
            }

            s.close();
        }
        catch(Exception e) {

        }

        return a;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Media> getBooks() {
        return books;
    }
}

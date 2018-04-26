package generators;
import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;
import main.model.user.User;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

import static generators.Generator.COMMON_USER_PATH;

/**
 * Created by Alessandro on 12/04/18.
 */
class UserGenerator {

    private UserGenerator() {}

    public static ArrayList<User> generateUsers(int howMany) {
        ArrayList<User> users = new ArrayList<>();
        Random rand = new Random();
        RandomWords firstNames = new RandomWords(PoolLoader.fromTXTFile(COMMON_USER_PATH + "nomi_italiani.txt"));
        RandomWords lastNames = new RandomWords(PoolLoader.fromTXTFile(COMMON_USER_PATH + "cognomi_italiani.txt"));

        for(int i = 0; i < howMany; i++) {
            String fn = firstNames.nextWord();
            String ln = lastNames.nextWord();
            GregorianCalendar g = new GregorianCalendar((rand.nextInt(75) + 1935), rand.nextInt(12), rand.nextInt(31));

            users.add(new User(fn, ln, (fn + ln) + rand.nextInt(1000), Long.toString(rand.nextLong()), g));
        }

        return users;
    }
}

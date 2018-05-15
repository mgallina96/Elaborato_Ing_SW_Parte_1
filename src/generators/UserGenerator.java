package generators;
import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;
import main.model.user.Customer;
import main.model.user.Operator;
import main.model.user.User;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

import static generators.Generator.COMMON_USER_PATH;

/**
 * Class UserGenerator to generate a given amount of users with random parameters such as first name, last name,
 * birth date, etc.
 *
 * @author Alessandro Polcini.
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

            if(rand.nextDouble() > 0.99)
                users.add(new Operator(fn, ln, strip(fn + ln) + rand.nextInt(1000), Long.toString(rand.nextLong()), g));
            else
                users.add(new Customer(fn, ln, strip(fn + ln) + rand.nextInt(1000), Long.toString(rand.nextLong()), g));
        }

        return users;
    }

    private static String strip(String s) {
        s = s.replaceAll("\\s+", "");

        return s;
    }
}

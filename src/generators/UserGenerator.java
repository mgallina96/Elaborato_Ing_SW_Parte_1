package generators;
import main.model.user.User;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by Alessandro on 12/04/18.
 */
class UserGenerator {

    private UserGenerator() {}

    public static ArrayList<User> generateUsers(int howMany, ArrayList<String> firstNames, ArrayList<String> lastNames) {
        ArrayList<User> users = new ArrayList<>();
        Random rand = new Random();
        int lenFN = firstNames.size();
        int lenLN = lastNames.size();

        for(int i = 0; i < howMany; i++) {
            String fn = firstNames.get(rand.nextInt(lenFN));
            String ln = lastNames.get(rand.nextInt(lenLN));
            GregorianCalendar g = new GregorianCalendar((rand.nextInt(75) + 1935), rand.nextInt(12), rand.nextInt(31));

            users.add(new User(fn, ln, (fn + ln), Long.toString(rand.nextLong()), g));
        }

        return users;
    }
}

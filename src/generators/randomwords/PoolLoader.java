package generators.randomwords;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Pool loader class. This class loads a pool of words from a text file and puts them in an array.
 *
 * @author Alessandro Polcini
 */
public class PoolLoader {

    private PoolLoader() {}

    /**
     * Opens a .txt file and loads each word separately into an array.
     *
     * @param path The path from which the .txt file will be loaded.
     * @return A {@code String} array containing the words.
     */
    public static String[] fromTXTFile(String path) {
        ArrayList<String> arrayList = new ArrayList<>();
        int counter = 0;
        File f = new File(path);

        try (
            BufferedReader s = new BufferedReader(new FileReader(f))
        ) {
            String line;
            while((line = s.readLine()) != null) {
                arrayList.add(line.matches("[^a-z].*") ? line.replaceAll("\\p{C}", "") : line);
                counter++;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        String[] toString = new String[counter];

        for(int i = 0; i < counter; i++) {
            toString[i] = arrayList.get(i);
        }

        return toString;
    }

}

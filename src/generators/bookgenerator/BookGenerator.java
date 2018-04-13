package generators.bookgenerator;
import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;
import main.model.media.Book;
import main.model.media.Media;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alessandro on 12/04/18.
 */
public class BookGenerator {

    private BookGenerator() {}

    public static ArrayList<Media> generateBooks(int howMany) {
        Random rand = new Random();
        ArrayList<Media> media = new ArrayList<>();
        TitleGenerator titleGenerator = TitleGenerator.getInstance();

        RandomWords firstNames = new RandomWords(PoolLoader.fromTXTFile("test_resources\\users\\nomi_italiani.txt"));
        RandomWords lastNames = new RandomWords(PoolLoader.fromTXTFile("test_resources\\users\\cognomi_italiani.txt"));
        RandomWords genres = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\generi_libro.txt"));

        for(int i = 0; i < howMany; i++) {
            int year;
            if(rand.nextDouble() > 0.85)
                year = 1200 + rand.nextInt(600);
            else
                year = 1800 + rand.nextInt(219);

            media.add(new Book(
                    titleGenerator.generateTitle(),
                    (firstNames.nextWord() + " " + lastNames.nextWord()),
                    genres.nextWord(),
                    year,
                    (firstNames.nextWord() + " " + lastNames.nextWord() + " Editore")
            ));
        }

        return media;
    }

}

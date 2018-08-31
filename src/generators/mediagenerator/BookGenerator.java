package generators.mediagenerator;

import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;
import generators.usergenerator.PersonNameGenerator;
import main.model.media.Book;
import main.model.media.Media;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static generators.Generator.COMMON_BOOK_PATH;

/**
 * Book generator class. Generates an arbitrary number of books with random parameters.
 *
 * @author Alessandro Polcini
 */
public class BookGenerator {

    private static Random rand = new Random();
    private static String[] genres = PoolLoader.fromTXTFile(COMMON_BOOK_PATH + "book_genres.txt");
    private static RandomWords publishers = new RandomWords(PoolLoader.fromTXTFile(COMMON_BOOK_PATH + "book_publishers.txt"));
    private static int numOfGenres = genres.length;

    private BookGenerator() {}

    /**
     * Generates an arbitrary number of books with random parameters.
     *
     * @return An ArrayList containing the randomly generated books.
     */
    public static ArrayList<Media> generateBooks() {
        ArrayList<Media> media = new ArrayList<>();
        PersonNameGenerator personNameGenerator = new PersonNameGenerator();

        for(int i = 0; i < numOfGenres; i++) {
            String currentGenre = genres[i];
            TitleGenerator t = new TitleGenerator("books", currentGenre);
            int howMany = t.getLength();
            String[] titles = t.getTitles();

            for(int j = 0; j < howMany; j++) {
                media.add(new Book(
                        titles[j],
                        personNameGenerator.getRandomFullName(),
                        currentGenre,
                        generatePlausibleYear(),
                        publishers.nextWord()
                ));
            }
        }

        Collections.shuffle(media);

        return media;
    }

    private static int generatePlausibleYear() {
        if(rand.nextDouble() > 0.85)
            return 1200 + rand.nextInt(600);
        else
            return 1800 + rand.nextInt(219);
    }

}

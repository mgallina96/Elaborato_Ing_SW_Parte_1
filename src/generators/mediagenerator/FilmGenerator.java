package generators.mediagenerator;

import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;
import generators.usergenerator.PersonNameGenerator;
import main.model.media.Film;
import main.model.media.Media;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static generators.Generator.COMMON_FILM_PATH;

/**
 * Film generator class. Generates an arbitrary number of films with random parameters.
 *
 * @author Alessandro Polcini
 */
public class FilmGenerator {

    private static Random rand = new Random();
    private static String[] genres = PoolLoader.fromTXTFile(COMMON_FILM_PATH + "film_genres.txt");
    private static RandomWords filmCompanies = new RandomWords(PoolLoader.fromTXTFile(COMMON_FILM_PATH + "film_companies.txt"));
    private static int numOfGenres = genres.length;

    private FilmGenerator() {}

    /**
     * Generates an arbitrary number of films with random parameters.
     *
     * @return An ArrayList containing the randomly generated films.
     */
    public static ArrayList<Media> generateFilms() {
        ArrayList<Media> media = new ArrayList<>();
        PersonNameGenerator personNameGenerator = new PersonNameGenerator();

        for(int i = 0; i < numOfGenres; i++) {
            String currentGenre = genres[i];
            TitleGenerator t = new TitleGenerator("films", currentGenre);
            int howMany = t.getLength();
            String[] titles = t.getTitles();

            for(int j = 0; j < howMany; j++) {
                media.add(new Film(
                        titles[j],
                        personNameGenerator.getRandomFullName(),
                        currentGenre,
                        generatePlausibleYear(),
                        filmCompanies.nextWord()
                ));
            }
        }

        Collections.shuffle(media);

        return media;
    }

    private static int generatePlausibleYear() {
        if(rand.nextDouble() > 0.85)
            return 1800 + rand.nextInt(130);
        else
            return 1930 + rand.nextInt(85);
    }
}

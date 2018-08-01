package generators.usergenerator;

import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;

import static generators.Generator.COMMON_USER_PATH;

public class PersonNameGenerator {

    private RandomWords firstNames;
    private RandomWords lastNames;

    public PersonNameGenerator() {
        firstNames = new RandomWords(PoolLoader.fromTXTFile(COMMON_USER_PATH + "nomi_italiani.txt"));
        lastNames = new RandomWords(PoolLoader.fromTXTFile(COMMON_USER_PATH + "cognomi_italiani.txt"));
    }

    public String getRandomFirstName() {
        return firstNames.nextWord();
    }

    public String getRandomLastName() {
        return lastNames.nextWord();
    }

    public String getRandomFullName() {
        return firstNames.nextWord() + " " + lastNames.nextWord();
    }
}

package generators.bookgenerator;
import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;
import java.util.Random;

public class TitleGenerator {

    private static final int GRAMMAR_BOUND = 4;
    private static final int ARTICLE_POS = 0;
    private static final int ADJECTIVE_POS = 1;
    private static final int NOUN_POS = 2;
    private static final int PREPOSITION_POS = 3;
    private static final String SPACE = " ";

    private static TitleGenerator instance;

    private Random random = new Random();
    private RandomWords firstNames, lastNames, nounsMS, nounsMP, nounsFS, nounsFP, adjectivesMS, adjectivesMP, adjectivesFS, adjectivesFP,
            articlesMS, articlesMP, articlesFS, articlesFP, conjunctions, adverbs, prepositions, prepsMS, prepsMP, prepsFS, prepsFP, verbs;

    public TitleGenerator() {
        loadRandomizers();
    }

    public static TitleGenerator getInstance() {
        if(instance == null)
            instance = new TitleGenerator();

        return instance;
    }

    public String generateTitle() {
        String finalTitle = "";

        if(random.nextDouble() > 0.85) {
            if(random.nextBoolean()) {
                finalTitle = randomNoun();
            }
            else {
                finalTitle = random.nextBoolean() ? (adverbs.nextWord()) : (verbs.nextWord());
            }
        }
        else {
            switch(random.nextInt(10)) {
                case 0: //la casa / nel buio
                    finalTitle = articleNoun();
                    break;
                case 1: //la casa incantata / il cavallo pazzo
                    finalTitle = articleNounAdjective(false);
                    break;
                case 2: //le avventure di harry potter
                    finalTitle = articleNoun() + SPACE + prepositions.nextWord() + SPACE + firstnameLastname();
                    break;
                case 3: //harry potter e la pietra filosofale
                    finalTitle = firstnameLastname() + SPACE + conjunctions.nextWord() + SPACE + articleNounAdjective(false);
                    break;
                case 4: //molto forte, incredibilmente vicino
                    finalTitle = adverbs.nextWord() + SPACE + randomAdjective() + ", " + adverbs.nextWord() + SPACE + randomAdjective();
                    break;
                case 5: //l'estremo orizzonte
                    finalTitle = articleNounAdjective(false);
                    break;
                case 6: //giancarlo e maria nella casa
                    finalTitle = firstNames.nextWord() + SPACE + conjunctions.nextWord() + SPACE + firstNames.nextWord() + SPACE + location();
                    break;
                case 7: //la tragica storia di giancarlo e maria
                    finalTitle = articleNounAdjective(true) + SPACE + prepositions.nextWord() + SPACE + firstNames.nextWord() + " e " + firstNames.nextWord();
                    break;
                case 8: //la principessa sul pisello
                    finalTitle = articleNoun() + SPACE + location();
                    break;
                case 9: //uno strano caso
                    finalTitle = articleNounAdjective(true);
                    break;
            }
        }

        return capitalize(finalTitle);
    }

    private String[] sentenceGenerator(int which) {
        switch(which) {
            case 0:
                return new String[]{articlesMS.nextWord(), adjectivesMS.nextWord(), nounsMS.nextWord(), prepsMS.nextWord()};
            case 1:
                return new String[]{articlesMP.nextWord(), adjectivesMP.nextWord(), nounsMP.nextWord(), prepsMP.nextWord()};
            case 2:
                return new String[]{articlesFS.nextWord(), adjectivesFS.nextWord(), nounsFS.nextWord(), prepsFS.nextWord()};
            case 3:
                return new String[]{articlesFP.nextWord(), adjectivesFP.nextWord(), nounsFP.nextWord(), prepsFP.nextWord()};
            default:
                return new String[]{"", "", "", "", "", ""};
        }
    }

    private String articleNoun() {
        String[] components = sentenceGenerator(random.nextInt(GRAMMAR_BOUND));

        //checkGrammar();

        return components[ARTICLE_POS] + SPACE + components[NOUN_POS];
    }

    private String articleNounAdjective(boolean flipNounAdjective) {
        String[] components = sentenceGenerator(random.nextInt(GRAMMAR_BOUND));

        //checkGrammar();

        return components[ARTICLE_POS] + SPACE + (flipNounAdjective ?
                (components[ADJECTIVE_POS] + SPACE + components[NOUN_POS]) :
                (components[NOUN_POS] + SPACE + components[ADJECTIVE_POS]));
    }

    private String location() {
        String[] components = sentenceGenerator(random.nextInt(GRAMMAR_BOUND));

        //checkGrammar();

        return components[PREPOSITION_POS] + SPACE + components[NOUN_POS];
    }

    String firstnameLastname() {
        return firstNames.nextWord() + SPACE + lastNames.nextWord();
    }

    String randomNoun() {
        return sentenceGenerator(random.nextInt(GRAMMAR_BOUND))[NOUN_POS];
    }

    private String randomAdjective() {
        return sentenceGenerator(random.nextInt(GRAMMAR_BOUND))[ADJECTIVE_POS];
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private void loadRandomizers() {
        firstNames = new RandomWords(PoolLoader.fromTXTFile("test_resources\\users\\nomi_italiani.txt"));
        lastNames = new RandomWords(PoolLoader.fromTXTFile("test_resources\\users\\cognomi_italiani.txt"));
        nounsMS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\sostantivi_maschili_singolari.txt"));
        nounsMP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\sostantivi_maschili_plurali.txt"));
        nounsFS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\sostantivi_femminili_singolari.txt"));
        nounsFP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\sostantivi_femminili_plurali.txt"));
        adjectivesMS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\aggettivi_maschili_singolari.txt"));
        adjectivesMP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\aggettivi_maschili_plurali.txt"));
        adjectivesFS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\aggettivi_femminili_singolari.txt"));
        adjectivesFP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\aggettivi_femminili_plurali.txt"));
        articlesMS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\articoli_maschili_singolari.txt"));
        articlesMP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\articoli_maschili_plurali.txt"));
        articlesFS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\articoli_femminili_singolari.txt"));
        articlesFP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\articoli_femminili_plurali.txt"));
        conjunctions = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\congiunzioni_italiane.txt"));
        adverbs = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\avverbi.txt"));
        prepositions = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\preposizioni_italiane.txt"));
        prepsMS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\preposizioni_articolate_maschili_singolari.txt"));
        prepsMP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\preposizioni_articolate_maschili_plurali.txt"));
        prepsFS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\preposizioni_articolate_femminili_singolari.txt"));
        prepsFP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\preposizioni_articolate_femminili_plurali.txt"));
        verbs = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\verbi_italiani.txt"));
    }
}

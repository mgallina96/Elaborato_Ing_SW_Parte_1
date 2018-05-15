package generators.mediagenerator;
import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;

import java.util.Random;

import static generators.Generator.COMMON_MEDIA_PATH;
import static generators.Generator.COMMON_USER_PATH;

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
            switch(random.nextInt(13)) {
                case 0: //la casa / nel buio
                    finalTitle = articleNoun();
                    break;
                case 1: //la casa incantata / il cavallo pazzo
                    finalTitle = articleNounAdjective(false);
                    break;
                case 2: //le avventure di harry potter
                    finalTitle = articleNoun() + SPACE + "di" + SPACE + firstnameLastname();
                    break;
                case 3: //harry potter e la pietra filosofale
                    finalTitle = firstnameLastname() + SPACE + "e" + SPACE + articleNounAdjective(false);
                    break;
                case 4: //molto forte, incredibilmente vicino
                    finalTitle = adverbs.nextWord() + SPACE + randomAdjective() + ", " + adverbs.nextWord() + SPACE + randomAdjective();
                    break;
                case 5: //l'estremo orizzonte
                    finalTitle = articleNounAdjective(false);
                    break;
                case 6: //giancarlo e maria nella casa / piero e lucia per la pace
                    finalTitle = firstNames.nextWord() + SPACE + "e" + SPACE + firstNames.nextWord() + SPACE + prepNoun();
                    break;
                case 7: //la tragica storia di giancarlo e maria
                    finalTitle = articleNounAdjective(true) + SPACE + prepositions.nextWord() + SPACE + firstNames.nextWord() + SPACE + "e" + SPACE + firstNames.nextWord();
                    break;
                case 8: //la principessa sul pisello / il morto con la cravatta
                    finalTitle = articleNoun() + SPACE + prepNoun();
                    break;
                case 9: //uno strano caso
                    finalTitle = articleNounAdjective(true);
                    break;
                case 10: //cadere dal tetto / giocare col fuoco
                    finalTitle = verbs.nextWord() + SPACE + prepNoun();
                    break;
                case 11: //franco (cognome) volerà sulla città
                    finalTitle = firstNames.nextWord() + SPACE + (random.nextBoolean() ? lastNames.nextWord() + SPACE : "") + verbs.nextWord() + SPACE + prepNoun();
                    break;
                case 12:
                    //perchè morire? / perchè la morte?
                    finalTitle = conjunctions.nextWord() + SPACE + (random.nextBoolean() ? verbs.nextWord() : articleNoun()) + (random.nextBoolean() ? "" : "?");
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
        return twoComponents(ARTICLE_POS, NOUN_POS);
    }

    private String prepNoun() {
        return twoComponents(PREPOSITION_POS, NOUN_POS);
    }

    private String articleNounAdjective(boolean flipNounAdjective) {
        String[] components;
        String article;
        String noun;
        String adjective;

        do {
            components = sentenceGenerator(random.nextInt(GRAMMAR_BOUND));
            article = components[ARTICLE_POS];
            adjective = components[ADJECTIVE_POS];
            noun = components[NOUN_POS];
        } while(flipNounAdjective ? !isValidGrammar(article, adjective) : !isValidGrammar(article, noun));

        return article + SPACE + (flipNounAdjective ?
                                    adjective + SPACE + noun :
                                    noun + SPACE + adjective);
    }

    private String twoComponents(int posA, int posB) {
        String[] components;
        String a;
        String b;

        do {
            components = sentenceGenerator(random.nextInt(GRAMMAR_BOUND));
            a = components[posA];
            b = components[posB];
        } while(!isValidGrammar(a, b));

        return a + SPACE + b;
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

    String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private boolean isValidGrammar(String article, String noun) {
        boolean conditionMSing = (article.matches(".*o") && noun.matches("[sz][^aeiou].*")) || (article.matches(".*[ln]") && noun.matches("[^aeiouzs][^aeiou]?[aeiou].*"));
        boolean conditionMPlur = (article.matches("gli") && noun.matches("s[^aeiou].*")) || (article.matches("i") && noun.matches("[^aeious][^aeiou]?[aeiou].*"));
        boolean conditionFSingPlur = (article.matches(".*[ae]") && noun.matches("[^aeiou].*"));
        boolean conditionBoth = (article.matches(".*('|gli)") && noun.matches("[aeiou].*"));

        return conditionMSing || conditionMPlur || conditionFSingPlur || conditionBoth;
    }

    private void loadRandomizers() {
        firstNames = new RandomWords(PoolLoader.fromTXTFile(COMMON_USER_PATH + "nomi_italiani.txt"));
        lastNames = new RandomWords(PoolLoader.fromTXTFile(COMMON_USER_PATH + "cognomi_italiani.txt"));
        nounsMS = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "sostantivi_maschili_singolari.txt"));
        nounsMP = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "sostantivi_maschili_plurali.txt"));
        nounsFS = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "sostantivi_femminili_singolari.txt"));
        nounsFP = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "sostantivi_femminili_plurali.txt"));
        adjectivesMS = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "aggettivi_maschili_singolari.txt"));
        adjectivesMP = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "aggettivi_maschili_plurali.txt"));
        adjectivesFS = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "aggettivi_femminili_singolari.txt"));
        adjectivesFP = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "aggettivi_femminili_plurali.txt"));
        articlesMS = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "articoli_maschili_singolari.txt"));
        articlesMP = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "articoli_maschili_plurali.txt"));
        articlesFS = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "articoli_femminili_singolari.txt"));
        articlesFP = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "articoli_femminili_plurali.txt"));
        conjunctions = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "congiunzioni_italiane.txt"));
        adverbs = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "avverbi.txt"));
        prepositions = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "preposizioni_italiane.txt"));
        prepsMS = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "preposizioni_articolate_maschili_singolari.txt"));
        prepsMP = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "preposizioni_articolate_maschili_plurali.txt"));
        prepsFS = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "preposizioni_articolate_femminili_singolari.txt"));
        prepsFP = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "preposizioni_articolate_femminili_plurali.txt"));
        verbs = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "verbi_italiani.txt"));
    }
}

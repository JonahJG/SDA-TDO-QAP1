import com.keyin.SuggestionEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SuggestionEngineTest {
    public SuggestionEngine suggestionEngine = new SuggestionEngine();

    @Test
    public void testLoadDictionaryData() {
        // Absolute path to the "words.txt" file
        String absolutePath = "C:\\Users\\jonah\\Desktop\\Semester4\\DevOps\\QAP1\\SDA-TDO-QAP1\\src\\main\\resources\\words.txt";

        // Load dictionary data from the absolute path
        try {
            System.out.println("Loading dictionary data from: " + absolutePath);
            suggestionEngine.loadDictionaryData(Paths.get(absolutePath));
            System.out.println("Dictionary data loaded successfully.");

            // Display the dictionary contents
            System.out.println("Dictionary contents:");
            suggestionEngine.getWordSuggestionDB().forEach((word, count) -> {
//                System.out.println(word + " - " + count);
            });
        } catch (IOException e) {
            System.err.println("Error loading dictionary data: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }



    @Test
    public void testGenerateSuggestions() {
        // Test generateSuggestions method from SuggestionEngine, using a correct word (Apple) and an incorrect word. Add system.out statements to see if the words.txt file is being accessed properly
        try {
            suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        String correctWord = "apple";
        String incorrectWord = "applee";

        // Test with a correct word
        String suggestionsForCorrectWord = suggestionEngine.generateSuggestions(correctWord);
        System.out.println("Suggestions for correct word: " + suggestionsForCorrectWord);
        assertEquals("", suggestionsForCorrectWord);

        // Test with an incorrect word
        String suggestionsForIncorrectWord = suggestionEngine.generateSuggestions(incorrectWord);
        System.out.println("Suggestions for incorrect word: " + suggestionsForIncorrectWord);
        Assertions.assertFalse(suggestionsForIncorrectWord.isEmpty());
    }

    @Test
    public void testKnown() {
        // Test known method from SuggestionEngine, using a correct word (jazzy) and an unknown word.
        try {
            System.out.println("Loading dictionary data...");
            suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
            System.out.println("Dictionary data loaded successfully.");
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        String knownWord = "jazzy";
        String unknownWord = "jazzyy";

        System.out.println("Dictionary contents:");
        suggestionEngine.getWordSuggestionDB().forEach((word, count) -> {
//            System.out.println(word + " - " + count);
        });

        // Test with a known word
        System.out.println("Checking known word: " + knownWord);
        boolean isKnownWord = suggestionEngine.known(Stream.of(knownWord)).count() > 0;
        System.out.println("Is known word: " + isKnownWord);
        assertTrue(isKnownWord);

        // Test with an unknown word
        System.out.println("Checking unknown word: " + unknownWord);
        boolean isUnknownWord = suggestionEngine.known(Stream.of(unknownWord)).count() == 0;
        System.out.println("Is unknown word: " + isUnknownWord);
        assertTrue(isUnknownWord);
    }


}


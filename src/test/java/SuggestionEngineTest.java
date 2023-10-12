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
        try {
            // Load dictionary data from "words.txt"
            System.out.println("Loading dictionary data from classpath: words.txt");
            suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
            System.out.println("Dictionary data loaded successfully.");

            // Display the dictionary contents
            System.out.println("Dictionary contents:");
            suggestionEngine.getWordSuggestionDB().forEach((word, count) -> {
                System.out.println(word + " - " + count);
            });
        } catch (URISyntaxException | IOException e) {
            System.err.println("Error loading dictionary data: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGenerateSuggestions() {
        try {
            suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        String correctWord = "apple";
        String incorrectWord = "applee";

        // Test for a correct word
        String suggestionsForCorrectWord = suggestionEngine.generateSuggestions(correctWord);
        System.out.println("Suggestions for correct word: " + suggestionsForCorrectWord);
        assertEquals("", suggestionsForCorrectWord);

        // Test for an incorrect word
        String suggestionsForIncorrectWord = suggestionEngine.generateSuggestions(incorrectWord);
        System.out.println("Suggestions for incorrect word: " + suggestionsForIncorrectWord);
        Assertions.assertFalse(suggestionsForIncorrectWord.isEmpty());
    }

    @Test
    public void testKnown() {
        try {
            System.out.println("Loading dictionary data...");
            suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
            System.out.println("Dictionary data loaded successfully.");
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        String knownWord = "jazzy";
        String unknownWord = "jazzyy";

        // Display the dictionary contents
        System.out.println("Dictionary contents:");
        suggestionEngine.getWordSuggestionDB().forEach((word, count) -> {
            // System.out.println(word + " - " + count);
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



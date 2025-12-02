package EventHandlingAssignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads flashcards from a text file.
 *
 * Expected format for each non-empty line:
 *   englishWord=translation text
 */
public class FlashcardLoader {

    /**
     * Load flashcards from the given file name.
     *
     * @param fileName path to the text file (relative or absolute)
     * @return list of Flashcard objects
     * @throws IOException if the file cannot be read
     */
    public static List<Flashcard> loadFromFile(String fileName) throws IOException {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName must not be null");
        }

        List<Flashcard> cards = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(
                Paths.get(fileName), StandardCharsets.UTF_8)) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    // skip empty lines
                    continue;
                }

                // Split on the first "=" only
                String[] parts = line.split("=", 2);
                if (parts.length < 2) {
                    // malformed line, ignore
                    continue;
                }

                String front = parts[0].trim(); // English word
                String back  = parts[1].trim(); // Translation

                cards.add(new Flashcard(front, back));
            }
        }

        return cards;
    }
}
package EventHandlingAssignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlashcardLoader {

    public static List<Flashcard> loadFromFile(String fileName) throws IOException {
        List<Flashcard> cards = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.contains("=")) continue;

                String[] parts = line.split("=", 2);
                String english = parts[0].trim();
                String french = parts[1].trim();

                cards.add(new Flashcard(english, french));
            }
        }

        return cards;
    }
}

package EventHandlingAssignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlashcardLoader {
    //We create a static method so that we can call it without creating an object
    public static List<Flashcard> loadFromFile(String fileName) throws IOException {
        // then we create an empty list
        List<Flashcard> cards = new ArrayList<>();

        // we use a try block it can close the file automatically if an error happens.
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // we declare a String variable to store each line of text
            String line;

            // then read each line until the end of file so we need while loop
            while ((line = br.readLine()) != null) {
                if (!line.contains("=")) continue;

                // split the line in 2 parts
                String[] parts = line.split("=", 2);
                String english = parts[0].trim();
                String french = parts[1].trim();

                // add the object into the lists
                cards.add(new Flashcard(english, french));
            }
        }

        return cards;
    }
}

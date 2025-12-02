package EventHandlingAssignment;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

/**
 * Launches the FlashcardApp using ONLY English → French flashcards.
 */
public class Main {
    public static void main(String[] args) {

        // Only English → French mode
        String fileName = "src/Flashcards_Text/flashcards_en_fr.txt";

        try {
            List<Flashcard> cards = FlashcardLoader.loadFromFile(fileName);

            SwingUtilities.invokeLater(() -> new FlashcardApp(cards));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not load flashcards.");
        }
    }
}

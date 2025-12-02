package EventHandlingAssignment;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

/**
 * Entry point:
 *  1. Load the English->French flashcard file.
 *  2. Launch the GUI application.
 */
public class Main {
    public static void main(String[] args) {

        // Fixed configuration: we only focus on ENGLISH -> FRENCH
        String fileName = "src/Flashcards_Text/flashcards_en_fr.txt";
        String languageMode = "English -> French";
        String progressFileName = "progress_fr.txt";

        // --- Load flashcards and launch the GUI ---

        try {
            List<Flashcard> cards = FlashcardLoader.loadFromFile(fileName);
            System.out.println("Loaded " + cards.size() + " cards from " + fileName);

            // Start the Swing GUI on the Event Dispatch Thread
            SwingUtilities.invokeLater(
                    () -> new FlashcardApp(cards, languageMode, progressFileName)
            );
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not load flashcards.");
        }
    }
}
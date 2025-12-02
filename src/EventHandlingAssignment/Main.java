package EventHandlingAssignment;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

/**
 * Entry point:
 *  1. Ask the user which language to study.
 *  2. Load the corresponding flashcard file.
 *  3. Launch the GUI application.
 */
public class Main {
    public static void main(String[] args) {

        String[] options = {"French", "German", "Spanish"};
        String choice = (String) JOptionPane.showInputDialog(
                null,
                "Choose language:",
                "Language",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == null) {
            // user cancelled
            return;
        }

        String fileName;
        String languageMode;
        String progressFileName;

        // Map menu choice -> card file + language label + progress file
        switch (choice) {
            case "French":
                fileName = "src/Flashcards_Text/flashcards_en_fr.txt";
                languageMode = "English -> French";
                progressFileName = "progress_fr.txt";
                break;
            case "German":
                fileName = "src/Flashcards_Text/flashcards_en_de.txt";
                languageMode = "English -> German";
                progressFileName = "progress_de.txt";
                break;
            case "Spanish":
                fileName = "src/Flashcards_Text/flashcards_en_es.txt";
                languageMode = "English -> Spanish";
                progressFileName = "progress_es.txt";
                break;
            default:
                return;
        }

        try {
            List<Flashcard> cards = FlashcardLoader.loadFromFile(fileName);
            System.out.println("Loaded " + cards.size() + " cards from " + fileName);

            SwingUtilities.invokeLater(
                    () -> new FlashcardApp(cards, languageMode, progressFileName)
            );
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not load flashcards.");
        }
    }
}
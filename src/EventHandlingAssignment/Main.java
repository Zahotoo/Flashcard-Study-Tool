package EventHandlingAssignment;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String fileName = "flashcards_en_fr.txt";

        try {
            List<Flashcard> cards = FlashcardLoader.loadFromFile(fileName);

            SwingUtilities.invokeLater(() -> new FlashcardApp(cards));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading flashcards.");
        }
    }
}

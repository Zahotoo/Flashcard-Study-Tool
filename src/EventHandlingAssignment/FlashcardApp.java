package EventHandlingAssignment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//FlashcardApp extends JFrame also
public class FlashcardApp extends JFrame {
    //We should create a constructor receives the list of flashcards loaded from the file and builds
    //the entire GUI around it.
    public FlashcardApp(List<Flashcard> cards) {
        // set the title
        super("Flashcard Study Tool - English → French");

        // we can tell the window to exit the program when closed automatically
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // now we create a new FlashcardPanel object here
        FlashcardPanel panel = new FlashcardPanel();
        panel.setCards(cards);

        // then place the flashcard panel in the center of the window
        add(panel, BorderLayout.CENTER);

        // finally, do some window setup
        pack();
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);

        panel.requestFocusInWindow();
    }
}

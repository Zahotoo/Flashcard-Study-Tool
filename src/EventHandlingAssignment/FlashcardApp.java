package EventHandlingAssignment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FlashcardApp extends JFrame {

    public FlashcardApp(List<Flashcard> cards) {
        super("Flashcard Study Tool - English → French");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        FlashcardPanel panel = new FlashcardPanel();
        panel.setCards(cards);

        add(panel, BorderLayout.CENTER);

        pack();
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);

        panel.requestFocusInWindow();
    }
}

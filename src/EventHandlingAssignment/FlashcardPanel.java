package EventHandlingAssignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FlashcardPanel extends JPanel {

    private Flashcard currentCard;
    private List<Flashcard> cards;
    private int currentIndex = 0;

    private JLabel textLabel;
    private boolean showingFront = true;
    private int flipCount = 0;

    public FlashcardPanel() {

        textLabel = new JLabel("No card loaded", SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 26));

        setLayout(new BorderLayout());
        add(textLabel, BorderLayout.CENTER);

        // Mouse flip event
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentCard == null) return;

                showingFront = !showingFront;
                flipCount++;

                if (showingFront)
                    textLabel.setText(currentCard.getFront());
                else
                    textLabel.setText(currentCard.getBack());
            }
        });

        // Arrow key navigation
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (cards == null || cards.isEmpty()) return;

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (currentIndex > 0) currentIndex--;
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (currentIndex < cards.size() - 1) currentIndex++;
                }

                setFlashcard(cards.get(currentIndex));
            }
        });

        setFocusable(true);
    }

    public void setCards(List<Flashcard> cards) {
        this.cards = cards;
        if (!cards.isEmpty()) {
            currentIndex = 0;
            setFlashcard(cards.get(0));
        }
    }

    private void setFlashcard(Flashcard card) {
        this.currentCard = card;
        showingFront = true;

        if (card != null)
            textLabel.setText(card.getFront());
        else
            textLabel.setText("No card loaded");
    }

    public int getFlipCount() {
        return flipCount;
    }
}

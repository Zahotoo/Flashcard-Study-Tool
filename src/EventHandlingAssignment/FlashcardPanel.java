package EventHandlingAssignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * Panel that displays a single flashcard.
 *
 * Q2:
 *   - Large JPanel with a JLabel in the center.
 *   - Mouse click toggles between front (English) and back (translation).
 *
 * Q3:
 *   - ESC   : jump back to the first card.
 *   - LEFT  : previous card.
 *   - RIGHT : next card.
 *   Every navigation shows the front side (translation hidden).
 */
public class FlashcardPanel extends JPanel implements MouseListener, KeyListener {

    private Flashcard currentCard;
    private final JLabel textLabel;
    private boolean showingFront = true;
    private int flipCount = 0;          // counts how many times the user flipped

    private List<Flashcard> cards;      // all flashcards for navigation
    private int currentIndex = -1;      // index of the currently shown card

    public FlashcardPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 200));

        textLabel = new JLabel("No card loaded", SwingConstants.CENTER);
        textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 24f));

        setLayout(new BorderLayout());
        add(textLabel, BorderLayout.CENTER);

        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
    }

    /**
     * Set the full list of cards and show the first one (if any).
     */
    public void setCards(List<Flashcard> cards) {
        this.cards = cards;
        if (cards != null && !cards.isEmpty()) {
            currentIndex = 0;
            setFlashcard(cards.get(0));
        } else {
            currentIndex = -1;
            setFlashcard(null);
        }
    }

    /**
     * Set the currently displayed card.
     * Always starts by showing the front (English word).
     */
    public void setFlashcard(Flashcard card) {
        this.currentCard = card;
        this.showingFront = true;

        if (card != null) {
            textLabel.setText(card.getFront());
        } else {
            textLabel.setText("No card loaded");
        }

        repaint();
    }

    public int getFlipCount() {
        return flipCount;
    }

    // -------- MouseListener: click to flip --------

    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentCard == null) {
            return;
        }

        showingFront = !showingFront;
        flipCount++;

        if (showingFront) {
            textLabel.setText(currentCard.getFront());
        } else {
            textLabel.setText(currentCard.getBack());
        }
    }
//have implenment all 5 actions but no need these 4(just mouse clicked)
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }

    // -------- KeyListener: ESC / LEFT / RIGHT navigation --------

    public int esc_cnt = 0;
    @Override
    public void keyPressed(KeyEvent e) {
        if (cards == null || cards.isEmpty()) {
            return;
        }

        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_ESCAPE:
                // restart from first card
                currentIndex = 0;
                esc_cnt++;
                break;
            case KeyEvent.VK_LEFT:
                // previous card
                if (currentIndex > 0) {
                    currentIndex--;
                }
                break;
            case KeyEvent.VK_RIGHT:
                // next card
                if (currentIndex < cards.size() - 1) {
                    currentIndex++;
                }
                break;
            default:
                // ignore all other keys
                return;
        }

        // navigation always resets to front side
        setFlashcard(cards.get(currentIndex));
    }

    @Override public void keyTyped(KeyEvent e) { }
    @Override public void keyReleased(KeyEvent e) { }
}
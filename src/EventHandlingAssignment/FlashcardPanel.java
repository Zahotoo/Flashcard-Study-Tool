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

    // --- UI state for the currently displayed flashcard ---

    private Flashcard currentCard;
    private final JLabel textLabel;
    private boolean showingFront = true;
    private int flipCount = 0;          // counts how many times the user flipped

    // --- Full deck and navigation index ---

    private List<Flashcard> cards;      // all flashcards for navigation
    private int currentIndex = -1;      // index of the currently shown card

    /**
     * Construct the panel and set up its basic appearance and listeners.
     */
    public FlashcardPanel() {
        // Panel styling
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 200));

        // Center label to show flashcard text
        textLabel = new JLabel("No card loaded", SwingConstants.CENTER);
        textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 24f));

        // Layout: label in the center
        setLayout(new BorderLayout());
        add(textLabel, BorderLayout.CENTER);

        // Register this panel as a mouse and key listener
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

    /**
     * Returns how many times the user has flipped between front/back.
     */
    public int getFlipCount() {
        return flipCount;
    }

    // ---------------------------------------------------------------------
    // MouseListener implementation: click to flip the current card
    // ---------------------------------------------------------------------

    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentCard == null) {
            return;
        }

        // Toggle between front and back
        showingFront = !showingFront;
        flipCount++;

        if (showingFront) {
            textLabel.setText(currentCard.getFront());
        } else {
            textLabel.setText(currentCard.getBack());
        }
    }

    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }

    // ---------------------------------------------------------------------
    // KeyListener implementation: ESC / LEFT / RIGHT for navigation
    // ---------------------------------------------------------------------
    public int esc_cnt = 0;
    @Override
    public void keyPressed(KeyEvent e) {

        if (cards == null || cards.isEmpty()) {
            return;
        }

        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_ESCAPE:
                // Restart from the first card
                currentIndex = 0;
                esc_cnt++;
                break;
            case KeyEvent.VK_LEFT:
                // Go to previous card if possible
                if (currentIndex > 0) {
                    currentIndex--;
                }
                break;
            case KeyEvent.VK_RIGHT:
                // Go to next card if possible
                if (currentIndex < cards.size() - 1) {
                    currentIndex++;
                }
                break;
            default:
                // Ignore all other keys
                return;
        }

        // Navigation always resets to front side
        setFlashcard(cards.get(currentIndex));
    }

    @Override public void keyTyped(KeyEvent e) { }
    @Override public void keyReleased(KeyEvent e) { }
}
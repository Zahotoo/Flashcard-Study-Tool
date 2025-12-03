package EventHandlingAssignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

// So this class extends JPanel.
public class FlashcardPanel extends JPanel {
    //We create currentCard which holds the card currently on screen.
    private Flashcard currentCard;
    //Cards is the full list of flashcards
    private List<Flashcard> cards;
    //currentIndex is the index of the card we are showing
    private int currentIndex = 0;

    //and we create textLabel which is the large text shown in the center
    private JLabel textLabel;
    //then the variable showingFront tracks whether we are showing English or French
    private boolean showingFront = true;

    //Now, Let’s focus on the constructor
    public FlashcardPanel() {
        //we should create a centered JLabel that will display the
        //flashcard text.
        textLabel = new JLabel("No card loaded", SwingConstants.CENTER);
        // And set a large bold font to make the text easy to read.
        textLabel.setFont(new Font("Arial", Font.BOLD, 26));

        //Then we want to place the label in the center of the panel, so we new a BorderLayout
        setLayout(new BorderLayout());
        add(textLabel, BorderLayout.CENTER);

        //Then we should add the flip logic, so we add a MouseAdapter in MouseListener so clicking on
        //the panel flips the card. If no card is loaded, clicking should do nothing.
        // If showingFront is true, display the English word. Otherwise, show the French translation.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentCard == null) return;

                showingFront = !showingFront;

                if (showingFront)
                    textLabel.setText(currentCard.getFront());
                else
                    textLabel.setText(currentCard.getBack());
            }
        });

        //Now, we want to do the left and right logic, so add the KeyAdapter into the KeyListener to
        //detect arrow key presses.
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (cards == null || cards.isEmpty()) return;

                //- Here I want to show the left key logic, when the left arrow is pressed, move to the previous
                //card, unless we are already at the first card.
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (currentIndex > 0) currentIndex--;
                }

                //- When the right arrow is pressed, move to the next card, unless we are at the last card.
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (currentIndex < cards.size() - 1) currentIndex++;
                }

                //Finally, after moving to a different index, we should update the UI by calling setFlashcard().
                setFlashcard(cards.get(currentIndex));
            }
        });

        //And set the keyboard focusable to detect the keyboard input.
        setFocusable(true);
    }

    //In the end, we should do some setter methods
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
}
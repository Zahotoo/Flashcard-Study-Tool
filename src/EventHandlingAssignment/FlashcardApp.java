package EventHandlingAssignment;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Main application window that hosts the FlashcardPanel and
 * provides a "Save Progress" button (Q4).
 */
public class FlashcardApp extends JFrame {

    // --- State for the window and study session ---

    // UI panel that displays and navigates flashcards
    private final FlashcardPanel flashcardPanel;
    // All cards for this session
    private final List<Flashcard> cards;
    // Human-readable description of the language mode, e.g. "English -> French"
    private final String languageMode;
    // File name to which progress should be saved, e.g. "progress_fr.txt"
    private final String progressFileName;
    // Timestamp (in milliseconds) recorded when the window is created
    private final long startTimeMillis;

    /**
     * Construct the main application window.
     *
     * @param cards            list of flashcards to study
     * @param languageMode     label describing the language direction
     * @param progressFileName file to append the progress summary to
     */
    public FlashcardApp(List<Flashcard> cards,
                        String languageMode,
                        String progressFileName) {
        super("Flashcard Study Tool");

        // --- Basic argument validation ---

        if (cards == null) {
            throw new IllegalArgumentException("cards must not be null");
        }
        if (languageMode == null || progressFileName == null) {
            throw new IllegalArgumentException("languageMode and progressFileName must not be null");
        }

        // --- Initialize fields used during the session ---

        this.cards = cards;
        this.languageMode = languageMode;
        this.progressFileName = progressFileName;
        this.startTimeMillis = System.currentTimeMillis();

        // --- Basic frame configuration ---

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Center: flashcard display panel ---

        flashcardPanel = new FlashcardPanel();
        flashcardPanel.setCards(cards);
        add(flashcardPanel, BorderLayout.CENTER);

        // --- South: "Save Progress" button and container panel ---

        JButton saveButton = new JButton("Save Progress");
        saveButton.addActionListener(e -> saveProgress());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(saveButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Final window setup (size + position + focus) ---

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Ensure the panel receives keyboard focus for key events
        flashcardPanel.requestFocusInWindow();
    }

    /**
     * Build and write a study summary to the progress file.
     * Example output:
     *
     * Language mode: English -> French
     * Total flashcards studied: 14
     * Flips: 21
     * Repeats: 6
     * Time spent: 3 minutes 41 seconds
     */
    private void saveProgress() {
        // --- Collect statistics about the session ---

        int totalFlashcards = cards.size();
        int flips = flashcardPanel.getFlipCount();
        // Repeats = how many flips go beyond the number of distinct cards
        int repeats = Math.max(0, flips - totalFlashcards);

        long elapsedMillis = System.currentTimeMillis() - startTimeMillis;
        long totalSeconds = elapsedMillis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        // --- Build a human-readable summary string ---

        StringBuilder sb = new StringBuilder();
        sb.append("Language mode: ").append(languageMode).append(System.lineSeparator());
        sb.append("Total flashcards studied: ").append(totalFlashcards).append(System.lineSeparator());
        sb.append("Flips: ").append(flips).append(System.lineSeparator());
        sb.append("Repeats: ").append(repeats).append(System.lineSeparator());
        sb.append("Time spent: ")
          .append(minutes).append(" minutes ").append(seconds).append(" seconds")
          .append(System.lineSeparator());
        sb.append(System.lineSeparator());

        // --- Append the summary to the appropriate progress file ---

        try (PrintWriter out = new PrintWriter(new FileWriter(progressFileName, true))) {
            out.print(sb.toString());
            JOptionPane.showMessageDialog(this, "Progress saved to " + progressFileName);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not save progress.");
        }
    }
}
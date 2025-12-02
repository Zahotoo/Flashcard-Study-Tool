package EventHandlingAssignment;

/**
 * Simple value object representing a flashcard:
 * front  = English word
 * back   = translation
 */
public class Flashcard {

    // --- Immutable state for a single flashcard ---

    private final String front;
    private final String back;

    /**
     * Construct a flashcard with a front (English) and back (translation).
     */
    public Flashcard(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }
}
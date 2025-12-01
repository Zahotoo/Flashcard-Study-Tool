package EventHandlingAssignment;

public class Flashcard {

    private String front;  // English
    private String back;   // Translation

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


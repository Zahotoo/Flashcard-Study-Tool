package EventHandlingAssignment;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Flashcard Program – Comprehensive Test Suite")
public class FlashcardTest {

    /* ============================================================================================
     *  Q1 — Flashcard Tests
     * ============================================================================================ */
    @Nested
    @DisplayName("Flashcard Class Tests")
    class FlashcardDataTests {

        @Test
        @DisplayName("Front side should store English word correctly")
        void shouldStoreEnglishCorrectly() {
            Flashcard c = new Flashcard("apple", "la pomme");
            assertEquals("apple", c.getFront());
        }

        @Test
        @DisplayName("Back side should store French translation correctly")
        void shouldStoreFrenchCorrectly() {
            Flashcard c = new Flashcard("apple", "la pomme");
            assertEquals("la pomme", c.getBack());
        }

        @ParameterizedTest(name = "front = \"{0}\" should not be null")
        @ValueSource(strings = {"dog", "cat", "book"})
        @DisplayName("Front side should never be null")
        void shouldHaveNonNullFront(String word) {
            Flashcard c = new Flashcard(word, "xxx");
            assertNotNull(c.getFront());
        }

        @ParameterizedTest(name = "back = \"{0}\" should not be null")
        @ValueSource(strings = {"le chien", "le chat", "le livre"})
        @DisplayName("Back side should never be null")
        void shouldHaveNonNullBack(String translation) {
            Flashcard c = new Flashcard("xxx", translation);
            assertNotNull(c.getBack());
        }
    }



    /* ============================================================================================
     *  Q2 — FlashcardLoader Tests
     * ============================================================================================ */
    @Nested
    @DisplayName("FlashcardLoader File I/O Tests")
    class FlashcardLoaderTests {

        File tempFile;

        @BeforeEach
        void setupTempFile() throws IOException {
            tempFile = File.createTempFile("testcards", ".txt");
            try (PrintWriter out = new PrintWriter(tempFile)) {
                out.println("apple=la pomme");
                out.println("dog=le chien");
                out.println("cat=le chat");
            }
        }

        @Test
        @DisplayName("Loader should return a non-null list")
        void shouldReturnList() throws IOException {
            assertNotNull(FlashcardLoader.loadFromFile(tempFile.getAbsolutePath()));
        }

        @Test
        @DisplayName("Loader should read correct number of cards")
        void shouldLoadCorrectCount() throws IOException {
            assertEquals(3, FlashcardLoader.loadFromFile(tempFile.getAbsolutePath()).size());
        }

        @Test
        @DisplayName("Loader should parse English correctly")
        void shouldParseEnglish() throws IOException {
            assertEquals("apple",
                    FlashcardLoader.loadFromFile(tempFile.getAbsolutePath()).get(0).getFront());
        }

        @Test
        @DisplayName("Loader should parse French correctly")
        void shouldParseFrench() throws IOException {
            assertEquals("la pomme",
                    FlashcardLoader.loadFromFile(tempFile.getAbsolutePath()).get(0).getBack());
        }

        @Test
        @DisplayName("Invalid lines should be ignored")
        void shouldIgnoreInvalidLines() throws IOException {
            try (PrintWriter out = new PrintWriter(tempFile)) {
                out.println("badline");
                out.println("orange=orange");
            }
            List<Flashcard> cards = FlashcardLoader.loadFromFile(tempFile.getAbsolutePath());
            assertEquals(1, cards.size());
        }

        @Test
        @DisplayName("Loader should trim whitespace properly")
        void shouldTrimWhitespace() throws IOException {
            try (PrintWriter out = new PrintWriter(tempFile)) {
                out.println("  cat   =   le chat   ");
            }
            List<Flashcard> cards = FlashcardLoader.loadFromFile(tempFile.getAbsolutePath());
            assertEquals("cat", cards.get(0).getFront());
            assertEquals("le chat", cards.get(0).getBack());
        }

        @Test
        @DisplayName("Empty file should produce empty card list")
        void shouldReturnEmptyList() throws IOException {
            try (PrintWriter ignored = new PrintWriter(tempFile)) {}
            assertEquals(0, FlashcardLoader.loadFromFile(tempFile.getAbsolutePath()).size());
        }

        @Test
        @DisplayName("Missing file should throw IOException")
        void shouldThrowIOException() {
            assertThrows(IOException.class,
                    () -> FlashcardLoader.loadFromFile("missing.txt"));
        }
    }



    /* ============================================================================================
     *  Q3 — FlashcardPanel Tests (Mouse + Keyboard Event Handling)
     * ============================================================================================ */
    @Nested
    @DisplayName("FlashcardPanel GUI / Event Tests")
    class FlashcardPanelTests {

        FlashcardPanel panel;
        List<Flashcard> cards;

        @BeforeEach
        void setupPanel() throws Exception {
            panel = new FlashcardPanel();
            cards = Arrays.asList(
                    new Flashcard("apple", "la pomme"),
                    new Flashcard("dog", "le chien"),
                    new Flashcard("cat", "le chat")
            );

            SwingUtilities.invokeAndWait(() -> {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);

                panel.setCards(cards);
                panel.requestFocusInWindow();
            });
        }

        @Test
        @DisplayName("Panel should show the first card initially")
        void shouldShowInitialCard() {
            assertEquals("apple", getLabelText(panel));
        }

        @Test
        @DisplayName("Mouse click should flip to back side")
        void shouldFlipToBack() throws Exception {
            click(panel);
            assertEquals("la pomme", getLabelText(panel));
        }

        @Test
        @DisplayName("Second click should flip back to front")
        void shouldFlipBackToFront() throws Exception {
            click(panel);
            click(panel);
            assertEquals("apple", getLabelText(panel));
        }

        /* ================= Keyboard Navigation Tests ================= */

        @Test
        @DisplayName("RIGHT key should move to next card")
        void shouldGoToNextCard() throws Exception {
            press(panel, KeyEvent.VK_RIGHT);
            assertEquals("dog", getLabelText(panel));
        }

        @Test
        @DisplayName("LEFT key at first card should remain on first")
        void shouldNotGoBeforeFirst() throws Exception {
            press(panel, KeyEvent.VK_LEFT);
            assertEquals("apple", getLabelText(panel));
        }

        @Test
        @DisplayName("RIGHT key should stop at last card")
        void shouldStopAtLastCard() throws Exception {
            press(panel, KeyEvent.VK_RIGHT);
            press(panel, KeyEvent.VK_RIGHT);
            press(panel, KeyEvent.VK_RIGHT);
            assertEquals("cat", getLabelText(panel));
        }

        @Test
        @DisplayName("Navigation should always reset back to front side")
        void shouldResetToFrontOnNavigate() throws Exception {
            press(panel, KeyEvent.VK_RIGHT);
            click(panel);
            press(panel, KeyEvent.VK_LEFT);
            assertEquals("apple", getLabelText(panel));
        }
    }



    /* ============================================================================================
     *  Q4 — FlashcardApp Tests
     * ============================================================================================ */
    @Nested
    @DisplayName("FlashcardApp Window Tests")
    class FlashcardAppTests {

        @Test
        @DisplayName("App should create a valid JFrame")
        void shouldCreateWindow() throws Exception {
            FlashcardApp[] holder = new FlashcardApp[1];
            SwingUtilities.invokeAndWait(() ->
                    holder[0] = new FlashcardApp(List.of(new Flashcard("x", "y")))
            );
            assertNotNull(holder[0]);
        }

        @Test
        @DisplayName("App title should be correct")
        void shouldHaveCorrectTitle() throws Exception {
            FlashcardApp[] holder = new FlashcardApp[1];
            SwingUtilities.invokeAndWait(() ->
                    holder[0] = new FlashcardApp(List.of(new Flashcard("x", "y")))
            );
            assertEquals("Flashcard Study Tool - English → French", holder[0].getTitle());
        }

        @Test
        @DisplayName("App should contain exactly one panel")
        void shouldContainPanel() throws Exception {
            FlashcardApp[] holder = new FlashcardApp[1];
            SwingUtilities.invokeAndWait(() ->
                    holder[0] = new FlashcardApp(List.of(new Flashcard("x", "y")))
            );
            assertEquals(1, holder[0].getContentPane().getComponentCount());
        }

        @Test
        @DisplayName("App should launch without crashing")
        void shouldLaunchSafely() {
            assertDoesNotThrow(() ->
                    new FlashcardApp(List.of(new Flashcard("x", "y"))));
        }
    }

    /* ============================================================================================
     *  Helper Methods
     * ============================================================================================ */
    private String getLabelText(FlashcardPanel p) {
        try {
            return ((JLabel) p.getComponent(0)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    private void click(FlashcardPanel p) throws Exception {
        SwingUtilities.invokeAndWait(() ->
                p.dispatchEvent(new MouseEvent(p, MouseEvent.MOUSE_CLICKED,
                        System.currentTimeMillis(), 0,
                        5, 5, 1, false))
        );
    }

    private void press(FlashcardPanel p, int key) throws Exception {
        SwingUtilities.invokeAndWait(() ->
                p.dispatchEvent(new KeyEvent(
                        p, KeyEvent.KEY_PRESSED,
                        System.currentTimeMillis(), 0,
                        key, (char) key))
        );
    }
}

# Flashcard Study Tool — EN -> FR Edition
### Event Handling • File I/O

---

## Overview

This project implements a **English -> French flashcard study tool** using Java Swing, designed to demonstrate:

- **Event Handling**
    - `ActionEvent` (button clicks)
    - `MouseEvent` (flashcard flipping)
    - `KeyEvent` (keyboard navigation)

- **File Handling**
    - Load flashcards from `.txt` files
    - Save study progress to an output file


Users can flip cards, navigate using keys, and save progress reports.

This project is structured as a tutorial lab and produces more than **300 lines of fully event-driven Java code** across multiple classes.

---

## Project Structure

```
src/
  Flashcard.java
  FlashcardLoader.java
  FlashcardPanel.java
  FlashcardApp.java
  Main.java

flashcards_en_fr.txt
```

---

## Exercise — Flashcard Study Tool

### Q1 — Load Flashcards from File (File Handling)

Implement:

#### a) `Flashcard` class
Stores:
- English word (`front`)
- Translation (`back`)

#### b) `FlashcardLoader` class
Reads the file:
- `flashcards_en_fr.txt`

Format:

```
apple=la pomme
book=le livre
computer=l’ordinateur
```

Requirements:

- Read with `BufferedReader`
- Split on `=`
- Build a `List<Flashcard>`
- Choose language mode via JOptionPane menu

---

### Q2 — Display Flashcards & Reveal Translation (MouseEvent)

GUI requirements:

- A large `JPanel` (flashcard display)
- A `JLabel` showing the English word

Mouse behavior:

- Click → show translation
- Click again → hide translation
- Track “flip counter”

Example:

```
apple
(click)
la pomme
(click)
apple
```

---

### Q3 — Navigate Flashcards via Keyboard (KeyEvent)

Attach a KeyListener with the following controls:

| Key | Action |
|------|---------|
| ESC | Restart from first |
| LEFT | Previous card |
| RIGHT | Next card |

Navigation resets translation to hidden.

---

### Q4 — Save Study Summary (ActionEvent + File Handling)

Add a **Save Progress** button.

On click:

- Trigger ActionEvent
- Save progress to:
    - `progress_fr.txt`

Saved data example:

```
Language mode: English → French
Total flashcards studied: 14
Flips: 21
Repeats: 6
Time spent: 3 minutes 41 seconds
```

---

## Where Event Handling Is Used

### ✔ MouseEvent
Used in Q2 to flip cards and track flips.

### ✔ KeyEvent
Used in Q3 to navigate between flashcards.

### ✔ ActionEvent
Used in Q4 to save progress to a file.

---

## Example Flashcard Files

### `flashcards_en_fr.txt`
```
apple=la pomme
book=le livre
computer=l’ordinateur
water=l’eau
house=la maison
```
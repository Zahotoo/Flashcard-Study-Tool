# Flashcard Study Tool — EN -> FR Edition
### Event Handling • File I/O

---

## Overview

This lab focuses on the use of Java Event Handling and File I/O through the development of a simple Flashcard Study Tool.
The tool allows a user to study English→French vocabulary by loading word pairs from a file and interacting with a graphical interface using mouse clicks and keyboard input.

Each question in this lab corresponds to the implementation of one class in the program.

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

### Q1 — Flashcard Class
#### Responsibilities:
1. Store the English word (front)
2. Store the French translation (back)

#### Code Summary:
```
private final String front;
private final String back;
```

### Q2 — FlashcardLoader Class (File I/O)
The goal of Question 2 was to implement file reading to load vocabulary pairs from `flashcards_en_fr.txt`.

Each line in the file is formatted as:
```
english_word=french_translation
```
#### Responsibilities:
1. Read data using `BufferedReader`
2. Split each line on `"="`
3. Trim extra whitespace
4. Create a `Flashcard` object for each line
5. Return a list of flashcards

#### Code Summary:
```
public static List<Flashcard> loadFromFile(String fileName) throws IOException
```


### Q3 — FlashcardPanel Class (MouseEvent & KeyEvent)
This question required implementing user interaction through event handling.
FlashcardPanel extends `JPanel` and handles:

| Key | Action |
|------|---------|
| LEFT | Previous card |
| RIGHT | Next card |

#### Mouse Click (MouseEvent)
1. Flips the card between English (front) and French (back)
2. Uses a `MouseAdapter` to simplify implementation
#### Keyboard Navigation (KeyEvent)
1. `LEFT` -> Previous card
2. `RIGHT` -> Next card
3. Always shows the front (English) when navigating

#### Code Summary
```
addMouseListener(new MouseAdapter(){});
addKeyListener(new KeyAdapter(){});
```
---

### Q4 — FlashcardApp
The final question integrates all previous components into a running graphical application.

#### FlashcardApp responsibilities:
1. Create the main window (`JFrame`)
2. Create the FlashcardPanel
3. Load cards into the Panel
4. Set layout, size, and window properties

#### Code Summary
```
public class FlashcardApp extends JFrame
```

#### Main Responsibilities:
1. Load the flashcard file
2. Launch the GUI application using SwingUtilities.invokeLater

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
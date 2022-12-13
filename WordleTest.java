package org.cis1200.wordle;

import org.junit.jupiter.api.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing for Wrodle
 * Note that I did not test the edge case for deleting a character as my
 * GameBoard Handles the edge case where
 * the wordsize is less than 0
 */

public class WordleTest {

    @Test
    public void createsEmptyWordleBoard() {

        Wordle wordle = new Wordle();

        for (int i = 0; i < 6; i++) {

            for (int j = 0; j < 5; j++) {

                assertEquals(wordle.getGrid(i, j).getText(), "");

            }

        }

    }

    @Test
    public void testSetCharacterIncreasesWordTyped() {

        Wordle wordle = new Wordle();
        wordle.setCharacter('a');
        wordle.setCharacter('b');
        wordle.setCharacter('c');
        wordle.setCharacter('d');
        assertEquals(wordle.getWordTyped().size(), 4);

    }

    @Test
    public void deleteCharacterRemovesItFromWordList() {
        Wordle wordle = new Wordle();
        wordle.setCharacter('a');
        wordle.setCharacter('b');
        wordle.setCharacter('c');
        wordle.setCharacter('d');
        wordle.deleteCharacter();
        assertEquals(wordle.getWord(), "abc");

    }

    @Test
    public void deleteCharacterChangesSize() {
        Wordle wordle = new Wordle();
        wordle.setCharacter('a');
        wordle.setCharacter('b');
        wordle.setCharacter('c');
        wordle.setCharacter('d');
        wordle.deleteCharacter();
        assertEquals(wordle.getWordTyped().size(), 3);

    }

    @Test
    public void deleteCharacterUpdatesGrid() {
        Wordle wordle = new Wordle();
        wordle.setCharacter('a');
        wordle.setCharacter('b');
        wordle.setCharacter('c');
        wordle.setCharacter('d');
        wordle.deleteCharacter();
        for (int i = 0; i < wordle.getWordTyped().size(); i++) {
            assertNotEquals(wordle.getGrid(0, i).getText(), "");
        }
        assertEquals(wordle.getGrid(0, 4).getText(), "");

    }

    @Test
    public void testGetWordFromWordTypedNonEmptyWord() {

        Wordle wordle = new Wordle();
        wordle.setCharacter('a');
        wordle.setCharacter('b');
        wordle.setCharacter('c');
        wordle.setCharacter('d');
        assertEquals(wordle.getWord(), "abcd");

    }

    @Test
    public void testGetWordFromWordTypedEmptyWord() {

        Wordle wordle = new Wordle();
        assertEquals(wordle.getWord(), "");

    }

    @Test
    public void testGridCharacterUpdates() {

        Wordle wordle = new Wordle();
        wordle.setCharacter('a');
        wordle.setCharacter('b');
        wordle.setCharacter('c');
        wordle.setCharacter('d');

        for (int i = 0; i < wordle.getWordTyped().size(); i++) {
            assertNotEquals(wordle.getGrid(0, i).getText(), "");
        }
    }

    @Test
    public void testColorMapAllRight() {
        Wordle wordle = new Wordle();
        String word = "abcd";
        wordle.setCharacter('a');
        wordle.setCharacter('b');
        wordle.setCharacter('c');
        wordle.setCharacter('d');
        wordle.setCharacter('e');
        Wordle.Colors[] color = wordle.getColorMap(wordle.getWord(), "abcde");

        for (int i = 0; i < 5; i++) {
            assertEquals(color[i], Wordle.Colors.RIGHT);
        }

    }

    @Test
    public void testColorMapNotAllRight() {
        Wordle wordle = new Wordle();
        wordle.setCharacter('a');
        wordle.setCharacter('c');
        wordle.setCharacter('c');
        wordle.setCharacter('d');
        wordle.setCharacter('e');
        Wordle.Colors[] color = wordle.getColorMap(wordle.getWord(), "abcde");
        assertEquals(color[0], Wordle.Colors.RIGHT);
        assertEquals(color[1], Wordle.Colors.WRONG);
        assertEquals(color[2], Wordle.Colors.RIGHT);
        assertEquals(color[3], Wordle.Colors.RIGHT);
        assertEquals(color[4], Wordle.Colors.RIGHT);
    }

    @Test
    public void testValidWordWordUsedAlready() {

        Wordle wordle = new Wordle();
        wordle.setCharacter('a');
        wordle.setCharacter('c');
        wordle.setCharacter('c');
        wordle.setCharacter('d');
        wordle.setCharacter('e');
        wordle.increaseUsedWords();
        assertFalse(wordle.validWord());
    }

    @Test
    public void testValidWordInvalidWord() {

        Wordle wordle = new Wordle();
        wordle.setCharacter('a');
        wordle.setCharacter('c');
        wordle.setCharacter('c');
        wordle.setCharacter('d');
        wordle.setCharacter('#');
        assertFalse(wordle.validWord());
    }

    @Test
    public void testValidWord() {

        Wordle wordle = new Wordle();
        wordle.setCharacter('h');
        wordle.setCharacter('e');
        wordle.setCharacter('l');
        wordle.setCharacter('l');
        wordle.setCharacter('o');
        assertTrue(wordle.validWord());
    }

    @Test
    public void testCheckWinnerNotWinner() {

        Wordle wordle = new Wordle();
        wordle.setCharacter('h');
        wordle.setCharacter('e');
        wordle.setCharacter('l');
        wordle.setCharacter('l');
        wordle.setCharacter('o');

        assertFalse(wordle.checkWinner(wordle.getWord(), "waits"));

    }

    @Test
    public void testCheckWinnerWinner() {

        Wordle wordle = new Wordle();
        wordle.setCharacter('h');
        wordle.setCharacter('e');
        wordle.setCharacter('l');
        wordle.setCharacter('l');
        wordle.setCharacter('o');

        assertTrue(wordle.checkWinner(wordle.getWord(), "hello"));

    }

    @Test
    public void testIncrementDoesNotIncrementWhenLessThan5() {
        Wordle wordle = new Wordle();
        wordle.setCharacter('h');
        wordle.setCharacter('e');
        wordle.setCharacter('l');

        wordle.increment();

        assertEquals(wordle.getRow(), 0);

    }

    @Test
    public void testIncrementCorrect() {
        Wordle wordle = new Wordle();
        wordle.setCharacter('h');
        wordle.setCharacter('e');
        wordle.setCharacter('l');
        wordle.setCharacter('l');
        wordle.setCharacter('l');

        wordle.increment();

        assertEquals(wordle.getRow(), 1);

    }

    @Test
    public void testWordle() {

        Wordle wordle = new Wordle();
        String wordleWord = "guess";

        wordle.setCharacter('h');
        wordle.setCharacter('e');
        wordle.setCharacter('l');
        wordle.setCharacter('l');
        wordle.setCharacter('o');

        Wordle.Colors[] color = wordle.getColorMap(wordle.getWord(), wordleWord);
        assertEquals(color[0], Wordle.Colors.INCORRECT);
        assertEquals(color[1], Wordle.Colors.WRONG);
        assertEquals(color[2], Wordle.Colors.INCORRECT);
        assertEquals(color[3], Wordle.Colors.INCORRECT);
        assertEquals(color[4], Wordle.Colors.INCORRECT);

        wordle.increment();

        assertFalse(wordle.checkWinner(wordle.getWord(), wordleWord));

        wordle.resetLinkedList();
        wordle.setCharacter('s');
        wordle.setCharacter('t');
        wordle.setCharacter('a');
        wordle.setCharacter('t');
        wordle.setCharacter('e');

        color = wordle.getColorMap(wordle.getWord(), wordleWord);
        assertEquals(color[0], Wordle.Colors.WRONG);
        assertEquals(color[1], Wordle.Colors.INCORRECT);
        assertEquals(color[2], Wordle.Colors.INCORRECT);
        assertEquals(color[3], Wordle.Colors.INCORRECT);
        assertEquals(color[4], Wordle.Colors.WRONG);

        wordle.increment();

        assertFalse(wordle.checkWinner(wordle.getWord(), wordleWord));

        wordle.resetLinkedList();
        wordle.setCharacter('w');
        wordle.setCharacter('a');
        wordle.setCharacter('i');
        wordle.setCharacter('t');
        wordle.setCharacter('s');

        color = wordle.getColorMap(wordle.getWord(), wordleWord);
        assertEquals(color[0], Wordle.Colors.INCORRECT);
        assertEquals(color[1], Wordle.Colors.INCORRECT);
        assertEquals(color[2], Wordle.Colors.INCORRECT);
        assertEquals(color[3], Wordle.Colors.INCORRECT);
        assertEquals(color[4], Wordle.Colors.RIGHT);

        wordle.increment();

        assertFalse(wordle.checkWinner(wordle.getWord(), wordleWord));

        wordle.resetLinkedList();
        wordle.setCharacter('s');
        wordle.setCharacter('u');
        wordle.setCharacter('c');
        wordle.setCharacter('k');
        wordle.setCharacter('s');

        color = wordle.getColorMap(wordle.getWord(), wordleWord);
        assertEquals(color[0], Wordle.Colors.WRONG);
        assertEquals(color[1], Wordle.Colors.RIGHT);
        assertEquals(color[2], Wordle.Colors.INCORRECT);
        assertEquals(color[3], Wordle.Colors.INCORRECT);
        assertEquals(color[4], Wordle.Colors.RIGHT);

        wordle.increment();

        assertFalse(wordle.checkWinner(wordle.getWord(), wordleWord));

        wordle.resetLinkedList();
        wordle.setCharacter('g');
        wordle.setCharacter('u');
        wordle.setCharacter('e');
        wordle.setCharacter('s');
        wordle.setCharacter('s');

        color = wordle.getColorMap(wordle.getWord(), wordleWord);
        assertEquals(color[0], Wordle.Colors.RIGHT);
        assertEquals(color[1], Wordle.Colors.RIGHT);
        assertEquals(color[2], Wordle.Colors.RIGHT);
        assertEquals(color[3], Wordle.Colors.RIGHT);
        assertEquals(color[4], Wordle.Colors.RIGHT);

        assertTrue(wordle.checkWinner(wordle.getWord(), wordleWord));

    }
}

package org.cis1200.wordle;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Controller and Model Class for the Wordle Game.
 * Essentially does all the backend logic from storing the grid on the server
 * side
 * to chekcing validity, saving the back end board and writing to a file
 */
public class Wordle {

    // backend implementation of board
    private final Tile[][] grid;
    //set of used words
    private final HashSet<String> usedWords;
    // Instance of dictionary to check valid words and get wordle word
    private Dictonary d;
    //current row
    private int row;
    //wordle word
    private String targetWord;
    //current word typed by character
    private LinkedList<Character> wordTyped;

    /**
     * Constructor for Wordle Class, creates a new dictionary and new board
     */

    public Wordle() {
        grid = new Tile[6][5];
        try {
            d = new Dictonary();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        usedWords = new HashSet<String>();
        targetWord = d.generateWordleWord();
        wordTyped = new LinkedList<Character>();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = new Tile(i, j);
            }
        }
    }

    //returns grid
    public Tile getGrid(int r, int c) {
        Tile t = grid[r][c];
        return t;

    }

    //load colors and characters into 2d array
    private void loadSavedGrid(String guess, String targetWord) {

        for (int i = 0; i < 5; i++) {
            wordTyped.add(guess.charAt(i));
            usedWords.add(guess);
            Colors[] map = getColorMap(guess, targetWord);
            grid[row][i].setText(wordTyped.get(i) + "");
            grid[row][i].setBackground(map[i].color);
        }

    }

    //checks if word typed is wordleword
    public boolean checkWinner(String guess, String target) {
        return target.equals(guess);
    }

    //resets game by recalling the class
    public void resetGame() {
        Wordle w = new Wordle();
    }

    //writes the current characters and wordle word to file
    public void writeToFile(String path) {
        try {
            File f = new File(path);
            FileWriter writer = new FileWriter(f, false);
            writer.write(targetWord);
            for (int i = 0; i < row; i++) {
                writer.write("\n");
                for (int j = 0; j < 5; j++) {
                    writer.write(grid[i][j].getText());
                }

            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //checks if word is valid word before entering
    public boolean validWord() {

        return d.validWord(getWord()) && !usedWords.contains(getWord().toUpperCase());

    }

    //gets the colors for each individual Tile in the row when enter is clicked
    public Colors[] getColorMap(String guess, String word) {

        Colors[] letterStatus = new Colors[5];
        for (int i = 0; i < 5; i++) {

            String guessLetter = String.valueOf(guess.charAt(i)).toLowerCase();
            String validLetter = String.valueOf(word.charAt(i)).toLowerCase();
            if (guessLetter.equals(validLetter)) {
                letterStatus[i] = Colors.RIGHT;
            } else if (word.contains(guessLetter)) {
                letterStatus[i] = Colors.WRONG;
            } else {
                letterStatus[i] = Colors.INCORRECT;
            }
        }

        return letterStatus;
    }

    //gets target word
    public String getWordToGuess() {
        String temp = targetWord;
        return temp;
    }

    //gets word typed
    public String getWord() {

        String temp = "";

        for (Character c : wordTyped) {
            temp += c + "";
        }
        System.out.println(temp);
        return temp;

    }

    //actually loads the game and resets the board beforehand
    public void loadGame(String path) {
        try {
            usedWords.clear();
            setRow(0);
            wordTyped = new LinkedList<>();
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    grid[i][j].setText("");
                    grid[i][j].setBackground(Color.darkGray);
                }
            }

            File f = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(f));
            targetWord = reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                loadSavedGrid(line, targetWord);
                System.out.print(usedWords.size());
                row++;
                wordTyped = new LinkedList<Character>();
            }
            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "This Game Doesn't Exist");
            e.printStackTrace();
        }

    }

    //sets the text for the Jbutton
    public void setCharacter(char c) {
        wordTyped.add(c);
        grid[getRow()][wordTyped.size() - 1].setText((c + "").toUpperCase());
    }

    //deletes the text for a JButton
    public void deleteCharacter() {

        grid[row][wordTyped.size() - 1].setText("");

        wordTyped.removeLast();
    }

    //gets the LinkedList of Characters (Used for indexing)
    public LinkedList<Character> getWordTyped() {
        LinkedList<Character> temp = new LinkedList();
        temp.addAll(wordTyped);
        return temp;
    }

    //sets the color of a Tile
    public void setColor() {
        Colors[] color = getColorMap(getWord(), targetWord);

        for (int i = 0; i < wordTyped.size(); i++) {

            grid[row][i].setBackground(color[i].color);

        }
    }

    //adds to the used words list
    public void increaseUsedWords() {
        usedWords.add(getWord());
    }

    //resets the wordTyped
    public void resetLinkedList() {
        wordTyped = new LinkedList<>();
    }

    //gets current row
    public int getRow() {
        return row;
    }

    //sets current row
    public void setRow(int x) {
        row = x;
    }

    //increments current row
    public void increment() {
        if (getWordTyped().size() == 5) {
            row++;
        }
    }

    //Enumerated Type for Colors for easier readibility
    public enum Colors {
        RIGHT(new Color(2, 107, 2)),
        WRONG(new Color(183, 183, 14)),
        INCORRECT(new Color(54, 53, 53));

        private Color color;

        Colors(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color c) {
            color = c;
        }

    }

}

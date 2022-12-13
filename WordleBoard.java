package org.cis1200.wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * Creates the GUI for Wordle Board. Creates the JFrane and all respective buttons to handle
 * the changes in game state
 */
public class WordleBoard extends JFrame {

    private static final String FILE_PATH = "files/last";
    private final JLabel statusLabel;
    private final Wordle w;
    private int row;

    public WordleBoard() {

        w = new Wordle();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                this.add(w.getGrid(i, j));
            }
        }

        w.setRow(0);

        JOptionPane.showMessageDialog(
                null, "Welcome to Wordle! \n The game is simple." +
                        " I"
                        + " an anonymous program have generated a " +
                        "random word for you to guess off of a "
                        +
                        "crazy dictionary that " + "I store. Your job is to guess that word. \n " +
                        "ow you may be thinking, how in the world can I do this?"
                        + " Well its simple. \n " +
                        "Every time you guess a word, the tiles will change colors. " +
                        "If it turns green then"
                        +
                        " you have the right letter in the right position. \n " +
                        "If it turns yellow, " +
                        "that letter is contained "
                        +
                        "somwhere in the word, but its not in the right position. " +
                        "And finally if it doesn't change then the "
                        +
                        "letter does not exist in the target word. \n Here are " +
                        "the restrictions you have: \n " +
                        "1. You have 6 "
                        +
                        "guesses and only 6 once you hit that 6th guess the game will end " +
                        "and you will have to " +
                        "start a new "
                        +
                        "one"
                        + ". \n 2. The word will not contain special characters and thus you " +
                        "are not allowed to type "
                        +
                        "them (It wont let you. \n 3. The word will be 5 letters long and thus " +
                        "you cannot submit a word less " + "than that length. \n " +
                        "4. You cannot retype a word you already typed (Why would you) \n 5." +
                        " The word you type must be an actual word. It cannot be like " +
                        "EEEEE (that won't work) " +
                        "Have Fun! \n "
                        +
                        "6. Since  I wanted the game to be harder than actual wordle, " +
                        "if you input a word with a double letter" + " and the word only has one " +
                        "occurence of that letter, it will show up as grreen AND yellow :)"
        );

        this.setTitle("Wordle");
        this.setBackground(Color.black);

        statusLabel = new JLabel();
        statusLabel.setBounds(50, 600, 300, 100);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        this.add(statusLabel);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                requestFocus();
                updateStatus();

                System.out.println("Key Pressed");

                if ((Character.isLetter(e.getKeyChar())) && (w.getWordTyped().size() < 5)) {
                    w.setCharacter(e.getKeyChar());

                }
                if ((e.getKeyChar() == KeyEvent.VK_BACK_SPACE) && (w.getWordTyped().size() >= 1)) {

                    w.deleteCharacter();

                }
                if ((e.getKeyChar() == KeyEvent.VK_ENTER) && w.validWord()) {

                    w.setColor();

                    if (w.checkWinner(w.getWord(), w.getWordToGuess())) {

                        updateStatus();

                        JOptionPane.showMessageDialog(
                                null,
                                "You have Won!, Please exit and click new game to play again"
                        );

                        setFocusable(false);

                    }

                    w.increaseUsedWords();

                    w.increment();

                    if (w.getRow() == 6) {

                        updateStatus();

                        JOptionPane.showMessageDialog(
                                null,
                                "You lost, the target was " + w.getWordToGuess()
                                        + " please exit and click play again"
                        );

                    }

                    w.resetLinkedList();

                }

            }
        });

        JButton newGame = new JButton("New Game");
        newGame.setBounds(10, 15, 150, 70);
        newGame.addActionListener(e -> resetGame());
        this.add(newGame);

        JButton loadGame = new JButton("Load Last Saved");
        loadGame.setBounds(156, 15, 150, 70);
        loadGame.addActionListener(e -> {

            loadGame();
            requestFocus();

        });
        setFocusable(true);

        this.add(loadGame);

        JButton saveGame = new JButton("Save Game");
        saveGame.setBounds(300, 15, 150, 70);
        saveGame.addActionListener(e -> {

            JOptionPane.showMessageDialog(null, "Please Exit, Your Game has been Saved");

            writeToFile();

            requestFocus();
        });

        this.add(saveGame);
        this.setSize(455, 740);
        this.setLayout(null);
        this.setVisible(true);
        this.setFocusable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void resetGame() {
        w.resetGame();
        WordleBoard wa = new WordleBoard();
        wa.setFocusable(true);
        wa.setVisible(true);
    }

    private void loadGame() {

        w.loadGame(FILE_PATH);
        requestFocus();
    }

    private void writeToFile() {
        w.writeToFile(FILE_PATH);
        requestFocus();
    }

    private void updateStatus() {

        if (!w.checkWinner(w.getWord(), w.getWordToGuess())) {

            if (w.getRow() >= 6) {
                statusLabel.setText("You Lost!");
            } else {
                statusLabel.setText(
                        "You have not won yet. You have " + (6 - (w.getRow() + 1)) + " guesses left"
                );
            }

        } else {
            statusLabel.setText("You have won");
        }
    }
}

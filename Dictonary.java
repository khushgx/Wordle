package org.cis1200.wordle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 * Dictionary for the Wordle Game. Reades in a dictionary of wordle words into a
 * hashmap (O(1) Lookup) and generates
 * the wordle word
 */

public class Dictonary {

    // File Path for the wordle words
    private final String filePath = "files/WordleWords.txt";
    // Files current line
    int line;
    private final BufferedReader reader;
    // Wordle Word
    private String wordToGuess;
    // What the file gets stored in (HashMap to reduce lookup
    private final HashMap<Integer, String> dictionaryWords = new HashMap<>();

    /**
     * constructor of the dictionary class and reads in the file to the hashmap
     */
    public Dictonary() throws IOException {
        reader = new BufferedReader(new FileReader(this.filePath));

        line = 0;

        String curr;

        while ((curr = reader.readLine()) != null) {

            dictionaryWords.put(line, curr);

            line++;
        }

    }

    /**
     * Generates the Wordle Word, Returns a String with that word
     */
    public String generateWordleWord() {

        Random rand = new Random();

        wordToGuess = dictionaryWords.get(rand.nextInt(line));

        String temp = wordToGuess;

        return temp;

    }

    /**
     * Checks of a word the user guessed is in the dictionary (The user should not
     * have access to the hashmap,
     * so the checking of the dictionary is done in this class
     */
    public boolean validWord(String word) {

        return dictionaryWords.containsValue(word);
    }

}

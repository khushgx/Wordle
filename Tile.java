package org.cis1200.wordle;

import javax.swing.*;
import java.awt.*;

/**
 * Tile for the Wordle Game. Represents one Tile in the GameBoard. Each Tile
 * stores its current row, and its current
 * column. In order to set the text for the tile, the tile extends the JButton
 * Class, allowing for the user to
 * set the text, change the text and change the colir using the methods of the
 * super class
 */

public class Tile extends JButton {

    // Current Row
    int row;

    // Current Column
    int col;

    /**
     * Constructor for tile class. Initializes private instance variables and sets
     * base conditions for JButton such as
     * opacity, bounds and font
     *
     */

    public Tile(int r, int c) {

        row = r;
        col = c;
        this.setBounds(20 + (c * 85), 110 + (r * 85), 75, 75);
        this.setFont(new Font("Helvetica", Font.BOLD, 25));
        this.setForeground(Color.white);
        this.setBackground(Color.darkGray);
        this.setOpaque(true);
        this.setBorderPainted(false);

    }

}

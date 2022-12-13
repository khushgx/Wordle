package org.cis1200.wordle;

public class RunGame implements Runnable {

    @Override
    public void run() {

        WordleBoard w = new WordleBoard();
        w.setVisible(true);
    }
}

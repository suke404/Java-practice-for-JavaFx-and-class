package ca.cmpt213.asn4.memorygame.game;

/**
 * The Card class represents a single card in the memory-matching game.
 * Each card has an image and can be either face up or face down.
 */
public class Card {
    private int id;
    private boolean isMatched;
    private boolean isRevealed;
    private int row;
    private int col;

    public Card(int id, int row, int col) {
        this.id = id;
        this.isMatched = false;
        this.isRevealed = false;
        this.row = row;
        this.col = col;
    }

    public int getId() {
        return id;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

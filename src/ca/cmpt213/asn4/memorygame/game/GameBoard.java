package ca.cmpt213.asn4.memorygame.game;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * The GameBoard class represents the game board for the memory-matching game.
 * It manages the cards and handles shuffling and checking for matches.
 */
public class GameBoard {
    private Card[][] board;
    private final int SIZE = 4;

    public GameBoard() {
        initializeBoard();
    }

    private void initializeBoard() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < SIZE * SIZE / 2; i++) {
            cards.add(new Card(i, -1, -1));
            cards.add(new Card(i, -1, -1));
        }
        Collections.shuffle(cards);

        board = new Card[SIZE][SIZE];
        int index = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Card card = cards.get(index++);
                card = new Card(card.getId(), row, col);
                board[row][col] = card;
            }
        }
    }

    public Card getCard(int row, int col) {
        return board[row][col];
    }

    public void resetBoard() {
        initializeBoard();
    }

    public int length() {
        return SIZE;
    }
}

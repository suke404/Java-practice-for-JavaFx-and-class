package ca.cmpt213.asn4.memorygame.game;

/**
 * The GameLogic class handles the game logic for the memory-matching game.
 * It manages the state of the game, including the current selections and the number of moves.
 */
public class GameLogic {
    private GameBoard gameBoard;
    private int tries;
    private int isMatched = 0;

    public GameLogic() {
        gameBoard = new GameBoard();
        tries = 0;
    }

    public boolean checkMatch(int row1, int col1, int row2, int col2) {
        Card card1 = gameBoard.getCard(row1, col1);
        Card card2 = gameBoard.getCard(row2, col2);

        if (card1.getId() == card2.getId()) {
            card1.setMatched(true);
            card2.setMatched(true);
            isMatched++;

            return true;
        }
        return false;
    }

    public void incrementTries() {
        tries++;
    }

    public int getTries() {
        return tries;
    }

    public void resetGame() {
        gameBoard.resetBoard();
        tries = 0;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public boolean isGameComplete() {
        if(isMatched == 8) {
            isMatched = 0;
            return true;
        } else {
            return false;
        }
    }

}

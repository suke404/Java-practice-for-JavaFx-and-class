package ca.cmpt213.asn4.memorygame.ui;

import ca.cmpt213.asn4.memorygame.game.GameLogic;
import ca.cmpt213.asn4.memorygame.game.Card;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The GameUI class manages the user interface for the memory-matching game.
 * It handles the layout of the game board, the moves counter, and the new game button.
 */
public class GameUI {
    private BorderPane root;
    private GameLogic gameLogic;
    private Button[][] cardButtons;
    private Image[] images;
    private Text movesText;
    private Card firstCard;
    private Card secondCard;
    private boolean checkingMatch;

    private static final String GIF_FILE_PATH = "/images/hidden.gif";
    private static final int FRAME_WIDTH = 100; // Adjust according to your GIF's frame width
    private static final int FRAME_HEIGHT = 100; // Adjust according to your GIF's frame height
    private static final int FRAME_COUNT = 10; // Number of frames in your GIF
    private static final int FRAME_DURATION = 100_000_000; // Duration between frames (in nanoseconds)

    private ImageView imageView;
    private int currentFrame = 0;
    private long lastFrameTime = 0;

    public GameUI() {
        gameLogic = new GameLogic();
        root = new BorderPane();
        cardButtons = new Button[4][4];
        images = new Image[16];
        loadImages();
        setupBoard();
        setupControls();
    }

    private void loadImages() {
        for (int i = 0; i < 8; i++) {
            images[i] = new Image(getClass().getResourceAsStream("/images/image" + i + ".jpg"));
            images[i + 8] = images[i]; // Ensure pairs of images
        }
    }

    private void setupBoard() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER); // Center the grid pane

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Button button = new Button();
                button.setMinSize(100, 100);
                button.setPrefSize(100, 100);
                ImageView hiddenView = new ImageView(new Image(getClass().getResourceAsStream("/images/hidden.gif")));
                hiddenView.setFitWidth(100);
                hiddenView.setFitHeight(100);
                hiddenView.setPreserveRatio(true);
                button.setGraphic(hiddenView);
                int finalRow = row;
                int finalCol = col;
                button.setOnAction(event -> handleCardClick(finalRow, finalCol));
                cardButtons[row][col] = button;
                gridPane.add(button, col, row);
            }
        }

        root.setCenter(gridPane);
    }

    private void setupControls() {
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10, 10, 10, 10));
        controlPanel.setAlignment(Pos.CENTER); // Center the control panel

        movesText = new Text("Moves: 0");
        movesText.setFont(new Font(18));
        Button newGameButton = new Button("New Game");
        newGameButton.setFont(new Font(18));
        newGameButton.setMaxSize(200,200);
        newGameButton.setOnAction(event -> resetGame());

        controlPanel.getChildren().addAll(movesText, newGameButton);
        root.setBottom(controlPanel);
    }


    private void handleCardClick(int row, int col) {
        if (checkingMatch) {
            return;
        }

        Card card = gameLogic.getGameBoard().getCard(row, col);
        if (card.isRevealed() || card.isMatched()) {
            return;
        }

        card.setRevealed(true);
        ImageView imageView = new ImageView(images[card.getId()]);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        cardButtons[row][col].setGraphic(imageView);
        updateBoard();

        if (firstCard == null) {
            firstCard = card;
        } else if (secondCard == null) {
            secondCard = card;
            gameLogic.incrementTries();
            checkingMatch = true;

            new Thread(() -> {
                try {
                    Thread.sleep(1000); // Pause for a moment to let the user see the second card
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {
                    if (!gameLogic.checkMatch(firstCard.getRow(), firstCard.getCol(), secondCard.getRow(), secondCard.getCol())) {
                        firstCard.setRevealed(false);
                        secondCard.setRevealed(false);
                        ImageView hiddenView1 = new ImageView(new Image(getClass().getResourceAsStream("/images/hidden.gif")));
                        hiddenView1.setFitWidth(100);
                        hiddenView1.setFitHeight(100);
                        hiddenView1.setPreserveRatio(true);
                        cardButtons[firstCard.getRow()][firstCard.getCol()].setGraphic(hiddenView1);

                        ImageView hiddenView2 = new ImageView(new Image(getClass().getResourceAsStream("/images/hidden.gif")));
                        hiddenView2.setFitWidth(100);
                        hiddenView2.setFitHeight(100);
                        hiddenView2.setPreserveRatio(true);
                        cardButtons[secondCard.getRow()][secondCard.getCol()].setGraphic(hiddenView2);
                    }

                    firstCard = null;
                    secondCard = null;
                    checkingMatch = false;

                    updateBoard();
                    checkGameCompletion();
                });
            }).start();
        }
    }

    private void updateBoard() {
        movesText.setText("Moves: " + gameLogic.getTries());
    }

    private void resetGame() {
        gameLogic.resetGame();
        firstCard = null;
        secondCard = null;
        checkingMatch = false;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                ImageView hiddenView = new ImageView(new Image(getClass().getResourceAsStream("/images/hidden.gif")));
                hiddenView.setFitWidth(100);
                hiddenView.setFitHeight(100);
                hiddenView.setPreserveRatio(true);
                cardButtons[row][col].setGraphic(hiddenView);
            }
        }
        updateBoard();
    }

    private void checkGameCompletion() {
        if (gameLogic.isGameComplete()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Congratulations!");
            alert.setHeaderText(null);
            alert.setContentText("You've matched all the pairs in " + gameLogic.getTries() + " moves!");
            alert.showAndWait();
        }
    }

    public BorderPane getRoot() {
        return root;
    }
}

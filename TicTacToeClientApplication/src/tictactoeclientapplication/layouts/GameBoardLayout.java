package tictactoeclientapplication.layouts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import static javafx.geometry.Orientation.HORIZONTAL;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tictactoeclientapplication.data.Game;
import tictactoeclientapplication.data.Move;
import tictactoeclientapplication.utils.Dialog;
import tictactoeclientapplication.utils.DialogClicks;
import tictactoeclientapplication.utils.MediumLevel;
import tictactoeclientapplication.utils.OnNavigation;
import tictactoeclientapplication.utils.ProgressIndicatorClass;
import java.util.logging.Handler;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class GameBoardLayout extends BorderPane implements DialogClicks {

    private Button[][] buttons;
    private char currentPlayer;
    private char[][] board;
    String typeOfGame;
    String data;
    Game gameData;

    String playerName1;
    String playerName2;
    String winningSymbol;

    ArrayList<Move> moves;
    String date;

    boolean hardFlag;
    boolean recordFlag;

    FlowPane flowPane;
    GridPane gridPane;
    OnNavigation onNav;
    Button backButton;
    Button recordButton;
    VBox vBox;
    Text player1;
    Text player2;

    public GameBoardLayout(OnNavigation onNav, String typeOfGame, String data) {
        this.typeOfGame = typeOfGame;
        this.onNav = onNav;
        this.data = data;
        init();
    }

    public GameBoardLayout(OnNavigation onNav, String typeOfGame, Game game) {
        this.typeOfGame = typeOfGame;
        this.onNav = onNav;
        this.gameData = game;
        init();
    }

    void init() {
        recordFlag = false;

        winningSymbol = "";
        moves = new ArrayList<>();

        playerName1 = "no-name";
        playerName2 = "no-name";

        Date time = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        date = dateFormat.format(time);

        hardFlag = true;

        buttons = new Button[3][3];
        board = new char[3][3];
        currentPlayer = 'X';

        getStyleClass().add("Pane");
        gridPane = new GridPane();
        player1 = new Text();
        player2 = new Text();
        backButton = new Button();
        recordButton = new Button();
        vBox = new VBox();
        Image img = new Image("tictactoeclientapplication/res/iconback.png");
        ImageView view = new ImageView(img);
        backButton.setGraphic(view);
        backButton.getStyleClass().addAll("RoundButton");
        backButton.setPrefSize(30, 30);
        backButton.setOnAction(e -> onNav.onNavClick("computer", null));

        recordButton.setPrefSize(200, 30);

        recordButton.setText("record");
        recordButton.getStyleClass().add("PinkButton");
        recordButton.setStyle("-fx-background-color: #F81E25;");
        recordButton.setOnAction(e -> {
            if (recordFlag) {
                recordFlag = false;
                recordButton.setText("record");
                recordButton.setStyle("-fx-background-color: #F81E25;");
                System.out.println("record");

            } else {
                recordFlag = true;
                recordButton.setText("recording...");
                recordButton.setStyle("-fx-background-color: #F8B8BA; ");
                System.out.println("recording...");

            }

        });

        player1.getStyleClass().add("PinkText");
        player2.getStyleClass().add("PinkText");
        player1.setWrappingWidth(150);
        player2.setWrappingWidth(150);
        player1.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        player2.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        flowPane = new FlowPane(HORIZONTAL, player1, player2);
        flowPane.setHgap(50);
        BorderPane.setMargin(flowPane, new Insets(50.0, 0.0, 25.0, 0.0));
        flowPane.getStyleClass().add("Pane");

        HBox hBox = new HBox();
        AnchorPane left = new AnchorPane();
        HBox.setHgrow(left, Priority.ALWAYS);
        AnchorPane right = new AnchorPane();
        hBox.getChildren().addAll(left, right);
        left.getChildren().add(backButton);
        hBox.setMargin(right, new Insets(10));
        if (!typeOfGame.equals("view")) {
            right.getChildren().add(recordButton);
        }

        vBox.getChildren().addAll(hBox, flowPane);
        gridPane.getStyleClass().add("GridPane");
        setTop(vBox);
        setCenter(gridPane);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = createButton(row, col);
                buttons[row][col] = button;
                gridPane.add(button, col, row);
            }
        }

        if (typeOfGame.equals("computer")) {
            playerName1 = "Player";
            playerName2 = "Computer";
        } else if (typeOfGame.equals("local")) {
            playerName1 = "Player 1";
            playerName2 = "Player 2";
        } else if (typeOfGame.equals("view")) {
            backButton.setOnAction(e -> onNav.onNavClick("games", null));
            playerName1 = gameData.getPlayer();
            playerName2 = gameData.getOpponent();
            Task<Void> printingTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    for (Move move : gameData.getMoves()) {
                        int row = Integer.valueOf(move.getRow());
                        int col = Integer.valueOf(move.getCol());
                        String symbol = move.getSymbol();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                buttons[row][col].setText(symbol);
                            }
                        });

                        //buttons[row][col].setText(symbol);
                        Thread.sleep(1000);
                    }
                    return null;
                }
            };

            new Thread(printingTask).start();

        } else {
            //online

        }
        player1.setText(playerName1);
        player2.setText(playerName2);
    }

    private Button createButton(int row, int col) {
        Button button = new Button();
        button.setMinWidth(100);
        button.setMinHeight(100);
        button.getStyleClass().add("PinkButton");
        if (!typeOfGame.equals("view")) {
            button.setOnAction(event -> {
                if (button.getText().isEmpty()) {
                    moves.add(new Move(String.valueOf(row), String.valueOf(col), String.valueOf(currentPlayer)));
                    //count++;
                    button.setText(String.valueOf(currentPlayer));
                    board[row][col] = currentPlayer;
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    if (!isGameOver()) {
                        if (typeOfGame.equals("computer")) {
                            //case computer
                            makeAIMove();
                        }
                        //local //handled
                        //1 vs 1 --> todo
                    } else {
                        if (isWinningMove('X')) {
                            winningSymbol = "X";

                        } else if (isWinningMove('O')) {
                            winningSymbol = "O";
                        } else {
                            winningSymbol = "draw";
                        }
                        if (recordFlag) {
                            createFileToMoves();
                            addNewFileName();
                        }

                        displayGameOverMessage();

                    }
                }
            });
        }
        return button;
    }

    void createFileToMoves() {

        System.out.println("moves length: " + moves.size());
        FileWriter fileWriter = null;
        BufferedWriter writer = null;

        FileOutputStream mouth = null;
        File file = new File(date + ".txt");
        try {

            mouth = new FileOutputStream(file);
            String moveStr = "";
            for (Move move : moves) {
                moveStr += move.getRow() + ":" + move.getCol() + ":" + move.getSymbol() + "\n";
            }
            mouth.write(moveStr.getBytes());
            System.out.println("moves:" + moveStr);
            System.out.println("success:createFileToMoves");
        } catch (IOException ex) {
            System.out.println("error:createFileToMoves");
            Logger.getLogger(GameBoardLayout.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (mouth != null) {
                try {
                    mouth.close();
                } catch (IOException ex) {
                    Logger.getLogger(GameBoardLayout.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    void addNewFileName() {

        BufferedReader reader = null;
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        BufferedWriter writer = null;
        List<String> fileContent = new ArrayList<>();
        try {
            fileReader = new FileReader("games.txt");
            reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            fileContent.add(date + ".txt" + ":" + player1.getText() + ":" + player2.getText() + ":" + winningSymbol);
            fileWriter = new FileWriter("games.txt");
            writer = new BufferedWriter(fileWriter);
            for (String filenamestr : fileContent) {
                writer.write(filenamestr);
                writer.newLine();
            }
            System.out.println("success:addNewFileName0");

        } catch (FileNotFoundException ex) {
            try {
                fileWriter = new FileWriter("games.txt");
                writer = new BufferedWriter(fileWriter);
                writer.write(date + ".txt" + ":" + player1.getText() + ":" + player2.getText() + ":" + winningSymbol);
                writer.newLine();
                System.out.println("success:addNewFileName1");

            } catch (IOException e) {
                System.out.println("IOException0");
                e.printStackTrace();
            }
        } catch (IOException ex) {
            System.out.println("IOException1");
            Logger.getLogger(GameBoardLayout.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException2");
            }
        }
    }

    private void makeAIMove() {
        int[] bestMove = findBestMove();
        int row = bestMove[0];
        int col = bestMove[1];

        //why setting O ?
        //buttons[row][col].setText("O");
        //board[row][col] = 'O';
        //currentPlayer = 'X';
        moves.add(new Move(String.valueOf(row), String.valueOf(col), String.valueOf(currentPlayer)));

        buttons[row][col].setText(String.valueOf(currentPlayer));
        board[row][col] = currentPlayer;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        if (isGameOver()) {
            if (isWinningMove('X')) {
                winningSymbol = "X";

            } else if (isWinningMove('O')) {
                winningSymbol = "O";
            } else {
                winningSymbol = "draw";
            }

            if (recordFlag) {
                createFileToMoves();
                addNewFileName();
            }
            displayGameOverMessage();
        }
    }

    private int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;

        int[] bestMove = {-1, -1};

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '\0') {
                    board[row][col] = 'O';
                    int score = minimax(board, 0, false);
                    board[row][col] = '\0';

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }

        return bestMove;
    }

    private int minimax(char[][] board, int depth, boolean isMaximizingPlayer) {
        if (isGameOver()) {
            if (data == "hard") {
                return evaluateHard(board);
            } else if (data == "easy") {
                return evaluateEasy(board);
            } else {
                if (hardFlag) {
                    hardFlag = false;
                    return evaluateHard(board);
                } else {
                    hardFlag = true;
                    return evaluateEasy(board);
                }
            }
        }

        if (isMaximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == '\0') {
                        board[row][col] = 'O';
                        int score = minimax(board, depth + 1, false);
                        board[row][col] = '\0';
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }

            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == '\0') {
                        board[row][col] = 'X';
                        int score = minimax(board, depth + 1, true);
                        board[row][col] = '\0';
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }

            return bestScore;
        }
    }

    private boolean isGameOver() {
        return isWinningMove('X') || isWinningMove('O') || isBoardFull();
    }

    private boolean isWinningMove(char player) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == player && board[row][1] == player && board[row][2] == player) {
                return true;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col] == player && board[1][col] == player && board[2][col] == player) {
                return true;
            }
        }

        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }

        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    private int evaluateHard(char[][] board) {
        if (isWinningMove('O')) {
            return 1;
        }

        if (isWinningMove('X')) {
            return -1;
        }

        return 0;
    }

    private int evaluateEasy(char[][] board) {
        if (isWinningMove('O')) {
            return -1;
        }

        if (isWinningMove('X')) {
            return 1;
        }

        return 0;
    }

    private void displayGameOverMessage() {
        //String message = "Game Over!";
        //Open Dialog
        new Dialog().displayWinDialog(this, "lose");

    }

    private void resetBoard() {
        currentPlayer = 'X';
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                board[row][col] = '\0';
            }
        }
    }

    @Override
    public void onGreenBtnCkick() {

        resetBoard();
    }

    @Override
    public void onRedBtnCkick() {
        onNav.onNavClick("home", null);
    }

    /*private void createButtons(GridPane grid) { //create buttons and handle button presses

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                Button btn = new Button("");
                btn.getStyleClass().add("PinkButton");
                btn.setPrefWidth(100);
                btn.setPrefHeight(100);
                grid.add(btn, col, row);
                gameBoardButton[row][col] = btn;
                btn.setOnAction(e -> {
                    //here
                    if (btn.getText().equals("")) {
                        if (count % 2 == 0) {
                            playerName = "X";
                        } else {
                            playerName = "O";
                        }
                        btn.setText(playerName);

                        count++;

                        if (checkRowsForWinner() || checkColumnsForWinner() || checkDiagonalRightForWinner() || checkDiagonalLeftForWinner()) {
                            System.out.println(playerName + " win");
                            endgame("win");
                        } else if (count > 8) {
                            endgame("draw");
                        }
                    }
                    //(x,y,sympol)
                });

            }
        }

    }

    private void createButtonsComputer(GridPane grid) { //create buttons and handle button presses

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                Button btn = new Button("");
                btn.getStyleClass().add("PinkButton");
                btn.setPrefWidth(100);
                btn.setPrefHeight(100);
                grid.add(btn, col, row);
                gameBoardButton[row][col] = btn;
                btn.setOnAction(e -> {
                    if (btn.getText().equals("") && count % 2 == 0) {
                        
                        btn.setText("X");

                        count++;

                        if (checkRowsForWinner() || checkColumnsForWinner() || checkDiagonalRightForWinner() || checkDiagonalLeftForWinner()) {
                            System.out.println("X" + " win");
                            endgame("win");
                        } else if (count > 8) {
                            endgame("draw");
                        } else {
                            //computer your turn
                            int x, y;
                            x = new Random().nextInt(3 + 0) + 0;
                            y = new Random().nextInt(3 + 0) + 0;
                            
                            while (!gameBoardButton[x][y].getText().equals("")) {
                                x = new Random().nextInt(3 + 0) + 0;
                                y = new Random().nextInt(3 + 0) + 0;
                            }
                            gameBoardButton[x][y].setText("O");
                            count++;
                            if (checkRowsForWinner() || checkColumnsForWinner() || checkDiagonalRightForWinner() || checkDiagonalLeftForWinner()) {
                                System.out.println("O" + " win");
                                endgame("win");
                            } else if (count > 8) {
                                endgame("draw");
                            }
                            
                        }
                    }

                });

            }
        }

    }

    private boolean checkRowsForWinner() { //go through all three rows to check for winner and end game if symbols are the same on a row
        for (int row = 0; row < 3; row++) {
            if (gameBoardButton[row][0].getText().equals(gameBoardButton[row][1].getText())
                    && gameBoardButton[row][0].getText().equals(gameBoardButton[row][2].getText())
                    && !gameBoardButton[row][0].getText().equals("")) { //to avoid registering three blank buttons in a line as a win 
                //endgame();
                return true;
            }
        }
        return false;

    }

    private boolean checkColumnsForWinner() {
        for (int col = 0; col < 3; col++) {
            if (gameBoardButton[0][col].getText().equals(gameBoardButton[1][col].getText())
                    && gameBoardButton[0][col].getText().equals(gameBoardButton[2][col].getText())
                    && !gameBoardButton[0][col].getText().equals("")) {
                //endgame();
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalLeftForWinner() {
        if (gameBoardButton[0][0].getText().equals(gameBoardButton[1][1].getText())
                && gameBoardButton[0][0].getText().equals(gameBoardButton[2][2].getText())
                & !gameBoardButton[0][0].getText().equals("")) {
            //endgame();
            return true;
        }

        return false;
    }

    private boolean checkDiagonalRightForWinner() {
        if (gameBoardButton[0][2].getText().equals(gameBoardButton[1][1].getText())
                && gameBoardButton[0][2].getText().equals(gameBoardButton[2][0].getText())
                && !gameBoardButton[0][2].getText().equals("")) { //to avoid registering three blank buttons in a line as a win
            //endgame();
            return true;
        }
        return false;
    }

    private void endgame(String status) {
        //check
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameBoardButton[row][col].setDisable(true);
            }
        }

        //some editing is needed here
         new Dialog().displayWinDialog(this, "lose");
        //new Dialog().displayTextDialog(this,"Do u want to play again");
        //new ProgressIndicatorClass().showProgressDialog(true);
    }*/
}

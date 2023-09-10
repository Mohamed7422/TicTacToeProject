package tictactoeclientapplication.layouts;

import java.util.Random;
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
import tictactoeclientapplication.utils.Dialog;
import tictactoeclientapplication.utils.DialogClicks;
import tictactoeclientapplication.utils.OnNavigation;
import tictactoeclientapplication.utils.ProgressIndicatorClass;

public class GameBoardLayout extends BorderPane implements DialogClicks {

    final private Button[][] gameBoardButton;
    private String playerName;
    private int count;
    FlowPane flowPane;
    GridPane gridPane;
    OnNavigation onNav;
    Button backButton;
    VBox vBox;

    public GameBoardLayout(OnNavigation onNav, String typeOfGame) {
        this.onNav = onNav;
        gameBoardButton = new Button[3][3];
        playerName = "X";
        count = 0;
        getStyleClass().add("Pane");
        gridPane = new GridPane();
        Text player1 = new Text("test 1");
        Text player2 = new Text("test 2");
        backButton = new Button();
        vBox = new VBox();
        Image img = new Image("tictactoeclientapplication/res/iconback.png");
        ImageView view = new ImageView(img);
        backButton.setGraphic(view);
        backButton.getStyleClass().addAll("RoundButton");
        backButton.setPrefSize(30, 30);
        backButton.setOnAction(e -> onNav.onNavClick("home"));
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
        vBox.getChildren().addAll(backButton, flowPane);
        gridPane.getStyleClass().add("GridPane");
        setTop(vBox);
        setCenter(gridPane);

        if (typeOfGame.equals("computer")) {
            player1.setText("Player");
            player2.setText("computer");
            //needed to be changed with min-max algorithm
            createButtonsComputer(gridPane);

        } else if (typeOfGame.equals("local")) {
            player1.setText("Player 1");
            player2.setText("Player 2");
            createButtons(gridPane);

        } else {
            //online

        }

        //setLeft(player1);
        //setRight(player2);
        //setTop(flowPane);
    }

    private void createButtons(GridPane grid) { //create buttons and handle button presses

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                Button btn = new Button("");
                btn.getStyleClass().add("PinkButton");
                btn.setPrefWidth(100);
                btn.setPrefHeight(100);
                grid.add(btn, col, row);
                gameBoardButton[row][col] = btn;
                btn.setOnAction(e -> {
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

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameBoardButton[row][col].setDisable(true);
            }
        }

        //some editing is needed here
         new Dialog().displayWinDialog(this, "lose");
        //new Dialog().displayTextDialog(this, "lose","Loser");
        //new ProgressIndicatorClass().showProgressDialog(true);
    }

    @Override
    public void onGreenBtnCkick() {

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                gameBoardButton[col][row].setText("");
                gameBoardButton[col][row].setDisable(false);
                count = 0;
            }
        }
    }

    @Override
    public void onRedBtnCkick() {
        onNav.onNavClick("home");
    }

}

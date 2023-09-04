package tictactoeclientapplication;

import com.sun.deploy.ui.CacheUpdateProgressDialog;
import java.time.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import javafx.application.Application;
import javafx.geometry.Insets;
import static javafx.geometry.Orientation.HORIZONTAL;
import static javafx.geometry.Orientation.VERTICAL;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Hanaa Hany
 */
public class GameBoardLayout extends BorderPane {

    private Button[][] gameBoardButton; //array representation of the game board
    private String playerName; //player X or O
    private int count; //counter for the number of turns player X has taken 
    //private int playerOCount; //counter for the number of turns player O has taken
    Circle recordCircle;

    public GameBoardLayout() {
        gameBoardButton = new Button[3][3];
        playerName = "X";
        count = 0;
        // playerOCount = 0;
        recordCircle = new Circle();

        GridPane gridPane = new GridPane();
        Text player1 = new Text("Hanaa");
        Text player2 = new Text("Mohammed");
        float sceneHeight = 230f;
        float sceneWidth = 230f;
        recordCircle.setRadius(15.0f);
        recordCircle.setFill(Paint.valueOf("#F33F3F"));
        FlowPane flowPane = new FlowPane(HORIZONTAL, player1, player2, recordCircle);
        flowPane.setId("pane");
        //BorderPane borderPane = new BorderPane();
        gridPane.setId("gridPane");
        createButtons(gridPane);
        //RecordCircle Action

        recordCircle.setOnMouseClicked(e -> {

            Alert alert = new Alert(AlertType.INFORMATION);
            String s = "Record done!";
            alert.setContentText(s);
            alert.show();

        });

        setTop(flowPane);
        setCenter(gridPane);

    }

    /*@Override
    public void start(Stage primaryStage) throws Exception {

        


    }

    public static void main(String[] args) {
        launch(args);
    }*/
    public void createButtons(GridPane grid) { //create buttons and handle button presses

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                Button btn = new Button("");
                btn.setId("buttonXO");
                btn.setPrefWidth(100);
                grid.add(btn, col, row);
                gameBoardButton[row][col] = btn;
                btn.setOnAction(e -> {
                    if (btn.getText() == "") {
                        if (count % 2 == 0) {
                            playerName = "X";
                            btn.setStyle("-fx-text-fill: #1A2DA8; ");
                        } else {
                            playerName = "O";
                            btn.setStyle("-fx-text-fill: #fff; ");
                        }
                        btn.setText(playerName);

                        count++;

                        if (checkRowsForWinner() || checkColumnsForWinner() || checkDiagonalRightForWinner() || checkDiagonalLeftForWinner()) {
                            System.out.println(playerName + " win");
                        } else if (count > 8) {
                            endgame();
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
                endgame();
            }
        }
        return false;

    }

    private boolean checkColumnsForWinner() {
        for (int col = 0; col < 3; col++) {
            if (gameBoardButton[0][col].getText().equals(gameBoardButton[1][col].getText())
                    && gameBoardButton[0][col].getText().equals(gameBoardButton[2][col].getText())
                    && !gameBoardButton[0][col].getText().equals("")) {
                endgame();
            }
        }
        return false;
    }

    private boolean checkDiagonalLeftForWinner() {
        if (gameBoardButton[0][0].getText().equals(gameBoardButton[1][1].getText())
                && gameBoardButton[0][0].getText().equals(gameBoardButton[2][2].getText())
                & !gameBoardButton[0][0].getText().equals("")) {
            endgame();
        }

        return false;
    }

    private boolean checkDiagonalRightForWinner() {
        if (gameBoardButton[0][2].getText().equals(gameBoardButton[1][1].getText())
                && gameBoardButton[0][2].getText().equals(gameBoardButton[2][0].getText())
                && !gameBoardButton[0][2].getText().equals("")) { //to avoid registering three blank buttons in a line as a win
            endgame();
        }
        return false;
    }

    private void endgame() {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameBoardButton[row][col].setDisable(true); //disable buttons

            }
        }
        display("Do u Want to play Again");

    }

    public void display(String text) {

        Stage stage = new Stage();
        //used to ensure that you can only interact with one window at a time.
        //With the APPLICATION.MODAL setting, once the dialog appears,
        //you will not be able to interact with any other window till the dialog is closed.
        stage.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label(text);

        label.prefWidthProperty().bind(stage.widthProperty());
        label.setAlignment(Pos.CENTER);
        Button btn = new Button("Play Again");
        Button btn1 = new Button("Leave");

        btn.setOnAction(e -> {
            for (int col = 0; col < 3; col++) {
                for (int row = 0; row < 3; row++) {
                    gameBoardButton[col][row].setText("");
                    gameBoardButton[col][row].setDisable(false);
                    count = 0;
                }
            }
            stage.close();
        });
        FlowPane layout = new FlowPane(HORIZONTAL, label, btn, btn1);
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 100);

        stage.setTitle("Dialog");
        stage.setScene(scene);
        stage.show();
    }

}

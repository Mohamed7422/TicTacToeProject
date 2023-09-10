package tictactoeclientapplication.utils;

import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Dialog {

    boolean homeFlag = true;

    public void displayWinDialog(DialogClicks onClick, String status) {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        AnchorPane pane = new AnchorPane();

        File file = null;
        if (status.equals("win")) {

        } else if (status.equals("lose")) {
            file = new File("lose.mp4");
        } else if (status.equals("computer-win")) {
            //comuter dance

        } else if (status.equals("local")) {
            //half-&-half

        } else {
            //draw

        }

        Media media = new Media(file.toURI().toString());
        MediaPlayer mp = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mp);
        mp.setAutoPlay(true);

        stage.setOnHidden(e -> {
            if (homeFlag) {
                mp.stop();
                onClick.onRedBtnCkick();
            }
        });

        HBox hBox = new HBox();
        Button btnGreen = new Button();
        Button btnRed = new Button();

        pane.setMaxHeight(USE_PREF_SIZE);
        pane.setMaxWidth(USE_PREF_SIZE);
        pane.setMinHeight(USE_PREF_SIZE);
        pane.setMinWidth(USE_PREF_SIZE);
        pane.setPrefHeight(300.0);
        pane.setPrefWidth(500.0);

        mediaView.setFitHeight(250.0);
        mediaView.setFitWidth(500.0);

        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.setLayoutY(250.0);
        hBox.setPrefHeight(50.0);
        hBox.setPrefWidth(430.0);
        hBox.setSpacing(100.0);

        btnGreen.setMnemonicParsing(false);
        btnGreen.setText("Play Again");
        btnGreen.setPrefWidth(150);
        btnGreen.getStyleClass().add("GreenButton");
        HBox.setMargin(btnGreen, new Insets(0.0));

        btnRed.setMnemonicParsing(false);
        btnRed.setText("Leave");
        btnRed.setPrefWidth(150);
        btnRed.getStyleClass().add("PurpleButton");

        btnGreen.setOnAction(e -> {
            homeFlag = false;
            mp.stop();
            onClick.onGreenBtnCkick();
            stage.close();
        });

        btnRed.setOnAction(e -> {
            mp.stop();
            onClick.onRedBtnCkick();
            stage.close();
        });

        pane.getChildren().add(mediaView);
        hBox.getChildren().add(btnGreen);
        hBox.getChildren().add(btnRed);
        pane.getChildren().add(hBox);

        Scene scene = new Scene(pane, 430, 300);

        scene.getStylesheets().add(getClass().getResource("Style.css").toString());

        stage.setTitle("Dialog");
        stage.setScene(scene);
        stage.show();

    }



    public void displayTextDialog(DialogClicks onClick, String status,String msg) {
        Stage stage = new Stage();
        
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        AnchorPane pane = new AnchorPane();
        Label txt = new Label(msg);
        txt.setId("dialogWithText");
        txt.setText(msg);
        txt.setAlignment(Pos.CENTER);
        
        
        if (status.equals("win")) {

        } else if (status.equals("lose")) {
           
        }

        

        stage.setOnHidden(e -> {
            if (homeFlag) {
                
                onClick.onRedBtnCkick();
            }
        });

        HBox hBox = new HBox();
        Button btnGreen = new Button();
        Button btnRed = new Button();

        pane.setMaxHeight(USE_PREF_SIZE);
        pane.setMaxWidth(USE_PREF_SIZE);
        pane.setMinHeight(USE_PREF_SIZE);
        pane.setMinWidth(USE_PREF_SIZE);
        pane.setPrefHeight(200.0);
        pane.setPrefWidth(500.0);

     

        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.setLayoutY(100.0);
        hBox.setPrefHeight(50.0);
        hBox.setPrefWidth(430.0);
       hBox.setSpacing(100.0);

        btnGreen.setMnemonicParsing(false);
        btnGreen.setText("Play Again");
        btnGreen.setPrefWidth(150);
        btnGreen.getStyleClass().add("GreenButton");
        HBox.setMargin(btnGreen, new Insets(0.0));

        btnRed.setMnemonicParsing(false);
        btnRed.setText("Leave");
        btnRed.setPrefWidth(150);
        btnRed.getStyleClass().add("PurpleButton");

        btnGreen.setOnAction(e -> {
            homeFlag = false;
           
            onClick.onGreenBtnCkick();
            stage.close();
        });

        btnRed.setOnAction(e -> {
         
            onClick.onRedBtnCkick();
            stage.close();
        });

        pane.getChildren().add(txt);
        hBox.getChildren().add(btnGreen);
        hBox.getChildren().add(btnRed);
        pane.getChildren().add(hBox);

        Scene scene = new Scene(pane, 430, 200);

        scene.getStylesheets().add(getClass().getResource("Style.css").toString());

        stage.setTitle("Dialog");
        stage.setScene(scene);
        stage.show();

    }
}

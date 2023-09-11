package tictactoeclientapplication.layouts;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import tictactoeclientapplication.network.ClientSocket;
import tictactoeclientapplication.utils.OnNavigation;

public class LevelsLayout extends BorderPane {

    Button btnEasy;
    Button btnMed;
    Button btnHard;
    Button backButton;

    public LevelsLayout(OnNavigation onNav) {
        FlowPane pane = new FlowPane();
        pane.setVgap(20);

        btnEasy = new Button("Easy");
        btnMed = new Button("Meduem");
        btnHard = new Button("Hard");
        backButton = new Button();

        btnEasy.getStyleClass().add("PinkButton");
        btnEasy.setPrefHeight(50.0);
        btnEasy.setPrefWidth(300.0);

        btnMed.getStyleClass().add("PurpleButton");
        btnMed.setPrefHeight(50.0);
        btnMed.setPrefWidth(300.0);

        btnHard.getStyleClass().add("GreenButton");
        btnHard.setPrefHeight(50.0);
        btnHard.setPrefWidth(300.0);

        btnEasy.getStyleClass().add("computerButton");

        btnMed.getStyleClass().add("localButton");

        btnHard.getStyleClass().add("onlineButton");

        // Create an ImageView for the back button
        Image img = new Image("tictactoeclientapplication/res/iconback.png");
        ImageView view = new ImageView(img);
        backButton.setGraphic(view);

        // Style the back button as a rounded button
        backButton.getStyleClass().addAll("RoundButton");
        backButton.setPrefSize(30, 30);

        btnEasy.setOnAction(e -> onNav.onNavClick("board"));
        //btnMed.setOnAction(e -> onNav.onNavClick("board"));
        btnMed.setOnAction(e -> {
            ClientSocket.getInstance().say("test:test", (msg)->{});
        
        });
        btnHard.setOnAction(e -> onNav.onNavClick("board"));
        backButton.setOnAction(e -> onNav.onNavClick("home"));

        pane.getChildren().addAll(btnEasy, btnMed, btnHard);

        pane.setOrientation(Orientation.VERTICAL);
        pane.getStyleClass().add("Pane");

        setCenter(pane);
        setTop(backButton);
        getStyleClass().add("Pane");

    }
}

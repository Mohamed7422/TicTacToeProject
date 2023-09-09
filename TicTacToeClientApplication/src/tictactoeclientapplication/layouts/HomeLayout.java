package tictactoeclientapplication.layouts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import tictactoeclientapplication.utils.OnNavigation;

public class HomeLayout extends BorderPane {

    Button computerButton;
    Button localButton;
    Button onlineButton;
    Button backButton;

    public HomeLayout(OnNavigation onNav) {

        FlowPane pane = new FlowPane();
        pane.setVgap(20);
        computerButton = new Button("Computer");
        localButton = new Button("Local");
        onlineButton = new Button("Online");
        backButton = new Button();

        computerButton.getStyleClass().add("PinkButton");
        computerButton.setPrefHeight(50.0);
        computerButton.setPrefWidth(300.0);

        localButton.getStyleClass().add("PurpleButton");
        localButton.setPrefHeight(50.0);
        localButton.setPrefWidth(300.0);

        onlineButton.getStyleClass().add("GreenButton");
        onlineButton.setPrefHeight(50.0);
        onlineButton.setPrefWidth(300.0);

        Image img = new Image("tictactoeclientapplication/res/iconback.png");
        ImageView view = new ImageView(img);
        backButton.setGraphic(view);
        backButton.getStyleClass().addAll("RoundButton");
        backButton.setPrefSize(30, 30);

        computerButton.getStyleClass().add("computerButton");

        localButton.getStyleClass().add("localButton");

        onlineButton.getStyleClass().add("onlineButton");

        computerButton.setOnAction(e -> onNav.onNavClick("computer"));
        localButton.setOnAction(e -> onNav.onNavClick("local"));
        onlineButton.setOnAction(e -> onNav.onNavClick("online"));
        backButton.setOnAction(e -> onNav.onNavClick("login"));

        pane.getChildren().addAll(computerButton, localButton, onlineButton);
        
        setCenter(pane);
        setTop(backButton);
        BorderPane.setAlignment(pane, Pos.CENTER);
        pane.setOrientation(Orientation.VERTICAL);
        pane.getStyleClass().add("Pane");
        getStyleClass().add("Pane");

    }
}

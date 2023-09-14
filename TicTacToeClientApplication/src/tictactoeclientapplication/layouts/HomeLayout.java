package tictactoeclientapplication.layouts;

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
    Button gamesButton;
    Button backButton;

    public HomeLayout(OnNavigation onNav) {
        FlowPane pane = new FlowPane();
        pane.setVgap(20);
        computerButton = new Button("Computer");
        localButton = new Button("Local");
        onlineButton = new Button("Online");
        gamesButton = new Button("My Games");

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

        gamesButton.getStyleClass().add("PinkButton");
        gamesButton.setPrefHeight(50.0);
        gamesButton.setPrefWidth(300.0);

        Image img = new Image("tictactoeclientapplication/res/iconback.png");
        ImageView view = new ImageView(img);
        backButton.setGraphic(view);
        backButton.getStyleClass().addAll("RoundButton");
        backButton.setPrefSize(30, 30);
        
        /*computerButton.getStyleClass().add("computerButton");
        localButton.getStyleClass().add("localButton");
        onlineButton.getStyleClass().add("onlineButton");
        gamesButton.getStyleClass().add("computerButton");*/

        computerButton.setOnAction(e -> onNav.onNavClick("computer",null));
        localButton.setOnAction(e -> onNav.onNavClick("local",null));
        onlineButton.setOnAction(e -> onNav.onNavClick("online",null));
        backButton.setOnAction(e -> onNav.onNavClick("login",null));
        gamesButton.setOnAction(e -> onNav.onNavClick("games",null));
        
        pane.getChildren().addAll(computerButton, localButton, onlineButton,gamesButton);
        setCenter(pane);
        setTop(backButton);
        BorderPane.setAlignment(pane, Pos.CENTER);
        pane.setOrientation(Orientation.VERTICAL);
        pane.getStyleClass().add("Pane");
        getStyleClass().add("Pane");
    }
}

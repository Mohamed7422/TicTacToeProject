package tictactoeclientapplication;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class HomeLayout extends FlowPane {

    Button computerButton;
    Button localButton;
    Button onlineButton;

    HomeLayout(OnNavigation onNav) {
        setVgap(20);
        computerButton = new Button("Computer");
        localButton = new Button("Local");
        onlineButton = new Button("Online");

        computerButton.getStyleClass().add("PinkButton");
        computerButton.setPrefHeight(50.0);
        computerButton.setPrefWidth(300.0);

        localButton.getStyleClass().add("PurpleButton");
        localButton.setPrefHeight(50.0);
        localButton.setPrefWidth(300.0);

        onlineButton.getStyleClass().add("GreenButton");
        onlineButton.setPrefHeight(50.0);
        onlineButton.setPrefWidth(300.0);

        computerButton.getStyleClass().add("computerButton");

        localButton.getStyleClass().add("localButton");

        onlineButton.getStyleClass().add("onlineButton");

        computerButton.setOnAction(e -> onNav.onNavClick("computer"));
        localButton.setOnAction(e -> onNav.onNavClick("local"));
        onlineButton.setOnAction(e -> onNav.onNavClick("online"));

        getChildren().addAll(computerButton, localButton, onlineButton);

        setOrientation(Orientation.VERTICAL);
        getStyleClass().add("Pane");

    }
}

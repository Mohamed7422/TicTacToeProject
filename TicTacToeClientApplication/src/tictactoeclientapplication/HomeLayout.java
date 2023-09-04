package tictactoeclientapplication;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class HomeLayout extends FlowPane {

    Button computerButton;
    Button localButton;
    Button onlineButton;

    HomeLayout(OnNavigation onNav) {
        computerButton = new Button("Computer");
        localButton = new Button("Local");
        onlineButton = new Button("Online");

        computerButton.setId("computerButton");
        computerButton.setPrefWidth(400);
        computerButton.setPrefHeight(50);

        localButton.setId("localButton");
        localButton.setPrefWidth(400);
        localButton.setPrefHeight(50);

        onlineButton.setId("onlineButton");
        onlineButton.setPrefWidth(400);
        onlineButton.setPrefHeight(50);

        computerButton.setOnAction(e -> onNav.onNavClick("computer"));
        localButton.setOnAction(e -> onNav.onNavClick("local"));
        onlineButton.setOnAction(e -> onNav.onNavClick("online"));

        getChildren().addAll(computerButton, localButton, onlineButton);

        setOrientation(Orientation.VERTICAL);
        setId("pane");

    }
}

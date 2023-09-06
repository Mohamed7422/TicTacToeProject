package tictactoeclientapplication.layouts;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import tictactoeclientapplication.utils.OnNavigation;

public class LevelsLayout extends FlowPane {

    Button btnEasy;
    Button btnMed;
    Button btnHard;

    public LevelsLayout(OnNavigation onNav) {
        setVgap(20);

        btnEasy = new Button("Easy");
        btnMed = new Button("Meduem");
        btnHard = new Button("Hard");

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

        btnEasy.setOnAction(e -> onNav.onNavClick("board"));
        btnMed.setOnAction(e -> onNav.onNavClick("board"));
        btnHard.setOnAction(e -> onNav.onNavClick("board"));

        getChildren().addAll(btnEasy, btnMed, btnHard);

        setOrientation(Orientation.VERTICAL);
        getStyleClass().add("Pane");

    }
}

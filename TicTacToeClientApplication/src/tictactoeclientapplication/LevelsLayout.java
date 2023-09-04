package tictactoeclientapplication;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class LevelsLayout extends FlowPane {

    Button btnEasy;
    Button btnMed;
    Button btnHard;

    LevelsLayout(OnNavigation onNav) {
        btnEasy = new Button("Easy");
        btnMed = new Button("Meduem");
        btnHard = new Button("Hard");

        btnEasy.setId("computerButton");
        btnEasy.setPrefWidth(400);
        btnEasy.setPrefHeight(50);

        btnMed.setId("localButton");
        btnMed.setPrefWidth(400);
        btnHard.setPrefHeight(50);

        btnHard.setId("onlineButton");
        btnHard.setPrefWidth(400);
        btnHard.setPrefHeight(50);

        btnEasy.setOnAction(e -> {onNav.onNavClick("easy");});
        btnMed.setOnAction(e -> {onNav.onNavClick("meduem");});
        btnHard.setOnAction(e -> {onNav.onNavClick("hard");});

        getChildren().addAll(btnEasy, btnMed, btnHard);

        setOrientation(Orientation.VERTICAL);
        setId("pane");

    }
}

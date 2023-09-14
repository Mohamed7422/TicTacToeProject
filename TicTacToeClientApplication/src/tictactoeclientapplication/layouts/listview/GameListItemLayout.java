package tictactoeclientapplication.layouts.listview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import tictactoeclientapplication.utils.OnNavigation;
import tictactoeclientapplication.data.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tictactoeclientapplication.data.Game;
import tictactoeclientapplication.layouts.HomeLayout;
import tictactoeclientapplication.layouts.ListViewLayout;
import tictactoeclientapplication.layouts.LoginLayout;
import tictactoeclientapplication.network.ClientSocket;
import tictactoeclientapplication.utils.Dialog;
import tictactoeclientapplication.utils.DialogClicks;

public class GameListItemLayout extends HBox {

    protected final Text playerName;
    protected final Text statue;
    protected final Button challangeBtn;
    Thread th;

    public GameListItemLayout(Game game, OnNavigation onNav) {

        playerName = new Text();
        statue = new Text();
        challangeBtn = new Button();

        setPrefHeight(48.0);
        setPrefWidth(300.0);
        getStyleClass().add("ListItem");

        playerName.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        playerName.setStrokeWidth(0.0);
        playerName.setText("Mohamed");
        playerName.setWrappingWidth(300);
        playerName.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        playerName.getStyleClass().add("WhiteText");

        statue.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        statue.setStrokeWidth(0.0);
        statue.setText("Text");
        statue.setWrappingWidth(170);
        statue.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        setAlignment(Pos.CENTER);

        challangeBtn.setAlignment(javafx.geometry.Pos.CENTER);
        challangeBtn.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        challangeBtn.setMnemonicParsing(false);
        challangeBtn.setText("Play");
        challangeBtn.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        challangeBtn.getStyleClass().add("YellowButton");
        challangeBtn.setOnAction(e -> {
            onNav.onNavClick("view-game",game);
            System.out.println("play the game in view mode");
            
        });

        getChildren().add(playerName);
        getChildren().add(statue);
        getChildren().add(challangeBtn);
        playerName.setText(game.getPlayer() + " vs " + game.getOpponent());
        statue.getStyleClass().add("IngameText");
        statue.setText(game.getDate());

    }

}

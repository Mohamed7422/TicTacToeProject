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

public class ListItemLayout extends HBox {

    protected final Text playerName;
    protected final Text statue;
    protected final Button challangeBtn;
    Thread th;
    OnNavigation onNav;

    public ListItemLayout(Player player, OnNavigation onNav) {
        this.onNav = onNav;
        playerName = new Text();
        statue = new Text();
        challangeBtn = new Button();

        setPrefHeight(48.0);
        setPrefWidth(300.0);
        getStyleClass().add("ListItem");

        playerName.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        playerName.setStrokeWidth(0.0);
        playerName.setText("Mohamed");
        playerName.setWrappingWidth(150);
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
        challangeBtn.setText("CHALLANGE");
        challangeBtn.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        challangeBtn.getStyleClass().add("YellowButton");
        challangeBtn.setOnAction(e -> {
            if (!ClientSocket.getInstance().isConnected()) {
                try {
                    ClientSocket.getInstance().openConnection();
                    System.out.println("ListItemLayout: connected");
                } catch (IOException ex) {
                    System.out.println("ListItemLayout: can't connect");
                }
            }
            if (ClientSocket.getInstance().isConnected()) {
                th = new Thread(() -> {
                    ClientSocket.getInstance().say("challenge:" + player.getName(), (msg) -> {
                        System.out.println("ListItemLayout: general listening -> " + msg);
                        String[] split = msg.split(":");
                        if (split[0].trim().equals("get-players-success") || split[0].trim().equals("get-players-fail")) {
                            ListViewLayout.updateList(msg);
                        } else if (split[0].trim().equals("invite")) {//invite:ali
                            System.out.println("ListItemLayout: invitation");
                            showDialog(split[1].trim());
                        } else if (split[0].trim().equals("accepted-invite")) {
                            System.out.println("ListItemLayout: acceptedinvitation");
                            onNav.onNavClick("online-game", new Game(split[1]+":O"));
                        }
                    });
                });
                th.start();
            }
        });

        getChildren().add(playerName);
        getChildren().add(statue);
        getChildren().add(challangeBtn);

        playerName.setText(player.getName());
        if (player.getStatus().equals("online")) {
            statue.getStyleClass().add("OnlineText");
        } else if (player.getStatus().equals("offline")) {
            statue.getStyleClass().add("OfflineText");
        } else {
            statue.getStyleClass().add("IngameText");
        }
        statue.setText(player.getStatus());

    }

    void showDialog(String name) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                new Dialog().displayTextDialog(new DialogClicks() {
                    @Override
                    public void onGreenBtnCkick() {
                        ClientSocket.getInstance().say("accept-challenge:" + name);
                        onNav.onNavClick("online-game", new Game(name+":X"));
                        System.out.println("accept");
                    }

                    @Override
                    public void onRedBtnCkick() {
                        System.out.println("decline");
                    }

                }, name + " is inviting you.", "accept", "decline");
            }
        });
    }
}

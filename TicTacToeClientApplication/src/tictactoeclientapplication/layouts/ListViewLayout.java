package tictactoeclientapplication.layouts;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tictactoeclientapplication.utils.OnNavigation;
import tictactoeclientapplication.data.Player;
import tictactoeclientapplication.layouts.listview.PlayerCellFactory;
import tictactoeclientapplication.network.ClientSocket;
import tictactoeclientapplication.utils.Dialog;
import tictactoeclientapplication.utils.DialogClicks;

public class ListViewLayout extends BorderPane {

    static ListView listView;
    static ObservableList<Player> playerList;
    Button backButton;
    Button logoutButton;
    Thread th;

    public ListViewLayout(OnNavigation onNav) {
        playerList = FXCollections.observableArrayList();
        listView = new ListView<>(playerList);
        if (!ClientSocket.getInstance().isConnected()) {
            try {
                ClientSocket.getInstance().openConnection();
                System.out.println("ListViewLayout: connected");
            } catch (IOException ex) {
                System.out.println("ListViewLayout: can't connect");
            }
        }
        if (ClientSocket.getInstance().isConnected()) {

            //i gonna delete it/////////////////////////////////////////////////
            th = new Thread(() -> {
                ClientSocket.getInstance().say("get-players", (msg) -> {
                    System.out.println("ListViewLayout: general listening -> " + msg);
                    String[] split = msg.split(":");
                    if (split[0].trim().equals("get-players-success")) {
                        System.out.println("ListViewLayout: " + msg);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                playerList.clear();
                                for (int i = 1; i < split.length; i++) {
                                    if (!split[i].trim().equals(ClientSocket.getUsername())) {
                                        playerList.add(new Player(split[i], 50, "online"));
                                    }
                                }
                            }
                        });
                    } else if (split[0].equals("get-players-fail")) {
                        System.out.println("ListViewLayout: no players found");
                    } else if (split[0].trim().equals("invite")) {
                        System.out.println("ListViewLayout: invitation");
                        showDialog(split[1].trim());

                    }
                });
            });
            th.start();
            //i gonna delete it/////////////////////////////////////////////////

            /*ClientSocket.getInstance().say("get-players", (msg) -> {//get-players-success:ahmed
                //System.out.println("ListViewLayout: general listening -> "+msg);
                String[] split = msg.split(":");
                if (split[0].trim().equals("get-players-success")) {
                    System.out.println("ListViewLayout: " + msg);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            playerList.clear();
                            for (int i = 1; i < split.length; i++) {
                                playerList.add(new Player(split[i], 50, "online"));
                            }
                        }
                    });
                } else if (split[0].equals("get-players-fail")) {
                    System.out.println("ListViewLayout: no players found");
                }
            });*/
        }
        listView.setFocusTraversable(false);
        listView.setCellFactory(new PlayerCellFactory(onNav));
        listView.setDisable(false);
        BorderPane.setAlignment(listView, javafx.geometry.Pos.CENTER);
        listView.setPrefHeight(200.0);
        listView.setPrefWidth(200.0);
        setCenter(listView);
        backButton = new Button();
        logoutButton = new Button();
        Image img = new Image("tictactoeclientapplication/res/iconback.png");
        ImageView view = new ImageView(img);
        backButton.setGraphic(view);
        backButton.setOnAction(e -> {
            //th.stop();
            onNav.onNavClick("home",null);
        });
        backButton.getStyleClass().addAll("RoundButton");
        backButton.setPrefSize(30, 30);
        Image img1 = new Image("tictactoeclientapplication/res/iconlogout.png");
        ImageView view1 = new ImageView(img1);
        logoutButton.setGraphic(view1);
        logoutButton.setOnAction(e -> {

            FileInputStream ear = null;
            FileOutputStream mouth = null;
            try {
                File file = new File("auth.txt");
                ClientSocket.getInstance().say("logout");
                mouth = new FileOutputStream(file);
                mouth.write(new String("logedout").getBytes());
                onNav.onNavClick("login",null);
            } catch (FileNotFoundException ex) {
                System.out.println("ListViewLayout: file not found");
                Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("ListViewLayout: IOException");
            } finally {
                try {
                    //th.stop();
                    mouth.close();
                } catch (IOException ex) {
                    System.out.println("ListViewLayout: IOException");
                }
            }
        });
        logoutButton.getStyleClass().addAll("RoundButton");
        logoutButton.setPrefSize(30, 30);
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        HBox hBox = new HBox();
        AnchorPane left = new AnchorPane();
        HBox.setHgrow(left, Priority.ALWAYS);
        AnchorPane right = new AnchorPane();
        hBox.getChildren().addAll(left, right);
        left.getChildren().add(backButton);
        right.getChildren().add(logoutButton);
        setTop(hBox);
    }

    public static void updateList(String msg) {
        String[] split = msg.split(":");
        if (split[0].trim().equals("get-players-success")) {
            System.out.println("ListViewLayout(updateList): " + msg);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    playerList.clear();
                    for (int i = 1; i < split.length; i++) {
                        if (!split[i].trim().equals(ClientSocket.getUsername())) {
                            playerList.add(new Player(split[i], 50, "online"));
                        }
                    }
                }
            });
        } else if (split[0].equals("get-players-fail")) {
            System.out.println("ListViewLayout: no players found");
        }
    }
    
    void showDialog(String name) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                new Dialog().displayTextDialog(new DialogClicks() {
                    @Override
                    public void onGreenBtnCkick() {
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

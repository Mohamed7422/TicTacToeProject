package tictactoeclientapplication.layouts;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
import tictactoeclientapplication.data.Game;
import tictactoeclientapplication.data.Move;
import tictactoeclientapplication.utils.OnNavigation;
import tictactoeclientapplication.data.Player;
import static tictactoeclientapplication.layouts.ListViewLayout.playerList;
import tictactoeclientapplication.layouts.listview.GameCellFactory;
import tictactoeclientapplication.layouts.listview.PlayerCellFactory;
import tictactoeclientapplication.network.ClientSocket;
import tictactoeclientapplication.utils.Dialog;
import tictactoeclientapplication.utils.DialogClicks;

public class GamesListViewLayout extends BorderPane {

    static ListView listView;
    static ObservableList<Game> gameList;
    Button backButton;
    Button logoutButton;
    Thread th;

    public GamesListViewLayout(OnNavigation onNav) {
        gameList = FXCollections.observableArrayList();
        listView = new ListView<>(gameList);

        //call function to get list of games
        getGamesList();

        listView.setFocusTraversable(false);
        listView.setCellFactory(new GameCellFactory(onNav));
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
            onNav.onNavClick("home", null);
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
                onNav.onNavClick("login", null);
            } catch (FileNotFoundException ex) {
                System.out.println("ListViewLayout: file not found");
                Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("ListViewLayout: IOException");
            } finally {
                try {
                    if (mouth != null) {
                        mouth.close();
                    }
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

    void getGamesList() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                File f = new File("games.txt");
                FileInputStream ear = null;
                try {
                    ear = new FileInputStream(f);
                    BufferedReader br = new BufferedReader(new InputStreamReader(ear));

                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.trim().equals("")) {

                            String[] split = line.trim().split(":");
                            String[] splitFileName = split[0].trim().split("-");
                            String date = splitFileName[0].substring(6) + "-" + splitFileName[0].substring(4, 6) + "-" + splitFileName[0].substring(0, 4);

                            System.out.println("lines from games.txt: " + date);
                            gameList.add(new Game(getMoves(split[0]), split[1], split[2], date, true, split[3]));
                        }
                    }

                } catch (FileNotFoundException ex) {
                    System.out.println("GamesListViewLayout: file not found");
                } catch (IOException ex) {
                    System.out.println("GamesListViewLayout: IOException");
                } finally {
                    try {
                        if (ear != null) {
                            ear.close();
                        }
                    } catch (IOException ex) {
                        System.out.println("GamesListViewLayout: IOException");
                    }
                }
            }
        });
    }

    ArrayList<Move> getMoves(String gameTitle) {
        ArrayList<Move> moves = new ArrayList<>();
        File f = new File(gameTitle);
        FileInputStream ear = null;
        try {
            ear = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(ear));

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals("")) {
                    String[] split = line.trim().split(":");
                    moves.add(new Move(split[0], split[1], split[2]));
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("GamesListViewLayout: file not found");
        } catch (IOException ex) {
            System.out.println("GamesListViewLayout: IOException");
        } finally {
            try {
                ear.close();
            } catch (IOException ex) {
                System.out.println("GamesListViewLayout: IOException");
            }
            return moves;
        }

    }
}

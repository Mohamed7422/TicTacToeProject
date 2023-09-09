package tictactoeclientapplication.layouts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import tictactoeclientapplication.utils.OnNavigation;
import tictactoeclientapplication.data.Player;
import tictactoeclientapplication.layouts.listview.PlayerCellFactory;

public class ListViewLayout extends BorderPane {

    protected final ListView listView;
    Button backButton;
    Button logoutButton;

    public ListViewLayout(OnNavigation onNav) {
        //the list online clients from DB of server
        ObservableList<Player> playerList = FXCollections.observableArrayList();
        playerList.add(new Player("ahmed", 50, "online"));
        playerList.add(new Player("aly", 65, "online"));
        playerList.add(new Player("mohammed", 64, "offline"));
        playerList.add(new Player("hany", 48, "in game"));
        playerList.add(new Player("samty", 12, "in game"));
        playerList.add(new Player("ahmed", 50, "online"));
        playerList.add(new Player("aly", 65, "online"));
        playerList.add(new Player("mohammed", 64, "offline"));
        playerList.add(new Player("hany", 48, "in game"));
        playerList.add(new Player("samty", 12, "in game"));
        playerList.add(new Player("ahmed", 50, "online"));
        playerList.add(new Player("aly", 65, "online"));
        playerList.add(new Player("mohammed", 64, "offline"));
        playerList.add(new Player("hany", 48, "in game"));
        playerList.add(new Player("samty", 12, "in game"));
        playerList.add(new Player("ahmed", 50, "online"));
        playerList.add(new Player("aly", 65, "online"));
        playerList.add(new Player("mohammed", 64, "offline"));
        playerList.add(new Player("hany", 48, "in game"));
        playerList.add(new Player("samty", 12, "in game"));

        listView = new ListView<>(playerList);

        listView.setFocusTraversable(false);
        backButton = new Button();
        logoutButton = new Button();

        Image img = new Image("tictactoeclientapplication/res/iconback.png");
        ImageView view = new ImageView(img);
        backButton.setGraphic(view);
        backButton.setOnAction(e -> onNav.onNavClick("home"));
        backButton.getStyleClass().addAll("RoundButton");
        backButton.setPrefSize(30, 30);

        Image img1 = new Image("tictactoeclientapplication/res/iconlogout.png");
        ImageView view1 = new ImageView(img1);
        logoutButton.setGraphic(view1);
        logoutButton.setOnAction(e -> {
            FileOutputStream mouth = null;
            try {
                //start loading
                File file = new File("auth.txt");
                mouth = new FileOutputStream(file);
                String auth = "logedout";
                mouth.write(auth.getBytes());
                onNav.onNavClick("login");
                //dismiss loading
            } catch (FileNotFoundException ex) {
                //dismiss loading
                Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                //dismiss loading
                Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    mouth.close();
                } catch (IOException ex) {
                    Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //onNav.onNavClick("login");
        }
        );
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

        listView.setCellFactory(new PlayerCellFactory(onNav));
        listView.setDisable(false);

        BorderPane.setAlignment(listView, javafx.geometry.Pos.CENTER);
        listView.setPrefHeight(200.0);
        listView.setPrefWidth(200.0);
        setCenter(listView);
        setTop(hBox);

    }
}

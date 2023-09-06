package tictactoeclientapplication.layouts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import tictactoeclientapplication.utils.OnNavigation;
import tictactoeclientapplication.data.Player;
import tictactoeclientapplication.layouts.listview.PlayerCellFactory;

public class ListViewLayout extends BorderPane {

    protected final ListView listView;

    public ListViewLayout(OnNavigation onNav) {
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

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        listView.setCellFactory(new PlayerCellFactory(onNav));
        listView.setDisable(false);

        BorderPane.setAlignment(listView, javafx.geometry.Pos.CENTER);
        listView.setPrefHeight(200.0);
        listView.setPrefWidth(200.0);
        setCenter(listView);

    }
}

package tictactoeserverapplication;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

public class ServerLayout extends BorderPane {

    protected final ToggleButton btnToggle;
    protected boolean toggle;
    protected TicTacToeServer server;

    public ServerLayout() {
        
        
        toggle = false;

        btnToggle = new ToggleButton();

        btnToggle.setOnAction((e) -> {
            if (toggle) {
                toggle = false;
                btnToggle.setText("On");
                server.closeServer();
                server.stop();
            } else {
                toggle = true;
                btnToggle.setText("Off");
                server = new TicTacToeServer();
                server.start();
            }

        });

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(btnToggle, javafx.geometry.Pos.TOP_RIGHT);
        btnToggle.setMnemonicParsing(false);
        btnToggle.setText("On");
        BorderPane.setMargin(btnToggle, new Insets(8.0, 8.0, 0.0, 0.0));
        setTop(btnToggle);

    }
}

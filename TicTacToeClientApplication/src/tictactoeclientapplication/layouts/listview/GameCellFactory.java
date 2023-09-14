package tictactoeclientapplication.layouts.listview;

import tictactoeclientapplication.utils.OnNavigation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import tictactoeclientapplication.data.Game;

public class GameCellFactory implements Callback<ListView<Game>, ListCell<Game>> {

    OnNavigation onNav;

    public GameCellFactory(OnNavigation onNav) {
        this.onNav = onNav;

    }

    @Override
    public ListCell<Game> call(ListView<Game> param) {
        return new ListCell<Game>() {
            @Override
            public void updateItem(Game game, boolean empty) {
                super.updateItem(game, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (game != null) {
                    setText(null);
                    setGraphic(new GameListItemLayout(game, onNav));

                } else {
                    setText(null);
                    setGraphic(null);
                }
            }

            @Override
            public void updateSelected(boolean selected) {
            }

        };
    }

}


package tictactoeclientapplication;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.util.Callback;

/**
 *
 * @author muham
 */
public class PlayerCellFactory implements Callback<ListView<Player>, ListCell<Player>> {
    
    OnNavigation onNav;
    PlayerCellFactory(OnNavigation onNav){
        this.onNav = onNav;
    
    }

    public ListCell<Player> call(ListView<Player> param) {
        return new ListCell<Player>() {
            public void updateItem(Player player,boolean empty){
                super.updateItem(player, empty);
                
                if(empty){
                setText(null);
                setGraphic(null);
                }else if(player != null){
                setText(null);
                setGraphic(new ListItemLayout(player,onNav));
                
                }else{
                setText(null);
                setGraphic(null);
                }
            }
            @Override
            public void updateSelected(boolean selected) {
                // Disable the selected effect
                // Comment out or remove this method override if you want the default selected effect
            }
            
            

        };
    }

}

/*class Player {

    String name;
    int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

}*/

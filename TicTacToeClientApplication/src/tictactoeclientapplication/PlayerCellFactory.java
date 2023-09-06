
package tictactoeclientapplication;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PlayerCellFactory implements Callback<ListView<Player>, ListCell<Player>> {
    
    OnNavigation onNav;
    PlayerCellFactory(OnNavigation onNav){
        this.onNav = onNav;
    
    }

    @Override
    public ListCell<Player> call(ListView<Player> param) {
        return new ListCell<Player>() {
            @Override
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
            public void updateSelected(boolean selected) {}
            
            

        };
    }

}
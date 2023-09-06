package tictactoeclientapplication.layouts.listview;

import tictactoeclientapplication.utils.OnNavigation;
import tictactoeclientapplication.data.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public  class ListItemLayout extends HBox {

    protected final Text playerName;
    protected final Text statue;
    protected final Button challangeBtn;

    public ListItemLayout(Player player,OnNavigation onNav) {

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
        challangeBtn.setOnAction(e->{
            onNav.onNavClick("board");
        });

        getChildren().add(playerName);
        getChildren().add(statue);
        getChildren().add(challangeBtn);
        
        
        
        playerName.setText(player.getName());
        if(player.getStatus().equals("online")){
            statue.getStyleClass().add("OnlineText");
        }else if(player.getStatus().equals("offline")){
            statue.getStyleClass().add("OfflineText");
        }else {
            statue.getStyleClass().add("IngameText");
        }
        statue.setText(player.getStatus());
        

    }
}

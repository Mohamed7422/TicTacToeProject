package tictactoeclientapplication;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
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
        //setStyle("-fx-background-radius: ; -fx-background-color: #D36779;");
        setId("item");

        playerName.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        playerName.setStrokeWidth(0.0);
        playerName.setText("Mohamed");
        playerName.setWrappingWidth(150);
        playerName.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        playerName.setFont(new Font("Cooper Black", 24.0));


        statue.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        statue.setStrokeWidth(0.0);
        statue.setText("Text");
        statue.setWrappingWidth(170);
        statue.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        statue.setFont(new Font("Cooper Black", 24.0));
        
        setAlignment(Pos.CENTER);

        challangeBtn.setAlignment(javafx.geometry.Pos.CENTER);
        challangeBtn.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        challangeBtn.setMnemonicParsing(false);
        //challangeBtn.setStyle("-fx-background-radius: 30;");
        challangeBtn.setId("buttonChallenge");
        challangeBtn.setText("CHALLANGE");
        challangeBtn.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        challangeBtn.setTextFill(javafx.scene.paint.Color.valueOf("#3d27b9"));
        challangeBtn.setFont(new Font("Cooper Black", 24.0));
        challangeBtn.setOnAction(e->{
            onNav.onNavClick("board");
        });

        getChildren().add(playerName);
        getChildren().add(statue);
        getChildren().add(challangeBtn);
        
        
        
        playerName.setText(player.getName());
        //score.setText(String.valueOf(player.getScore()));
        statue.setText(player.getStatus());
        

    }
}

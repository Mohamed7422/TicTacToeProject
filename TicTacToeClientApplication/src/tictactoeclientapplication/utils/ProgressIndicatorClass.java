/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclientapplication.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Hanaa Hany
 */
public class ProgressIndicatorClass {

    //double increse = 0;
    static Stage stage;

    public static void show() {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.initStyle(StageStyle.TRANSPARENT);
        //stage.setResizable(false);

        // create a progress indicator
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefWidth(100);
        progressIndicator.setPrefHeight(100);
        progressIndicator.setStyle("-fx-box-border: transparent;");
        // create a tile pane
        TilePane r = new TilePane();

        // action event
        /*
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // set progress to different level of progressindicator
                increse += 5;
                progressIndicator.setProgress(increse);
            }
        };
        */

        //Get value of ProgressIndicator
        //Double progress = progressIndicator.getProgress();
        //System.out.println("Current Progress: " + progress);
        r.getChildren().add(progressIndicator);
        r.setAlignment(Pos.CENTER);
        Scene scene = new Scene(r, 100, 100);
        stage.setTitle("Dialog");
        stage.setScene(scene);

        //progressIndicator.setVisible(false);
      
            progressIndicator.setVisible(true);
            stage.show();
       
         
    }
    
    public static void dismiss(){
        if(stage!=null){
            stage.close();
        }
        
    }
}

package tictactoeserverapplication;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class TicTacToeServerApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new ServerLayout();
        
        Scene scene = new Scene(root,700,600);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclientapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author mohan
 */
public class TicTacToeClientApplication extends Application implements OnNavigation {

    Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        //SignUpLayout root = new SignUpLayout();
        LoginLayout root = new LoginLayout(this);
        //ListViewLayout root = new ListViewLayout();

        Scene scene = new Scene(root,800,600);

        scene.getStylesheets().add(getClass().getResource("Style.css").toString());

        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onNavClick(String des) {

        if (des == "sign up") {
            Scene scene = new Scene(new SignUpLayout(this),800,600);
            scene.getStylesheets().add(getClass().getResource("Style.css").toString());
            this.stage.setScene(scene);
        } else if (des == "login") {
            Scene scene = new Scene(new LoginLayout(this),800,600);
            scene.getStylesheets().add(getClass().getResource("Style.css").toString());
            this.stage.setScene(scene);
        } else if (des == "home") {
            Scene scene = new Scene(new HomeLayout(this),800,600);
            scene.getStylesheets().add(getClass().getResource("Style.css").toString());
            this.stage.setScene(scene);
        } else if (des == "computer") {
            Scene scene = new Scene(new LevelsLayout(this),800,600);
            scene.getStylesheets().add(getClass().getResource("Style.css").toString());
            this.stage.setScene(scene);
        } else if (des == "local" || des == "board") {
            Scene scene = new Scene(new GameBoardLayout(),800,600);
            scene.getStylesheets().add(getClass().getResource("Style.css").toString());
            this.stage.setScene(scene);
        } else if (des == "online") {
            //System.out.println("move to online screen");
            Scene scene = new Scene(new ListViewLayout(this),800,600);
            scene.getStylesheets().add(getClass().getResource("Style.css").toString());
            this.stage.setScene(scene);
        } else if (des == "easy") {
            Scene scene = new Scene(new GameBoardLayout(),800,600);
            scene.getStylesheets().add(getClass().getResource("Style.css").toString());
            this.stage.setScene(scene);
        } else if (des == "meduem") {
            System.out.println("move to online screen");
        } else if (des == "hard") {
            System.out.println("move to online screen");
        }

    }

}

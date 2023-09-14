package tictactoeclientapplication;

import tictactoeclientapplication.utils.OnNavigation;
import tictactoeclientapplication.layouts.SignUpLayout;
import tictactoeclientapplication.layouts.LevelsLayout;
import tictactoeclientapplication.layouts.HomeLayout;
import tictactoeclientapplication.layouts.ListViewLayout;
import tictactoeclientapplication.layouts.LoginLayout;
import tictactoeclientapplication.layouts.GameBoardLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tictactoeclientapplication.data.Game;
import tictactoeclientapplication.layouts.GamesListViewLayout;
import tictactoeclientapplication.network.ClientSocket;

public class TicTacToeClientApplication extends Application implements OnNavigation {

    Stage stage;
    Scene scene;
    String authed;

    public void init() {
        /*try {
            ClientSocket.getInstance().openConnection();
        } catch (IOException ex) {
            System.out.println("App: connection error");
        }*/
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = null;

        authed = "";
        File f = new File("auth.txt");
        FileInputStream ear = null;
        try {
            ear = new FileInputStream(f);
            byte[] b = new byte[ear.available()];
            ear.read(b);
            authed = new String(b).split(":")[0];
            if (authed.trim().equals("logedin")) {
                root = new HomeLayout(this);
            } else {
                root = new LoginLayout(this);
            }
        } catch (FileNotFoundException ex) {
            root = new LoginLayout(this);
            System.out.println("App: file not found");
        } catch (IOException ex) {
            root = new LoginLayout(this);
            System.out.println("App: IOException");
        } finally {
            try {
                ear.close();
            } catch (IOException ex) {
                System.out.println("App: IOException");
            }
        }
        scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("Style.css").toString());
        this.stage.setScene(scene);
        this.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onNavClick(String des,Game game) {

        switch (des) {
            case "sign up":
                scene.setRoot(new SignUpLayout(this));
                break;
            case "login":
                scene.setRoot(new LoginLayout(this));
                break;
            case "home":
                scene.setRoot(new HomeLayout(this));
                break;
            case "games":
                scene.setRoot(new GamesListViewLayout(this));
                break;
            case "view-game":
                scene.setRoot(new GameBoardLayout(this, "view",game));
                break;
            case "computer":
                scene.setRoot(new LevelsLayout(this));
                break;
            case "local":
                scene.setRoot(new GameBoardLayout(this, "local",""));
                break;
            case "board":
                scene.setRoot(new GameBoardLayout(this, "computer","no-name"));
                break;
            case "online":
                if (!ClientSocket.getUsername().trim().equals("no-name")) {
                    scene.setRoot(new ListViewLayout(this));
                } else {
                    scene.setRoot(new LoginLayout(this));
                }
                break;
            case "easy":
                scene.setRoot(new GameBoardLayout(this, "computer","easy"));
                break;
            case "medium":
                scene.setRoot(new GameBoardLayout(this, "computer","medium"));
                break;
            case "hard":
                scene.setRoot(new GameBoardLayout(this, "computer","hard"));
                break;
            default:
                break;
        }

        this.stage.setScene(scene);

    }

}

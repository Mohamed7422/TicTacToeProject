package tictactoeclientapplication;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TicTacToeClientApplication extends Application implements OnNavigation {

    Stage stage;
    Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        LoginLayout root = new LoginLayout(this);
        scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("Style.css").toString());

        this.stage.setScene(scene);
        this.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onNavClick(String des) {

        if (des.equals("sign up")) {
            scene.setRoot(new SignUpLayout(this));
        } else if (des.equals("login")) {
            scene.setRoot(new LoginLayout(this));
        } else if (des.equals("home")) {
            scene.setRoot(new HomeLayout(this));
        } else if (des.equals("computer")) {
            scene.setRoot(new LevelsLayout(this));
        } else if (des.equals("local") || des.equals("board")) {
            scene.setRoot(new GameBoardLayout(this));
        } else if (des.equals("online")) {
            //token;
            String token = "jkbjjk";///get from local cache
            if (token.equals("")) {
                scene.setRoot(new LoginLayout(this));
            } else {
                scene.setRoot(new ListViewLayout(this));
            }
        } else if (des.equals("easy")) {
            scene.setRoot(new GameBoardLayout(this));
        } else if (des.equals("meduem")) {
            System.out.println("move to online screen");
        } else if (des.equals("hard")) {
            System.out.println("move to online screen");
        }

        this.stage.setScene(scene);

    }

}

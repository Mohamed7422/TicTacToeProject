package tictactoeclientapplication;

import tictactoeclientapplication.utils.OnNavigation;
import tictactoeclientapplication.network.ClientSocket;
import tictactoeclientapplication.layouts.SignUpLayout;
import tictactoeclientapplication.layouts.LevelsLayout;
import tictactoeclientapplication.layouts.HomeLayout;
import tictactoeclientapplication.layouts.ListViewLayout;
import tictactoeclientapplication.layouts.LoginLayout;
import tictactoeclientapplication.layouts.GameBoardLayout;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tictactoeclientapplication.utils.ProgressIndicatorClass;

public class TicTacToeClientApplication extends Application implements OnNavigation {

    Stage stage;
    Scene scene;

    public void init() {
        try {
            ClientSocket.getInstance().openConnection();
        } catch (IOException ex) {
            System.out.println("client: connection error");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        //ProgressIndicatorClass.show();
        this.stage = stage;
        
        Parent root = null;
        
        String authed = "";
        File f = new File("auth.txt");
        FileInputStream ear = null;
        try {
            ear = new FileInputStream(f);
            byte[] b = new byte[ear.available()];
            ear.read(b);
            authed = new String(b).split(":")[0];
            System.out.println(authed);
            if(authed.trim().equals("logedin")){
                System.out.println("i am loged");
                root = new HomeLayout(this);
            }else{
                System.out.println("i am not loged");
                root = new LoginLayout(this);
            }
        } catch (FileNotFoundException ex) {
            root = new LoginLayout(this);
            System.out.println("FileNotFoundException");
            //Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            root = new LoginLayout(this);
            System.out.println("IOException");
            //Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                ear.close();
            } catch (IOException ex) {
                System.out.println("IOException");
                //Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //LoginLayout root = new LoginLayout(this);
        //SignUpLayout root = new SignUpLayout(this);
        //HomeLayout root = new HomeLayout(this);
        //LevelsLayout root = new LevelsLayout(this);
        //ListViewLayout root = new ListViewLayout(this);
        //GameBoardLayout root = new GameBoardLayout(this);
        
        

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
        } else if (des.equals("local")) {
            scene.setRoot(new GameBoardLayout(this,"local"));
        }else if (des.equals("board")) {
            scene.setRoot(new GameBoardLayout(this,"computer"));
        }else if (des.equals("online")) {
            //token;
            String token = "jkbjjk";///get from local cache
            if (token.equals("")) {
                scene.setRoot(new LoginLayout(this));
            } else {
                scene.setRoot(new ListViewLayout(this));
            }
        } else if (des.equals("easy")) {
            scene.setRoot(new GameBoardLayout(this,"computer"));
        } else if (des.equals("meduem")) {
            System.out.println("move to online screen");
        } else if (des.equals("hard")) {
            System.out.println("move to online screen");
        }

        this.stage.setScene(scene);

    }

}

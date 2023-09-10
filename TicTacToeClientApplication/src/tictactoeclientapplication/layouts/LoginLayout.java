package tictactoeclientapplication.layouts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tictactoeclientapplication.network.ClientSocket;
import tictactoeclientapplication.utils.OnNavigation;
import tictactoeclientapplication.utils.ProgressIndicatorClass;

public class LoginLayout extends BorderPane {

    protected final VBox vBox;
    protected final VBox vBox0;
    protected final HBox hBox;
    protected final Text textUsername;
    protected final TextField textFieldUsername;
    protected final HBox hBox0;
    protected final Text textPassword;
    protected final PasswordField textFieldPassword;
    protected final HBox hBox1;
    protected final Text textTitle;
    protected final HBox hBox2;
    protected final HBox hBox3;
    protected final Text textHaveNot;
    protected final Text textSignUp;
    protected final Text textOr;
    protected final Text textGuest;
    protected final Button btnSignIn;
    protected final ProgressIndicatorClass progIndicator;
    

    public LoginLayout(OnNavigation onNav) {
        

        getStyleClass().add("Pane");

        vBox = new VBox();
        vBox0 = new VBox();
        hBox = new HBox();
        hBox0 = new HBox();
        hBox1 = new HBox();
        hBox2 = new HBox();
        hBox3 = new HBox();
        textTitle = new Text();
        textUsername = new Text();
        textPassword = new Text();
        textHaveNot = new Text();
        textSignUp = new Text();
        textFieldUsername = new TextField();
        textFieldPassword = new PasswordField();
        textOr = new Text();
        textGuest = new Text();
        btnSignIn = new Button("Login");
         progIndicator=new ProgressIndicatorClass();

        //Background background1 = new Background(new BackgroundFill(Color.valueOf("#A94064"), new CornerRadii(10), new Insets(10)));
        btnSignIn.setPrefHeight(50.0);
        btnSignIn.setPrefWidth(200.0);
        btnSignIn.getStyleClass().add("PinkButton");
        btnSignIn.setOnAction((e) -> {
            String userName = textFieldUsername.getText().trim();
            String pass = textFieldPassword.getText().trim();
            //progIndicator.showProgressDialog(true);
            if (!ClientSocket.getInstance().isConnected()) {
                try {
                    ClientSocket.getInstance().openConnection();
                } catch (IOException ex) {
                    //dialog to show that there is connection error
                    System.out.println("client: connection error");
                }
            }
            if (ClientSocket.getInstance().isConnected()) {
                ClientSocket.getInstance().say("login:" + userName + ":" + pass, (msg) -> {
                      //progIndicator.showProgressDialog(true);
                    if (msg.trim().equals("login-success")) {
                        FileOutputStream mouth = null;
                        try {
                            File file = new File("auth.txt");
                            mouth = new FileOutputStream(file);
                            String auth = "logedin";
                            mouth.write(auth.getBytes());
                            //progIndicator.showProgressDialog(false);
                            onNav.onNavClick("home");
                             
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            try {
                                mouth.close();
                            } catch (IOException ex) {
                                Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    } else if(msg.trim().equals("login-fail")) {
                        ////dialog to show that the user is unauthenticated
                        System.out.println("client: unauthenticated");
                    }
                });
            }
        });
        
        
        
        
        btnSignIn.setPrefWidth(250);

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);

        BorderPane.setAlignment(vBox, javafx.geometry.Pos.CENTER);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        hBox.setAlignment(javafx.geometry.Pos.CENTER);

        textUsername.getStyleClass().add("PinkText");
        textUsername.setText("Username");
        textUsername.setWrappingWidth(150);

        VBox.setMargin(hBox, new Insets(0.0));
        hBox.setPadding(new Insets(16.0, 0.0, 8.0, 0.0));

        hBox0.setAlignment(javafx.geometry.Pos.CENTER);
        hBox0.setPrefHeight(0.0);
        hBox0.setPrefWidth(600.0);

        textPassword.getStyleClass().add("PinkText");
        textPassword.setText("Password");
        textPassword.setWrappingWidth(150);

        VBox.setMargin(hBox0, new Insets(0.0));
        hBox0.setPadding(new Insets(8.0, 0.0, 8.0, 0.0));

        hBox1.setAlignment(javafx.geometry.Pos.CENTER);
        hBox1.setPrefHeight(0.0);
        hBox1.setPrefWidth(600.0);

        VBox.setMargin(hBox1, new Insets(0.0));
        hBox1.setPadding(new Insets(8.0, 0.0, 16.0, 0.0));
        setCenter(vBox);

        BorderPane.setAlignment(textTitle, javafx.geometry.Pos.CENTER);
        textTitle.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        textTitle.setStrokeWidth(0.0);
        textTitle.setText("Sign In");
        textTitle.getStyleClass().add("PinkTextLarge");
        textTitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        BorderPane.setMargin(textTitle, new Insets(100.0, 0.0, 25.0, 0.0));
        setTop(textTitle);

        BorderPane.setAlignment(vBox0, javafx.geometry.Pos.CENTER);

        hBox2.setAlignment(javafx.geometry.Pos.CENTER);
        hBox2.setSpacing(10.0);
        textHaveNot.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        textHaveNot.getStyleClass().add("BlackText");
        textHaveNot.setText("I don't have an account.");
        textSignUp.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        textSignUp.setStrokeWidth(0.0);
        textSignUp.setText("sign up");
        textSignUp.getStyleClass().add("EditableText");
        hBox2.setPadding(new Insets(25.0, 0.0, 0.0, 0.0));

        hBox3.setAlignment(javafx.geometry.Pos.CENTER);
        hBox3.setSpacing(10.0);
        textOr.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        textOr.getStyleClass().add("BlackText");
        textOr.setText("or");
        textGuest.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        textGuest.setStrokeWidth(0.0);
        textGuest.setText("as a Guest");
        textGuest.getStyleClass().add("EditableText");
        hBox3.setPadding(new Insets(0.0, 0.0, 25.0, 0.0));

        setBottom(vBox0);

        textSignUp.setOnMouseEntered(e -> {
            textSignUp.setStyle("-fx-fill: #FFED00;");
        });
        textSignUp.setOnMouseExited(e -> {
            textSignUp.setStyle("-fx-fill: #D36779;");
        });
        textSignUp.setOnMouseClicked(e -> {
            onNav.onNavClick("sign up");
        });

        textGuest.setOnMouseEntered(e -> {
            textGuest.setStyle("-fx-fill: #FFED00;");
        });
        textGuest.setOnMouseExited(e -> {
            textGuest.setStyle("-fx-fill: #D36779;");
        });
        textGuest.setOnMouseClicked(e -> {
            onNav.onNavClick("home");
        });

        textFieldUsername.setPrefWidth(200);
        textFieldPassword.setPrefWidth(200);
        textFieldUsername.getStyleClass().add("TextField");
        textFieldPassword.getStyleClass().add("TextField");

        hBox.getChildren().add(textUsername);
        hBox.getChildren().add(textFieldUsername);
        vBox.getChildren().add(hBox);
        hBox0.getChildren().add(textPassword);
        hBox0.getChildren().add(textFieldPassword);
        vBox.getChildren().add(hBox0);
        vBox.getChildren().add(hBox1);
        hBox2.getChildren().add(textHaveNot);
        hBox2.getChildren().add(textSignUp);
        hBox3.getChildren().add(textOr);
        hBox3.getChildren().add(textGuest);

        vBox.getChildren().add(btnSignIn);

        vBox0.getChildren().addAll(hBox2, hBox3);

    }
}

package tictactoeclientapplication.layouts;

import java.io.File;
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
import tictactoeclientapplication.utils.Dialog;
import tictactoeclientapplication.utils.DialogClicks;
import tictactoeclientapplication.utils.OnNavigation;

public class LoginLayout extends BorderPane implements DialogClicks{

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
    Dialog d  = new Dialog();

    OnNavigation onNav;

    public LoginLayout(OnNavigation onNav) {
        getStyleClass().add("Pane");
        this.onNav = onNav;
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
        btnSignIn.setPrefHeight(50.0);
        btnSignIn.setPrefWidth(200.0);
        btnSignIn.getStyleClass().add("PinkButton");
        btnSignIn.setOnAction((e) -> {
            String userName = textFieldUsername.getText().trim();
            String pass = textFieldPassword.getText().trim();
            if (userName.isEmpty() || pass.isEmpty()) {
                //create dialog one button
                d.displayOneBtnDialog(this, "Enter All Required Fields", "OK");
            }else{
            if (!ClientSocket.getInstance().isConnected()) {
                try {
                    ClientSocket.getInstance().openConnection();
                    System.out.println("LoginLayout: connected");
                } catch (IOException ex) {
                    System.out.println("LoginLayout: can't connect");
                     d.displayOneBtnDialog(this, "Check Internet Connection", "OK");
                }
            }
            if (ClientSocket.getInstance().isConnected()) {
                ClientSocket.getInstance().say("login:" + userName + ":" + pass, (msg) -> {
                    if (msg.trim().equals("login-success")) {
                        System.out.println("LoginLayout: "+msg);
                        FileOutputStream mouth = null;
                        try {
                            File file = new File("auth.txt");
                            mouth = new FileOutputStream(file);
                            String auth = "logedin:" + userName;
                            mouth.write(auth.getBytes());
                            onNav.onNavClick("home",null);
                        } catch (FileNotFoundException ex) {
                            System.out.println("LoginLayout: file not found");
                            d.displayOneBtnDialog(this, "file not found", "OK");
                            
                        } catch (IOException ex) {
                            System.out.println("LoginLayout: IOException");
                             d.displayOneBtnDialog(this, "Check Internet Connection", "OK");
                        } finally {
                            try {
                                mouth.close();
                            } catch (IOException ex) {
                                System.out.println("LoginLayout: IOException");
                                d.displayOneBtnDialog(this, "Check Internet Connection", "OK");

                            }
                        }
                    } else if (msg.trim().equals("login-fail")) {
                        System.out.println("LoginLayout: unauthenticated");
                    }
                });
            }
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
            onNav.onNavClick("sign up",null);
        });

        textGuest.setOnMouseEntered(e -> {
            textGuest.setStyle("-fx-fill: #FFED00;");
        });
        textGuest.setOnMouseExited(e -> {
            textGuest.setStyle("-fx-fill: #D36779;");
        });
        textGuest.setOnMouseClicked(e -> {
            onNav.onNavClick("home",null);
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

    @Override
    public void onGreenBtnCkick() {
        
    }

    @Override
    public void onRedBtnCkick() {
        onNav.onNavClick("login", null);
    }
}

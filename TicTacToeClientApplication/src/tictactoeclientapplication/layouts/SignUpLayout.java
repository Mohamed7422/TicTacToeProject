package tictactoeclientapplication.layouts;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tictactoeclientapplication.network.ClientSocket;
import tictactoeclientapplication.utils.OnNavigation;

public class SignUpLayout extends BorderPane {

    protected final VBox vBox;
    protected final HBox hBox;
    protected final Text textUsername;
    protected final TextField textFieldUsername;
    protected final HBox hBox0;
    protected final Text textPassword;
    protected final PasswordField textFieldPassword;
    protected final HBox hBox1;
    protected final Text textConfirm;
    protected final PasswordField textFieldConfirm;
    protected final Text textTitle;
    protected final HBox hBox2;
    protected final Text textAlready;
    protected final Text textLogin;
    protected final Button btnSignUp;

    public SignUpLayout(OnNavigation onNav) {

        //Background background = new Background(new BackgroundFill(Color.valueOf("#E4D5D5"), new CornerRadii(10), new Insets(10)));
        //this.setBackground(background);
        getStyleClass().add("Pane");

        vBox = new VBox();
        hBox = new HBox();
        hBox0 = new HBox();
        hBox1 = new HBox();
        hBox2 = new HBox();
        textTitle = new Text();
        textUsername = new Text();
        textPassword = new Text();
        textConfirm = new Text();
        textAlready = new Text();
        textLogin = new Text();
        textFieldUsername = new TextField();
        textFieldPassword = new PasswordField();
        textFieldConfirm = new PasswordField();
        btnSignUp = new Button("Create Account");

        //Background background1 = new Background(new BackgroundFill(Color.valueOf("#A94064"), new CornerRadii(10), new Insets(10)));
        btnSignUp.setPrefHeight(50.0);
        btnSignUp.setPrefWidth(200.0);
        btnSignUp.getStyleClass().add("PinkButton");
        btnSignUp.setOnAction((e) -> {
            String name = textFieldUsername.getText().trim();
            String pass = textFieldPassword.getText().trim();
            String passConfirm = textFieldConfirm.getText().trim();
            if (name.isEmpty() || pass.isEmpty() || passConfirm.isEmpty()) {
                System.out.println("Empty Fields is Required");
                //("Empty Fields is Required");
            } else if (textFieldPassword.getText().length() < 6) {
                   System.out.println("Your password less than 6 character");
                //("Your password less than 5 character");
            } else if (!textFieldPassword.getText().equals(textFieldConfirm.getText())) {
                System.out.println("Please check your password");
                //("Please check your password");
            } else {
                System.out.println("else");
                if (!ClientSocket.getInstance().isConnected()) {
                    try {
                        System.out.println("no connection");
                        ClientSocket.getInstance().openConnection();
                    } catch (IOException ex) {
                        System.out.println("client: connection error");
                    }
                }
                if (ClientSocket.getInstance().isConnected()) {
                    System.out.println("connected");
                    ClientSocket.getInstance().say("signup:"+name+":"+pass, (msg) -> {
                        System.out.println(msg);
                        if (msg.equals("signup-success")) {
                            onNav.onNavClick("home");
                        } else if (msg.equals("signup-fail")) {

                            System.out.println("Sign Up Fail");
                        }

                    });
                }
            }

            
        });

        btnSignUp.setPrefWidth(250);

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);

        BorderPane.setAlignment(vBox, javafx.geometry.Pos.CENTER);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        hBox.setAlignment(javafx.geometry.Pos.CENTER);

        textUsername.getStyleClass().add("PinkText");
        textUsername.setText("Username");
        textUsername.setWrappingWidth(250);

        VBox.setMargin(hBox, new Insets(0.0));
        hBox.setPadding(new Insets(16.0, 0.0, 8.0, 0.0));

        hBox0.setAlignment(javafx.geometry.Pos.CENTER);
        hBox0.setPrefHeight(0.0);
        hBox0.setPrefWidth(600.0);

        textPassword.getStyleClass().add("PinkText");
        textPassword.setText("Password");
        textPassword.setWrappingWidth(250);

        VBox.setMargin(hBox0, new Insets(0.0));
        hBox0.setPadding(new Insets(8.0, 0.0, 8.0, 0.0));

        hBox1.setAlignment(javafx.geometry.Pos.CENTER);
        hBox1.setPrefHeight(0.0);
        hBox1.setPrefWidth(600.0);

        textConfirm.getStyleClass().add("PinkText");
        textConfirm.setText("Confirm Password");
        textConfirm.setWrappingWidth(250);

        VBox.setMargin(hBox1, new Insets(0.0));
        hBox1.setPadding(new Insets(8.0, 0.0, 16.0, 0.0));
        setCenter(vBox);

        BorderPane.setAlignment(textTitle, javafx.geometry.Pos.CENTER);
        textTitle.setText("Sign Up");
        textTitle.getStyleClass().add("PinkTextLarge");
        textTitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        BorderPane.setMargin(textTitle, new Insets(100.0, 0.0, 25.0, 0.0));
        setTop(textTitle);

        BorderPane.setAlignment(hBox2, javafx.geometry.Pos.CENTER);
        hBox2.setAlignment(javafx.geometry.Pos.CENTER);
        hBox2.setSpacing(10.0);

        textAlready.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        textAlready.getStyleClass().add("BlackText");
        textAlready.setText("I already have an account.");

        textLogin.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        textLogin.setStrokeWidth(0.0);
        textLogin.setText("login");
        textLogin.getStyleClass().add("EditableText");
        hBox2.setPadding(new Insets(25.0, 0.0, 25.0, 0.0));
        setBottom(hBox2);
        textLogin.setOnMouseEntered(e -> {
            textLogin.setStyle("-fx-fill: #FFED00;");
        });
        textLogin.setOnMouseExited(e -> {
            textLogin.setStyle("-fx-fill: #D36779;");
        });
        textLogin.setOnMouseClicked(e -> {
            onNav.onNavClick("login");
        });

        textFieldUsername.setPrefWidth(200);
        textFieldPassword.setPrefWidth(200);
        textFieldConfirm.setPrefWidth(200);
        textFieldUsername.getStyleClass().add("TextField");
        textFieldPassword.getStyleClass().add("TextField");
        textFieldConfirm.getStyleClass().add("TextField");

        hBox.getChildren().add(textUsername);
        hBox.getChildren().add(textFieldUsername);
        vBox.getChildren().add(hBox);
        hBox0.getChildren().add(textPassword);
        hBox0.getChildren().add(textFieldPassword);
        vBox.getChildren().add(hBox0);
        hBox1.getChildren().add(textConfirm);
        hBox1.getChildren().add(textFieldConfirm);
        vBox.getChildren().add(hBox1);
        hBox2.getChildren().add(textAlready);
        hBox2.getChildren().add(textLogin);
        vBox.getChildren().add(btnSignUp);
    }
}

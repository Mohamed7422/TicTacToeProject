package tictactoeclientapplication;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoginLayout extends BorderPane {

    protected final BorderPane borderPane;
    protected final AnchorPane anchorPane;
    protected final HBox hBox;
    protected final Text text;
    protected final FlowPane flowPane;
    protected final Text text0;
    protected final TextField userNameBtn;
    protected final Text text1;
    protected final TextField passwordBtn;
    protected final Button loginBtn;
    protected final ColorAdjust colorAdjust;
    protected final Text text2;
    protected final Text text3;
    protected final Button btnSignUp;
    protected final ColorAdjust colorAdjust0;
    protected final Button btnGuest;
    protected final ColorAdjust colorAdjust1;

    public LoginLayout(OnNavigation onNav) {

        borderPane = new BorderPane();
        anchorPane = new AnchorPane();
        hBox = new HBox();
        text = new Text();
        flowPane = new FlowPane();
        text0 = new Text();
        userNameBtn = new TextField();
        text1 = new Text();
        passwordBtn = new TextField();
        loginBtn = new Button();
        colorAdjust = new ColorAdjust();
        text2 = new Text();
        text3 = new Text();
        btnSignUp = new Button();
        colorAdjust0 = new ColorAdjust();
        btnGuest = new Button();
        colorAdjust1 = new ColorAdjust();

        BorderPane.setAlignment(borderPane, javafx.geometry.Pos.CENTER);
        borderPane.setStyle("-fx-background-color: #E4D5D5;");

        BorderPane.setAlignment(anchorPane, javafx.geometry.Pos.CENTER);
        anchorPane.setId("AnchorPane");
        anchorPane.setMaxHeight(USE_PREF_SIZE);
        anchorPane.setMaxWidth(USE_PREF_SIZE);
        anchorPane.setMinHeight(USE_PREF_SIZE);
        anchorPane.setMinWidth(USE_PREF_SIZE);
        anchorPane.setStyle("-fx-background-color: #E4D5D5;");

        hBox.setLayoutX(214.0);
        hBox.setLayoutY(35.0);

        text.setFill(javafx.scene.paint.Color.valueOf("#a94064"));
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("LOGIN");
        text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        text.setWrappingWidth(112.13671875);
        text.setFont(new Font("Snap ITC", 25.0));

        flowPane.setLayoutX(51.0);
        flowPane.setLayoutY(117.0);
        flowPane.setPrefHeight(117.0);
        flowPane.setPrefWidth(489.0);

        text0.setFill(javafx.scene.paint.Color.valueOf("#a94064"));
        text0.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text0.setStrokeWidth(0.0);
        text0.setText("User Name");
        text0.setWrappingWidth(128.716796875);
        text0.setFont(new Font("Snap ITC", 18.0));

        userNameBtn.setPrefHeight(25.0);
        userNameBtn.setPrefWidth(319.0);
        userNameBtn.setStyle("-fx-background-radius: 30;");
        FlowPane.setMargin(userNameBtn, new Insets(0.0, 0.0, 0.0, 15.0));

        text1.setFill(javafx.scene.paint.Color.valueOf("#a94064"));
        text1.setLayoutX(10.0);
        text1.setLayoutY(29.0);
        text1.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text1.setStrokeWidth(0.0);
        text1.setText("Password");
        text1.setWrappingWidth(122.716796875);
        text1.setFont(new Font("Snap ITC", 18.0));
        FlowPane.setMargin(text1, new Insets(20.0, 0.0, 0.0, 0.0));

        passwordBtn.setLayoutX(133.0);
        passwordBtn.setLayoutY(10.0);
        passwordBtn.setPrefHeight(25.0);
        passwordBtn.setPrefWidth(319.0);
        passwordBtn.setStyle("-fx-background-radius: 30;");
        FlowPane.setMargin(passwordBtn, new Insets(30.0, 0.0, 0.0, 20.0));

        loginBtn.setLayoutX(226.0);
        loginBtn.setLayoutY(234.0);
        loginBtn.setMnemonicParsing(false);
        loginBtn.setPrefHeight(44.0);
        loginBtn.setPrefWidth(148.0);
        loginBtn.setStyle("-fx-background-color: #a94064; -fx-background-radius: 30;");
        loginBtn.getStyleClass().add("-fx-background-color: #20B2AA;-fx-background-radius: 15px;-fx-text-fill: #ffffff;");
        loginBtn.setText("Login");
        loginBtn.setTextFill(javafx.scene.paint.Color.WHITE);
        loginBtn.setFont(new Font("Snap ITC", 20.0));

        loginBtn.setOnAction(e -> {
            onNav.onNavClick("home");
        });

        loginBtn.setEffect(colorAdjust);

        text2.setLayoutX(131.0);
        text2.setLayoutY(338.0);
        text2.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text2.setStrokeWidth(0.0);
        text2.setText("Don't Have An Account?");
        text2.setFont(new Font(21.0));

        text3.setLayoutX(174.0);
        text3.setLayoutY(373.0);
        text3.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text3.setStrokeWidth(0.0);
        text3.setText("or");
        text3.setFont(new Font(21.0));

        btnSignUp.setLayoutX(360.0);
        btnSignUp.setLayoutY(309.0);
        btnSignUp.setMnemonicParsing(false);
        btnSignUp.setPrefHeight(32.0);
        btnSignUp.setPrefWidth(112.0);
        btnSignUp.setStyle("-fx-background-color: #E4D5D5; -fx-background-radius: 30;");
        btnSignUp.getStyleClass().add("-fx-background-color: #20B2AA;-fx-background-radius: 15px;-fx-text-fill: #ffffff;");
        btnSignUp.setText("SignUp");
        btnSignUp.setTextFill(javafx.scene.paint.Color.valueOf("#a94064"));
        btnSignUp.setFont(new Font("Snap ITC", 20.0));

        btnSignUp.setOnAction(e -> {
            onNav.onNavClick("sign up");
        });
        btnSignUp.setEffect(colorAdjust0);

        btnGuest.setLayoutX(194.0);
        btnGuest.setLayoutY(344.0);
        btnGuest.setMnemonicParsing(false);
        btnGuest.setPrefHeight(41.0);
        btnGuest.setPrefWidth(278.0);
        btnGuest.setStyle("-fx-background-color: #E4D5D5; -fx-background-radius: 30;");
        btnGuest.getStyleClass().add("-fx-background-color: #20B2AA;-fx-background-radius: 15px;-fx-text-fill: #ffffff;");
        btnGuest.setText("Continue As A Guest");
        btnGuest.setTextFill(javafx.scene.paint.Color.valueOf("#a94064"));
        btnGuest.setFont(new Font("Snap ITC", 20.0));
        btnGuest.setOnAction(e -> {
            onNav.onNavClick("home");
        });
        btnGuest.setEffect(colorAdjust1);
        borderPane.setCenter(anchorPane);
        setCenter(borderPane);

        hBox.getChildren().add(text);
        anchorPane.getChildren().add(hBox);
        flowPane.getChildren().add(text0);
        flowPane.getChildren().add(userNameBtn);
        flowPane.getChildren().add(text1);
        flowPane.getChildren().add(passwordBtn);
        anchorPane.getChildren().add(flowPane);
        anchorPane.getChildren().add(loginBtn);
        anchorPane.getChildren().add(text2);
        anchorPane.getChildren().add(text3);
        anchorPane.getChildren().add(btnSignUp);
        anchorPane.getChildren().add(btnGuest);

    }
}

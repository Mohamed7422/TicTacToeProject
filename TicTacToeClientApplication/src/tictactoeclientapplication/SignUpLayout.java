package tictactoeclientapplication;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SignUpLayout extends BorderPane {

    protected final VBox vBox;
    protected final HBox hBox;
    protected final Label label;
    protected final TextField textField;
    protected final HBox hBox0;
    protected final Label label0;
    protected final TextField textField0;
    protected final HBox hBox1;
    protected final Label label1;
    protected final TextField textField1;
    protected final Text text;
    protected final HBox hBox2;
    protected final Text text0;
    protected final Text text1;
    protected final Button btn1;

    public SignUpLayout(OnNavigation onNav) {

        Background background = new Background(new BackgroundFill(Color.valueOf("#E4D5D5"), new CornerRadii(10), new Insets(10)));
        this.setBackground(background);

        vBox = new VBox();
        hBox = new HBox();
        label = new Label();
        textField = new TextField();
        hBox0 = new HBox();
        label0 = new Label();
        textField0 = new TextField();
        hBox1 = new HBox();
        label1 = new Label();
        textField1 = new TextField();
        text = new Text();
        hBox2 = new HBox();
        text0 = new Text();
        text1 = new Text();
        btn1 = new Button("Create Account");

        Background background1 = new Background(new BackgroundFill(Color.valueOf("#A94064"), new CornerRadii(10), new Insets(10)));
        btn1.setPrefHeight(50.0);
        btn1.setPrefWidth(200.0);
        btn1.setFont(new Font("Snap ITC", 14.0));
        btn1.setTextFill(Color.valueOf("#FFFFFF"));
        btn1.setBackground(background1);
        btn1.setOnAction((e) -> {
            onNav.onNavClick("home");
        }
        );

        label.setFont(Font.font("Snap ITC"));
        label0.setFont(Font.font("Snap ITC"));
        label1.setFont(Font.font("Snap ITC"));

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(vBox, javafx.geometry.Pos.CENTER);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setPrefHeight(331.0);
        vBox.setPrefWidth(600.0);

        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.setPrefHeight(4.0);
        hBox.setPrefWidth(600.0);

        label.setPrefHeight(17.0);
        label.setPrefWidth(126.0);
        label.setTextFill(Color.valueOf("#A94064"));
        label.setText("Username");

        VBox.setMargin(hBox, new Insets(0.0));
        hBox.setPadding(new Insets(16.0, 0.0, 8.0, 0.0));

        hBox0.setAlignment(javafx.geometry.Pos.CENTER);
        hBox0.setPrefHeight(0.0);
        hBox0.setPrefWidth(600.0);

        label0.setPrefHeight(17.0);
        label0.setPrefWidth(126.0);
        label0.setTextFill(Color.valueOf("#A94064"));
        label0.setText("Password");

        VBox.setMargin(hBox0, new Insets(0.0));
        hBox0.setPadding(new Insets(8.0, 0.0, 8.0, 0.0));

        hBox1.setAlignment(javafx.geometry.Pos.CENTER);
        hBox1.setPrefHeight(0.0);
        hBox1.setPrefWidth(600.0);

        label1.setPrefHeight(17.0);
        label1.setPrefWidth(126.0);
        label1.setTextFill(Color.valueOf("#A94064"));
        label1.setText("Confirm Password");

        VBox.setMargin(hBox1, new Insets(0.0));
        hBox1.setPadding(new Insets(8.0, 0.0, 16.0, 0.0));
        setCenter(vBox);

        BorderPane.setAlignment(text, javafx.geometry.Pos.CENTER);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("Sign Up");
        text.setFill(Color.valueOf("#A94064"));
        text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        text.setWrappingWidth(190.724609375);
        BorderPane.setMargin(text, new Insets(25.0, 0.0, 25.0, 0.0));
        text.setFont(new Font("Snap ITC", 42.0));
        setTop(text);

        BorderPane.setAlignment(hBox2, javafx.geometry.Pos.CENTER);
        hBox2.setAlignment(javafx.geometry.Pos.CENTER);
        hBox2.setPrefHeight(0.0);
        hBox2.setPrefWidth(600.0);
        hBox2.setSpacing(10.0);

        text0.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text0.setStrokeWidth(0.0);
        text0.setFont(new Font("Snap ITC", 12.0));
        text0.setText("I already have an account.");

        text1.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text1.setStrokeWidth(0.0);
        text1.setFill(Color.valueOf("#A94064"));
        text1.setFont(new Font("Snap ITC", 18.0));
        text1.setText("login");
        hBox2.setPadding(new Insets(25.0, 0.0, 25.0, 0.0));
        setBottom(hBox2);
        text1.setOnMouseClicked(e->{
            onNav.onNavClick("login");
        });

        hBox.getChildren().add(label);
        hBox.getChildren().add(textField);
        vBox.getChildren().add(hBox);
        hBox0.getChildren().add(label0);
        hBox0.getChildren().add(textField0);
        vBox.getChildren().add(hBox0);
        hBox1.getChildren().add(label1);
        hBox1.getChildren().add(textField1);
        vBox.getChildren().add(hBox1);
        hBox2.getChildren().add(text0);
        hBox2.getChildren().add(text1);
        vBox.getChildren().add(btn1);

    }
}

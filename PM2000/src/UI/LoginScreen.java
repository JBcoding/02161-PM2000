import javafx.application.Application;
import javafx.event.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.layout.*;

import java.util.Set;

/**
 * Demonstrates a drag-and-drop feature.
 */
public class LoginScreen extends Application {

    protected AnchorPane root;
    protected Stage stage;

    protected Label loginLabel, userLabel;
    protected TextField userIDField;
    protected Button loginButton;

    @Override
    public void start(Stage stage) {
        root = new AnchorPane();
        Scene scene = new Scene(root, 400, 200);
        stage.setMinHeight(200);
        stage.setMaxHeight(200);
        stage.setMinWidth(400);
        stage.setMaxWidth(400);
        this.stage = stage;

        stage.setTitle("Login");

        userIDField = new TextField("");
        userIDField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    login();
                }
            }
        });

        loginButton = new Button("Login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                login();
            }
        });

        loginLabel = new Label("Login");
        loginLabel.setAlignment(Pos.CENTER);

        userLabel = new Label("User:");
        userLabel.setAlignment(Pos.CENTER);

        root.getChildren().addAll(loginLabel, loginButton, userIDField, userLabel);

        root.setTopAnchor(loginLabel, 10.0);
        root.setLeftAnchor(loginLabel, 10.0);
        root.setRightAnchor(loginLabel, 10.0);

        root.setTopAnchor(loginButton, 70.0);
        root.setLeftAnchor(loginButton, 10.0);
        root.setRightAnchor(loginButton, 10.0);

        root.setTopAnchor(userIDField, 37.5);
        root.setLeftAnchor(userIDField, 50.0);
        root.setRightAnchor(userIDField, 10.0);

        root.setTopAnchor(userLabel, 35.0);
        root.setLeftAnchor(userLabel, 10.0);

        stage.setScene(scene);
        stage.show();

        userIDField.requestFocus();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                stage.close();
            }
        });
    }

    private void login() {
        if (userIDField.getText().toLowerCase().equals("admin")) {
            new AdminPanel();
            stage.close();
        } else {
            Set<User> users = PM2000.getUsers();
            User thisUser = null;
            for (User u : users) {
                if (u.getID().toLowerCase().equals(userIDField.getText().toLowerCase())) {
                    thisUser = u;
                    break;
                }
            }
            if (thisUser != null) {
                // log in as this guy(or girl)
                new UserPanel(thisUser);
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "User does not exist", ButtonType.OK);
                alert.showAndWait();

            }
        }
    }

    public static void main(String[] args) {
        PM2000.load();
        Application.launch(args);
    }
}
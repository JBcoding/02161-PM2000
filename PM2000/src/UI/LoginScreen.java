import javafx.application.Application;
import javafx.event.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.WindowEvent;
import javafx.util.*;
import javafx.beans.value.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Demonstrates a drag-and-drop feature.
 */
public class LoginScreen extends Application {

    protected AnchorPane root;
    protected Stage stage;

    protected Label loginLabel;
    protected TextField UserIDField;
    protected Button loginButton;

    @Override
    public void start(Stage stage) {
        root = new AnchorPane();
        Scene scene = new Scene(root, 400, 130);
        stage.setMinHeight(130);
        stage.setMaxHeight(130);
        stage.setMinWidth(400);
        stage.setMaxWidth(400);
        this.stage = stage;

        stage.setTitle("Login");

        UserIDField = new TextField("");

        loginButton = new Button("Login");

        loginLabel = new Label("Login");
        loginLabel.setAlignment(Pos.CENTER);

        root.getChildren().addAll(loginLabel, loginButton, UserIDField);

        root.setTopAnchor(loginLabel, 10.0);
        root.setLeftAnchor(loginLabel, 10.0);
        root.setRightAnchor(loginLabel, 10.0);

        root.setTopAnchor(loginButton, 70.0);
        root.setLeftAnchor(loginButton, 10.0);
        root.setRightAnchor(loginButton, 10.0);

        root.setTopAnchor(UserIDField, 35.0);
        root.setLeftAnchor(UserIDField, 10.0);
        root.setRightAnchor(UserIDField, 10.0);



        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
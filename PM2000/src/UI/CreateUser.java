import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by madsbjoern on 02/05/16.
 */
public class CreateUser extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Button createUser;
    protected TextField nameField, mailField, telField;
    protected Label userID;

    public CreateUser() {
        root = new AnchorPane();
        scene = new Scene(root, 240, 350);
        this.setMaxWidth(240);
        this.setMaxHeight(350);
        this.setMinWidth(240);
        this.setMinHeight(350);
        setTitle("Create User");
        vbox = new VBox();
        root.getChildren().add(vbox);

        nameField = new TextField("Name");
        nameField.setOnKeyReleased(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {updateLabel();}});
        mailField = new TextField("Mail");
        telField = new TextField("Phone Number");
        userID = new Label("");
        vbox.setSpacing(30.0);
        root.setTopAnchor(vbox, 30.0);
        updateLabel();

        createUser = new Button("Create User");
        createUser.setPrefWidth(this.getMaxWidth());
        createUser.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {createUser();}});
        createUser.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {createUser();}}});

        vbox.getChildren().addAll(nameField, mailField, telField, userID, createUser);

        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
        scene.getStylesheets().add("style.css");
    }

    private void updateLabel() {
        userID.setText("The userID will be \"" + PM2000.generateUserIDFromName(nameField.getText()) + "\"");
    }

    public void createUser() {
        new User(nameField.getText(), mailField.getText(), telField.getText());
        close();
    }
}

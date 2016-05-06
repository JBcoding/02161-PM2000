import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by madsbjoern on 02/05/16.
 */
public class DeleteUser extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Button deleteUser;
    protected ListView<User> listView;

    public DeleteUser() {
        root = new AnchorPane();
        scene = new Scene(root, 400, 500);
        this.setMaxWidth(400);
        this.setMaxHeight(500);
        this.setMinWidth(400);
        this.setMinHeight(500);
        setTitle("Delete User");
        vbox = new VBox();
        root.getChildren().add(vbox);

        listView = new ListView<>();
        listView.getItems().addAll(PM2000.getUsers());
        listView.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {deleteUser();} else if ((ke.getCode() == KeyCode.ESCAPE)) {close();}}});

        deleteUser = new Button("Delete User");
        deleteUser.setPrefWidth(this.getMaxWidth());
        deleteUser.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {deleteUser();}});
        deleteUser.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {deleteUser();}}});

        listView.getStyleClass().add("list-view");
        vbox.getChildren().addAll(listView, deleteUser);

        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
        scene.getStylesheets().add("style.css");
    }

    public void deleteUser() {
        User user = listView.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No user selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + user + " ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            if (user.getProject() != null) {
                if (user.getProject().getProjectLead() == user) {
                    user.getProject().setProjectLead(null);
                }
                user.getProject().getMembers().remove(user);
            }
            for (Activity a : user.getActivities()) {
                a.getMembers().remove(user);
            }
            PM2000.getUsers().remove(user);
            PM2000.save();
            close();
        }
    }
}

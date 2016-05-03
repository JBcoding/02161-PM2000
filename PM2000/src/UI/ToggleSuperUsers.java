import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.stream.Collectors;

/**
 * Created by madsbjoern on 02/05/16.
 */
public class ToggleSuperUsers extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Button deleteUser;
    protected ListView<User> normalList, superList;

    public ToggleSuperUsers() {
        root = new AnchorPane();
        scene = new Scene(root, 400, 500);
        this.setMaxWidth(400);
        this.setMaxHeight(500);
        this.setMinWidth(400);
        this.setMinHeight(500);
        setTitle("Toggle Users");
        vbox = new VBox();
        root.getChildren().add(vbox);

        normalList = new ListView<>();
        normalList.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {makeSuperUser();} else if ((ke.getCode() == KeyCode.ESCAPE)) {close();}}});
        normalList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                superList.getSelectionModel().clearSelection();
            }
        });

        superList = new ListView<>();
        superList.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {makeNormalUser();} else if ((ke.getCode() == KeyCode.ESCAPE)) {close();}}});
        superList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                normalList.getSelectionModel().clearSelection();
            }
        });

        updateLists();

        normalList.setPrefWidth(this.getMaxWidth() / 2);
        superList.setPrefWidth(this.getMaxWidth() / 2);

        deleteUser = new Button("Toggle Users");
        deleteUser.setPrefWidth(this.getMaxWidth());
        deleteUser.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {toggleUser();}});
        deleteUser.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {toggleUser();}}});

        HBox hbox = new HBox();
        hbox.getChildren().addAll(normalList, superList);
        vbox.getChildren().addAll(hbox, deleteUser);

        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
    }

    public void toggleUser() {
        User user = normalList.getSelectionModel().getSelectedItem();
        if (user == null) {
            user = superList.getSelectionModel().getSelectedItem();
        }
        if (user != null && !user.toggleSuperUserStatus()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "User can not toggle", ButtonType.OK);
            alert.showAndWait();
        }
        updateLists();
    }

    public void makeNormalUser() {
        User user = superList.getSelectionModel().getSelectedItem();
        if (user != null && !user.makeNormalUser()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "User can not be made to a normal user", ButtonType.OK);
            alert.showAndWait();
        }
        updateLists();
    }

    public void makeSuperUser() {
        User user = normalList.getSelectionModel().getSelectedItem();
        if (user != null && !user.makeSuperUser()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "User can not be made to a super user", ButtonType.OK);
            alert.showAndWait();
        }
        updateLists();
    }

    public void updateLists() {
        normalList.getItems().clear();
        superList.getItems().clear();
        normalList.getItems().addAll((PM2000.getUsers().stream().filter(p -> !p.isSuperUser())).collect(Collectors.toList()));
        superList.getItems().addAll((PM2000.getUsers().stream().filter(p -> p.isSuperUser())).collect(Collectors.toList()));
    }

}
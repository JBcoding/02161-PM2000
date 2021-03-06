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

import java.util.Date;

/**
 * Created by madsbjoern on 02/05/16.
 */
public class UserSchedule extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Label outOf;
    protected ComboBox<User> userCombobox;
    protected Button ManageActivities;
    protected ListView<String> listOfActivities;

    public UserSchedule(User user) {
        root = new AnchorPane();
        scene = new Scene(root, 400, 500);
        this.setMaxWidth(400);
        this.setMaxHeight(500);
        this.setMinWidth(400);
        this.setMinHeight(500);
        setTitle("User Schedule");
        vbox = new VBox();
        root.getChildren().add(vbox);

        userCombobox = new ComboBox<>();
        userCombobox.getItems().addAll(user.getProject().getMembers());
        userCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                listOfActivities.getItems().clear();
                for (Activity a : newValue.getActivities()) {
                    String cell = a.toString();
                    Date s = a.getStartDate();
                    if (s == null) {
                        cell += " - no start date";
                    } else {
                        cell += " - " + ((s.getDate() < 10) ? "0" + s.getDate() : s.getDate()) + "/" + ((s.getMonth() < 9) ? "0" + (s.getMonth() + 1) : (s.getMonth() + 1)) + "/" + (s.getYear() + 1900);
                    }
                    s = a.getEndDate();
                    if (s == null) {
                        cell += " - no end date";
                    } else {
                        cell += " - " + ((s.getDate() < 10) ? "0" + s.getDate() : s.getDate()) + "/" + ((s.getMonth() < 9) ? "0" + (s.getMonth() + 1) : (s.getMonth() + 1)) + "/" + (s.getYear() + 1900);
                    }
                    listOfActivities.getItems().add(cell);
                }
                outOf.setText("  (" + newValue.getActivities().size() + "/" + (newValue.isSuperUser() ? "20" : "10") + ")");
            }
        });
        userCombobox.setPrefWidth(300);

        outOf = new Label("");
        outOf.setPrefHeight(userCombobox.getPrefHeight());

        listOfActivities = new ListView<>();
        listOfActivities.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });

        ManageActivities = new Button("Manage Activities");
        ManageActivities.setPrefWidth(this.getMaxWidth());
        ManageActivities.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {new ManageActivities(user.getProject()); close();}});
        ManageActivities.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {new ManageActivities(user.getProject()); close();}}});

        HBox hbox = new HBox();
        hbox.getChildren().addAll(userCombobox, outOf);

        vbox.getChildren().addAll(hbox, listOfActivities, ManageActivities);

        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
        scene.getStylesheets().add("style.css");

        userCombobox.getSelectionModel().select(0);
    }
}

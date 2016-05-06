import com.sun.javafx.font.freetype.HBGlyphLayout;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by madsbjoern on 02/05/16.
 */
public class RegisterAssistance extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected DatePicker datePicker;
    protected ComboBox<String> helperOrNot, startTime, endTime;
    protected ComboBox<Activity> activityComboBox;
    protected ComboBox<User> userComboBox;
    protected Button save;
    protected User thisUser;

    public RegisterAssistance(User user) {
        thisUser = user;
        root = new AnchorPane();
        scene = new Scene(root, 300, 450);
        this.setMaxWidth(300);
        this.setMaxHeight(450);
        this.setMinWidth(300);
        this.setMinHeight(450);
        setTitle("Register help");
        vbox = new VBox();
        root.getChildren().add(vbox);

        datePicker = new DatePicker();
        datePicker.setPrefWidth(225);
        datePicker.setShowWeekNumbers(true);
        datePicker.setEditable(false);
        datePicker.setValue(LocalDate.now());

        save = new Button("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {
            save();}});
        save.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {
            save();}}});

        helperOrNot = new ComboBox<>();
        helperOrNot.getItems().addAll("I helped", "I got help");
        helperOrNot.getSelectionModel().select(0);

        userComboBox = new ComboBox<>();
        userComboBox.getItems().addAll(PM2000.getUsers());
        userComboBox.getItems().remove(thisUser);

        startTime = new ComboBox<>();
        endTime = new ComboBox<>();
        for (int i = 0; i < 96; i ++) {
            String mL = "" + ((i * 15) % 60);
            String mH = "" + (i * 15 + 15) % 60;
            String hL = "" + (i / 4);
            String hH = "" + ((i + 1) / 4);
            if (mL.length() == 1) {mL = "0" + mL;}
            if (mH.length() == 1) {mH = "0" + mH;}
            if (hL.length() == 1) {hL = "0" + hL;}
            if (hH.length() == 1) {hH = "0" + hH;}
            String textLow = hL + ":" + mL;
            String textHigh = hH + ":" + mH;
            if (textHigh.equals("24:00")) {
                textHigh = "23:59";
            }
            startTime.getItems().add(textLow);
            endTime.getItems().add(textHigh);
        }
        startTime.getSelectionModel().select(32);
        endTime.getSelectionModel().select(63);

        activityComboBox = new ComboBox<>();
        for (Project p : PM2000.getProjects()) {
            activityComboBox.getItems().addAll(p.getActivities());
        }

        helperOrNot.setPrefWidth(this.getMaxWidth());
        activityComboBox.setPrefWidth(this.getMaxWidth());
        datePicker.setPrefWidth(this.getMaxWidth());
        startTime.setPrefWidth(this.getMaxWidth());
        endTime.setPrefWidth(this.getMaxWidth());
        save.setPrefWidth(this.getMaxWidth());
        userComboBox.setPrefWidth(this.getMaxWidth());
        vbox.getChildren().addAll(helperOrNot, activityComboBox, userComboBox, datePicker, startTime, endTime, save);

        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
    }

    private void save() {
        Activity activity = activityComboBox.getSelectionModel().getSelectedItem();
        if (activity == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No activity selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        User other = userComboBox.getSelectionModel().getSelectedItem();
        if (other == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No user selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Date startDate = Date.from(Instant.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
        Date endDate = Date.from(Instant.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
        endDate.setHours(1);
        Date startT = new Date(0, 0, 0, Integer.parseInt(startTime.getValue().split(":")[0]), Integer.parseInt(startTime.getValue().split(":")[1]), 0);
        Date endT = new Date(0, 0, 0, Integer.parseInt(endTime.getValue().split(":")[0]), Integer.parseInt(endTime.getValue().split(":")[1]), 0);
        try {
            if (helperOrNot.getValue().equals("I helped")) {
                thisUser.addUsedTime(activity, startDate, endDate, startT, endT);
            } else {
                other.addUsedTime(activity, startDate, endDate, startT, endT);
            }
            close();
            PM2000.save();
        } catch (NegativeTimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK);
            alert.showAndWait();
        }
    }
}

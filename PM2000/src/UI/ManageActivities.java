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

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by madsbjoern on 02/05/16.
 */
public class ManageActivities extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Button setActivityStartDateButton, setActivityEndDateButton, deleteActivityButton, toggleUserStatus;
    protected Label activityStartDateLabel, activityEndDateLabel;
    protected ListView<Activity> activityList;
    protected ListView<User> userInActivity, userNotInActivity;
    protected DatePicker startDatePicker, endDatePicker;
    protected Project project;

    public ManageActivities(Project project) {
        this.project = project;
        root = new AnchorPane();
        scene = new Scene(root, 1000, 600);
        this.setMaxWidth(1000);
        this.setMaxHeight(600);
        this.setMinWidth(1000);
        this.setMinHeight(600);
        setTitle("Manage Project");
        vbox = new VBox();
        root.getChildren().add(vbox);

        activityList = new ListView<>();
        activityList.getItems().addAll(project.getActivities());
        activityList.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if ((ke.getCode() == KeyCode.ESCAPE)) {close();}}});
        if (activityList.getItems().size() > 0) {
            activityList.getSelectionModel().clearAndSelect(0);
        }
        activityList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Activity>() {
            @Override
            public void changed(ObservableValue<? extends Activity> observable, Activity oldValue, Activity newValue) {
                selectedActivityChanged(newValue);
            }
        });

        activityList.setPrefWidth(this.getMaxWidth() / 3);
        activityList.setPrefHeight(this.getMaxHeight() - 100);

        activityStartDateLabel = new Label("Start date: ");
        activityEndDateLabel = new Label("End date: ");

        activityStartDateLabel.setPrefWidth(280);
        activityEndDateLabel.setPrefWidth(280);

        updateLabels();
        endDatePicker = new DatePicker();
        endDatePicker.setPrefWidth(225);
        endDatePicker.setShowWeekNumbers(true);
        endDatePicker.setEditable(false);

        startDatePicker = new DatePicker();
        startDatePicker.setPrefWidth(225);
        startDatePicker.setShowWeekNumbers(true);
        startDatePicker.setEditable(false);

        setActivityStartDateButton = new Button("Set start date");
        setActivityStartDateButton.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {setStartDate();}});
        setActivityStartDateButton.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {setStartDate();}}});

        setActivityEndDateButton = new Button("Set end date");
        setActivityEndDateButton.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {setEndDate();}});
        setActivityEndDateButton.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {setEndDate();}}});

        deleteActivityButton = new Button("Delete activity");
        deleteActivityButton.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {
            deleteActivity();}});
        deleteActivityButton.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {
            deleteActivity();}}});

        toggleUserStatus = new Button("Toggle user status");
        toggleUserStatus.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {
            toggleUserActivity();}});
        toggleUserStatus.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {
            toggleUserActivity();}}});

        setActivityStartDateButton.setPrefWidth(150);
        setActivityEndDateButton.setPrefWidth(150);
        toggleUserStatus.setPrefWidth(660);
        deleteActivityButton.setPrefWidth(660);

        userInActivity = new ListView<>();
        userInActivity.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {
            removeUserFromActivity();} else if ((ke.getCode() == KeyCode.ESCAPE)) {close();}}});

        userNotInActivity = new ListView<>();
        userNotInActivity.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {addUserToActivity();} else if ((ke.getCode() == KeyCode.ESCAPE)) {close();}}});

        userInActivity.setPrefWidth(333);
        userNotInActivity.setPrefWidth(333);

        updateLists();

        VBox rightBox = new VBox();

        HBox tempBox2 = new HBox();
        HBox tempBox3 = new HBox();
        HBox tempBox4 = new HBox();
        HBox tempBox5 = new HBox();
        HBox tempBox6 = new HBox();

        tempBox2.getChildren().addAll(activityStartDateLabel, startDatePicker, setActivityStartDateButton);
        tempBox3.getChildren().addAll(activityEndDateLabel, endDatePicker, setActivityEndDateButton);
        tempBox4.getChildren().addAll(userNotInActivity, userInActivity);
        tempBox5.getChildren().add(toggleUserStatus);
        tempBox6.getChildren().add(deleteActivityButton);

        rightBox.getChildren().addAll(tempBox2, tempBox3, tempBox4, tempBox5, tempBox6);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(activityList, rightBox);
        vbox.getChildren().addAll(hbox);

        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
        Activity activity = activityList.getSelectionModel().getSelectedItem();
        selectedActivityChanged(activity);
    }

    private void selectedActivityChanged(Activity newActivity) {
        if (newActivity == null) {
            if (activityList.getItems().size() > 0) {
                activityList.getSelectionModel().select(0);
            }
            return;
        }
        updateLabels();
        updateLists();
        if (newActivity.getStartDate() != null) {
            Date s = newActivity.getStartDate();
            startDatePicker.setValue(LocalDate.parse(((s.getDate() < 10) ? "0" + s.getDate() : s.getDate()) + "/" + ((s.getMonth() < 9) ? "0" + (s.getMonth() + 1) : (s.getMonth() + 1)) + "/" + (s.getYear() + 1900), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            startDatePicker.setValue(LocalDate.parse("01/01/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        if (newActivity.getEndDate() != null) {
            Date s = newActivity.getEndDate();
            endDatePicker.setValue(LocalDate.parse(((s.getDate() < 10) ? "0" + s.getDate() : s.getDate()) + "/" + ((s.getMonth() < 9) ? "0" + (s.getMonth() + 1) : (s.getMonth() + 1)) + "/" + (s.getYear() + 1900), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            endDatePicker.setValue(LocalDate.parse("01/01/2016", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }

    private void updateLabels() {
        Activity currentActivity = activityList.getSelectionModel().getSelectedItem();
        activityStartDateLabel.setText("Start date: " + ((currentActivity == null || currentActivity.getStartDate() == null) ? "no date set yet" : (currentActivity.getStartDate().getDate() + "-" + (currentActivity.getStartDate().getMonth() + 1) + "-" + (currentActivity.getStartDate().getYear() + 1900))));
        activityEndDateLabel.setText("End date: " + ((currentActivity == null || currentActivity.getEndDate() == null) ? "no date set yet" : (currentActivity.getEndDate().getDate() + "-" + (currentActivity.getEndDate().getMonth() + 1) + "-" + (currentActivity.getEndDate().getYear() + 1900))));
    }

    public void deleteActivity() {
        Activity activity = activityList.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + activity + " ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            for (User u : activity.getMembers()) {
                u.getActivities().remove(activity);
            }
            activity.getMembers().clear();
            project.getActivities().remove(activity);
            PM2000.save();
            activityList.getItems().clear();
            activityList.getItems().addAll(project.getActivities());
        }
    }

    public void setStartDate() {
        if (startDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No date selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Date date = Date.from(Instant.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
        Activity activity = activityList.getSelectionModel().getSelectedItem();
        try {
            activity.setStartDate(date);
            updateLabels();
            PM2000.save();
        } catch (NegativeTimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void setEndDate() {
        if (endDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No date selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Date date = Date.from(Instant.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
        Activity activity = activityList.getSelectionModel().getSelectedItem();
        try {
            activity.setEndDate(date);
            updateLabels();
            PM2000.save();
        } catch (NegativeTimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void toggleUserActivity() {
        User user = userInActivity.getSelectionModel().getSelectedItem();
        Activity activity = activityList.getSelectionModel().getSelectedItem();
        if (user == null) {
            user = userNotInActivity.getSelectionModel().getSelectedItem();
        }
        if (user != null) {
            if (user.getActivities().contains(activity)) {
                user.getActivities().remove(activity);
                activity.getMembers().remove(user);
            } else {
                if (user.addActivity(activity)) {
                    activity.addMember(user);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "User have reached the activity limit", ButtonType.OK);
                    alert.showAndWait();
                }
            }
            updateLists();
            PM2000.save();
        } else {
            // no user selected
            Alert alert = new Alert(Alert.AlertType.ERROR, "No user selected", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void removeUserFromActivity() {
        User user = userInActivity.getSelectionModel().getSelectedItem();
        Activity activity = activityList.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No user selected", ButtonType.OK);
            alert.showAndWait();
        } else {
            user.getActivities().remove(activity);
            activity.getMembers().remove(user);
            PM2000.save();
        }
        updateLists();
        updateLabels();
    }

    public void addUserToActivity() {
        User user = userNotInActivity.getSelectionModel().getSelectedItem();
        Activity activity = activityList.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No user selected", ButtonType.OK);
            alert.showAndWait();
        } else {
            if (user.addActivity(activity)) {
                activity.addMember(user);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "User have reached the activity limit", ButtonType.OK);
                alert.showAndWait();
            }
            PM2000.save();
        }
        updateLists();
    }

    public void updateLists() {
        Activity activity = activityList.getSelectionModel().getSelectedItem();
        userInActivity.getItems().clear();
        userNotInActivity.getItems().clear();
        userInActivity.getItems().addAll((PM2000.getUsers().stream().filter(p -> p.getProject() == project && p.getActivities().contains(activity))).collect(Collectors.toList()));
        userNotInActivity.getItems().addAll((PM2000.getUsers().stream().filter(p -> p.getProject() == project && !p.getActivities().contains(activity))).collect(Collectors.toList()));
    }
}


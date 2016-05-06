import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Date;


public class ActivitySummary extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Label ActivityStartDateLabel, ActivityEndDateLabel, ActivityRemainderLabel, ActivityCollectiveTime;
    protected ListView<User> activityUsers;
    protected ComboBox<Activity> activityPicker;
    protected int startToNow;
    protected int nowToEnd;
    protected Project project;
    protected Activity activity;

    public ActivitySummary(Project project) {
        this.project = project;
        root = new AnchorPane();
        scene = new Scene(root, 500, 550);
        this.setMaxWidth(550);
        this.setMaxHeight(450);
        this.setMinWidth(550);
        this.setMinHeight(450);
        setTitle("Activity Info");
        vbox = new VBox();
        root.getChildren().add(vbox);

        VBox leftBox = new VBox();

        activityPicker = new ComboBox<>();
        activityPicker.getItems().addAll(project.getActivities());
        if (activity == null) {
            if (activityPicker.getItems().size() > 0) {
                activityPicker.getSelectionModel().select(0);
            }
        }
        activity = activityPicker.getSelectionModel().getSelectedItem();

        activityPicker.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if ((ke.getCode() == KeyCode.ESCAPE)) {close();}}});
        if (activityPicker.getItems().size() > 0) {
            activityPicker.getSelectionModel().clearAndSelect(0);
        }
        activityPicker.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Activity>() {
            @Override
            public void changed(ObservableValue<? extends Activity> observable, Activity oldValue, Activity newValue) {
                selectedActivityChanged(newValue);
            }
        });


        Date day = new Date();
        startToNow = daysBetween(activity.startDate, day);
        nowToEnd = daysBetween(day, activity.endDate);

        HBox tempBoxStart = new HBox();
        HBox tempBoxEnd = new HBox();
        HBox tempBoxRemainder = new HBox();

        ActivityStartDateLabel = new Label("Start date: ");
        ActivityEndDateLabel = new Label("End date: ");

        ActivityRemainderLabel = new Label("Estimated Remaining Time:");

        tempBoxStart.getChildren().addAll(ActivityStartDateLabel);
        tempBoxEnd.getChildren().addAll(ActivityEndDateLabel);
        tempBoxRemainder.getChildren().addAll(ActivityRemainderLabel);

        ActivityStartDateLabel.setPrefWidth(280);
        ActivityEndDateLabel.setPrefWidth(280);
        ActivityRemainderLabel.setPrefWidth(280);

        leftBox.getChildren().addAll(activityPicker, tempBoxStart, tempBoxEnd, tempBoxRemainder);


        VBox middleBox = new VBox();
        activityUsers = new ListView<>();
        activityUsers.getItems().addAll(activity.getMembers());
        activityUsers.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if ((ke.getCode() == KeyCode.ESCAPE)) {close();}}});

        middleBox.getChildren().addAll(activityUsers);

        ActivityCollectiveTime = new Label("Samlet tid: ");

        HBox hbox = new HBox();
        hbox.getChildren().addAll(leftBox, middleBox);
        vbox.getChildren().addAll(hbox, ActivityCollectiveTime);

        updateLabels();
        updateLists();

        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt) -> {
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
        scene.getStylesheets().add("style.css");

        updateLabels();
        updateLists();
    }
    private void selectedActivityChanged(Activity newActivity) {
        if (newActivity == null) {
            if (activityPicker.getItems().size() > 0) {
                activityPicker.getSelectionModel().select(0);
            }
            return;
        }
        updateLabels();
        updateLists();
        if (newActivity.getStartDate() != null) {
            ActivityStartDateLabel.setText("Start date: " + ((newActivity == null || activity.getStartDate() == null) ? "no date set yet" : (newActivity.getStartDate().getDate() + "-" + (newActivity.getStartDate().getMonth() + 1) + "-" + (newActivity.getStartDate().getYear() + 1900))));
        } else {
            ActivityStartDateLabel.setText("Start date: ");
        }
        if (newActivity.getEndDate() != null) {
            ActivityEndDateLabel.setText("End date: " + ((newActivity == null || newActivity.getEndDate() == null) ? "no date set yet" : (newActivity.getEndDate().getDate() + "-" + (newActivity.getEndDate().getMonth() + 1) + "-" + (activity.getEndDate().getYear() + 1900))));
        } else {
            ActivityEndDateLabel.setText("End date: ");
        }
        if (newActivity.getStartDate() != null || newActivity.getEndDate() != null) {
            ActivityRemainderLabel.setText("Estimated Remaining Time:" + ((activity.getUsedTimeOnActivity()/startToNow)*nowToEnd));
        } else {
            ActivityRemainderLabel.setText("Estimated Remaining Time: Missing Date(s)");
        }
        if (newActivity.getStartDate() != null || newActivity.getEndDate() != null) {
            ActivityCollectiveTime.setText("Samlet tid: " + activity.getUsedTimeOnActivity());
        } else {
            ActivityCollectiveTime.setText("Samlet tid: Missing Date(s)");
        }
        updateLists();
        updateLabels();
    }
    private int daysBetween(Date d1, Date d2){
        if (d1 == null) {
            d1 = new Date();
        }
        if (d2 == null) {
            d2 = new Date();
        }
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
    private void updateLabels() {
        Activity activity = activityPicker.getSelectionModel().getSelectedItem();
        ActivityStartDateLabel.setText("Start date: " + ((activity == null || activity.getStartDate() == null) ? "no date set yet" : (activity.getStartDate().getDate() + "-" + (activity.getStartDate().getMonth() + 1) + "-" + (activity.getStartDate().getYear() + 1900))));
        ActivityEndDateLabel.setText("End date: " + ((activity == null || activity.getEndDate() == null) ? "no date set yet" : (activity.getEndDate().getDate() + "-" + (activity.getEndDate().getMonth() + 1) + "-" + (activity.getEndDate().getYear() + 1900))));
        ActivityRemainderLabel.setText("Estimated Remaining Time:" + ((activity.getUsedTimeOnActivity()/(startToNow + 1))*nowToEnd));
        ActivityCollectiveTime.setText("Samlet tid: " + activity.getUsedTimeOnActivity());
    }
    public void updateLists() {
        Activity activity = activityPicker.getSelectionModel().getSelectedItem();
        activityUsers.getItems().clear();
        activityUsers.getItems().addAll(activity.getMembers());
    }
}
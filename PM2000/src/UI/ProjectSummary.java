import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Date;


public class ProjectSummary extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Label ProjectStartDateLabel, ProjectEndDateLabel, ProjectRemainderLabel,ProjectCollectiveTime;
    protected ListView<Activity> activityList;
    protected ListView<User> projectUsers;

    protected Project project;

    public ProjectSummary(Project project) {
        this.project = project;
        root = new AnchorPane();
        scene = new Scene(root, 1000, 600);
        this.setMaxWidth(1000);
        this.setMaxHeight(600);
        this.setMinWidth(1000);
        this.setMinHeight(600);
        setTitle("Project Info");
        vbox = new VBox();
        root.getChildren().add(vbox);

        VBox leftBox = new VBox();

        HBox tempBoxStart = new HBox();
        HBox tempBoxEnd = new HBox();
        HBox tempBoxRemainder = new HBox();

        ProjectStartDateLabel = new Label("Start date: ");
        ProjectStartDateLabel.setText("Start date: " + ((project == null || project.getStartDate() == null) ? "no date set yet" : (project.getStartDate().getDate() + "-" + (project.getStartDate().getMonth() + 1) + "-" + (project.getStartDate().getYear() + 1900))));

        ProjectEndDateLabel = new Label("End date: ");
        ProjectEndDateLabel.setText("End date: " + ((project == null || project.getEndDate() == null) ? "no date set yet" : (project.getEndDate().getDate() + "-" + (project.getEndDate().getMonth() + 1) + "-" + (project.getEndDate().getYear() + 1900))));

        Date day = new Date();
        int startToNow = daysBetween(project.startDate, day);
        int nowToEnd = daysBetween(day, project.endDate);;
        ProjectRemainderLabel = new Label("Estimated Remaining Time:" + ((project.getUsedTimeOnProject()/startToNow)*nowToEnd));

        tempBoxStart.getChildren().addAll(ProjectStartDateLabel);
        tempBoxEnd.getChildren().addAll(ProjectEndDateLabel);
        tempBoxRemainder.getChildren().addAll(ProjectRemainderLabel);

        ProjectStartDateLabel.setPrefWidth(280);
        ProjectEndDateLabel.setPrefWidth(280);
        ProjectRemainderLabel.setPrefWidth(280);

        leftBox.getChildren().addAll(tempBoxStart, tempBoxEnd, tempBoxRemainder);


        VBox middleBox = new VBox();
        projectUsers = new ListView<>();
        projectUsers.getItems().addAll(project.getMembers());
        String userList = project.getMembers().toString();


        middleBox.getChildren().addAll(projectUsers);

        ProjectCollectiveTime = new Label("Samlet tid: ");
        ProjectCollectiveTime.setText("Samlet tid: " + project.getUsedTimeOnProject());

        VBox rightBox = new VBox();
        activityList = new ListView<>();
        activityList.getItems().addAll(project.getActivities());

        rightBox.getChildren().addAll(activityList);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(leftBox, middleBox, rightBox);
        vbox.getChildren().addAll(hbox,ProjectCollectiveTime);

        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt) -> {
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
    }
    private int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

}
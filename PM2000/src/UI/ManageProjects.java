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
public class ManageProjects extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Button setProjectLeadButton, setProjectStartDateButton, setProjectEndDateButton, deleteProjectButton, toggleUserStatus;
    protected Label projectLeadLabel, projectStartDateLabel, projectEndDateLabel;
    protected ListView<Project> projectList;
    protected ComboBox<User> projectLeadPicker;
    protected ListView<Project> userInProject, userNotInProject;
    protected Project currentProject;

    public ManageProjects() {
        root = new AnchorPane();
        scene = new Scene(root, 1000, 600);
        this.setMaxWidth(1000);
        this.setMaxHeight(600);
        this.setMinWidth(1000);
        this.setMinHeight(600);
        setTitle("Manage Project");
        vbox = new VBox();
        root.getChildren().add(vbox);

        projectList = new ListView<>();
        projectList.getItems().addAll(PM2000.getProjects());
        projectList.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if ((ke.getCode() == KeyCode.ESCAPE)) {close();}}});
        projectList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Project>() {
            @Override
            public void changed(ObservableValue<? extends Project> observable, Project oldValue, Project newValue) {
                updateLabels();
            }
        });

        projectList.setPrefWidth(this.getMaxWidth() / 3);
        projectList.setPrefHeight(this.getMaxHeight() - 100);

        projectLeadLabel = new Label("Project leader: ");
        projectStartDateLabel = new Label("Start date: ");
        projectEndDateLabel = new Label("End date: ");

        projectLeadLabel.setPrefWidth(280);
        projectStartDateLabel.setPrefWidth(280);
        projectEndDateLabel.setPrefWidth(280);

        updateLabels();

        projectLeadPicker = new ComboBox<>();
        projectLeadPicker.getItems().addAll(PM2000.getUsers());

        projectLeadPicker.setPrefWidth(225);

        setProjectLeadButton = new Button("Set project lead");
        setProjectStartDateButton = new Button("Set start date");
        setProjectEndDateButton = new Button("Set end date");
        deleteProjectButton = new Button("Delete project");
        toggleUserStatus = new Button("Toggle user status");

        setProjectLeadButton.setPrefWidth(150);
        setProjectStartDateButton.setPrefWidth(375);
        setProjectEndDateButton.setPrefWidth(375);
        toggleUserStatus.setPrefWidth(660);
        deleteProjectButton.setPrefWidth(660);

        userInProject = new ListView<>();
        userNotInProject = new ListView<>();

        userInProject.setPrefWidth(333);
        userNotInProject.setPrefWidth(333);

        VBox rightBox = new VBox();

        HBox tempBox1 = new HBox();
        HBox tempBox2 = new HBox();
        HBox tempBox3 = new HBox();
        HBox tempBox4 = new HBox();
        HBox tempBox5 = new HBox();
        HBox tempBox6 = new HBox();

        tempBox1.getChildren().addAll(projectLeadLabel, projectLeadPicker, setProjectLeadButton);
        tempBox2.getChildren().addAll(projectStartDateLabel, setProjectStartDateButton);
        tempBox3.getChildren().addAll(projectEndDateLabel, setProjectEndDateButton);
        tempBox4.getChildren().addAll(userInProject, userNotInProject);
        tempBox5.getChildren().add(toggleUserStatus);
        tempBox6.getChildren().add(deleteProjectButton);

        rightBox.getChildren().addAll(tempBox1, tempBox2, tempBox3, tempBox4, tempBox5, tempBox6);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(projectList, rightBox);
        vbox.getChildren().addAll(hbox);

        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
    }

    private void updateLabels() {
        projectLeadLabel.setText("Project leader: " + ((currentProject == null || currentProject.getProjectLead() == null) ? "nobody" : currentProject.getProjectLead()));
        projectStartDateLabel.setText("Start date: " + ((currentProject == null || currentProject.getStartDate() == null) ? "no date set yet" : currentProject.getStartDate()));
        projectEndDateLabel.setText("End date: " + ((currentProject == null || currentProject.getEndDate() == null) ? "no date set yet" : currentProject.getEndDate()));
    }
}

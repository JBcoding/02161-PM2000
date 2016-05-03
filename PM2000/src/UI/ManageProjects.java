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
import java.time.ZoneId;
import java.util.Date;
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
    protected ListView<User> userInProject, userNotInProject;
    protected DatePicker startDatePicker, endDatePicker;

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
        if (projectList.getItems().size() > 0) {
            projectList.getSelectionModel().clearAndSelect(0);
        }
        projectList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Project>() {
            @Override
            public void changed(ObservableValue<? extends Project> observable, Project oldValue, Project newValue) {
                updateLabels();
                updateLists();
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
        endDatePicker = new DatePicker();
        endDatePicker.setPrefWidth(225);
        endDatePicker.setShowWeekNumbers(true);

        startDatePicker = new DatePicker();
        startDatePicker.setPrefWidth(225);
        startDatePicker.setShowWeekNumbers(true);

        projectLeadPicker = new ComboBox<>();
        projectLeadPicker.getItems().addAll(PM2000.getUsers());

        projectLeadPicker.setPrefWidth(225);

        setProjectLeadButton = new Button("Set project lead");
        setProjectLeadButton.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {setSetProjectLead();}});
        setProjectLeadButton.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {setSetProjectLead();}}});

        setProjectStartDateButton = new Button("Set start date");
        setProjectStartDateButton.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {setStartDate();}});
        setProjectStartDateButton.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {setStartDate();}}});

        setProjectEndDateButton = new Button("Set end date");
        setProjectEndDateButton.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {setEndDate();}});
        setProjectEndDateButton.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {setEndDate();}}});

        deleteProjectButton = new Button("Delete project");
        deleteProjectButton.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {deleteProject();}});
        deleteProjectButton.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {deleteProject();}}});

        toggleUserStatus = new Button("Toggle user status");
        toggleUserStatus.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {toggleUserProject();}});
        toggleUserStatus.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {toggleUserProject();}}});

        setProjectLeadButton.setPrefWidth(150);
        setProjectStartDateButton.setPrefWidth(150);
        setProjectEndDateButton.setPrefWidth(150);
        toggleUserStatus.setPrefWidth(660);
        deleteProjectButton.setPrefWidth(660);

        userInProject = new ListView<>();
        userInProject.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {removeUserFromProject();} else if ((ke.getCode() == KeyCode.ESCAPE)) {close();}}});
        userInProject.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                userNotInProject.getSelectionModel().clearSelection();
            }
        });

        userNotInProject = new ListView<>();
        userNotInProject.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {addUserToProject();} else if ((ke.getCode() == KeyCode.ESCAPE)) {close();}}});
        userNotInProject.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                userInProject.getSelectionModel().clearSelection();
            }
        });
        userInProject.setPrefWidth(333);
        userNotInProject.setPrefWidth(333);

        updateLists();

        VBox rightBox = new VBox();

        HBox tempBox1 = new HBox();
        HBox tempBox2 = new HBox();
        HBox tempBox3 = new HBox();
        HBox tempBox4 = new HBox();
        HBox tempBox5 = new HBox();
        HBox tempBox6 = new HBox();

        tempBox1.getChildren().addAll(projectLeadLabel, projectLeadPicker, setProjectLeadButton);
        tempBox2.getChildren().addAll(projectStartDateLabel, startDatePicker, setProjectStartDateButton);
        tempBox3.getChildren().addAll(projectEndDateLabel, endDatePicker, setProjectEndDateButton);
        tempBox4.getChildren().addAll(userNotInProject, userInProject);
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
        Project currentProject = projectList.getSelectionModel().getSelectedItem();
        projectLeadLabel.setText("Project leader: " + ((currentProject == null || currentProject.getProjectLead() == null) ? "nobody" : currentProject.getProjectLead()));
        projectStartDateLabel.setText("Start date: " + ((currentProject == null || currentProject.getStartDate() == null) ? "no date set yet" : (currentProject.getStartDate().getDate() + "-" + (currentProject.getStartDate().getMonth() + 1) + "-" + (currentProject.getStartDate().getYear() + 1900))));
        projectEndDateLabel.setText("End date: " + ((currentProject == null || currentProject.getEndDate() == null) ? "no date set yet" : (currentProject.getEndDate().getDate() + "-" + (currentProject.getEndDate().getMonth() + 1) + "-" + (currentProject.getEndDate().getYear() + 1900))));
    }

    public void deleteProject() {
        Project project = projectList.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + project + " ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            PM2000.getProjects().remove(project);
            projectList.getItems().clear();
            projectList.getItems().addAll(PM2000.getProjects());
        }
    }
    public void setSetProjectLead() {
        User user = projectLeadPicker.getSelectionModel().getSelectedItem();
        Project project = projectList.getSelectionModel().getSelectedItem();
        project.setProjectLead(user);
        updateLabels();
    }
    public void setStartDate() {
        if (startDatePicker.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No date selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Date date = Date.from(Instant.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
        Project project = projectList.getSelectionModel().getSelectedItem();
        if(project.setStartDate(date)){
            updateLabels();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Start date invalid", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void setEndDate() {
        if (endDatePicker.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No date selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Date date = Date.from(Instant.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
        Project project = projectList.getSelectionModel().getSelectedItem();
        if(project.setEndDate(date)){
            updateLabels();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "End date invalid", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void toggleUserProject() {
        User user = userInProject.getSelectionModel().getSelectedItem();
        Project project = projectList.getSelectionModel().getSelectedItem();
        if (user == null) {
            user = userNotInProject.getSelectionModel().getSelectedItem();
        }
        if (user != null) {
            if (user.getProject() == project) {
                user.setProject(null);
            } else {
                user.setProject(project);
            }
            updateLists();
            PM2000.save();
        } else {
            // no user selected
            Alert alert = new Alert(Alert.AlertType.ERROR, "No user selected", ButtonType.OK);
            alert.showAndWait();
        }
   }

    public void removeUserFromProject() {
        User user = userNotInProject.getSelectionModel().getSelectedItem();
        Project project = projectList.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "User can not be made to a normal user", ButtonType.OK);
            alert.showAndWait();
        } else {
            user.setProject(null);
            if (project.getProjectLead() == user) {
                project.setProjectLead(null);
            }
            PM2000.save();
        }
        updateLists();
        updateLabels();
    }

    public void addUserToProject() {
        User user = userNotInProject.getSelectionModel().getSelectedItem();
        Project project = projectList.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "User can not be made to a normal user", ButtonType.OK);
            alert.showAndWait();
        } else {
            user.setProject(project);
            PM2000.save();
        }
        updateLists();
    }

    public void updateLists() {
        Project project = projectList.getSelectionModel().getSelectedItem();
        userInProject.getItems().clear();
        userNotInProject.getItems().clear();
        projectLeadPicker.getItems().clear();
        userInProject.getItems().addAll((PM2000.getUsers().stream().filter(p -> p.getProject() == project)).collect(Collectors.toList()));
        userNotInProject.getItems().addAll((PM2000.getUsers().stream().filter(p -> p.getProject() == null)).collect(Collectors.toList()));
        projectLeadPicker.getItems().addAll((PM2000.getUsers().stream().filter(p -> p.getProject() == project)).collect(Collectors.toList()));
    }
}


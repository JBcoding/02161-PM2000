import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Set;

/**
 * Created by madsbjoern on 02/05/16.
 */
public class CreateProject extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Button createProject;
    protected TextField nameField;
    protected Label nameTakenLabel;

    public CreateProject() {
        root = new AnchorPane();
        scene = new Scene(root, 240, 300);
        this.setMaxWidth(240);
        this.setMaxHeight(300);
        this.setMinWidth(240);
        this.setMinHeight(300);
        setTitle("Create Project");
        vbox = new VBox();
        vbox.setSpacing(20.0);
        root.setTopAnchor(vbox, 50.0);
        root.getChildren().add(vbox);

        nameField = new TextField("Name");
        nameField.setOnKeyReleased(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {updateLabel();}});
        nameField.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {createProject();}}});
        nameTakenLabel = new Label("");
        updateLabel();

        createProject = new Button("Create Project");
        createProject.setPrefWidth(this.getMaxWidth());
        createProject.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {createProject();}});
        createProject.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {createProject();}}});

        vbox.getChildren().addAll(nameField, nameTakenLabel, createProject);

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
        boolean taken = isTaken();
        nameTakenLabel.setText("The project name is " + ((!taken) ? "not " : "") + "taken");
        if (taken) {
            nameTakenLabel.setTextFill(Color.web("#FF0000"));
        } else {
            nameTakenLabel.setTextFill(Color.web("#00AA00"));
        }
    }

    public void createProject() {
        if (isTaken()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Project name taken", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try {
            new Project(nameField.getText());
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        close();
    }

    public boolean isTaken() {
        boolean taken = false;
        Set<Project> projects = PM2000.getProjects();
        for (Project p : projects) {
            if (p.getName().toLowerCase().equals(nameField.getText().toLowerCase())) {
                taken = true;
                break;
            }
        }
        return taken;
    }
}

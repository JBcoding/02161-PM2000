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
public class CreateActivity extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Button createActivity;
    protected TextField nameField;
    protected Label nameTakenLabel;
    protected Project project;

    public CreateActivity(Project project) {
        this.project = project;
        root = new AnchorPane();
        scene = new Scene(root, 240, 300);
        this.setMaxWidth(240);
        this.setMaxHeight(300);
        this.setMinWidth(240);
        this.setMinHeight(300);
        setTitle("Create Activity");
        vbox = new VBox();
        vbox.setSpacing(20.0);
        root.setTopAnchor(vbox, 50.0);
        root.getChildren().add(vbox);

        nameField = new TextField("Name");
        nameField.setOnKeyReleased(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {updateLabel();}});
        nameField.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {
            createActivity();}}});
        nameTakenLabel = new Label("");
        updateLabel();

        createActivity = new Button("Create Activity");
        createActivity.setPrefWidth(this.getMaxWidth());
        createActivity.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {
            createActivity();}});
        createActivity.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {
            createActivity();}}});

        vbox.getChildren().addAll(nameField, nameTakenLabel, createActivity);

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
        nameTakenLabel.setText("The activity name is " + ((!taken) ? "not " : "") + "taken");
        if (taken) {
            nameTakenLabel.setTextFill(Color.web("#FF0000"));
        } else {
            nameTakenLabel.setTextFill(Color.web("#00AA00"));
        }
    }

    public void createActivity() {
        if (isTaken()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Activity name taken", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try {
            project.addActivity(new Activity(nameField.getText(), project));
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Name not valid", ButtonType.OK);
            alert.showAndWait();
        }
        PM2000.save();
        close();
    }

    public boolean isTaken() {
        boolean taken = false;
        Set<Activity> activities = project.getActivities();
        for (Activity a : activities) {
            if (a.getName().toLowerCase().equals(nameField.getText().toLowerCase())) {
                taken = true;
                break;
            }
        }
        return taken;
    }
}

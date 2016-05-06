import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InfoSummary extends Stage {
    protected User thisUser;
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Button projectSummary, activitySummary;
    protected Project project;

    public InfoSummary(Project project) {
        this.project = project;
        this.thisUser = thisUser;
        root = new AnchorPane();
        scene = new Scene(root, 240, 500);
        this.setMaxWidth(240);
        this.setMaxHeight(500);
        this.setMinWidth(240);
        this.setMinHeight(500);
        setTitle("Info Summary");
        vbox = new VBox();
        root.getChildren().add(vbox);

        projectSummary = new Button("Project Time");
        projectSummary.setPrefWidth(this.getMaxWidth());
        projectSummary.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {projectSummary();}});
        projectSummary.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {projectSummary();}}});

        activitySummary = new Button("Activity Time");
        activitySummary.setPrefWidth(this.getMaxWidth());
        activitySummary.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {activitySummary();}});
        activitySummary.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {activitySummary();}}});

        vbox.getChildren().addAll(projectSummary,activitySummary);
        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
    }

    private void projectSummary() {
        new ProjectSummary(project);
    }

    private void activitySummary() {new ActivitySummary(project);
    }
}

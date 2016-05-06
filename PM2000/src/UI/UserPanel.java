import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserPanel extends Stage {
    protected User thisUser;
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Button workCalender, createActivity, manageActivities, registerAssistance, projectSummary, userSchedule;

    public UserPanel(User thisUser) {
        this.thisUser = thisUser;
        root = new AnchorPane();
        scene = new Scene(root, 240, 500);
        this.setMaxWidth(240);
        this.setMaxHeight(500);
        this.setMinWidth(240);
        this.setMinHeight(500);
        setTitle("User Panel");
        vbox = new VBox();
        root.getChildren().add(vbox);

        workCalender = new Button("Manage Work Calender");
        workCalender.setPrefWidth(this.getMaxWidth());
        workCalender.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {workCalender();}});
        workCalender.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {workCalender();}}});

        vbox.getChildren().addAll(workCalender);

        if (thisUser.getProject() != null) {
            registerAssistance = new Button("Register Assistance");
            registerAssistance.setPrefWidth(this.getMaxWidth());
            registerAssistance.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {registerAssistance();}});
            registerAssistance.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {registerAssistance();}}});

            vbox.getChildren().addAll(registerAssistance);
        }
        if (thisUser.getProject() != null && thisUser.getProject().getProjectLead() == thisUser) {
            createActivity = new Button("Create Activity");
            createActivity.setPrefWidth(this.getMaxWidth());
            createActivity.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {createActivity();}});
            createActivity.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {createActivity();}}});

            manageActivities = new Button("Manage Activities");
            manageActivities.setPrefWidth(this.getMaxWidth());
            manageActivities.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {manageActivities();}});
            manageActivities.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {manageActivities();}}});

            userSchedule = new Button("User Schedules");
            userSchedule.setPrefWidth(this.getMaxWidth());
            userSchedule.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {userSchedule();}});
            userSchedule.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {userSchedule();}}});

            projectSummary = new Button("Project Info");
            projectSummary.setPrefWidth(this.getMaxWidth());
            projectSummary.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {projectSummary();}});
            projectSummary.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {projectSummary();}}});

            vbox.getChildren().addAll(createActivity, manageActivities, userSchedule, projectSummary);
        }

        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
    }

    private void projectSummary() {
        new ProjectSummary();
    }

    private void registerAssistance() {
        new RegisterAssistance();
    }

   private void manageActivities() {new ManageActivities(thisUser.getProject());}

    private void userSchedule() {
        new UserSchedule();
    }

    public void workCalender() {
        new WorkCalender();
    }

    public void createActivity() {new CreateActivity(thisUser.getProject());}
}

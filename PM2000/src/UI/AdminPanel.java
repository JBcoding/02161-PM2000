import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by madsbjoern on 02/05/16.
 */
public class AdminPanel extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected Button createUser, deleteUser, toggleSuperUsers, createProject, manageProjects;

    public AdminPanel() {
        root = new AnchorPane();
        scene = new Scene(root, 240, 500);
        this.setMaxWidth(240);
        this.setMaxHeight(500);
        this.setMinWidth(240);
        this.setMinHeight(500);
        setTitle("Admin panel");
        vbox = new VBox();
        root.getChildren().add(vbox);

        createUser = new Button("Create User");
        createUser.setPrefWidth(this.getMaxWidth());
        createUser.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {createUser();}});
        createUser.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {createUser();}}});

        deleteUser = new Button("Delete User");
        deleteUser.setPrefWidth(this.getMaxWidth());
        deleteUser.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {deleteUser();}});
        deleteUser.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {deleteUser();}}});

        toggleSuperUsers = new Button("Toggle Super Users");
        toggleSuperUsers.setPrefWidth(this.getMaxWidth());
        toggleSuperUsers.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {toggleSuperUsers();}});
        toggleSuperUsers.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {toggleSuperUsers();}}});

        createProject = new Button("Create Project");
        createProject.setPrefWidth(this.getMaxWidth());
        createProject.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {createProject();}});
        createProject.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {createProject();}}});

        manageProjects = new Button("Manage Projects");
        manageProjects.setPrefWidth(this.getMaxWidth());
        manageProjects.setOnAction(new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent event) {manageProjects();}});
        manageProjects.setOnKeyPressed(new EventHandler<KeyEvent>() {@Override public void handle(KeyEvent ke) {if (ke.getCode().equals(KeyCode.ENTER)) {manageProjects();}}});

        vbox.getChildren().addAll(createUser, deleteUser, toggleSuperUsers, createProject, manageProjects);

        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
    }

    private void manageProjects() {
        new ManageProjects();
    }

    private void createProject() {
        new CreateProject();
    }

    private void toggleSuperUsers() {
        new ToggleSuperUsers();
    }

    public void createUser() {
        new CreateUser();
    }

    public void deleteUser() {
        new DeleteUser();
    }
}

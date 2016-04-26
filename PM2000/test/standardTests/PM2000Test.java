/**
 * Created by madsbjoern on 04/04/16.
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class PM2000Test extends BeforeAndAfterTest {
    @Test
    public void TestPM2000_AddUser_AddsUser() {
        User user1 = new User("name1", "mail", "tel");
        PM2000.addUser(user1);
        User user2 = new User("name2", "mail", "tel");
        PM2000.addUser(user2);
        User user3 = new User("name3", "mail", "tel");
        PM2000.addUser(user3);
        assertEquals(PM2000.getUsers().size(), 3);
        assertTrue(PM2000.getUsers().contains(user1));
        assertTrue(PM2000.getUsers().contains(user2));
        assertTrue(PM2000.getUsers().contains(user3));
    }

    @Test
    public void TestPM2000_AddProject_AddsProject() {
        Project project1 = new Project("name1");
        PM2000.addProject(project1);
        Project project2 = new Project("name2");
        PM2000.addProject(project2);
        Project project3 = new Project("name3");
        PM2000.addProject(project3);
        assertEquals(PM2000.getProjects().size(), 3);
        assertTrue(PM2000.getProjects().contains(project1));
        assertTrue(PM2000.getProjects().contains(project2));
        assertTrue(PM2000.getProjects().contains(project3));
    }
}

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by madsbjoern on 08/05/16.
 */
public class CaseAddUserToActivityTest extends BeforeAndAfterTest {

    @Test
    public void TestMainScenario() {
        Project project = new Project("Name");
        Activity activity = new Activity("Name", 96, project);
        User user = new User("Name", "mail@mail.net", "12345678");
        assertTrue(activity.addMember(user));
        assertTrue(activity.getMembers().contains(user));
    }

    @Test
    public void TestAlternativeScenarioA() {
        Project project = new Project("Name");
        Activity activity = new Activity("Name", 96, project);
        User user = new User("Name", "mail@mail.net", "12345678");
        activity.addMember(user);
        assertFalse(activity.addMember(user));
    }

    @Test
    public void TestAlternativeScenarioB() {
        Project project = new Project("Name");
        Activity activity = new Activity("Name", 96, project);
        User user = new User("Name", "mail@mail.net", "12345678");
        for (int i = 0; i < 10; i ++) {
            Activity newActivity = new Activity("Name" + i, 1, project);
            activity.addMember(user);
        }
        assertFalse(activity.addMember(user));
    }

    /*
    @Test
    public void TestAlternativeScenarioC
    We cannot test a the cancel button
     */
}

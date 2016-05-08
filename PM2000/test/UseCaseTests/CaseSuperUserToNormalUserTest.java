import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by madsbjoern on 08/05/16.
 */
public class CaseSuperUserToNormalUserTest extends BeforeAndAfterTest {

    @Test
    public void TestMainScenario() throws NegativeTimeException {
        User user = new User("Name", "mail@mail.net", "12345678");
        user.makeSuperUser();
        assertTrue(user.makeNormalUser());
        assertFalse(user.isSuperUser());
    }

    @Test
    public void TestAlternativeScenarioA() throws NegativeTimeException {
        User user = new User("Name", "mail@mail.net", "12345678");
        assertFalse(user.makeNormalUser());
    }

    // Bonus to check is user have to many activities to become normal user
    @Test
    public void TestAlternativeScenarioB() throws NegativeTimeException {
        Project project = new Project("Name");
        User user = new User("Name", "mail@mail.net", "12345678");
        user.makeSuperUser();
        for (int i = 0; i < 15; i ++) {
            Activity newActivity = new Activity("Name" + i, 1, project);
            user.addActivity(newActivity);
        }
        assertFalse(user.makeNormalUser());
    }
}

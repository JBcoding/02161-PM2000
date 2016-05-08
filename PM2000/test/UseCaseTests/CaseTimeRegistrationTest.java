import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by madsbjoern on 08/05/16.
 */
public class CaseTimeRegistrationTest extends BeforeAndAfterTest {

    @Test
    public void TestMainScenario() throws NegativeTimeException {
        Project project = new Project("Name");
        Activity activity = new Activity("Name", 1, project);
        User user = new User("Name", "mail@mail.net", "12345678");
        user.addUsedTime(activity, new Date(2016, 2, 10), new Date(2016, 2, 12), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 0, 0));
        assertEquals(user.getUsedTimeOnActivity(activity), 96);
    }

    @Test
    public void TestAlternativeScenarioA() throws NegativeTimeException {
        Project project = new Project("Name");
        Activity activity1 = new Activity("Name1", 1, project);
        Activity activity2 = new Activity("Name2", 1, project);
        User user = new User("Name", "mail@mail.net", "12345678");
        user.addUsedTime(activity1, new Date(2016, 2, 10), new Date(2016, 2, 12), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 0, 0));
        user.addUsedTime(activity2, new Date(2016, 2, 10), new Date(2016, 2, 12), new Date(0, 0, 0, 12, 0, 0), new Date(0, 0, 0, 16, 0, 0));
        assertEquals(user.getUsedTimeOnActivity(activity1), 48);
    }

    /*
    @Test
    public void TestAlternativeScenarioB
    Changes was made, so you cannot add both anymore
     */

    /*
    @Test
    public void TestAlternativeScenarioC
    We cannot test a the cancel button
     */
}

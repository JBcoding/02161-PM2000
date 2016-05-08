import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by madsbjoern on 08/05/16.
 */
public class CasePlanningTest extends BeforeAndAfterTest {

    @Test
    public void TestMainScenario() throws NegativeTimeException {
        Project project = new Project("Name");
        Activity activity = new Activity("Holiday", 96, project);
        User user = new User("Name", "mail@mail.net", "12345678");
        // This planning because it is next year :)
        user.addUsedTime(activity, new Date(2017, 2, 10), new Date(2017, 2, 12), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 0, 0));
        assertEquals(user.getUsedTimeOnActivity(activity), 96);
    }

    @Test
    public void TestAlternativeScenarioA() throws NegativeTimeException {
        User user = new User("Name", "mail@mail.net", "12345678");
        try {
            user.addUsedTime(null, new Date(2016, 2, 10), new Date(2016, 2, 12), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 0, 0));
            fail("You can add time with null activity");
        } catch (IllegalArgumentException e) {
            // it passed
        }
    }

    @Test
    public void TestAlternativeScenarioB() throws NegativeTimeException {
        Project project = new Project("Name");
        Activity activity1 = new Activity("Holiday1", 1, project);
        Activity activity2 = new Activity("Holiday2", 1, project);
        User user = new User("Name", "mail@mail.net", "12345678");
        user.addUsedTime(activity1, new Date(2017, 2, 10), new Date(2017, 2, 12), new Date(0, 0, 0, 0, 0, 0), new Date(0, 0, 0, 23, 59, 0));
        // Changes was made so no warning appears
        user.addUsedTime(activity2, new Date(2017, 2, 12), new Date(2017, 2, 14), new Date(0, 0, 0, 0, 0, 0), new Date(0, 0, 0, 23, 59, 0));
        assertEquals(user.getUsedTimeOnActivity(activity1), 192);
    }

    /*
    @Test
    public void TestAlternativeScenarioC
    We cannot test a the cancel button
     */
}

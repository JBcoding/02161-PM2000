import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by madsbjoern on 08/05/16.
 */
public class CaseCreateActivityTest extends BeforeAndAfterTest {

    @Test
    public void TestMainScenario() throws NegativeTimeException {
        Project project = new Project("Name");
        Activity activity = new Activity("Name", 96, project);
        assertTrue(project.getActivities().contains(activity));
    }

    @Test
    public void TestAlternativeScenarioA() throws NegativeTimeException {
        Project project = new Project("Name");
        try {
            new Activity("", 0, project);
            fail("You can add a activity with no name");
        } catch (NullPointerException e) {
            // it passed
        }
    }

    /*
    @Test
    public void TestAlternativeScenarioB
    Changes was made, so now the activity class automatically generates a new id.
     */

    /*
    @Test
    public void TestAlternativeScenarioC
    We cannot test a the cancel button, because a press on that just makes no activity
     */
}

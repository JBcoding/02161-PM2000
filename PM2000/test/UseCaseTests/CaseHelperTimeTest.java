import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by madsbjoern on 08/05/16.
 */
public class CaseHelperTimeTest extends BeforeAndAfterTest {

    @Test
    public void TestMainScenario() throws NegativeTimeException {
        Project project = new Project("Name");
        Activity activity = new Activity("Name", 96, project);
        User user = new User("Name", "mail@mail.net", "12345678");
        user.addUsedTime(activity, new Date(2017, 2, 10), new Date(2017, 2, 10, 1, 0, 0), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 8, 15, 0));
        assertEquals(user.getUsedTimeOnActivity(activity), 1);
        // Changes was made, so no ones acceptance is needed
    }
}

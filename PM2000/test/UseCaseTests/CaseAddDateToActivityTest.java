import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by madsbjoern on 08/05/16.
 */
public class CaseAddDateToActivityTest extends BeforeAndAfterTest {

    @Test
    public void TestMainScenario() throws NegativeTimeException {
        Project project = new Project("Name");
        Activity activity = new Activity("Name", 96, project);
        Date startDate = new Date(2016, 2, 10);
        Date endDate = new Date(2016, 3, 10);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        assertEquals(activity.getStartDate(), startDate);
        assertEquals(activity.getEndDate(), endDate);
    }

    @Test
    public void TestAlternativeScenarioA() throws NegativeTimeException {
        Project project = new Project("Name");
        Activity activity = new Activity("Name", 96, project);
        Date startDate = new Date(2016, 3, 10);
        Date endDate = new Date(2016, 2, 10);
        activity.setStartDate(startDate);
        try {
            activity.setEndDate(endDate);
            fail("End date can come before start date");
        } catch (NegativeTimeException e) {
            assertEquals(e.getMessage(), "End set before start date");
        }
    }
}

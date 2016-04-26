import org.junit.Test;

/**
 * Created by madsbjoern on 04/04/16.
 */

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import static org.junit.Assert.*;

/**
 * Created by madsbjoern on 18/04/16.
 */
public class AddDateToActivityTest extends BeforeAndAfterTest {

    @Test
    public void TestInputA() {
        Activity activity = new Activity("name", new Project("name"));
        try {
            activity.setStartDate(null);
            fail("Can add null to start date");
        } catch (NullPointerException e) {} catch (NegativeTimeException e) {}
    }

    @Test
    public void TestInputB1() {
        Activity activity = null;
        try {
            activity.setStartDate(new Date(2016, 4, 10));
            fail("Can add start date to null");
        } catch (NullPointerException e) {} catch (NegativeTimeException e) {}
    }

    @Test
    public void TestInputB2() {
        Activity activity = new Activity("name", new Project("name"));
        try {
            activity.setStartDate(null);
            fail("Can add null to start date");
        } catch (NullPointerException e) {} catch (NegativeTimeException e) {}
    }

    @Test
    public void TestInputB3() {
        Activity activity = new Activity("name", new Project("name"));
        try {
            activity.setEndDate(null);
            fail("Can add null to end date");
        } catch (NullPointerException e) {} catch (NegativeTimeException e) {}
    }

    @Test
    public void TestInputC() throws NegativeTimeException {
        Activity activity = new Activity("name", new Project("name"));

        Date startDate = new Date(2016, 4, 23);
        Date endDate = new Date(2016, 5, 14);

        activity.setStartDate(startDate);
        activity.setEndDate(endDate);

        assertEquals(activity.getStartDate(), startDate);
        assertEquals(activity.getEndDate(), endDate);
    }

    @Test
    public void TestInputD() throws NegativeTimeException {
        Activity activity = new Activity("name", new Project("name"));
        activity.setStartDate(new Date(2016, 4, 23));

        try {
            activity.setEndDate(new Date(2015, 5, 14));

            fail("End data can be before start date");
        } catch (NegativeTimeException e) {}
    }

    // Test E, can not be recreated in code

}

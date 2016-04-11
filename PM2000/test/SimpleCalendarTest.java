import org.junit.Test;

/**
 * Created by madsbjoern on 04/04/16.
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Date;
import static org.junit.Assert.*;

public class SimpleCalendarTest {
    @Test
    public void TestSimpleCalendar_AddUsedTime_AddsUsedTime() throws NegativeTimeException {
        Activity activity = new Activity("", new Project("", ""));

        SimpleCalendar simpleCalendar = new SimpleCalendar();

        simpleCalendar.addUsedTime(activity, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 30, 0));
        assertEquals(simpleCalendar.getUsedTimeOnActivity(activity), 680); // 680 quaters
    }

    @Test
    public void TestSimpleCalendar_AddUsedTime_OverridesUsedTime() throws NegativeTimeException {
        Activity activity1 = new Activity("", new Project("", ""));
        Activity activity2 = new Activity("", new Project("", ""));

        SimpleCalendar simpleCalendar = new SimpleCalendar();

        simpleCalendar.addUsedTime(activity1, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 30, 0));
        simpleCalendar.addUsedTime(activity2, new Date(2016, 4, 11), new Date(2016, 4, 30), new Date(0, 0, 0, 12, 0, 0), new Date(0, 0, 0, 16, 30, 0));
        assertEquals(simpleCalendar.getUsedTimeOnActivity(activity2), 360); // 360 quaters
    }

    @Test
    public void TestDay_AddUsedTime_ErrorOnNegativeTime() {
        Activity activity = new Activity("", new Project("", ""));

        SimpleCalendar simpleCalendar = new SimpleCalendar();

        try {
            simpleCalendar.addUsedTime(activity, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 12, 0, 0), new Date(0, 0, 0, 7, 30, 0));

            fail("SimpleCalendar allow negative time to be added");
        } catch (NegativeTimeException e) {
            assertEquals(e.getMessage(), "You cannot add negative time");
        }
    }

    @Test
    public void TestDay_AddUsedTime_ErrorOnNegativeDate() {
        Activity activity = new Activity("", new Project("", ""));

        SimpleCalendar simpleCalendar = new SimpleCalendar();

        try {
            simpleCalendar.addUsedTime(activity, new Date(2016, 4, 10), new Date(2016, 4, 1), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 30, 0));

            fail("SimpleCalendar allow negative time to be added");
        } catch (NegativeTimeException e) {
            assertEquals(e.getMessage(), "You cannot add negative dates");
        }
    }
}
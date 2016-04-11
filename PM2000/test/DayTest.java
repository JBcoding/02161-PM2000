import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import static org.junit.Assert.*;

public class DayTest {
    @Test
    public void TestDay_AddUsedTime_AddsUsedTime() throws NegativeTimeException {
        Activity activity = new Activity("", new Project("", ""));

        Day day = new Day();

        day.addUsedTime(activity, new Date(0, 0, 0, 10, 15, 0), new Date(0, 0, 0, 15, 45, 0));

        assertEquals(day.getUsedTimeOnActivity(activity), 22); // 22 quaters
    }

    @Test
    public void TestDay_AddUsedTime_OverridesUsedTime() throws NegativeTimeException {
        Activity activity1 = new Activity("", new Project("", ""));
        Activity activity2 = new Activity("", new Project("", ""));

        Day day = new Day();

        day.addUsedTime(activity1, new Date(0, 0, 0, 10, 15, 0), new Date(0, 0, 0, 15, 45, 0));
        day.addUsedTime(activity2, new Date(0, 0, 0, 13, 15, 0), new Date(0, 0, 0, 17, 0, 0));

        assertEquals(day.getUsedTimeOnActivity(activity2), 15); // 15 quaters
    }

    @Test
    public void TestDay_AddUsedTime_ErrorOnNegativeTime() {
        Activity activity = new Activity("", new Project("", ""));

        Day day = new Day();

        try {
            day.addUsedTime(activity, new Date(0, 0, 0, 15, 15, 0), new Date(0, 0, 0, 13, 45, 0));

            fail("Days allow negative time to be added");
        } catch (NegativeTimeException e) {
            assertEquals(e.getMessage(), "You cannot add negative time");
        }
    }
}


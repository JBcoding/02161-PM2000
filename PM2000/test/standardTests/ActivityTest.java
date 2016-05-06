import org.junit.Test;

/**
 * Created by madsbjoern on 04/04/16.
 */

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import static org.junit.Assert.*;

public class ActivityTest extends BeforeAndAfterTest {

    @Test
    public void TestActivity_Constructor_SetsName() {
        Project project = new Project("Project name");
        Activity activity = new Activity("Activity name", project);

        assertEquals(activity.getName(), "Activity name");
    }

    @Test
    public void TestActivity_Constructor_SetsProject() {
        Project project = new Project("Project name");
        Activity activity = new Activity("Activity name", project);

        assertEquals(activity.getProject(), project);
    }

    @Test
    public void TestActivity_Constructor_NoNullName() {
        Project project = new Project("Project name");

        try {
            Activity activity = new Activity(null, project);

            fail("Activity allows null name");
        } catch (NullPointerException e) {

        }
    }

    @Test
    public void TestActivity_Constructor_NoNullProject() {
        try {
            Activity activity = new Activity("Activity name", null);

            fail("Activity allows null project");
        } catch (NullPointerException e) {

        }
    }

    @Test
    public void TestActivity_AddMember_AddsMember() {
        Project project = new Project("Project name");
        Activity activity = new Activity("Activity name", project);

        User user = new User("name1", "mail1", "123456781");
        activity.addMember(user);

        assertTrue(activity.getMembers().contains(user));
    }

    @Test
    public void TestActivity_AddMember_AddsMembersOnce() {
        Project project = new Project("Project name");
        Activity activity = new Activity("Activity name", project);

        User user = new User("name1", "mail1", "123456781");
        activity.addMember(user);
        activity.addMember(user);

        assertEquals(activity.getMembers().size(), 1);
    }

    @Test
    public void TestActivity_SetStartDate_SetsStartDate() throws NegativeTimeException {
        Project project = new Project("Project name");
        Activity activity = new Activity("Activity name", project);

        Date startDate = new Date(2016, 4, 1, 0, 0, 0);

        activity.setStartDate(startDate);

        assertEquals(activity.getStartDate(), startDate);
    }

    @Test
    public void TestActivity_SetEndDate_SetsEndDate() throws NegativeTimeException {
        Project project = new Project("Project name");
        Activity activity = new Activity("Activity name", project);

        Date endDate = new Date(2016, 4, 1, 0, 0, 0);

        activity.setEndDate(endDate);

        assertEquals(activity.getEndDate(), endDate);
    }

    @Test
    public void TestActivity_SetStartDate_NoStartDateAfterEndDate() throws NegativeTimeException {
        Project project = new Project("Project name");
        Activity activity = new Activity("Activity name", project);

        Date startDate = new Date(2016, 4, 2, 0, 0, 0);
        Date endDate = new Date(2016, 4, 1, 0, 0, 0);

        activity.setEndDate(endDate);
        try {
            activity.setStartDate(startDate);
            fail("Start set after end date");
        } catch (NegativeTimeException e) {}
        assertNotEquals(activity.getStartDate(), startDate);
    }

    @Test
    public void TestActivity_SetEndDate_NoEndDateBeforeStartDate() throws NegativeTimeException {
        Project project = new Project("Project name");
        Activity activity = new Activity("Activity name", project);

        Date startDate = new Date(2016, 4, 2, 0, 0, 0);
        Date endDate = new Date(2016, 4, 1, 0, 0, 0);

        activity.setStartDate(startDate);
        try {
            activity.setEndDate(endDate);
            fail("End date set before start date");
        } catch (NegativeTimeException e) {}
        assertNotEquals(activity.getEndDate(), endDate);
    }

    @Test
    public void TestActivity_GetUsedTimeOnActivity_GetsUsedTime() throws NegativeTimeException {
        Project project = new Project("Project name");
        Activity activity = new Activity("Activity name", project);

        User user1 = new User("name1", "mail1", "123456781");
        User user2 = new User("name2", "mail2", "123456782");

        activity.addMember(user1);
        activity.addMember(user2);

        user1.addUsedTime(activity, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 30, 0));
        user2.addUsedTime(activity, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 9, 0, 0), new Date(0, 0, 0, 16, 15, 0));

        assertEquals(activity.getUsedTimeOnActivity(), 1260); // 1260 quaters
    }

    @Test
    public void TestActivity_ToString() {
        Project project = new Project("Project name");
        Activity activity = new Activity("Activity name", project);

        assertEquals(activity.toString(), "Activity name (" + activity.getActivityID() + ")");
    }
}

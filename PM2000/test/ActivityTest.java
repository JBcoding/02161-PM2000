import org.junit.Test;

/**
 * Created by madsbjoern on 04/04/16.
 */

import org.junit.Test;

import javax.jws.soap.SOAPBinding;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import static org.junit.Assert.*;

public class ActivityTest {

    @Test
    public void TestActivity_Constructor_SetsName() {
        Project project = new Project("Project name", "Project ID");
        Activity activity = new Activity("Activity name", project);

        assertEquals(activity.getName(), "Activity name");
    }

    @Test
    public void TestActivity_Constructor_SetsProject() {
        Project project = new Project("Project name", "Project ID");
        Activity activity = new Activity("Activity name", project);

        assertEquals(activity.getProject(), project);
    }

    @Test
    public void TestActivity_Constructor_SetsActivityID1() {
        Project project = new Project("Project name", "Project ID");
        Activity activity = new Activity("Activity name", project);

        assertEquals(activity.getActivityID(), 1);
    }

    @Test
    public void TestActivity_Constructor_SetsActivityID2() {
        Project project = new Project("Project name", "Project ID");
        for (int i = 0; i < 20; i ++) {
            Activity temp = new Activity("Activity name", project);
        }

        Activity activity = new Activity("Activity name", project);
        assertEquals(activity.getActivityID(), 21);
    }

    @Test
    public void TestActivity_AddMember_AddsMember() {
        Project project = new Project("Project name", "Project ID");
        Activity activity = new Activity("Activity name", project);

        User user = new User();
        activity.addMember(user);

        assertTrue(activity.getMembers().contains(user));
    }

    @Test
    public void TestActivity_AddMember_AddsMembersOnce() {
        Project project = new Project("Project name", "Project ID");
        Activity activity = new Activity("Activity name", project);

        User user = new User();
        activity.addMember(user);
        activity.addMember(user);

        assertEquals(activity.getMembers().size(), 1);
    }

    @Test
    public void TestActivity_SetStartDate_SetsStartDate() {
        Project project = new Project("Project name", "Project ID");
        Activity activity = new Activity("Activity name", project);

        Date startDate = new Date(2016, 4, 1, 0, 0, 0);

        assertTrue(activity.setStartDate(startDate));

        assertEquals(activity.getStartDate(), startDate);
    }

    @Test
    public void TestActivity_SetEndDate_SetsEndDate() {
        Project project = new Project("Project name", "Project ID");
        Activity activity = new Activity("Activity name", project);

        Date endDate = new Date(2016, 4, 1, 0, 0, 0);

        assertTrue(activity.setEndDate(endDate));

        assertEquals(activity.getEndDate(), endDate);
    }

    @Test
    public void TestActivity_SetStartDate_NoStartDateAfterEndDate() {
        Project project = new Project("Project name", "Project ID");
        Activity activity = new Activity("Activity name", project);

        Date startDate = new Date(2016, 4, 2, 0, 0, 0);
        Date endDate = new Date(2016, 4, 1, 0, 0, 0);

        activity.setEndDate(endDate);
        assertFalse(activity.setStartDate(startDate));
        assertNotEquals(activity.getStartDate(), startDate);
    }

    @Test
    public void TestActivity_SetEndDate_NoEndDateBeforeStartDate() {
        Project project = new Project("Project name", "Project ID");
        Activity activity = new Activity("Activity name", project);

        Date startDate = new Date(2016, 4, 2, 0, 0, 0);
        Date endDate = new Date(2016, 4, 1, 0, 0, 0);

        activity.setStartDate(startDate);
        assertFalse(activity.setEndDate(endDate));
        assertNotEquals(activity.getEndDate(), endDate);
    }

    @Test
    public void TestActivity_GetUsedTimeOnActivity_GetsUsedTime() {
        Project project = new Project("Project name", "Project ID");
        Activity activity = new Activity("Activity name", project);

        User user1 = new User();
        User user2 = new User();

        activity.addMember(user1);
        activity.addMember(user2);

        user1.addUsedTime(activity, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 30, 0));
        user2.addUsedTime(activity, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 9, 0, 0), new Date(0, 0, 0, 16, 15, 0));

        assertEquals(activity.getUsedTimeOnActivity(), 1260); // 1260 quaters
    }

}

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by madsbjoern on 04/04/16.
 */
public class ProjectTest extends BeforeAndAfterTest {
    @Test
    public void TestProject_Constructor_SetsName() {
        Project project = new Project("Project name");
        assertEquals(project.getName(), "Project name");
    }

    @Test
    public void TestProject_Constructor_SetsID() {
        Project project = new Project("Project name");
        assertEquals(project.getProjectID(), Integer.toString(Integer.parseInt(PM2000.getNextProjectID()) - 1));
    }

    @Test
    public void TestProject_Constructor_NoNullName() {
        try {
            Project project = new Project(null);

            fail("Project allows null name");
        } catch (NullPointerException e) {

        }
    }

    @Test
    public void TestProject_SetStartDate_SetsStartDate() {
        Project project = new Project("Project name");

        Date startDate = new Date(2016, 4, 1, 0, 0, 0);

        assertTrue(project.setStartDate(startDate));

        assertEquals(project.getStartDate(), startDate);
    }

    @Test
    public void TestProject_SetEndDate_SetsEndDate() {
        Project project = new Project("Project name");

        Date endDate = new Date(2016, 4, 1, 0, 0, 0);

        assertTrue(project.setEndDate(endDate));

        assertEquals(project.getEndDate(), endDate);
    }

    @Test
    public void TestProject_SetStartDate_NoStartDateAfterEndDate() {
        Project project = new Project("Project name");

        Date startDate = new Date(2016, 4, 2, 0, 0, 0);
        Date endDate = new Date(2016, 4, 1, 0, 0, 0);

        project.setEndDate(endDate);
        assertFalse(project.setStartDate(startDate));
        assertNotEquals(project.getStartDate(), startDate);
    }

    @Test
    public void TestProject_SetEndDate_NoEndDateBeforeStartDate() {
        Project project = new Project("Project name");

        Date startDate = new Date(2016, 4, 2, 0, 0, 0);
        Date endDate = new Date(2016, 4, 1, 0, 0, 0);

        project.setStartDate(startDate);
        assertFalse(project.setEndDate(endDate));
        assertNotEquals(project.getEndDate(), endDate);
    }

    @Test
    public void TestProject_SetStartDate_StartDateBeforeEndDate() {
        Project project = new Project("Project name");

        Date startDate = new Date(2016, 4, 1, 0, 0, 0);
        Date endDate = new Date(2016, 4, 2, 0, 0, 0);

        project.setEndDate(endDate);
        assertTrue(project.setStartDate(startDate));
        assertEquals(project.getStartDate(), startDate);
    }

    @Test
    public void TestProject_SetEndDate_EndDateAfterStartDate() {
        Project project = new Project("Project name");

        Date startDate = new Date(2016, 4, 1, 0, 0, 0);
        Date endDate = new Date(2016, 4, 2, 0, 0, 0);

        project.setStartDate(startDate);
        assertTrue(project.setEndDate(endDate));
        assertEquals(project.getEndDate(), endDate);
    }

    @Test
    public void TestProject_AddMember_AddsMember() {
        Project project = new Project("Project name");

        User user = new User("name1", "mail1", "123456781");
        project.addMember(user);

        assertTrue(project.getMembers().contains(user));
    }

    @Test
    public void TestProject_AddMember_AddsMembersOnce() {
        Project project = new Project("Project name");

        User user = new User("name1", "mail1", "123456781");
        project.addMember(user);
        project.addMember(user);

        assertEquals(project.getMembers().size(), 1);
    }

    @Test
    public void TestProject_AddActivity_AddsActivity() {
        Project project = new Project("Project name");

        Activity activity = new Activity("", project);
        project.addActivity(activity);

        assertTrue(project.getActivities().contains(activity));
    }

    @Test
    public void TestProject_AddActivity_AddActivitysOnce() {
        Project project = new Project("Project name");

        Activity activity = new Activity("", project);
        project.addActivity(activity);
        project.addActivity(activity);

        assertEquals(project.getActivities().size(), 1);
    }

    @Test
    public void TestProject_SetProjectLead_SetsProjectLead() {
        Project project = new Project("Project name");

        User user = new User("name1", "mail1", "123456781");
        project.setProjectLead(user);

        assertEquals(project.getProjectLead(), user);
    }

    @Test
    public void TestActivity_GetUsedTimeOnActivity_GetsUsedTime() throws NegativeTimeException {
        Project project = new Project("Project name");
        Activity activity1 = new Activity("Activity name1", project);

        User user1 = new User("name1", "mail1", "123456781");
        User user2 = new User("name2", "mail2", "123456782");

        activity1.addMember(user1);
        activity1.addMember(user2);

        user1.addUsedTime(activity1, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 30, 0));
        user2.addUsedTime(activity1, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 9, 0, 0), new Date(0, 0, 0, 16, 15, 0));


        Activity activity2 = new Activity("Activity name2", project);

        User user3 = new User("name3", "mail3", "123456783");
        User user4 = new User("name4", "mail4", "123456784");

        activity2.addMember(user3);
        activity2.addMember(user4);

        user3.addUsedTime(activity2, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 30, 0));
        user4.addUsedTime(activity2, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 9, 0, 0), new Date(0, 0, 0, 16, 15, 0));

        project.addActivity(activity1);
        project.addActivity(activity2);

        assertEquals(project.getUsedTimeOnProject(), 2520); // 2520 quaters
    }

    @Test
    public void TestStringToString(){
        Project project = new Project("Testname");

        assertEquals(project.toString(), "Testname (201601)");
    }
}

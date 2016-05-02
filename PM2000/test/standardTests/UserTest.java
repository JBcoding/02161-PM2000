import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.results.PrintableResult;

import java.util.Date;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

/**
 * Created by madsbjoern on 04/04/16.
 */
public class UserTest extends BeforeAndAfterTest {
    @Test
    public void TestUser_Constructor_SetsName() {
        User user = new User("Mads Madsen", "Mads@mail.net", "+45 12345678");
        assertEquals(user.getName(), "Mads Madsen");
    }

    @Test
    public void TestUser_Constructor_SetsMail() {
        User user = new User("Mads Madsen", "Mads@mail.net", "+45 12345678");
        assertEquals(user.getMail(), "Mads@mail.net");
    }

    @Test
    public void TestUser_Constructor_SetsTelefonNumber() {
        User user = new User("Mads Madsen", "Mads@mail.net", "+45 12345678");
        assertEquals(user.getTel(), "+45 12345678");
    }

    @Test
    public void TestUser_Constructor_NoNullName() {
        try {
            User user = new User(null, "Mads@mail.net", "+45 12345678");

            fail("User allows null name");
        } catch (NullPointerException e) {

        }
    }

    @Test
    public void TestUser_Constructor_NoNullMail() {
        try {
            User user = new User("Mads Madsen", null, "+45 12345678");

            fail("User allows null mail");
        } catch (NullPointerException e) {

        }
    }

    @Test
    public void TestUser_Constructor_NoNullTel() {
        try {
            User user = new User("Mads Madsen", "Mads@mail.net", null);

            fail("User allows null telefon");
        } catch (NullPointerException e) {

        }
    }

    @Test
    public void TestUser_SetProject_SetsProject() {
        Project project = new Project("name");
        User user = new User("Mads Madsen", "Mads@mail.net", "+45 12345678");

        user.setProject(project);
        assertEquals(user.getProject(), project);
    }

    @Test
    public void TestUser_AddActivity_AddsActivity() {
        Project project = new Project("name");
        User user = new User("Mads Madsen", "Mads@mail.net", "+45 12345678");
        Activity activity = new Activity("", project);

        user.addActivity(activity);
        assertTrue(user.getActivities().contains(activity));
    }

    @Test
    public void TestSimpleCalendar_AddUsedTime_AddsUsedTime() throws NegativeTimeException {
        Activity activity = new Activity("", new Project("name"));

        User user = new User("Mads Madsen", "Mads@mail.net", "+45 12345678");

        user.addUsedTime(activity, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 30, 0));
        assertEquals(user.getUsedTimeOnActivity(activity), 680); // 680 quaters
    }

    @Test
    public void TestSimpleCalendar_AddUsedTime_OverridesUsedTime() throws NegativeTimeException {
        Activity activity1 = new Activity("", new Project("name1"));
        Activity activity2 = new Activity("", new Project("name2"));

        User user = new User("Mads Madsen", "Mads@mail.net", "+45 12345678");

        user.addUsedTime(activity1, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 30, 0));
        user.addUsedTime(activity2, new Date(2016, 4, 11), new Date(2016, 4, 30), new Date(0, 0, 0, 12, 0, 0), new Date(0, 0, 0, 16, 30, 0));
        assertEquals(user.getUsedTimeOnActivity(activity2), 360); // 360 quaters
    }

    @Test
    public void TestDay_AddUsedTime_ErrorOnNegativeTime() {
        Activity activity = new Activity("", new Project("name"));

        User user = new User("Mads Madsen", "Mads@mail.net", "+45 12345678");

        try {
            user.addUsedTime(activity, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 12, 0, 0), new Date(0, 0, 0, 7, 30, 0));

            Assert.fail("SimpleCalendar allow negative time to be added");
        } catch (NegativeTimeException e) {
            assertEquals(e.getMessage(), "You cannot add negative time");
        }
    }

    @Test
    public void TestDay_AddUsedTime_ErrorOnNegativeDate() {
        Activity activity = new Activity("", new Project("name"));

        User user = new User("Mads Madsen", "Mads@mail.net", "+45 12345678");

        try {
            user.addUsedTime(activity, new Date(2016, 4, 10), new Date(2016, 4, 1), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 30, 0));

            Assert.fail("SimpleCalendar allow negative time to be added");
        } catch (NegativeTimeException e) {
            assertEquals(e.getMessage(), "You cannot add negative dates");
        }
    }

    @Test
    public void TestDay_ToggleSuperUserStatus_TogglesSuperUserStatus() {
        User user = new User("Mads Madsen", "Mads@mail.net", "+45 12345678");

        assertFalse(user.isSuperUser());
        assertTrue(user.toggleSuperUserStatus());
        assertTrue(user.isSuperUser());
        assertTrue(user.toggleSuperUserStatus());
        assertFalse(user.isSuperUser());
    }

    @Test
    public void TestDay_ToggleSuperUserStatus_NoSuperUserStatus() {
        User user = new User("Mads Madsen", "Mads@mail.net", "+45 12345678");

        user.toggleSuperUserStatus();

        for (int i = 0; i < 15; i ++) {
            user.addActivity(new Activity("", new Project("name" + i)));
        }

        assertFalse(user.toggleSuperUserStatus());
        assertTrue(user.isSuperUser());
    }

    @Test
    public void TestUserID_Constructor_SetsUserID() {
        new User("Mark Tosse Hansen", "Eksempel@mail", "43345312");
        new User("Mark Tosse Holstrup", "Eksempel@mail", "43345312");
        User user2 = new User("Mikkel Tåstrup Holger, ", "Eksempel@mail", "43345312");
        assertEquals(user2.getID(), "MTHB");
    }
}

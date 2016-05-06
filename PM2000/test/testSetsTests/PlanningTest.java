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
public class PlanningTest extends BeforeAndAfterTest {

    @Test
    public void TestInputA() {
        User user = new User("name", "mail", "12345678");
        Activity activity = new Activity("name", 1, new Project("name"));
        try {
            user.addUsedTime(null, null, null, null, null);
            fail("Time can be added with null activity");
        } catch (NegativeTimeException e) {} catch (IllegalArgumentException e) {}
    }

    @Test
    public void TestInputB1() {
        User user = new User("name", "mail", "12345678");
        Activity activity = new Activity("name", 1, new Project("name"));
        try {
            user.addUsedTime(null, new Date(2016, 4, 23), new Date(2016, 5, 14), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 0, 0));
            fail("Time can be added with null activity");
        } catch (NegativeTimeException e) {} catch (IllegalArgumentException e) {}
    }

    @Test
    public void TestInputB2() {
        User user = new User("name", "mail", "12345678");
        Activity activity = new Activity("name", 1, new Project("name"));
        try {
            user.addUsedTime(activity, null, new Date(2016, 5, 14), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 0, 0));
            fail("Time can be added with null date");
        } catch (NegativeTimeException e) {} catch (IllegalArgumentException e) {}
    }

    @Test
    public void TestInputB3() {
        User user = new User("name", "mail", "12345678");
        Activity activity = new Activity("name", 1, new Project("name"));
        try {
            user.addUsedTime(activity, new Date(2016, 4, 23), null, new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 0, 0));
            fail("Time can be added with null date");
        } catch (NegativeTimeException e) {} catch (IllegalArgumentException e) {}
    }

    @Test
    public void TestInputB4() {
        User user = new User("name", "mail", "12345678");
        Activity activity = new Activity("name", 1, new Project("name"));
        try {
            user.addUsedTime(activity, new Date(2016, 4, 23), new Date(2016, 5, 14), null, new Date(0, 0, 0, 16, 0, 0));
            fail("Time can be added with null date");
        } catch (NegativeTimeException e) {} catch (IllegalArgumentException e) {}
    }

    @Test
    public void TestInputB5() {
        User user = new User("name", "mail", "12345678");
        Activity activity = new Activity("name", 1, new Project("name"));
        try {
            user.addUsedTime(activity, new Date(2016, 4, 23), new Date(2016, 5, 14), new Date(0, 0, 0, 8, 0, 0), null);
            fail("Time can be added with null date");
        } catch (NegativeTimeException e) {} catch (IllegalArgumentException e) {}
    }

    @Test
    public void TestInputC() throws NegativeTimeException {
        User user = new User("name", "mail", "12345678");
        Activity activity = new Activity("name", 1, new Project("name"));
        user.addUsedTime(activity, new Date(2016, 4, 23), new Date(2016, 5, 14), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 0, 0));
    }

    @Test
    public void TestInputD1() {
        User user = new User("name", "mail", "12345678");
        Activity activity = new Activity("name", 1, new Project("name"));
        try {
            user.addUsedTime(null, new Date(2016, 4, 23), new Date(2016, 5, 14), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 0, 0));
            fail("Time can be added with null activity");
        } catch (NegativeTimeException e) {} catch (IllegalArgumentException e) {}
    }

    @Test
    public void TestInputD2() throws NegativeTimeException {
        User user = new User("name", "mail", "12345678");
        Activity activity = new Activity("name", 1, new Project("name"));
        activity.setEndDate(new Date(2016, 1, 1));
        activity.setStartDate(new Date(2015, 1, 1));
        try {
            user.addUsedTime(activity, new Date(2015, 4, 23), new Date(2016, 5, 14), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 0, 0));
            fail("negative time");
        } catch (NegativeTimeException e) {
            assertEquals(e.getMessage(), "Aktivitet Afsluttet Før Slut Dato");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void TestInputD3() throws NegativeTimeException {
        User user = new User("name", "mail", "12345678");
        Activity activity = new Activity("name", 1, new Project("name"));
        activity.setEndDate(new Date(2017, 1, 1));
        activity.setStartDate(new Date(2016, 1, 1));
        try {
            user.addUsedTime(activity, new Date(2015, 4, 23), new Date(2016, 5, 14), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 0, 0));
            fail("negative time");
        } catch (NegativeTimeException e) {
            assertEquals(e.getMessage(), "Aktivitet Ikke Begyndt ved Start Dato");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void TestInputE() {
        User user = new User("name", "mail", "12345678");
        Activity activity = new Activity("name", 1, new Project("name"));
        try {
            user.addUsedTime(activity, new Date(2016, 4, 23), new Date(2015, 5, 14), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 0, 0));
            fail("negative time");
        } catch (NegativeTimeException e) {} catch (IllegalArgumentException e) {}
    }

    @Test
    public void TestInputF() throws NegativeTimeException {
        Activity activity1 = new Activity("a", 1, new Project("name1"));
        Activity activity2 = new Activity("a", 1, new Project("name2"));

        User user = new User("Mads Madsen", "Mads@mail.net", "+45 12345678");

        user.addUsedTime(activity1, new Date(2016, 4, 1), new Date(2016, 4, 20), new Date(0, 0, 0, 8, 0, 0), new Date(0, 0, 0, 16, 30, 0));
        user.addUsedTime(activity2, new Date(2016, 4, 11), new Date(2016, 4, 30), new Date(0, 0, 0, 12, 0, 0), new Date(0, 0, 0, 16, 30, 0));
        assertEquals(user.getUsedTimeOnActivity(activity2), 360); // 360 quaters
    }

    // Test G and H, can not be recreated in code

}

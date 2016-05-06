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
public class AddUserToActivityTest extends BeforeAndAfterTest {

    @Test
    public void TestInputA() {
        Activity activity = new Activity("name", 1, new Project("name"));
        try {
            activity.addMember(null);
            fail("Can add null user");
        } catch (NullPointerException e) {}
    }

    @Test
    public void TestInputB() {
        Activity activity = new Activity("name", 1, new Project("name"));
        try {
            activity.addMember(null);
            fail("Can add non exiting user");
        } catch (NullPointerException e) {}
    }

    @Test
    public void TestInputC() {
        Activity activity = new Activity("name", 1, new Project("name"));
        activity.addMember(new User("name", "mail", "12345678"));
        assertEquals(activity.getMembers().size(), 1);
    }

    @Test
    public void TestInputD() {
        Activity activity = new Activity("name", 1, new Project("name"));
        User user = new User("name", "mail", "12345678");
        activity.addMember(user);
        assertFalse(activity.addMember(user));
        assertEquals(activity.getMembers().size(), 1);
    }

    @Test
    public void TestInputE() {
        User user = new User("name", "mail", "12345678");
        for (int i = 0; i < 10; i ++) {
            Activity activity = new Activity("name" + i, 1, new Project("name" + i));
            assertTrue(activity.addMember(user));
        }
        Activity activity = new Activity("name11", 1, new Project("name11"));
        assertFalse(activity.addMember(user));
        assertEquals(user.getActivities().size(), 10);
    }

    @Test
    public void TestInputF() {
        User user = new User("name", "mail", "12345678");
        user.makeSuperUser();
        for (int i = 0; i < 20; i ++) {
            Activity activity = new Activity("name" + i, 1, new Project("name" + i));
            assertTrue(activity.addMember(user));
        }
        Activity activity = new Activity("name21", 1, new Project("name21"));
        assertFalse(activity.addMember(user));
        assertEquals(user.getActivities().size(), 20);
    }

    // Test G, can not be recreated in code

}

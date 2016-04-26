import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by madsbjoern on 04/04/16.
 */

public class SuperUserToNormalUserTest extends BeforeAndAfterTest {

    @Test
    public void TestInputA() {
        User user = null;
        try {
            user.makeNormalUser();
            fail("Can make null user to normal user");
        } catch (NullPointerException e) {}
    }

    @Test
    public void TestInputB() {
        User user = new User("name", "mail", "12345678");
        user.makeSuperUser();
        assertTrue(user.makeNormalUser());
        assertFalse(user.isSuperUser());
    }

    @Test
    public void TestInputC() {
        User user = new User("name", "mail", "12345678");
        user.makeSuperUser();
        user.makeNormalUser();
        assertFalse(user.makeNormalUser());
        assertFalse(user.isSuperUser());
    }

    // Test D, can not be recreated in code
}

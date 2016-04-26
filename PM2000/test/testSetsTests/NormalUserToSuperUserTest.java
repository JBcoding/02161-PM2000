import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by madsbjoern on 04/04/16.
 */

public class NormalUserToSuperUserTest extends BeforeAndAfterTest {

    @Test
    public void TestInputA() {
        User user = null;
        try {
            user.makeSuperUser();
            fail("Can make null user to super user");
        } catch (NullPointerException e) {}
    }

    @Test
    public void TestInputB() {
        User user = new User("name", "mail", "12345678");
        assertTrue(user.makeSuperUser());
        assertTrue(user.isSuperUser());
    }

    @Test
    public void TestInputC() {
        User user = new User("name", "mail", "12345678");
        user.makeSuperUser();
        assertFalse(user.makeSuperUser());
        assertTrue(user.isSuperUser());
    }

    // Test D, can not be recreated in code
}
